package com.example.wordgame;

import android.os.Parcel;
import android.os.Parcelable;

public class Result implements Parcelable {
        int questionNo;
        ResultState resultState;
        String time;
        String questionString;
        String resultString;

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

    public Result() {
    }

    protected Result(Parcel in) {
        this.questionNo = in.readInt();
        int tmpResultState = in.readInt();
        this.resultState = tmpResultState == -1 ? null : ResultState.values()[tmpResultState];
        this.time = in.readString();
        this.questionString = in.readString();
        this.resultString = in.readString();
    }

    public static final Creator<Result> CREATOR = new Creator<Result>() {
        @Override
        public Result createFromParcel(Parcel source) {
            return new Result(source);
        }

        @Override
        public Result[] newArray(int size) {
            return new Result[size];
        }
    };
}

enum ResultState {
        WRONG,
        RIGHT,
        SKIPPED,
        TIMEOUT
}