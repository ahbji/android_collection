package com.codingnight.example.parcelable;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class MainActivity extends AppCompatActivity {
    private static final String FILE_NAME = "test_file";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViewById(R.id.button).setOnClickListener((v) -> {
            Student student = new Student("测试",10, new Score(90, 80, 70));
            Intent intent = new Intent(MainActivity.this, DetailActivity.class);
//            intent.putExtra("student", student);
            Bundle bundle = new Bundle();
            bundle.putParcelable("student", student);
            intent.putExtra("data", bundle);
            startActivity(intent);
        });
    }
}