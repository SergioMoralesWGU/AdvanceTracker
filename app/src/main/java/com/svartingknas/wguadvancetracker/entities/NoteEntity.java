package com.svartingknas.wguadvancetracker.entities;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "notes_table")
public class NoteEntity {
    @PrimaryKey
    private int id;

//    @NonNull
//    @ColumnInfo(name = "note_title")
    private String noteTitle;

//    @NonNull
//    @ColumnInfo(name = "note_text")
    private String noteText;

//    @NonNull
//    @ColumnInfo(name = "note_courseId")
    private int noteCourseId;

    public NoteEntity(int id, @NonNull String noteTitle, @NonNull String noteText, int noteCourseId) {
        this.id = id;
        this.noteTitle = noteTitle;
        this.noteText = noteText;
        this.noteCourseId = noteCourseId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @NonNull
    public String getNoteTitle() {
        return noteTitle;
    }

    public void setNoteTitle(@NonNull String noteTitle) {
        this.noteTitle = noteTitle;
    }

    @NonNull
    public String getNoteText() {
        return noteText;
    }

    public void setNoteText(@NonNull String noteText) {
        this.noteText = noteText;
    }

    @NonNull
    public int getNoteCourseId() {
        return noteCourseId;
    }

    public void setNoteCourseId( int noteCourseId) {
        this.noteCourseId = noteCourseId;
    }
}