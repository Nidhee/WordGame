package com.example.wordgame.data;

public class Answer {
    public ButtonState buttonState;
    public String letter;
    public int index;

    public Answer() {
        buttonState = ButtonState.UNSELECTED;
        letter = "";
        index = -1;
    }

    Answer reset() {
        buttonState = ButtonState.UNSELECTED;
        letter = "";
        index = -1;
        return this;
    }
}

