package com.example.wordgame;

class AnswerState {
    ButtonState buttonState;
    String letter;
    int index;

    AnswerState() {
        buttonState = ButtonState.UNSELECTED;
        letter = "";
        index = -1;
    }

    AnswerState reset() {
        buttonState = ButtonState.UNSELECTED;
        letter = "";
        index = -1;
        return this;
    }
}

enum ButtonState {
    SELECTED,
    UNSELECTED
}