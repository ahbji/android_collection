package com.codeingnight.android.words;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.Button;

import com.codeingnight.android.words.persistent.Word;

import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class MainActivity extends AppCompatActivity {
    Button buttonInsert, buttonClear;
    WordViewModel wordViewModel;

    RecyclerView recyclerView;
    MyAdapter myAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recyclerView = findViewById(R.id.recyclerView);

        wordViewModel = new ViewModelProvider(this).get(WordViewModel.class);
        wordViewModel.getAllWordsLive().observe(this, words -> {
            int temp = myAdapter.getItemCount();
            myAdapter.setAllWords(words);
            if (temp!=words.size()) {
                myAdapter.notifyDataSetChanged();
            }
        });

        myAdapter = new MyAdapter(wordViewModel);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(myAdapter);

        buttonInsert = findViewById(R.id.insert);
        buttonClear = findViewById(R.id.clear);

        buttonInsert.setOnClickListener(v -> {
            String[] english = {
                    "Hello",
                    "World",
                    "Android",
                    "Google",
                    "Studio",
                    "Project",
                    "Database",
                    "Recycler",
                    "View",
                    "String",
                    "Value",
                    "Integer"
            };
            String[] chinese = {
                    "你好",
                    "世界",
                    "安卓系统",
                    "谷歌公司",
                    "工作室",
                    "项目",
                    "数据库",
                    "回收站",
                    "视图",
                    "字符串",
                    "价值",
                    "整数类型"
            };
            wordViewModel.blukInsertWords(IntStream.range(0, english.length)
                    .boxed()
                    .map(i -> new Word(english[i], chinese[i]))
                    .collect(Collectors.toList()));
        });
        buttonClear.setOnClickListener(v -> wordViewModel.deleteAllWords());
    }
}