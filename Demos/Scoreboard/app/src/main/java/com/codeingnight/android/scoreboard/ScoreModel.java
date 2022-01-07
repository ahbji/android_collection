package com.codeingnight.android.scoreboard;

public class ScoreModel {
    public ScoreModel(int homeScore, int guestScore) {
        this.homeScore = homeScore;
        this.guestScore = guestScore;
    }

    public int getHomeScore() {
        return homeScore;
    }

    public int getGuestScore() {
        return guestScore;
    }

    private int homeScore = 0;
    private int guestScore = 0;
}
