package com.codingnight.android.viewmodel;

public class MainModel {
    private int number = 0;

    public void increment() {
        number++;
    }

    public int getNumber() {
        return number;
    }
}