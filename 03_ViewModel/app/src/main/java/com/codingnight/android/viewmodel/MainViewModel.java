package com.codingnight.android.viewmodel;

import androidx.lifecycle.ViewModel;

public class MainViewModel extends ViewModel {
    private MainModel model;

    public void increment() {
        model.increment();
    }

    public MainModel getModel() {
        if (model == null) {
            model = new MainModel();
        }
        return model;
    }
}