package com.example.todoapp;

import android.app.Application;

import androidx.room.Room;

import com.example.todoapp.database.local.AppDataBase;
import com.example.todoapp.utils.Prefs;

public class App extends Application {
    public static AppDataBase dataBase ;
    @Override
    public void onCreate() {
        super.onCreate();
        new Prefs(this);
        dataBase = Room.databaseBuilder(getApplicationContext(),AppDataBase.class,"database")
                .fallbackToDestructiveMigration()
                .allowMainThreadQueries()
                .build();
    }
}
