package com.example.wordgame;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.widget.TextView;

import java.util.ArrayList;

public class ResultActivity extends AppCompatActivity {

    TextView lblScore,lblDetails;
    ArrayList<Result> results;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);
        lblScore = findViewById(R.id.lblScore);
        lblDetails = findViewById(R.id.lbldetails);

        Toolbar mActionBarToolbar = findViewById(R.id.toolbarResult);
        mActionBarToolbar.setTitle("Results");
        mActionBarToolbar.setTitleTextColor(getResources().getColor(android.R.color.white));
        setSupportActionBar(mActionBarToolbar);

        Bundle bundle = getIntent().getExtras();
        results = bundle.getParcelableArrayList("ARG_RESULT");
        calculateResult();
    }
    private void calculateResult(){
        int totalScore = 0;
        int totalSkipped = 0;
        int totalWrong = 0;
        int totalTimedOut = 0;
        for(Result result : results){
            switch(result.resultState){
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
        String score = "SCORE - " + totalScore +" / " + results.size();
        String details = "Skipped - " + totalSkipped + "\n" +
        "Timed out - " + totalTimedOut + "\n" +
                "Wrong - " + totalWrong + "\n";

        lblScore.setText(score);
        lblDetails.setText(details);
    }
}
