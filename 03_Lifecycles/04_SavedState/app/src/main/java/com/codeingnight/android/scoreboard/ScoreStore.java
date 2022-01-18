package com.codeingnight.android.scoreboard;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Arrays;
import java.util.Stack;

public class ScoreStore implements Parcelable {

    private Stack<ScoreModel> scoreStack;

    public ScoreStore() {
        this.scoreStack = new Stack<>();
    }

    private ScoreStore(Parcel in, Stack<ScoreModel> scoreStack) {
        this.scoreStack = scoreStack;
        Parcelable[] parcelableArray = in.readParcelableArray(ScoreModel.class.getClassLoader());
        ScoreModel[] scores = new ScoreModel[parcelableArray.length];
        if (parcelableArray != null) {
            scores = Arrays.copyOf(parcelableArray, parcelableArray.length, ScoreModel[].class);
        }
        assert scores != null;
        Arrays.stream(scores).forEach(this.scoreStack::push);
    }

    public static final Creator<ScoreStore> CREATOR = new Creator<ScoreStore>() {
        public ScoreStore createFromParcel(Parcel in) {
            return new ScoreStore(in, new Stack<>());
        }

        public ScoreStore[] newArray(int size) {
            return new ScoreStore[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel out, int flags) {
        ScoreModel[] array = new ScoreModel[scoreStack.size()];
        for (int i = 0; i < scoreStack.size(); i++ ) {
            array[i] = scoreStack.get(i);
        }
        out.writeParcelableArray(array, flags);
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