package com.codeingnight.android.scoreboard;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.Objects;

public class ScoreboardViewModel extends ViewModel {

    public MutableLiveData<ScoreStore> getScoreStore() {
        if (scoreStore == null) {
            scoreStore = new MutableLiveData<>();
            scoreStore.setValue(ScoreStore.getSingleton());
            incrementScore(new ScoreModel(0, 0));
        }
        return scoreStore;
    }

    private void incrementScore(ScoreModel item) {
        Objects.requireNonNull(scoreStore.getValue()).push(item);
        scoreStore.setValue(scoreStore.getValue());
    }

    public void rollBack() {
        Objects.requireNonNull(scoreStore.getValue()).pop();
        scoreStore.setValue(scoreStore.getValue());
    }

    public void reset() {
        Objects.requireNonNull(scoreStore.getValue()).empty();
        scoreStore.setValue(scoreStore.getValue());
    }

    private MutableLiveData<ScoreStore> scoreStore;

    public void incrementHomeScore(int point) {
        incrementScore(new ScoreModel(point, 0));
    }

    public void incrementGuestScore(int point) {
        incrementScore(new ScoreModel(0, point));
    }
}
