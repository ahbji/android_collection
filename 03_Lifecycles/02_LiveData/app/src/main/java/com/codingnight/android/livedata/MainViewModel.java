package com.codingnight.android.livedata;

import android.os.SystemClock;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.Objects;

public class MainViewModel extends ViewModel {

    private MutableLiveData<MainModel> mainModelLiveData;

    public MutableLiveData<MainModel> getMainModelLiveData() {
        if (mainModelLiveData == null) {
            // 初始化 LiveData
            mainModelLiveData = new MutableLiveData<>();
            // 为 LiveData 设置初始值
            mainModelLiveData.setValue(new MainModel());
        }
        return mainModelLiveData;
    }

    private MainModel getModel() {
        return mainModelLiveData.getValue();
    }

    public boolean toggleIncrementMode() {
        switch (Objects.requireNonNull(incrementMode.getValue())) {
            case MANUAL_MODE:
                prepareAutoIncrementMode();
                break;
            case AUTO_TASK_STOPED:
                toggleManualMode();
                break;
            case AUTO_TASK_STARTED:
                exitAutoIncrementMode();
                break;
        }
        // 长按操作需要返回 true
        return true;
    }

    private void toggleManualMode() {
        incrementMode.setValue(IncrementMode.MANUAL_MODE);
    }

    private void prepareAutoIncrementMode() {
        incrementMode.setValue(IncrementMode.AUTO_TASK_STOPED);
    }

    private void exitAutoIncrementMode() {
        incrementMode.setValue(IncrementMode.MANUAL_MODE);
        runThread = false;
    }

    public MutableLiveData<IncrementMode> getIncrementMode() {
        return incrementMode;
    }

    private final MutableLiveData<IncrementMode> incrementMode = new MutableLiveData<>(IncrementMode.MANUAL_MODE);

    public void handleIncrement() {
        switch (Objects.requireNonNull(incrementMode.getValue())) {
            case MANUAL_MODE:
                getModel().increment();
                break;
            case AUTO_TASK_STARTED:
                stopAutoIncrement();
                break;
            case AUTO_TASK_STOPED:
                startAutoIncrement();
                break;
        }
        mainModelLiveData.setValue(getModel());
    }

    private void startAutoIncrement() {
        incrementMode.setValue(IncrementMode.AUTO_TASK_STARTED);
        new Thread(task).start();
    }

    private void stopAutoIncrement() {
        incrementMode.setValue(IncrementMode.AUTO_TASK_STOPED);
        runThread = false;
    }

    private volatile boolean runThread = false;

    private final Runnable task = () -> {
        runThread = true;
        while (runThread) {
            getModel().increment();
            mainModelLiveData.postValue(getModel());
            SystemClock.sleep(1000);
        }
    };
}