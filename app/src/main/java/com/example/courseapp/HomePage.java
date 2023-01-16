package com.example.courseapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.example.courseapp.Adapters.CourseItemsAdapter;
import com.example.courseapp.Adapters.MyCoursesAdapter;
import com.example.courseapp.Helpers.CourseHelper;
import com.example.courseapp.Interfaces.getItemsCallback;
import com.example.courseapp.Model.Course;
import com.example.courseapp.Utils.CourseDescription;
import com.example.courseapp.Utils.LoadingDialog;

import java.util.ArrayList;
import java.util.List;

import ru.nikartm.support.ImageBadgeView;

public class HomePage extends AppCompatActivity {
    RecyclerView recyclerView,myCourses;
    ArrayList<Course> cart;
    ImageBadgeView badgeView;
    LoadingDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);
        init_views();
        displayRecyclerView();
        setListeners();
    }
    private void setListeners() {
        badgeView.setOnClickListener(view -> viewCart());

    }

    private void viewCart() {
        if (cart.size()==0){
            Toast.makeText(this, "Cart is empty", Toast.LENGTH_SHORT).show();
        }
        else {
            Intent intent=new Intent(getApplicationContext(),CartActivity.class);
            intent.putParcelableArrayListExtra("cart",cart);
            startActivity(intent);
        }

    }

    private void displayRecyclerView() {

        CourseHelper.getMyCourses(new getItemsCallback() {
            @Override
            public void onSuccess(Object object) {
                List<Course> courseList=(List<Course>)object;
                //add store goods from DB.
                getCoursesNotAppliedFor(courseList);
                MyCoursesAdapter adapters=new MyCoursesAdapter(courseList,getApplicationContext(), (pos) -> {
                    Intent intent=new Intent(getApplicationContext(),MarkCourse.class);
                    intent.putExtra("course",courseList.get(pos));
                    startActivity(intent);
                });
                myCourses.setAdapter(adapters);
                myCourses.setLayoutManager(new LinearLayoutManager(getApplicationContext(),LinearLayoutManager.HORIZONTAL,false));
            }

            @Override
            public void onError() {

            }
        });

    }

    private void getCoursesNotAppliedFor(List<Course> courseList){
        CourseHelper.getAllCourses(courseList,new getItemsCallback() {
            @Override
            public void onSuccess(Object object) {
                List<Course> courseList=(List<Course>)object;
                //add store goods from DB.

                CourseItemsAdapter adapters=new CourseItemsAdapter(getApplicationContext(), courseList, (pos) -> {
                    CourseDescription layout=new CourseDescription(HomePage.this,courseList.get(pos),cart,badgeView);
                    layout.startDialog();
                });
                recyclerView.setAdapter(adapters);
                recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                dialog.dismissDialog();
            }

            @Override
            public void onError() {
                Toast.makeText(HomePage.this, "Error getting data", Toast.LENGTH_SHORT).show();
            }
        });
    }




    private void init_views() {
        recyclerView=findViewById(R.id.storeRecyclerView);
        cart=new ArrayList<>();
        badgeView=findViewById(R.id.btnShowCart);
        myCourses=findViewById(R.id.categoryRecyclerView);
        badgeView.clearBadge();
        dialog=new LoadingDialog(this);
        dialog.startLoadingAlertDialog();
    }

    @Override
    public void onResume() {
        super.onResume();
        cart=new ArrayList<>();
        badgeView.clearBadge();
    }
}