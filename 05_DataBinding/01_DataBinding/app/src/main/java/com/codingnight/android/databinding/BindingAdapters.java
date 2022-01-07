package com.codingnight.android.databinding;

import android.content.Context;
import android.graphics.drawable.Drawable;

import androidx.core.content.ContextCompat;
import androidx.databinding.BindingAdapter;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class BindingAdapters {

    @BindingAdapter("app:mainFabIcon")
    public static void mainFabIcon(FloatingActionButton button, IncrementMode incrementMode) {
        button.setImageDrawable(getDrawerMainFab(incrementMode, button.getContext()));
    }

    private static Drawable getDrawerMainFab(IncrementMode incrementMode, Context context) {
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