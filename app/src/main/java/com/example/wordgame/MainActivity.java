package com.example.wordgame;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    TextView lblCountDownTimer, lblanswer1, lblanswer2, lblanswer3, lblanswer4,
            lblIndex1, lblIndex2, lblIndex3, lblIndex4, lblQuestionNo;
    Button btnSuggestion1, btnSuggestion2, btnSuggestion3, btnSuggestion4, btnNext, btnSkip, btnBackSpace;
    ArrayList<String> arrayList;
    View.OnClickListener onClickListener, onClickListenerBackSpace;
    int characterPosition = -1;
    ArrayList<TextView> textViewArrayList = new ArrayList<>();
    ArrayList<Button> buttonArrayList = new ArrayList<>(4);
    ArrayList<AnswerState> answerStateArrayList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        onClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AnswerState answerState;
                switch (v.getId()) {
                    case R.id.btn1:
                        answerState = (AnswerState) v.getTag();
                        if (answerState.buttonState == ButtonState.UNSELECTED) {
                            answerState.buttonState = ButtonState.SELECTED;
                            answerState.letter = ((Button) v).getText().toString();
                            answerState.index = ++characterPosition;
                            v.setTag(answerState);
                            textViewArrayList.get(characterPosition).setText(answerState.letter);
                            buttonArrayList.add((Button) v);
                        }

                        break;
                    case R.id.btn2:
                        answerState = (AnswerState) v.getTag();
                        if (answerState.buttonState == ButtonState.UNSELECTED) {
                            answerState.buttonState = ButtonState.SELECTED;
                            answerState.letter = ((Button) v).getText().toString();
                            answerState.index = ++characterPosition;
                            v.setTag(answerState);
                            textViewArrayList.get(characterPosition).setText(answerState.letter);
                            buttonArrayList.add((Button) v);
                        }

                        break;
                    case R.id.btn3:
                        answerState = (AnswerState) v.getTag();
                        if (answerState.buttonState == ButtonState.UNSELECTED) {
                            answerState.buttonState = ButtonState.SELECTED;
                            answerState.letter = ((Button) v).getText().toString();
                            answerState.index = ++characterPosition;
                            v.setTag(answerState);
                            textViewArrayList.get(characterPosition).setText(answerState.letter);
                            buttonArrayList.add((Button) v);
                        }

                        break;
                    case R.id.btn4:
                        answerState = (AnswerState) v.getTag();
                        if (answerState.buttonState == ButtonState.UNSELECTED) {
                            answerState.buttonState = ButtonState.SELECTED;
                            answerState.letter = ((Button) v).getText().toString();
                            answerState.index = ++characterPosition;
                            v.setTag(answerState);
                            textViewArrayList.get(characterPosition).setText(answerState.letter);
                            buttonArrayList.add((Button) v);
                        }
                        break;
                }
                if (characterPosition == 3) {
                    String result = "";
                    for(TextView tx : textViewArrayList){
                        result = result + tx.getText();
                    }
                    if(result.equals(arrayList.get(0))){
                        Toast.makeText(getApplicationContext(),
                                "matched "+ result ,
                                Toast.LENGTH_SHORT).show();
                    }else{
                        Toast.makeText(getApplicationContext(),
                                "" ,
                                Toast.LENGTH_SHORT).show();
                    }
                }
            }
        };
        onClickListenerBackSpace = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (characterPosition != -1) {
                    textViewArrayList.get(characterPosition).setText("");
                    Button btn = buttonArrayList.get(characterPosition);
                    btn.setTag(new AnswerState());
                    buttonArrayList.remove(characterPosition);
                    characterPosition--;
                }
            }
        };

        init();
        setupNewQues(0, shuffleString(arrayList.get(0)));


    }

    void init() {
        lblCountDownTimer = findViewById(R.id.lblCountDownTimer);

        lblanswer1 = findViewById(R.id.lbl1);
        lblanswer2 = findViewById(R.id.lbl2);
        lblanswer3 = findViewById(R.id.lbl3);
        lblanswer4 = findViewById(R.id.lbl4);
        textViewArrayList.add(lblanswer1);
        textViewArrayList.add(lblanswer2);
        textViewArrayList.add(lblanswer3);
        textViewArrayList.add(lblanswer4);

        lblIndex1 = findViewById(R.id.lblindex1);
        lblIndex2 = findViewById(R.id.lblindex2);
        lblIndex3 = findViewById(R.id.lblindex3);
        lblIndex4 = findViewById(R.id.lblindex4);

        lblQuestionNo = findViewById(R.id.lblQuestion);

        btnSuggestion1 = findViewById(R.id.btn1);
        btnSuggestion1.setOnClickListener(onClickListener);
        btnSuggestion1.setTag(new AnswerState());

        btnSuggestion2 = findViewById(R.id.btn2);
        btnSuggestion2.setOnClickListener(onClickListener);
        btnSuggestion2.setTag(new AnswerState());

        btnSuggestion3 = findViewById(R.id.btn3);
        btnSuggestion3.setOnClickListener(onClickListener);
        btnSuggestion3.setTag(new AnswerState());

        btnSuggestion4 = findViewById(R.id.btn4);
        btnSuggestion4.setOnClickListener(onClickListener);
        btnSuggestion4.setTag(new AnswerState());

        btnNext = findViewById(R.id.btnNext);
        btnSkip = findViewById(R.id.btnSkip);
        btnBackSpace = findViewById(R.id.btnBackspace);
        btnBackSpace.setOnClickListener(onClickListenerBackSpace);

        arrayList = new ArrayList<>();
        arrayList.add("NINE");
        arrayList.add("BALL");
        arrayList.add("DATE");
    }

    void setupNewQues(int quesNo, char[] chars) {

        btnSuggestion1.setText(String.valueOf(chars[0]));
        btnSuggestion2.setText(String.valueOf(chars[1]));
        btnSuggestion3.setText(String.valueOf(chars[2]));
        btnSuggestion4.setText(String.valueOf(chars[3]));

        lblQuestionNo.setText(String.valueOf(quesNo));
    }

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

    enum ButtonState {
        SELECTED,
        UNSELECTED
    }

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
}
