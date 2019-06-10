package com.example.wordgame.di;

import com.example.wordgame.HomeActivity;
import com.example.wordgame.QuestionFragment;
import com.example.wordgame.ResultActivity;

import dagger.Component;

@Component(modules = WordGameModule.class)
public interface WordGameComponent {

    void inject(QuestionFragment questionFragment);
    void inject(HomeActivity homeActivity);
    void inject(ResultActivity resultActivity);
}
