package com.codeingnight.android.navigatewithviewmodel

import android.view.View
import android.widget.SeekBar
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.navigation.Navigation

class MainViewModel : ViewModel() {

    private var _number: MutableLiveData<Int> = MutableLiveData<Int>(0)
    val number: LiveData<Int>
        get() = _number

    fun go(view: View) {
        Navigation.findNavController(view).navigate(R.id.action_mainFragment_to_detailFragment)
    }

    fun back(view: View) {
        Navigation.findNavController(view).navigate(R.id.action_detailFragment_to_mainFragment)
    }

    fun onProgressChanged(seekBar:SeekBar, progress: Int, fromUser: Boolean) {
        _number.value = progress
    }

    fun plus(value: Int) {
        _number.value = _number.value?.plus(value)
    }

    fun minus(value: Int) {
        _number.value = _number.value?.minus(value)
    }
}