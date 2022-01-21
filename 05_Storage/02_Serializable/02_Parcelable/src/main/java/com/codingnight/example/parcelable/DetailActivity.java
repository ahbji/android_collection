package com.codingnight.example.parcelable;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

public class DetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        Student student = getIntent().getBundleExtra("data").getParcelable("student");
        TextView name = (TextView)findViewById(R.id.name);
        TextView age = (TextView)findViewById(R.id.age);
        TextView grade = (TextView)findViewById(R.id.grade);
        name.setText(student.getName());
        age.setText(String.valueOf(student.getAge()));
        grade.setText(student.getScore().getGrade());
    }
}