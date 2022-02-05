package com.example.todoapp.database.local;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.todoapp.models.Aboba;

import java.util.List;

@Dao
public interface TaskDao {
    @Query("SELECT * FROM Aboba")
    List<Aboba> getAllTasks();
    @Insert
    void addTask(Aboba aboba);

    @Update
    void update(Aboba aboba);

    @Delete
    void delete (Aboba aboba);
}
