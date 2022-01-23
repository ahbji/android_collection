package com.codingnight.example.chronometer

import android.os.SystemClock
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel

class MeterViewModel(val handle: SavedStateHandle) : ViewModel() {
    private val ELAPSED_TIME_KEY = "ELAPSED_TIME"

    init {
        if (!handle.contains(ELAPSED_TIME_KEY)) {
            handle.set(ELAPSED_TIME_KEY, 0)
        }
    }

    fun getElapsedTime(): MutableLiveData<Long> {
        return handle.getLiveData(ELAPSED_TIME_KEY)
    }

    fun setElapsedTime(base: Long) {
        handle.set(ELAPSED_TIME_KEY, SystemClock.elapsedRealtime() - base)
    }

    fun getBase(): Long {
        return SystemClock.elapsedRealtime() - getElapsedTime().value!!
    }
}