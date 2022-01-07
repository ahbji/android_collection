package com.codingnight.android.viewmodel;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class MainActivity extends AppCompatActivity {
    FloatingActionButton fab;
    TextView textView;
    MainViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textView = findViewById(R.id.textView);
        fab = findViewById(R.id.floatingActionButton);

        ViewModelProvider.Factory factory = new MainViewModelFactory();
        viewModel = new ViewModelProvider(this, factory).get(MainViewModel.class);
        textView.setText(String.valueOf(viewModel.getModel().getNumber()));
        fab.setOnClickListener((view) -> {
            viewModel.increment();
            textView.setText(String.valueOf(viewModel.getModel().getNumber()));
        });
    }
}