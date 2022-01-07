package com.codeingnight.android.scoreboard;

import java.util.Stack;

public class ScoreStore {
    private static final ScoreStore scoreStore = new ScoreStore();

    private final Stack<ScoreModel> scoreStack;

    private ScoreStore() {
        scoreStack = new Stack<>();
    }

    public static ScoreStore getSingleton() {
        return scoreStore;
    }

    public ScoreModel pop() {
        return scoreStack.pop();
    }

    public ScoreModel push(ScoreModel item) {
        return scoreStack.push(item);
    }

    public boolean isNotEmpty() {
        return !scoreStack.empty();
    }

    public void empty() {
        while (!scoreStack.empty()) {
            scoreStack.pop();
        }
    }

    private ScoreModel getTotalScore() {
        int totalHomeScore = 0,totalGuestScore = 0;
        for (ScoreModel scoreItem: scoreStack) {
            totalHomeScore += scoreItem.getHomeScore();
            totalGuestScore += scoreItem.getGuestScore();
        }
        return new ScoreModel(totalHomeScore, totalGuestScore);
    }

    public String getHomeTotalScore() {
        return String.valueOf(getTotalScore().getHomeScore());
    }

    public String getGuestTotalScore() {
        return String.valueOf(getTotalScore().getGuestScore());
    }
}