package com.example.courseapp.Utils;

import android.app.Activity;
import android.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.courseapp.Model.Course;
import com.example.courseapp.R;


import java.util.ArrayList;
import java.util.List;

import ru.nikartm.support.ImageBadgeView;

public class CourseDescription {
    Activity activity;
    AlertDialog dialog;
    Spinner goodsDescription;
    Course course;
    Button addToCart;
    TextView courseTitle,courseDescription;
    List<Course> cart,courseItems;
    int selectedIndex;
    ImageBadgeView badgeView;


    public CourseDescription(Activity activity, Course course_Selected, List<Course> cart, ImageBadgeView badgeView) {
        this.activity = activity;
        this.course=course_Selected;
        this.cart=cart;
        this.badgeView=badgeView;

        courseItems=new ArrayList<>();
        selectedIndex=0;
    }
    public void startDialog(){

        AlertDialog.Builder builder=new AlertDialog.Builder(activity);
        LayoutInflater inflater=activity.getLayoutInflater();
        View view=inflater.inflate(R.layout.product_selector,null);
        initViews(view);

        courseTitle.setText(course.getTitle());
        courseDescription.setText(course.getDescription());
        //set texts from goods

        builder.setView(view);
        builder.setCancelable(true);
        dialog=builder.create();
        dialog.show();
        setListeners();

    }

    private void setListeners() {
        addToCart.setOnClickListener(view -> addItemToCart());

    }

    private void addItemToCart() {
        for (int index=0;index<cart.size();index++){
            if (cart.get(index).getCourseID().equals(courseItems.get(selectedIndex).getCourseID())){
                Toast.makeText(activity, "item already in cart", Toast.LENGTH_SHORT).show();
                dismissDialog();
                return;
            }

        }

        cart.add(course);
        badgeView.setBadgeValue(cart.size());
        Toast.makeText(activity, "item added to cart", Toast.LENGTH_SHORT).show();
        dismissDialog();

    }

    private void initViews(View view) {


        addToCart=view.findViewById(R.id.btn_add_course_to_cart);
        courseTitle=view.findViewById(R.id.courseTitle);
        courseDescription=view.findViewById(R.id.courseTitleDescription);

    }

    public void dismissDialog(){
        dialog.dismiss();
    }


}
