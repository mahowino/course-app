package com.example.courseapp;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class FinalActivitySender extends AppCompatActivity {


    Button finish;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_final_sender);
        initViews();
        setListeners();
    }

    private void setListeners() {

        finish.setOnClickListener(view -> redirect());
    }

    private void redirect() {
        Intent intent=new Intent(getApplicationContext(), HomePage.class);
        startActivity(intent);
        finish();
    }

    private void initViews() {

        finish=findViewById(R.id.btnRedrectToHomePage);

    }
}