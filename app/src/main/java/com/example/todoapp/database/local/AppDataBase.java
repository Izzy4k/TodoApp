package com.example.todoapp.database.local;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.example.todoapp.models.Task;

@Database(entities = {Task.class },version = 1)
public  abstract class AppDataBase  extends RoomDatabase {
    public abstract TaskDao taskDao();

//    public static AppDataBase getInstance(){
//
//    }



}
