package com.svartingknas.wguadvancetracker.database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.svartingknas.wguadvancetracker.entities.TermEntity;

import java.util.List;

@Dao
public interface TermDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert (TermEntity term);

    @Query("DELETE FROM terms_table")
    void deleteAllTerms();

    @Delete
    void delete(TermEntity term);

    @Query("SELECT * FROM terms_table ORDER BY id DESC")
    LiveData<List<TermEntity>> getAllTerms();

    @Query("SELECT * FROM terms_table WHERE id = :id")
    public TermEntity loadTerm(int id);





}

