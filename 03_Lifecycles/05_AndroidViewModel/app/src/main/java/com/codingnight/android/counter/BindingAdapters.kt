package com.codingnight.android.counter

import android.content.Context
import android.graphics.drawable.Drawable
import androidx.core.content.ContextCompat
import androidx.databinding.BindingAdapter
import com.google.android.material.floatingactionbutton.FloatingActionButton

object BindingAdapters {
    @JvmStatic
    @BindingAdapter("app:mainFabIcon")
    fun mainFabIcon(button: FloatingActionButton, incrementMode: IncrementMode) {
        button.setImageDrawable(getDrawerMainFab(incrementMode, button.context))
    }

    private fun getDrawerMainFab(incrementMode: IncrementMode, context: Context): Drawable? {
        return when (incrementMode) {
            IncrementMode.AUTO_TASK_STARTED -> ContextCompat.getDrawable(
                context,
                R.drawable.ic_pause
            )
            IncrementMode.AUTO_TASK_STOPED -> ContextCompat.getDrawable(context, R.drawable.ic_play)
            else -> ContextCompat.getDrawable(context, R.drawable.ic_add)
        }
    }
}