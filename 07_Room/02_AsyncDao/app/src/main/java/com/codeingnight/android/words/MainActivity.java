package com.codeingnight.android.words;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    TextView textView;
    Button buttonInsert, buttonUpdate, buttonDelete, buttonClear;
    WordViewModel wordViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        wordViewModel = new ViewModelProvider(this).get(WordViewModel.class);

        textView = findViewById(R.id.text);
        wordViewModel.getAllWordsLive().observe(this, words -> {
            StringBuilder text = new StringBuilder();
            for (int i = 0; i < words.size(); i++) {
                Word word = words.get(i);
                text.append(word.getId()).append(":").append(word.getWord()).append("=").append(word.getChineseMeaning()).append("\n");
            }
            textView.setText(text.toString());
        });

        buttonInsert = findViewById(R.id.insert);
        buttonClear = findViewById(R.id.clear);
        buttonDelete = findViewById(R.id.delete);
        buttonUpdate = findViewById(R.id.update);

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
            for(int i = 0;i<english.length;i++) {
                wordViewModel.insertWords(new Word(english[i],chinese[i]));
            }
        });
        buttonClear.setOnClickListener(v -> wordViewModel.deleteAllWords());

        buttonUpdate.setOnClickListener(v -> {
            Word word = new Word("Hi", "你好啊!");
            word.setId(186);
            wordViewModel.updateWords(word);
        });

        buttonDelete.setOnClickListener(v -> {
            Word word = new Word("Hi", "你好啊!");
            word.setId(182);
            wordViewModel.deleteWords(word);
        });
    }
}