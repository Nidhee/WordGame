package com.example.wordgame;

import android.content.Intent;
import android.os.Bundle;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.wordgame.data.ScoreData;

import javax.inject.Inject;

public class HomeActivity extends AppCompatActivity implements QuestionFragment.OnFragmentInteractionListener {
    @Inject
    HomePresenter homePresenter;

    LinearLayout fragmentContainer;
    FragmentManager fragmentManager;
    QuestionFragment questionFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        WordGameApplication.getWordGameComponent().inject(this);

        // set tool bar
        Toolbar mActionBarToolbar = findViewById(R.id.toolbarHome);
        mActionBarToolbar.setTitle(getString(R.string.str_game));
        mActionBarToolbar.setTitleTextColor(getResources().getColor(android.R.color.white));
        setSupportActionBar(mActionBarToolbar);

        fragmentContainer = findViewById(R.id.llFragmentContainer);
        fragmentManager = getSupportFragmentManager();

        addNewQuestion(homePresenter.getNextQuestion(), homePresenter.getCurrentQuestionNumber());
    }

    private void addNewQuestion(String questionString, int questionNo) {

        ScoreData scoreData = homePresenter.getScoreData(questionNo, questionString);
        if (fragmentManager.findFragmentByTag(QuestionFragment.TAG) == null) {
            questionFragment = QuestionFragment.newInstance(scoreData);

            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.add(R.id.llFragmentContainer, questionFragment, QuestionFragment.TAG);
            fragmentTransaction.commit();

        } else {
            questionFragment = (QuestionFragment) fragmentManager.findFragmentByTag(QuestionFragment.TAG);
            if (questionFragment != null && questionFragment.isAdded()) {
                questionFragment.setNextQuestion(scoreData);
            }
        }
    }


    @Override
    public void onFragmentInteraction(ScoreData scoreData) {
        // save score data of last question
        homePresenter.collectScoreData(scoreData);

        // check if has more word, true then show next question else navigate to result string
        if (homePresenter.hasMoreWord()) {
            addNewQuestion(homePresenter.getNextQuestion(), homePresenter.getCurrentQuestionNumber());
        } else {
            // Navigate to scoreData screen
            Intent intent = new Intent(getApplicationContext(), ResultActivity.class);
            Bundle bundle = new Bundle();
            bundle.putParcelableArrayList("ARG_RESULT", homePresenter.getAllResult());
            intent.putExtras(bundle);
            finish();
            startActivity(intent);
        }
    }
}