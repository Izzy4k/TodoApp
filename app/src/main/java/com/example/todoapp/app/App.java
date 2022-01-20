package com.example.todoapp.app;

import android.app.Application;

import com.example.todoapp.database.sharedpreferences.Prefs;

public class App extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        new Prefs(this);
    }
}
