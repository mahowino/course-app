package com.example.courseapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.courseapp.Helpers.CourseHelper;
import com.example.courseapp.Interfaces.onResult;
import com.example.courseapp.Model.Course;

public class MarkCourse extends AppCompatActivity {

    TextView title,description;
    Button markCourse;
    Spinner showGrade;
    Course course;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mark_course);
        initViews();
        setTexts();
        setListeners();
    }

    private void setListeners() {
        markCourse.setOnClickListener(view -> mark());
    }

    private void mark() {
        course.setGrade(showGrade.getSelectedItem().toString());
        CourseHelper.gradeCourse(course, new onResult() {
            @Override
            public void onSuccess() {
                Intent intent=new Intent(getApplicationContext(),HomePage.class);
                startActivity(intent);
                Toast.makeText(MarkCourse.this, "Course graded successfully", Toast.LENGTH_SHORT).show();
                finish();
            }

            @Override
            public void onError(String e) {

                Toast.makeText(MarkCourse.this, e, Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void setTexts() {

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.grades, android.R.layout.simple_spinner_item);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        showGrade.setAdapter(adapter);
        title.setText(course.getTitle());
        description.setText(course.getDescription());

    }

    private void initViews() {
        title=findViewById(R.id.txtCourseName);
        description= findViewById(R.id.txtCourseDescriptionChosen);
        markCourse=findViewById(R.id.btnMarkCourse);
        showGrade=findViewById(R.id.grade_spinner);
        course=getIntent().getParcelableExtra("course");
    }
}