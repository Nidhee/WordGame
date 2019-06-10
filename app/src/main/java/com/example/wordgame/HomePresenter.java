package com.example.wordgame;

import com.example.wordgame.data.ScoreData;

import java.util.ArrayList;

public class HomePresenter {

    private ScoreData scoreData;
    private ArrayList<ScoreData> scoreDataArrayList;
    private ArrayList<String> questionArrayList;
    private int currentQuestion = -1;

    String getNextQuestion() {
        currentQuestion ++;
        if (questionArrayList == null) {
            questionArrayList = new ArrayList<>();
            questionArrayList.add("NINE");
            questionArrayList.add("BALL");
            questionArrayList.add("DATE");
            questionArrayList.add("CORN");
            questionArrayList.add("KING");
            questionArrayList.add("COOK");
            questionArrayList.add("JAVA");
            questionArrayList.add("TIME");
            questionArrayList.add("STAR");
            questionArrayList.add("CITY");
        }
        return questionArrayList.get(currentQuestion);
    }

    boolean hasMoreWord() {
        return currentQuestion < questionArrayList.size() - 1;
    }

    ScoreData getScoreData(int questionNo, String questionString) {
        scoreData = new ScoreData();
        scoreData.setQuestionNo(questionNo);
        scoreData.setQuestionString(questionString);
        return scoreData;
    }

    void collectScoreData(ScoreData newScoreData) {
        if (scoreDataArrayList == null) {
            scoreDataArrayList = new ArrayList<>();
        }
        scoreDataArrayList.add(newScoreData);
    }

    ArrayList<ScoreData> getAllResult() {
        return scoreDataArrayList;
    }

    int getCurrentQuestionNumber() {
        return currentQuestion;
    }
}
