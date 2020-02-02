package com.svartingknas.wguadvancetracker.database;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import com.svartingknas.wguadvancetracker.entities.AssessmentEntity;
import com.svartingknas.wguadvancetracker.entities.CourseEntity;
import com.svartingknas.wguadvancetracker.entities.NoteEntity;
import com.svartingknas.wguadvancetracker.entities.TermEntity;

import java.util.List;

public class InventoryManagementRepository {
    private AssessmentDao assessmentDao;
    private CourseDao courseDao;
    private NoteDao noteDao;
    private TermDao termDao;
    private LiveData<List<TermEntity>> allTerms;
    private LiveData<List<CourseEntity>> allCourses;
    private LiveData<List<AssessmentEntity>> allAssessments;
    private LiveData<List<NoteEntity>> allNotes;
    private LiveData<List<CourseEntity>> associatedCourses;
    private LiveData<List<AssessmentEntity>> associatedAssessments;
    private LiveData<List<NoteEntity>> associatedNotes;

    private int assessmentCourseId;
    private int courseTermId;
    private int noteCourseId;

    public InventoryManagementRepository(Application application){
        WguRoomDatabase db = WguRoomDatabase.getDatabase(application);

        assessmentDao = db.assessmentDao();
        allAssessments = assessmentDao.getAllAssessments();
        associatedAssessments = assessmentDao.getAssociatedAssessments(assessmentCourseId);

        courseDao = db.courseDao();
        allCourses = courseDao.getAllCourses();
        associatedCourses = courseDao.getAssociatedCourses(courseTermId);

        noteDao = db.noteDao();
        allNotes = noteDao.getAllNotes();
        associatedNotes = noteDao.getAssociatedNotes(noteCourseId);

        termDao = db.termDao();
        allTerms = termDao.getAllTerms();

    }

    public LiveData<List<AssessmentEntity>> getAllAssessments(){
        return allAssessments;
    }
    public LiveData<List<AssessmentEntity>> getAssociatedAssessments(int assessmentCourseId){
        return associatedAssessments;
    }
    public LiveData<List<CourseEntity>> getAllCourses(){
        return allCourses;
    }
    public LiveData<List<CourseEntity>> getAssociatedCourses(int courseTermId){
        return associatedCourses;
    }
    public LiveData<List<NoteEntity>> getAllNotes(){
        return allNotes;
    }
    public LiveData<List<NoteEntity>> getAssociatedNotes(int noteCourseId){
        return associatedNotes;
    }
    public LiveData<List<TermEntity>> getAllTerms(){
        return allTerms;
    }




    public void insert (AssessmentEntity assessmentEntity) {
        new insertAsyncTaskAssessment(assessmentDao).execute(assessmentEntity);
    }
    //using an asyncTask, this allows us to do things asynchronously. This is so that the main thread
    //is not clogged. Every time something is inserted in the database, it will be done asynchronously
    //keeping the thread clean. This asks to implement the doInBackground method.
    private static class insertAsyncTaskAssessment extends AsyncTask<AssessmentEntity, Void, Void> {
        private AssessmentDao asyncTaskDao;
        insertAsyncTaskAssessment(AssessmentDao dao) {
            asyncTaskDao = dao;
        }
        @Override
        protected Void doInBackground(AssessmentEntity... params) {
            asyncTaskDao.insert(params[0]);
            return null;
        }
    }



    public void insert (CourseEntity courseEntity) {
        new insertAsyncTaskCourse(courseDao).execute(courseEntity);
    }
    private static class insertAsyncTaskCourse extends AsyncTask<CourseEntity, Void, Void> {
        private CourseDao asyncTaskDao;
        insertAsyncTaskCourse(CourseDao dao) {
            asyncTaskDao = dao;
        }
        @Override
        protected Void doInBackground(CourseEntity... params) {
            asyncTaskDao.insert(params[0]);
            return null;
        }
    }


    public void insert (TermEntity termEntity) {
        new insertAsyncTaskTerm(termDao).execute(termEntity);
    }
    private static class insertAsyncTaskTerm extends AsyncTask<TermEntity, Void, Void> {
        private TermDao asyncTaskDao;
        insertAsyncTaskTerm(TermDao dao) {
            asyncTaskDao = dao;
        }
        @Override
        protected Void doInBackground(TermEntity... params) {
            asyncTaskDao.insert(params[0]);
            return null;
        }
    }


    public void insert (NoteEntity noteEntity) {
        new insertAsyncTaskNote(noteDao).execute(noteEntity);
    }
    private static class insertAsyncTaskNote extends AsyncTask<NoteEntity, Void, Void> {
        private NoteDao asyncTaskDao;
        insertAsyncTaskNote(NoteDao dao) {
            asyncTaskDao = dao;
        }
        @Override
        protected Void doInBackground(NoteEntity... params) {
            asyncTaskDao.insert(params[0]);
            return null;
        }
    }

    public void delete (AssessmentEntity assessmentEntity){
        new deleteAssessmentAsyncTask(assessmentDao).execute(assessmentEntity);
    }
    private static class deleteAssessmentAsyncTask extends AsyncTask<AssessmentEntity, Void, Void> {

        private AssessmentDao mAsyncTaskDao;

        deleteAssessmentAsyncTask(AssessmentDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final AssessmentEntity... params) {
            mAsyncTaskDao.delete(params[0]);
            return null;
        }
    }


    public void delete (CourseEntity courseEntity){
        new deleteCourseAsyncTask(courseDao).execute(courseEntity);
    }
    private static class deleteCourseAsyncTask extends AsyncTask<CourseEntity, Void, Void> {

        private CourseDao mAsyncTaskDao;

        deleteCourseAsyncTask(CourseDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final CourseEntity... params) {
            mAsyncTaskDao.delete(params[0]);
            return null;
        }
    }



    public void delete (TermEntity termEntity){
        new deleteTermAsyncTask(termDao).execute(termEntity);
    }
    private static class deleteTermAsyncTask extends AsyncTask<TermEntity, Void, Void> {

        private TermDao mAsyncTaskDao;

        deleteTermAsyncTask(TermDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final TermEntity... params) {
            mAsyncTaskDao.delete(params[0]);
            return null;
        }
    }


    public void delete (NoteEntity noteEntity){
        new deleteNoteAsyncTask(noteDao).execute(noteEntity);
    }
    private static class deleteNoteAsyncTask extends AsyncTask<NoteEntity, Void, Void> {

        private NoteDao mAsyncTaskDao;

        deleteNoteAsyncTask(NoteDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final NoteEntity... params) {
            mAsyncTaskDao.delete(params[0]);
            return null;
        }
    }

}
