package com.codingnight.android.livedata;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
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
        viewModel.getMainModelLiveData().observe(this, new Observer<MainModel>() {
            @Override
            public void onChanged(MainModel mainModel) {
                Log.i("MainActivity", "onChanged");
                textView.setText(String.valueOf(mainModel.getNumber()));
            }
        });
        viewModel.getIncrementMode().observe(this, new Observer<IncrementMode>() {
            @Override
            public void onChanged(IncrementMode incrementMode) {
                fab.setImageDrawable(getDrawerMainFab(incrementMode, getApplicationContext()));
            }
        });
        fab.setOnClickListener((view) ->
            viewModel.handleIncrement()
        );

        fab.setOnLongClickListener((view) ->
            viewModel.toggleIncrementMode()
        );
    }

    private Drawable getDrawerMainFab(IncrementMode incrementMode, Context context) {
        switch (incrementMode) {
            case AUTO_TASK_STARTED:
                return ContextCompat.getDrawable(context, R.drawable.ic_pause);
            case AUTO_TASK_STOPED:
                return ContextCompat.getDrawable(context, R.drawable.ic_play);
            default:
                return ContextCompat.getDrawable(context, R.drawable.ic_add);
        }
    }
}