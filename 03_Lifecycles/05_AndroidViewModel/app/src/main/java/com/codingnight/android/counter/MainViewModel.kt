package com.codingnight.android.counter

import android.app.Application
import android.content.Context
import android.os.SystemClock
import androidx.lifecycle.*

class MainViewModel(application: Application, val handle: SavedStateHandle) :
    AndroidViewModel(application) {

    private val shpName = getApplication<Application>().getString(R.string.shp_name)
    private val DATA_KEY = getApplication<Application>().getString(R.string.data_key)
    private val BTN_STATE_KEY = getApplication<Application>().getString(R.string.btn_state_key)

    @Volatile
    private var _runThread = false

    init {
        if (!handle.contains(DATA_KEY)) {
            load()
        }
    }

    fun getMainModelLiveData(): MutableLiveData<MainModel> {
        return handle.getLiveData(DATA_KEY)
    }

    private val model: MainModel?
        get() = getMainModelLiveData().value

    fun getIncrementMode(): MutableLiveData<IncrementMode> {
        if (!handle.contains(BTN_STATE_KEY)) {
            handle.set(BTN_STATE_KEY, IncrementMode.MANUAL_MODE)
            return handle.getLiveData(BTN_STATE_KEY)
        }
        if (!_runThread) {
            if (handle.getLiveData<IncrementMode>(BTN_STATE_KEY).value != IncrementMode.MANUAL_MODE)
                handle.set(BTN_STATE_KEY, IncrementMode.AUTO_TASK_STOPED)
        }
        return handle.getLiveData(BTN_STATE_KEY)
    }

    fun save() {
        val shp = getApplication<Application>().getSharedPreferences(shpName, Context.MODE_PRIVATE)
        getMainModelLiveData().value?.let { shp.edit().putInt(DATA_KEY, it.number) }
        shp.edit().apply()
    }

    fun load() {
        val shp = getApplication<Application>().getSharedPreferences(shpName, Context.MODE_PRIVATE)
        val model = MainModel()
        model.number = shp.getInt(DATA_KEY, 0)
        handle.set(DATA_KEY, model)
    }

    fun toggleIncrementMode(): Boolean {
        when (getIncrementMode().value) {
            IncrementMode.MANUAL_MODE -> prepareAutoIncrementMode()
            IncrementMode.AUTO_TASK_STOPED -> toggleManualMode()
            IncrementMode.AUTO_TASK_STARTED -> exitAutoIncrementMode()
            else -> {}
        }
        // 长按操作需要返回 true
        return true
    }

    private fun toggleManualMode() {
        getIncrementMode().value = IncrementMode.MANUAL_MODE
    }

    private fun prepareAutoIncrementMode() {
        getIncrementMode().value = IncrementMode.AUTO_TASK_STOPED
    }

    private fun exitAutoIncrementMode() {
        getIncrementMode().value = IncrementMode.MANUAL_MODE
        _runThread = false
    }

    fun handleIncrement() {
        when (getIncrementMode().value) {
            IncrementMode.MANUAL_MODE -> model!!.increment()
            IncrementMode.AUTO_TASK_STARTED -> stopAutoIncrement()
            IncrementMode.AUTO_TASK_STOPED -> startAutoIncrement()
            else -> {}
        }
        getMainModelLiveData().value = model
    }

    private fun startAutoIncrement() {
        getIncrementMode().value = IncrementMode.AUTO_TASK_STARTED
        Thread(task).start()
    }

    private fun stopAutoIncrement() {
        getIncrementMode().value = IncrementMode.AUTO_TASK_STOPED
        _runThread = false
    }

    private val task = Runnable {
        _runThread = true
        while (_runThread) {
            model!!.increment()
            getMainModelLiveData().postValue(model)
            SystemClock.sleep(1000)
        }
    }
}