package com.google.memorynotes.model;


import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;

//we're going to use notes as table so we'll use annotation @entity
@Entity(tableName = "notes")
//to move one activity to another use serializable
public class Notes implements Serializable {
    @PrimaryKey(autoGenerate = true)    //autogenrated will autometically generate a new item when we added a new item
    int id =0;

    @ColumnInfo(name="title")
    String title = "";

    @ColumnInfo(name="notes")
    String notes ="";

    @ColumnInfo(name="date")
    String date = "";

    @ColumnInfo(name="pinned")
    boolean pinned = false;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public boolean isPinned() {
        return pinned;
    }

    public void setPinned(boolean pinned) {
        this.pinned = pinned;
    }
}
