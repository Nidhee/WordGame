package com.example.wordgame;

import com.example.wordgame.data.ResultState;
import com.example.wordgame.data.ScoreData;

import java.util.Random;
import java.util.concurrent.TimeUnit;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class QuestionPresenter {
    private ScoreData scoreData;

    // shuffle question string using random
    char[] shuffleString(String input) {
        char[] chars = input.toCharArray();

        Random random = new Random();
        for (int i = chars.length; i > 1; i--) {
            int randompos = random.nextInt(i - 1);
            char rand = chars[randompos];
            char curr = chars[i - 1];
            chars[i - 1] = rand;
            chars[randompos] = curr;
        }
        return chars;
    }

    Observable<Long> getTimerObservable() {
        return Observable.interval(1, 1, TimeUnit.SECONDS)
                .take(15)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io());
    }

    void setScoreData(ScoreData scoreData) {
        this.scoreData = scoreData;
    }

    ScoreData getScoreData() {
        return scoreData;
    }

    void setResultState(ResultState resultState) {
        scoreData.setResultState(resultState);
    }

    void setTime(String time) {
        scoreData.setTime(time);
    }

    void setResultString(String resultString) {
        scoreData.setResultString(resultString);
    }

    int getQuestionNo() {
        return scoreData.getQuestionNo();
    }

    String getQuestionString() {
        return scoreData.getQuestionString();
    }


}
