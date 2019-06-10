package com.example.wordgame.data;

import android.os.Parcel;
import android.os.Parcelable;

public class ScoreData implements Parcelable {
    private int questionNo;
    private ResultState resultState;
    private String time;
    private String questionString;
    private String resultString;

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

    public void setQuestionNo(int questionNo) {
        this.questionNo = questionNo;
    }

    public void setResultState(ResultState resultState) {
        this.resultState = resultState;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public void setQuestionString(String questionString) {
        this.questionString = questionString;
    }

    public void setResultString(String resultString) {
        this.resultString = resultString;
    }

    public int getQuestionNo() {
        return questionNo;
    }

    public ResultState getResultState() {
        return resultState;
    }

    public String getTime() {
        return time;
    }

    public String getQuestionString() {
        return questionString;
    }

    public String getResultString() {
        return resultString;
    }
}

