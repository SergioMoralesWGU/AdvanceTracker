package com.svartingknas.wguadvancetracker.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.svartingknas.wguadvancetracker.database.InventoryManagementRepository;
import com.svartingknas.wguadvancetracker.entities.AssessmentEntity;

import java.util.List;

public class AssessmentViewModel extends AndroidViewModel {
    int assessmentCourseId;
    private InventoryManagementRepository mRepository;
    private LiveData<List<AssessmentEntity>> allAssessments;
    private LiveData<List<AssessmentEntity>> associatedAssessments;

    public AssessmentViewModel(@NonNull Application application, int assessmentCourseId) {
        super(application);
        mRepository = new InventoryManagementRepository(application);
        associatedAssessments = mRepository.getAssociatedAssessments(assessmentCourseId);
    }
    public AssessmentViewModel(@NonNull Application application) {
        super(application);
        mRepository = new InventoryManagementRepository(application);
        allAssessments = mRepository.getAllAssessments();
        associatedAssessments = mRepository.getAssociatedAssessments(assessmentCourseId);
    }
    public LiveData<List<AssessmentEntity>> getAllAssessments(){
        return allAssessments;
    }
    public LiveData<List<AssessmentEntity>> getAssociatedAssessments(int assessmentCourseId){
        return mRepository.getAssociatedAssessments(assessmentCourseId);
    }
    public void insert(AssessmentEntity assessmentEntity){
        mRepository.insert(assessmentEntity);
    }

    public void delete(AssessmentEntity assessmentEntity){
        mRepository.delete(assessmentEntity);
    }
    public int lastID(){
        if (allAssessments.getValue() == null){
            return 0;
        }
        int size = allAssessments.getValue().size();
        AssessmentEntity lastAssessment = allAssessments.getValue().get(size-1);
        if (lastAssessment == null){
            return 0;
        }
        return lastAssessment.getId();
    }
}
