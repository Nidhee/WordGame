package com.example.wordgame.data;

import android.os.Parcel;
import android.os.Parcelable;

public class ScoreData implements Parcelable {
    public int questionNo;
    public ResultState resultState;
    public String time;
    public String questionString;
    public String resultString;

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.questionNo);
        dest.writeInt(this.resultState == null ? -1 : this.resultState.ordinal());
        dest.writeString(this.time);
        dest.writeString(this.questionString);
        dest.writeString(this.resultString);
    }

    public ScoreData() {
    }

    private ScoreData(Parcel in) {
        this.questionNo = in.readInt();
        int tmpResultState = in.readInt();
        this.resultState = tmpResultState == -1 ? null : ResultState.values()[tmpResultState];
        this.time = in.readString();
        this.questionString = in.readString();
        this.resultString = in.readString();
    }

    public static final Creator<ScoreData> CREATOR = new Creator<ScoreData>() {
        @Override
        public ScoreData createFromParcel(Parcel source) {
            return new ScoreData(source);
        }

        @Override
        public ScoreData[] newArray(int size) {
            return new ScoreData[size];
        }
    };
}

