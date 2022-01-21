package com.codingnight.example.json;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Gson gson = new Gson();

        gson(gson);

        Student student1 = new Student("TEST", 10, new Score(90, 85, 80));
        String jsonStudent = gson.toJson(student1);
        Log.i("Student", jsonStudent);
        Student student2 = gson.fromJson(jsonStudent, Student.class);
        Log.i("Student", student2.getName() + " " + student2.getAge() + " " + student2.getScore().getGrade());

        gsonArrayAndList(gson, student1, student2);

        gsonSet(gson);

        gsonMap(gson);
    }

    private void gson(Gson gson) {
        Person person1 = new Person("TEST", 10);
        String jsonPerson = gson.toJson(person1);
        Log.i("PERSON", jsonPerson);
        Person person2 = gson.fromJson(jsonPerson, Person.class);
        Log.i("PERSON", person2.name + " " + person2.age);
    }

    private void gsonArrayAndList(Gson gson, Student student1, Student student2) {
        String jsonStudents = gson.toJson(new Student[]{student1, student2});
        Log.i("Array", jsonStudents);
        Student[] students = gson.fromJson(jsonStudents, Student[].class);

        for (Student it : students) {
            Log.i("Student", it.getName() + " " + it.getAge() + " " + it.getScore().getGrade());
        }

        Log.i("List", gson.toJson(Arrays.asList(students)));
        Type typeStudents = new TypeToken<List<Student>>() {
        }.getType();
        List<Student> list = gson.fromJson(gson.toJson(Arrays.asList(students)), typeStudents);
        for (Student it : list) {
            Log.i("Student", it.getName() + " " + it.getAge() + " " + it.getScore().getGrade());
        }
    }

    private void gsonSet(Gson gson) {
        Set<String> set1 = new HashSet<>();
        set1.add("a");
        set1.add("b");
        set1.add("c");
        Log.i("Set", gson.toJson(set1));
        Type typeSet = new TypeToken<Set<String>>() {
        }.getType();

        Set<String> set2 = gson.fromJson(gson.toJson(set1), typeSet);
        for (String it : set2) {
            Log.i("SET", it);
        }
    }

    private void gsonMap(Gson gson) {
        Map<Integer, String> hm1 = new HashMap<Integer, String>();
        hm1.put(1, "abc");
        hm1.put(2, "aaa");
        hm1.put(3, "bbb");
        Log.i("Map", gson.toJson(hm1));
        Type typeMap = new TypeToken<Map<Integer, String>>(){}.getType();
        Map<Integer, String> hm2 = gson.fromJson(gson.toJson(hm1), typeMap);
        Iterator<Integer> it = hm2.keySet().iterator();
        while (it.hasNext()) {
            Log.i("Map", hm2.get(it.next()));
        }
    }
}