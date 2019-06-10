package com.example.wordgame;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.widget.TextView;

import com.example.wordgame.data.ScoreData;

import java.util.ArrayList;

import javax.inject.Inject;

public class ResultActivity extends AppCompatActivity {

    TextView lblScore,lblDetails;

    @Inject
    ResultPresenter resultPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);
        WordGameApplication.getWordGameComponent().inject(this);

        lblScore = findViewById(R.id.lblScore);
        lblDetails = findViewById(R.id.lbldetails);

        Toolbar mActionBarToolbar = findViewById(R.id.toolbarResult);
        mActionBarToolbar.setTitle(getString(R.string.str_result));
        mActionBarToolbar.setTitleTextColor(getResources().getColor(android.R.color.white));
        setSupportActionBar(mActionBarToolbar);

        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            ArrayList<ScoreData> scoreData = bundle.getParcelableArrayList("ARG_RESULT");
            resultPresenter.setScoreData(scoreData);
        }
        updateResultViews();
    }
    private void updateResultViews(){

        resultPresenter.calculateResult();
        String score = "SCORE - " + resultPresenter.getTotalScore() +" / " + resultPresenter.getTotalQuestion();
        String details = "Skipped - " + resultPresenter.getTotalSkipped() + "\n" +
        "Timed out - " + resultPresenter.getTotalTimedOut() + "\n" +
                "Wrong - " + resultPresenter.getTotalWrong() + "\n";

        lblScore.setText(score);
        lblDetails.setText(details);
    }
}
