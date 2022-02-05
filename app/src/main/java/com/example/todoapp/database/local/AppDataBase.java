package com.example.todoapp.database.local;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.example.todoapp.models.Aboba;

@Database(entities = {Aboba.class },version = 3)
public  abstract class AppDataBase  extends RoomDatabase {
    public abstract TaskDao taskDao();

//    public static AppDataBase getInstance(){
//
//    }



}
