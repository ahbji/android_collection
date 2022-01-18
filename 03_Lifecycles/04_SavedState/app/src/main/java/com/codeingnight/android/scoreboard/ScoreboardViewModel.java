package com.codeingnight.android.scoreboard;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.SavedStateHandle;
import androidx.lifecycle.ViewModel;

import java.util.Objects;

public class ScoreboardViewModel extends ViewModel {

    private static final String KEY = "ScoreboardViewModel";

    private SavedStateHandle handle;
    public ScoreboardViewModel(SavedStateHandle savedStateHandle) {
        this.handle = savedStateHandle;
    }

    public MutableLiveData<ScoreStore> getScoreStore() {
        if (!handle.contains(KEY)) {
            handle.set(KEY, new ScoreStore());
            incrementScore(new ScoreModel(0, 0));
        }
        return handle.getLiveData(KEY);
    }

    private void incrementScore(ScoreModel item) {
        Objects.requireNonNull(getScoreStore().getValue()).push(item);
        getScoreStore().setValue(getScoreStore().getValue());
    }

    public void rollBack() {
        if (Objects.requireNonNull(getScoreStore().getValue()).isNotEmpty()) {
            getScoreStore().getValue().pop();
        }
        getScoreStore().setValue(getScoreStore().getValue());
    }

    public void reset() {
        if (Objects.requireNonNull(getScoreStore().getValue()).isNotEmpty()) {
            getScoreStore().getValue().empty();
        }
        getScoreStore().setValue(getScoreStore().getValue());
    }

    public void incrementHomeScore(int point) {
        incrementScore(new ScoreModel(point, 0));
    }

    public void incrementGuestScore(int point) {
        incrementScore(new ScoreModel(0, point));
    }
}
