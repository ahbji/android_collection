package com.codeingnight.android.scoreboard;

import android.os.Parcel;
import android.os.Parcelable;

public class ScoreModel implements Parcelable {
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

    protected ScoreModel(Parcel in) {
        homeScore = in.readInt();
        guestScore = in.readInt();
    }

    public static final Creator<ScoreModel> CREATOR = new Creator<ScoreModel>() {
        @Override
        public ScoreModel createFromParcel(Parcel in) {
            return new ScoreModel(in);
        }

        @Override
        public ScoreModel[] newArray(int size) {
            return new ScoreModel[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(homeScore);
        dest.writeInt(guestScore);
    }
}
