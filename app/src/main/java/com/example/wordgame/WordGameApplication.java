package com.example.wordgame;

import android.app.Application;

import com.example.wordgame.di.DaggerWordGameComponent;
import com.example.wordgame.di.WordGameComponent;
import com.example.wordgame.di.WordGameModule;

public class WordGameApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
    }

    public static WordGameComponent getWordGameComponent() {
        return DaggerWordGameComponent.builder()
                .wordGameModule(new WordGameModule())
                .build();
    }
}
