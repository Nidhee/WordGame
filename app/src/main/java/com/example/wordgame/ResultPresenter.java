package com.example.wordgame;

import com.example.wordgame.data.ScoreData;

import java.util.ArrayList;

public class ResultPresenter {

    private  ArrayList<ScoreData> scoreData;

    private int totalScore = 0;
    private int totalSkipped = 0;
    private int totalWrong = 0;
    private int totalTimedOut = 0;

    void calculateResult() {
        for (ScoreData scoreData : this.scoreData) {
            switch (scoreData.getResultState()) {
                case RIGHT:
                    totalScore++;
                    break;
                case WRONG:
                    totalWrong++;
                    break;
                case SKIPPED:
                    totalSkipped++;
                    break;
                case TIMEOUT:
                    totalTimedOut++;
                    break;
            }
        }
    }

    void setScoreData(ArrayList<ScoreData> scoreData) {
        this.scoreData = scoreData;
    }

    int getTotalScore(){
        return totalScore;
    }
    int getTotalSkipped(){
        return totalSkipped;
    }
    int getTotalWrong(){
        return totalWrong;
    }
    int getTotalTimedOut(){
        return totalTimedOut;
    }
    int getTotalQuestion(){
        return this.scoreData.size();
    }
}
