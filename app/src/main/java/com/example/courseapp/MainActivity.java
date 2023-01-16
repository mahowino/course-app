package com.example.courseapp;

import static java.lang.Thread.sleep;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Thread splashScreenDisplay = new Thread() {
            @Override
            public void run() {
                load_splashscreen();
                super.run();
            }
        };

        splashScreenDisplay.start();
    }

    private void load_splashscreen() {

        try {
            sleep(2000);
            Intent intent=new Intent(MainActivity.this, SignUpActivity.class);
            startActivity(intent);
            finish();
        } catch (Exception exception) {
            Toast.makeText(this, exception.getMessage(), Toast.LENGTH_SHORT).show();
        }

    }
}