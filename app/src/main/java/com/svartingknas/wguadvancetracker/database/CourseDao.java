package com.svartingknas.wguadvancetracker.database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.svartingknas.wguadvancetracker.entities.CourseEntity;

import java.util.List;

@Dao
public interface CourseDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(CourseEntity course);

    @Delete
    void delete(CourseEntity course);

    @Query("DELETE FROM courses_table")
    void deleteAllCourses();

    @Query("DELETE FROM courses_table WHERE id = :courseId")
    void deleteCourseById(int courseId);

    @Query("SELECT * FROM courses_table ORDER BY id DESC")
    LiveData<List<CourseEntity>> getAllCourses();

    @Query("SELECT * FROM courses_table WHERE courseTermId= :course_termId ORDER BY id ASC")
    LiveData<List<CourseEntity>> getAssociatedCourses(int course_termId);
}