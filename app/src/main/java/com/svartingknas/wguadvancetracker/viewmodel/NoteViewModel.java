package com.svartingknas.wguadvancetracker.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.svartingknas.wguadvancetracker.database.InventoryManagementRepository;
import com.svartingknas.wguadvancetracker.entities.NoteEntity;

import java.util.List;

public class NoteViewModel extends AndroidViewModel {
    int noteCourseId;
    private InventoryManagementRepository mRepository;
    private LiveData<List<NoteEntity>> associatedNotes;
    private LiveData<List<NoteEntity>> allNotes;


    public NoteViewModel(@NonNull Application application, int noteCourseId) {
        super(application);
        mRepository = new InventoryManagementRepository(application);
        associatedNotes = mRepository.getAssociatedNotes(noteCourseId);
    }
    public NoteViewModel(@NonNull Application application) {
        super(application);
        mRepository = new InventoryManagementRepository(application);
        allNotes = mRepository.getAllNotes();
        associatedNotes = mRepository.getAssociatedNotes(noteCourseId);
    }

    public LiveData<List<NoteEntity>> getAssociatedNotes(int noteCourseId){
        return mRepository.getAssociatedNotes(noteCourseId);
    }
    public LiveData<List<NoteEntity>> getAllNotes(){
        return allNotes;
    }
    public void insert(NoteEntity noteEntity){
        mRepository.insert(noteEntity);
    }
    public void delete(NoteEntity noteEntity){
        mRepository.delete(noteEntity);
    }

    public int lastID(){
        return  InventoryManagementRepository.getLastNoteId();
    }
}
