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

import androidx.fragment.app.Fragment;

import com.airbnb.lottie.LottieAnimationView;
import com.example.wordgame.data.Answer;
import com.example.wordgame.data.ButtonState;
import com.example.wordgame.data.ResultState;
import com.example.wordgame.data.ScoreData;

import java.util.ArrayList;
import java.util.Locale;

import javax.inject.Inject;

import rx.Observer;
import rx.Subscription;

public class QuestionFragment extends Fragment {

    @Inject
    QuestionPresenter questionPresenter;

    private int characterPosition;
    private ArrayList<TextView> textViewArrayList;
    private ArrayList<Button> buttonArrayList;
    static final String TAG = "questionFrag";
    private static final String ARG_RESULT = "scoreData";

    private TextView lblCountDownTimer, lblanswer1, lblanswer2, lblanswer3, lblanswer4,
            lblIndex1, lblIndex2, lblIndex3, lblIndex4, lblQuestionNo;

    private Button btnSuggestion1, btnSuggestion2, btnSuggestion3, btnSuggestion4, btnNext, btnSkip;
    private LottieAnimationView viewRight, viewWrong;

    private Subscription subscription;
    private OnFragmentInteractionListener mListener;

    private View.OnClickListener onCharacterButtonClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View button) {
            Answer answer;
            switch (button.getId()) {
                case R.id.btn1:
                    answer = (Answer) button.getTag();
                    if (answer.buttonState == ButtonState.UNSELECTED) {
                        updateAnswer(button, answer);
                        button.setBackground(getResources().getDrawable(R.drawable.ic_rounded_rectangle_select));
                    }
                    break;
                case R.id.btn2:
                    answer = (Answer) button.getTag();
                    if (answer.buttonState == ButtonState.UNSELECTED) {
                        updateAnswer(button, answer);
                        lblIndex2.setText(String.valueOf(answer.index + 1));
                    }
                    break;
                case R.id.btn3:
                    answer = (Answer) button.getTag();
                    if (answer.buttonState == ButtonState.UNSELECTED) {
                        updateAnswer(button, answer);
                        lblIndex3.setText(String.valueOf(answer.index + 1));
                    }
                    break;
                case R.id.btn4:
                    answer = (Answer) button.getTag();
                    if (answer.buttonState == ButtonState.UNSELECTED) {
                        updateAnswer(button, answer);
                        lblIndex4.setText(String.valueOf(answer.index + 1));
                    }
                    break;
            }
            evaluateResult();
        }
    };

    // update answer object according to button
    private void updateAnswer(View button, Answer answer) {
        button.setBackground(getResources().getDrawable(R.drawable.ic_rounded_rectangle_select));
        answer.buttonState = ButtonState.SELECTED;
        answer.letter = ((Button) button).getText().toString();
        answer.index = ++characterPosition;
        button.setTag(answer);
        textViewArrayList.get(characterPosition).setText(answer.letter);
        buttonArrayList.add((Button) button);
    }

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
                mListener.onFragmentInteraction(questionPresenter.getScoreData());
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
                questionPresenter.setResultState(ResultState.SKIPPED);
                mListener.onFragmentInteraction(questionPresenter.getScoreData());
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
        setRetainInstance(true);
        if (getArguments() != null) {
            questionPresenter.setScoreData((ScoreData) getArguments().getParcelable(ARG_RESULT));
        }

        characterPosition = -1;
        textViewArrayList = new ArrayList<>();
        buttonArrayList = new ArrayList<>();

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.e(TAG, "onCreateView: ");
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_question, container, false);

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

        btnSuggestion1.setOnClickListener(onCharacterButtonClickListener);
        btnSuggestion2.setOnClickListener(onCharacterButtonClickListener);
        btnSuggestion3.setOnClickListener(onCharacterButtonClickListener);
        btnSuggestion4.setOnClickListener(onCharacterButtonClickListener);

        btnNext = view.findViewById(R.id.btnNext);
        btnNext.setOnClickListener(onNextButtonClickListener);

        btnSkip = view.findViewById(R.id.btnSkip);
        btnSkip.setOnClickListener(onSkipButtonClickListener);

        ImageButton btnBackSpace = view.findViewById(R.id.btnBackspace);
        btnBackSpace.setOnClickListener(onBackSpaceButtonClickListener);

        setupQuestion(questionPresenter.shuffleString(questionPresenter.getQuestionString()));

        return view;
    }

    private void setupQuestion(char[] jumbledQue) {
        btnSuggestion1.setText(String.valueOf(jumbledQue[0]));
        btnSuggestion2.setText(String.valueOf(jumbledQue[1]));
        btnSuggestion3.setText(String.valueOf(jumbledQue[2]));
        btnSuggestion4.setText(String.valueOf(jumbledQue[3]));

        lblQuestionNo.setText(String.format(Locale.US, "Word Number - %d", questionPresenter.getQuestionNo() + 1));

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
        WordGameApplication.getWordGameComponent().inject(this);
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }


    void setNextQuestion(ScoreData scoreData) {
        questionPresenter.setScoreData(scoreData);
        setupQuestion(questionPresenter.shuffleString(questionPresenter.getQuestionString()));

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
        subscription = questionPresenter.getTimerObservable().subscribe(new Observer<Long>() {
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
                    mListener.onFragmentInteraction(questionPresenter.getScoreData());
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
            questionPresenter.setTime(String.valueOf(lblCountDownTimer.getText()));
            StringBuilder resultAnswerBuilder = new StringBuilder();
            for (TextView tx : textViewArrayList) {
                resultAnswerBuilder.append(tx.getText());
            }
            String resultAnswer = resultAnswerBuilder.toString();
            questionPresenter.setResultString(resultAnswer);
            if (resultAnswer.equals(questionPresenter.getQuestionString())) {
                questionPresenter.setResultState(ResultState.RIGHT);
                viewRight.setVisibility(View.VISIBLE);
                viewWrong.setVisibility(View.INVISIBLE);
            } else {
                questionPresenter.setResultState(ResultState.WRONG);
                viewRight.setVisibility(View.INVISIBLE);
                viewWrong.setVisibility(View.VISIBLE);
            }
            btnSkip.setEnabled(false);
        } else {
            questionPresenter.setResultState(ResultState.TIMEOUT);
            viewRight.setVisibility(View.INVISIBLE);
            viewWrong.setVisibility(View.INVISIBLE);
        }
    }

    public interface OnFragmentInteractionListener {
        void onFragmentInteraction(ScoreData scoreData);
    }


}
