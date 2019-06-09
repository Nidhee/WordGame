package com.example.wordgame;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.airbnb.lottie.LottieAnimationView;
import com.example.wordgame.data.Answer;
import com.example.wordgame.data.ButtonState;
import com.example.wordgame.data.ScoreData;
import com.example.wordgame.data.ResultState;

import java.util.ArrayList;
import java.util.Locale;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import rx.Observable;
import rx.Observer;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class QuestionFragment extends Fragment {

    private static final String ARG_RESULT = "scoreData";

    private TextView lblCountDownTimer, lblanswer1, lblanswer2, lblanswer3, lblanswer4,
            lblIndex1, lblIndex2, lblIndex3, lblIndex4, lblQuestionNo;

    private Button btnSuggestion1, btnSuggestion2, btnSuggestion3, btnSuggestion4, btnNext, btnSkip;
    private LottieAnimationView viewRight, viewWrong;
    private int characterPosition;
    private ArrayList<TextView> textViewArrayList;
    private ArrayList<Button> buttonArrayList;

    private OnFragmentInteractionListener mListener;

    private ScoreData scoreData;
    private Subscription subscription;
    private View.OnClickListener onCharacterButtonClickListner = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Answer answer;
            switch (v.getId()) {
                case R.id.btn1:
                    answer = (Answer) v.getTag();
                    if (answer.buttonState == ButtonState.UNSELECTED) {
                        v.setBackground(getResources().getDrawable(R.drawable.ic_rounded_rectangle_select));
                        answer.buttonState = ButtonState.SELECTED;
                        answer.letter = ((Button) v).getText().toString();
                        answer.index = ++characterPosition;
                        v.setTag(answer);
                        textViewArrayList.get(characterPosition).setText(answer.letter);
                        buttonArrayList.add((Button) v);
                        lblIndex1.setText(String.valueOf(answer.index + 1));
                    }

                    break;
                case R.id.btn2:
                    answer = (Answer) v.getTag();
                    if (answer.buttonState == ButtonState.UNSELECTED) {
                        v.setBackground(getResources().getDrawable(R.drawable.ic_rounded_rectangle_select));
                        answer.buttonState = ButtonState.SELECTED;
                        answer.letter = ((Button) v).getText().toString();
                        answer.index = ++characterPosition;
                        v.setTag(answer);
                        textViewArrayList.get(characterPosition).setText(answer.letter);
                        buttonArrayList.add((Button) v);
                        lblIndex2.setText(String.valueOf(answer.index + 1));
                    }

                    break;
                case R.id.btn3:
                    answer = (Answer) v.getTag();
                    if (answer.buttonState == ButtonState.UNSELECTED) {
                        v.setBackground(getResources().getDrawable(R.drawable.ic_rounded_rectangle_select));
                        answer.buttonState = ButtonState.SELECTED;
                        answer.letter = ((Button) v).getText().toString();
                        answer.index = ++characterPosition;
                        v.setTag(answer);
                        textViewArrayList.get(characterPosition).setText(answer.letter);
                        buttonArrayList.add((Button) v);
                        lblIndex3.setText(String.valueOf(answer.index + 1));
                    }

                    break;
                case R.id.btn4:
                    answer = (Answer) v.getTag();
                    if (answer.buttonState == ButtonState.UNSELECTED) {
                        v.setBackground(getResources().getDrawable(R.drawable.ic_rounded_rectangle_select));
                        answer.buttonState = ButtonState.SELECTED;
                        answer.letter = ((Button) v).getText().toString();
                        answer.index = ++characterPosition;
                        v.setTag(answer);
                        textViewArrayList.get(characterPosition).setText(answer.letter);
                        buttonArrayList.add((Button) v);
                        lblIndex4.setText(String.valueOf(answer.index + 1));
                    }
                    break;
            }
            evaluateResult();
        }
    };
    private View.OnClickListener onBackSpaceButtonClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (characterPosition != -1) {
                textViewArrayList.get(characterPosition).setText("");
                Button btn = buttonArrayList.get(characterPosition);
                btn.setBackground(getResources().getDrawable(R.drawable.ic_rounded_rectangle_unselected));
                btn.setTag(new Answer());
                buttonArrayList.remove(characterPosition);
                characterPosition--;
            }
        }
    };
    private View.OnClickListener onNextButtonClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (mListener != null) {
                mListener.onFragmentInteraction(scoreData);
            }
        }
    };
    private View.OnClickListener onSkipButtonClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (mListener != null) {
                if (subscription != null && !subscription.isUnsubscribed()) {
                    subscription.unsubscribe();
                }
                scoreData.resultState = ResultState.SKIPPED;
                mListener.onFragmentInteraction(scoreData);
            }
        }
    };

    public QuestionFragment() {
    }

    static QuestionFragment newInstance(ScoreData param1) {
        QuestionFragment fragment = new QuestionFragment();
        Bundle args = new Bundle();
        args.putParcelable(ARG_RESULT, param1);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            scoreData = getArguments().getParcelable(ARG_RESULT);
        }
        characterPosition = -1;
        textViewArrayList = new ArrayList<>();
        buttonArrayList = new ArrayList<>();

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_question, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        lblCountDownTimer = view.findViewById(R.id.lblCountDownTimer);
        viewRight = view.findViewById(R.id.animation_view_right);
        viewWrong = view.findViewById(R.id.animation_view_wrong);

        lblanswer1 = view.findViewById(R.id.lbl1);
        lblanswer2 = view.findViewById(R.id.lbl2);
        lblanswer3 = view.findViewById(R.id.lbl3);
        lblanswer4 = view.findViewById(R.id.lbl4);

        textViewArrayList.add(lblanswer1);
        textViewArrayList.add(lblanswer2);
        textViewArrayList.add(lblanswer3);
        textViewArrayList.add(lblanswer4);


        lblIndex1 = view.findViewById(R.id.lblindex1);
        lblIndex2 = view.findViewById(R.id.lblindex2);
        lblIndex3 = view.findViewById(R.id.lblindex3);
        lblIndex4 = view.findViewById(R.id.lblindex4);

        lblQuestionNo = view.findViewById(R.id.lblQuestion);

        btnSuggestion1 = view.findViewById(R.id.btn1);
        btnSuggestion2 = view.findViewById(R.id.btn2);
        btnSuggestion3 = view.findViewById(R.id.btn3);
        btnSuggestion4 = view.findViewById(R.id.btn4);

        btnSuggestion1.setTag(new Answer());
        btnSuggestion2.setTag(new Answer());
        btnSuggestion3.setTag(new Answer());
        btnSuggestion4.setTag(new Answer());

        btnSuggestion1.setOnClickListener(onCharacterButtonClickListner);
        btnSuggestion2.setOnClickListener(onCharacterButtonClickListner);
        btnSuggestion3.setOnClickListener(onCharacterButtonClickListner);
        btnSuggestion4.setOnClickListener(onCharacterButtonClickListner);

        btnNext = view.findViewById(R.id.btnNext);
        btnNext.setOnClickListener(onNextButtonClickListener);

        btnSkip = view.findViewById(R.id.btnSkip);
        btnSkip.setOnClickListener(onSkipButtonClickListener);

        ImageButton btnBackSpace = view.findViewById(R.id.btnBackspace);
        btnBackSpace.setOnClickListener(onBackSpaceButtonClickListener);

        setupQuestion(shuffleString(scoreData.questionString));

    }

    private void setupQuestion(char[] jumbledQue) {
        btnSuggestion1.setText(String.valueOf(jumbledQue[0]));
        btnSuggestion2.setText(String.valueOf(jumbledQue[1]));
        btnSuggestion3.setText(String.valueOf(jumbledQue[2]));
        btnSuggestion4.setText(String.valueOf(jumbledQue[3]));

        lblQuestionNo.setText(String.format(Locale.US, "Word Number - %d", scoreData.questionNo + 1));

        startTimer();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    private char[] shuffleString(String input) {
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

    void setNextQuestion(ScoreData scoreData) {
        this.scoreData = scoreData;
        setupQuestion(shuffleString(this.scoreData.questionString));

        characterPosition = -1;

        btnSuggestion1.setTag(new Answer());
        btnSuggestion2.setTag(new Answer());
        btnSuggestion3.setTag(new Answer());
        btnSuggestion4.setTag(new Answer());
        btnSuggestion1.setBackground(getResources().getDrawable(R.drawable.ic_rounded_rectangle_unselected));
        btnSuggestion2.setBackground(getResources().getDrawable(R.drawable.ic_rounded_rectangle_unselected));
        btnSuggestion3.setBackground(getResources().getDrawable(R.drawable.ic_rounded_rectangle_unselected));
        btnSuggestion4.setBackground(getResources().getDrawable(R.drawable.ic_rounded_rectangle_unselected));

        buttonArrayList.clear();

        for (TextView tx : textViewArrayList) {
            tx.setText("");
        }
        lblIndex1.setText("");
        lblIndex2.setText("");
        lblIndex3.setText("");
        lblIndex4.setText("");
        viewRight.setVisibility(View.INVISIBLE);
        viewWrong.setVisibility(View.INVISIBLE);
        btnSkip.setEnabled(true);
    }

    private void startTimer() {
        btnNext.setEnabled(false);

        if (subscription != null && !subscription.isUnsubscribed()) {
            subscription.unsubscribe();
        }
        lblCountDownTimer.setText(getString(R.string.time_minutes));
        Observable<Long> timerObservable = Observable.interval(1, 1, TimeUnit.SECONDS)
                .take(15)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io());

        subscription = timerObservable.subscribe(new Observer<Long>() {

            @Override
            public void onNext(Long aLong) {
                Log.d("wordgame", "onNext: ");
                lblCountDownTimer.setText(String.format(Locale.US, "00:%02d", aLong.intValue() + 1));
            }

            @Override
            public void onCompleted() {
                Toast.makeText(getActivity(), "Timeout !!!", Toast.LENGTH_SHORT).show();
                btnNext.setEnabled(true);
                evaluateResult();
                if (mListener != null) {
                    mListener.onFragmentInteraction(scoreData);
                }
            }

            @Override
            public void onError(Throwable e) {

            }
        });
    }

    private void evaluateResult() {
        if (subscription != null) {
            subscription.unsubscribe();
        }
        if (characterPosition == 3) {
            btnNext.setEnabled(true);
            scoreData.time = String.valueOf(lblCountDownTimer.getText());
            StringBuilder resultAnswerBuilder = new StringBuilder();
            for (TextView tx : textViewArrayList) {
                resultAnswerBuilder.append(tx.getText());
            }
            String resultAnswer = resultAnswerBuilder.toString();
            scoreData.resultString = resultAnswer;
            if (resultAnswer.equals(scoreData.questionString)) {
                scoreData.resultState = ResultState.RIGHT;
                viewRight.setVisibility(View.VISIBLE);
                viewWrong.setVisibility(View.INVISIBLE);
            } else {
                scoreData.resultState = ResultState.WRONG;
                viewRight.setVisibility(View.INVISIBLE);
                viewWrong.setVisibility(View.VISIBLE);
            }
            btnSkip.setEnabled(false);
        } else {
            scoreData.resultState = ResultState.TIMEOUT;
            viewRight.setVisibility(View.INVISIBLE);
            viewWrong.setVisibility(View.INVISIBLE);
        }
    }

    public interface OnFragmentInteractionListener {
        void onFragmentInteraction(ScoreData scoreData);
    }
}
