package com.example.courseapp;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.courseapp.Adapters.CartAdapter;
import com.example.courseapp.Firebase.Constants.FirebaseConstants;
import com.example.courseapp.Helpers.CourseHelper;
import com.example.courseapp.Interfaces.onResult;
import com.example.courseapp.Model.Course;

import java.util.ArrayList;

public class CartActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    ArrayList<Course> cartList;
    TextView goodsCost,service,total;
    Button confirm;

    @Override
    public void onBackPressed() {
        // Create an alert dialog builder
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

// Set the message and title of the dialog
        builder.setMessage("Going back will clear your cart. Are you sure you want to continue?")
                .setTitle("Warning");

// Set the positive and negative buttons of the dialog
        builder.setPositiveButton("Yes", (dialog, id) -> {
            // User confirmed that they want to go back and clear the cart
            // Clear the cart and navigate back to the previous activity
            super.onBackPressed();
            finish();
        });
        builder.setNegativeButton("No", (dialog, id) -> {
            // User cancelled, do nothing
        });

// Create and show the alert dialog
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_cart);
        initViews();
        setAdapter();
        setListeners();
    }
    private void setListeners() {
        confirm.setOnClickListener(view -> registerCourses());
    }

    private void registerCourses() {

        CourseHelper.addCoursesToUser(cartList, new onResult() {
            @Override
            public void onSuccess() {
                Intent intent=new Intent(getApplicationContext(),FinalActivitySender.class);
                startActivity(intent);
                Toast.makeText(CartActivity.this, "Registered courses", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onError(String e) {
                Toast.makeText(CartActivity.this, e, Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void setAdapter() {
        CartAdapter adapters=new CartAdapter(getApplicationContext(),cartList);
        recyclerView.setAdapter(adapters);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));

    }

    private void initViews() {
        cartList=getIntent().getParcelableArrayListExtra("cart");

        recyclerView=findViewById(R.id.cartRecyclerView);
        confirm=findViewById(R.id.btnConfirmCart);


    }
}