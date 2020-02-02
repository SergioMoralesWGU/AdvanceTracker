package com.svartingknas.wguadvancetracker.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.svartingknas.wguadvancetracker.database.InventoryManagementRepository;
import com.svartingknas.wguadvancetracker.entities.TermEntity;

import java.util.List;

public class TermViewModel extends AndroidViewModel {
    private InventoryManagementRepository mRepository;
    private LiveData<List<TermEntity>> allTerms;

    public TermViewModel(@NonNull Application application) {
        super(application);
        mRepository = new InventoryManagementRepository(application);
        allTerms = mRepository.getAllTerms();
    }
    public LiveData<List<TermEntity>> getAllTerms(){
        return allTerms;
    }
    public void insert(TermEntity termEntity){
        mRepository.insert(termEntity);
    }
    public void delete(TermEntity termEntity){
        mRepository.delete(termEntity);
    }

    public int lastID(){
        return allTerms.getValue().size();
    }
}
