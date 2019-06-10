package com.example.wordgame.di;

import com.example.wordgame.HomePresenter;
import com.example.wordgame.QuestionPresenter;
import com.example.wordgame.ResultPresenter;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public
class WordGameModule {

    @Provides
    QuestionPresenter providesQuestionPresenter() {
        return new QuestionPresenter();
    }

    @Provides
    HomePresenter providesHomePresenter() {
        return new HomePresenter();
    }

    @Provides
    ResultPresenter providesResultPresenter() {
        return new ResultPresenter();
    }
}
