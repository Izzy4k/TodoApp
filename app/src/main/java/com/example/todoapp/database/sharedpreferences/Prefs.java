package com.example.todoapp.database.sharedpreferences;

import android.content.Context;
import android.content.SharedPreferences;

public class Prefs {
    private SharedPreferences preferences;
    public static Prefs prefs;

    public Prefs(Context context) {
        prefs = this;
        preferences = context.getSharedPreferences("aboba", Context.MODE_PRIVATE);
    }

    public void saveState() {
        preferences.edit().putBoolean("show", true).apply();
    }

    public boolean isBoardShow() {
        return preferences.getBoolean("show", false);
    }

    public static Prefs getPrefs() {
        return prefs;
    }
}
