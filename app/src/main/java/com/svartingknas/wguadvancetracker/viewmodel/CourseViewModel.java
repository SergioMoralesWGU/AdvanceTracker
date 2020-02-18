package com.svartingknas.wguadvancetracker.viewmodel;

import android.app.Application;
import android.content.Intent;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.svartingknas.wguadvancetracker.database.InventoryManagementRepository;
import com.svartingknas.wguadvancetracker.entities.CourseEntity;

import java.util.List;

public class CourseViewModel extends AndroidViewModel {
    int courseTermId;
    private static Integer lastId;
    private InventoryManagementRepository mRepository;
    private LiveData<List<CourseEntity>> associatedCourses;
    private LiveData<List<CourseEntity>> allCourses;

    public CourseViewModel(@NonNull Application application, int courseTermId) {
        super(application);
        mRepository = new InventoryManagementRepository(application);
        associatedCourses = mRepository.getAssociatedCourses(courseTermId);
    }
    public CourseViewModel(@NonNull Application application) {
        super(application);
        mRepository = new InventoryManagementRepository(application);
        allCourses = mRepository.getAllCourses();
        associatedCourses = mRepository.getAssociatedCourses(courseTermId);
    }

    public LiveData<List<CourseEntity>> getAllCourses(){
        return allCourses;
    }

    public LiveData<List<CourseEntity>> getAssociatedCourses(int courseTermId){
        return mRepository.getAssociatedCourses(courseTermId);
    }

    public void insert(CourseEntity courseEntity){
        mRepository.insert(courseEntity);
    }

    public void delete(CourseEntity courseEntity){
        mRepository.delete(courseEntity);
    }

    public void deleteCourseById(int courseId){
        mRepository.deleteCourseById(courseId);
    }

    public int lastID(){
        return InventoryManagementRepository.getLastCourseId();

    }
}
