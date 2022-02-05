package com.example.todoapp.models;

import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import com.google.firebase.firestore.Exclude;

import java.io.Serializable;

@Entity
public class Aboba implements Serializable {
    @PrimaryKey(autoGenerate = true)
    private int id;
    private String title;
    @Exclude private String idFire;

    public Aboba(String title) {
        this.title = title;
    }
    @Ignore
    public Aboba() {

    }

    public String getIdFire() {
        return idFire;
    }

    public void setIdFire(String idFire) {
        this.idFire = idFire;
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
