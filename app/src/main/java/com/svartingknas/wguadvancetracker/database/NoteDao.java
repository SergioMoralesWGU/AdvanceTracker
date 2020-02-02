package com.svartingknas.wguadvancetracker.database;

import android.provider.ContactsContract;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.svartingknas.wguadvancetracker.entities.NoteEntity;

import java.util.List;

@Dao
public interface NoteDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert (NoteEntity note);

    @Query("DELETE FROM notes_table")
    void deleteAllNotes();

    @Delete
    void delete(NoteEntity note);

    //    Get all notes associated with course
    @Query ("SELECT * FROM notes_table WHERE id= :note_courseId ORDER BY id ASC")
    LiveData<List<NoteEntity>> getAssociatedNotes(int note_courseId);

    @Query("SELECT * FROM notes_table ORDER BY id DESC")
    LiveData<List<NoteEntity>> getAllNotes();

}
