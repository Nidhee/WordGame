package com.example.wordgame;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.util.Log;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.util.ArrayList;

public class HomeActivity extends AppCompatActivity implements QuestionFragment.OnFragmentInteractionListener {
    ArrayList<String> questionArrayList;
    LinearLayout fragmentContainer;
    int currentQuestion;
    FragmentManager fragmentManager;
    QuestionFragment questionFragment;
    ArrayList<Result> resultArrayList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        fragmentContainer = findViewById(R.id.llFragmentContainer);
        questionArrayList = new ArrayList<>();
        questionArrayList.add("NINE");
        questionArrayList.add("BALL");
        questionArrayList.add("DATE");

        fragmentManager = getSupportFragmentManager();
        currentQuestion = 0;
        addNewQuestion(questionArrayList.get(currentQuestion), currentQuestion);
        resultArrayList = new ArrayList<>();

        Toolbar mActionBarToolbar = findViewById(R.id.toolbarHome);
        mActionBarToolbar.setTitle("Word Game");
        mActionBarToolbar.setTitleTextColor(getResources().getColor(android.R.color.white));
        setSupportActionBar(mActionBarToolbar);
    }

    private void addNewQuestion(String questionString, int questionNo) {

        Result result = new Result();
        result.questionString = questionString;
        result.questionNo = questionNo;

        if (fragmentManager.findFragmentByTag("questionFrag") == null) {
            questionFragment = QuestionFragment.newInstance(result);
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.add(R.id.llFragmentContainer, questionFragment, "questionFrag");
            fragmentTransaction.commit();

        } else {
            questionFragment = (QuestionFragment) fragmentManager.findFragmentByTag("questionFrag");
            questionFragment.setNextQuestion(result);
        }
    }


    @Override
    public void onFragmentInteraction(Result result) {
        Log.e("WordGame", "result " + result.resultState
                + " quesno " + result.questionNo);
        resultArrayList.add(result);
        if (currentQuestion < questionArrayList.size() - 1) {
            currentQuestion++;
            addNewQuestion(questionArrayList.get(currentQuestion), currentQuestion);
        } else {

            // Navigate to result screen
            Toast.makeText(getApplicationContext(), "End of the show !!! ", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(getApplicationContext(),ResultActivity.class);
            Bundle bundle = new Bundle();
            bundle.putParcelableArrayList("ARG_RESULT",resultArrayList);
            intent.putExtras(bundle);
            finish();
            startActivity(intent);
        }
    }

}
