package com.example.wordgame;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.util.Log;
import android.widget.LinearLayout;

import com.example.wordgame.data.ScoreData;

import java.util.ArrayList;

public class HomeActivity extends AppCompatActivity implements QuestionFragment.OnFragmentInteractionListener {
    ArrayList<String> questionArrayList;
    LinearLayout fragmentContainer;
    int currentQuestion;
    FragmentManager fragmentManager;
    QuestionFragment questionFragment;
    ArrayList<ScoreData> scoreDataArrayList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        fragmentContainer = findViewById(R.id.llFragmentContainer);
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

        fragmentManager = getSupportFragmentManager();
        currentQuestion = 0;
        addNewQuestion(questionArrayList.get(currentQuestion), currentQuestion);
        scoreDataArrayList = new ArrayList<>();

        Toolbar mActionBarToolbar = findViewById(R.id.toolbarHome);
        mActionBarToolbar.setTitle(getString(R.string.str_game));
        mActionBarToolbar.setTitleTextColor(getResources().getColor(android.R.color.white));
        setSupportActionBar(mActionBarToolbar);
    }

    private void addNewQuestion(String questionString, int questionNo) {

        ScoreData scoreData = new ScoreData();
        scoreData.questionString = questionString;
        scoreData.questionNo = questionNo;

        if (fragmentManager.findFragmentByTag("questionFrag") == null) {
            questionFragment = QuestionFragment.newInstance(scoreData);
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.add(R.id.llFragmentContainer, questionFragment, "questionFrag");
            fragmentTransaction.commit();

        } else {
            questionFragment = (QuestionFragment) fragmentManager.findFragmentByTag("questionFrag");
            questionFragment.setNextQuestion(scoreData);
        }
    }


    @Override
    public void onFragmentInteraction(ScoreData scoreData) {
        Log.e("WordGame", "scoreData " + scoreData.resultState
                + " quesno " + scoreData.questionNo);
        scoreDataArrayList.add(scoreData);
        if (currentQuestion < questionArrayList.size() - 1) {
            currentQuestion++;
            addNewQuestion(questionArrayList.get(currentQuestion), currentQuestion);
        } else {

            // Navigate to scoreData screen
            Intent intent = new Intent(getApplicationContext(),ResultActivity.class);
            Bundle bundle = new Bundle();
            bundle.putParcelableArrayList("ARG_RESULT", scoreDataArrayList);
            intent.putExtras(bundle);
            finish();
            startActivity(intent);
        }
    }

}
