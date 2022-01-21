package com.codingnight.example.serializable;

import androidx.appcompat.app.AppCompatActivity;

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
        try {
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(openFileOutput(FILE_NAME, MODE_PRIVATE));
            Student student = new Student("测试",10, new Score(90, 80, 70));
            objectOutputStream.writeObject(student);
            objectOutputStream.flush();
            objectOutputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            ObjectInputStream objectInputStream = new ObjectInputStream(openFileInput(FILE_NAME));
            Student student = (Student)objectInputStream.readObject();
            Log.i("Student", student.getName() + " " + student.getAge() + " " +  student.getScore().getGrade());
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}