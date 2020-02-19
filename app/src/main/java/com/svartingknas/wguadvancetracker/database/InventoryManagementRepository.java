package com.svartingknas.wguadvancetracker.database;

import android.app.Application;
import android.os.AsyncTask;
import android.widget.TextView;

import androidx.lifecycle.LiveData;

import com.svartingknas.wguadvancetracker.entities.AssessmentEntity;
import com.svartingknas.wguadvancetracker.entities.CourseEntity;
import com.svartingknas.wguadvancetracker.entities.NoteEntity;
import com.svartingknas.wguadvancetracker.entities.TermEntity;

import java.util.List;

public class InventoryManagementRepository {
    private static AssessmentDao assessmentDao;
    private static CourseDao courseDao;
    private static NoteDao noteDao;
    private static TermDao termDao;


    private static Integer lastCourseId = 2; //TODO be aware we need to set this to match what's coming from test data
    private static Integer lastTermId = 2; //TODO be aware we need to set this to match what's coming from test data
    private static Integer lastNoteId = 2; //TODO be aware we need to set this to match what's coming from test data
    private static Integer lastAssessmentId = 2; //TODO be aware we need to set this to match what's coming from test data


    public static Integer getLastCourseId() {
        if (lastCourseId == null){
            return 2;//TODO be aware we need to set this to match what's coming from test data
        }
        return lastCourseId;
    }

    public static Integer getLastTermId() {
        if (lastTermId == null){
            return 2;//TODO be aware we need to set this to match what's coming from test data
        }
        return lastTermId;
    }

    public static Integer getLastNoteId() {
        if (lastNoteId == null){
            return 2;//TODO be aware we need to set this to match what's coming from test data
        }
        return lastNoteId;
    }

    public static Integer getLastAssessmentId() {
        if (lastAssessmentId == null){
            return 2;//TODO be aware we need to set this to match what's coming from test data
        }
        return lastAssessmentId;
    }



    public InventoryManagementRepository(Application application){
        WguRoomDatabase db = WguRoomDatabase.getDatabase(application);

        assessmentDao = db.assessmentDao();


        courseDao = db.courseDao();


        noteDao = db.noteDao();


        termDao = db.termDao();

    }

    public static boolean hasAssociatedCourses(int termId) {
        return courseDao.getAssociatedCourses(termId).getValue() != null && !courseDao.getAssociatedCourses(termId).getValue().isEmpty();
    }

    public LiveData<List<AssessmentEntity>> getAllAssessments(){
        return assessmentDao.getAllAssessments();
    }
    public LiveData<List<AssessmentEntity>> getAssociatedAssessments(int assessmentCourseId){
        return assessmentDao.getAssociatedAssessments(assessmentCourseId);
    }
    public static LiveData<List<CourseEntity>> getAllCourses(){
        return courseDao.getAllCourses();
    }
    public static LiveData<List<CourseEntity>> getAssociatedCourses(int courseTermId){
        return courseDao.getAssociatedCourses(courseTermId);
    }
    public LiveData<List<NoteEntity>> getAllNotes(){
        return noteDao.getAllNotes();
    }
    public LiveData<List<NoteEntity>> getAssociatedNotes(int noteCourseId){
        return noteDao.getAssociatedNotes(noteCourseId);
    }

    public LiveData<List<TermEntity>> getAllTerms(){
        return termDao.getAllTerms();
    }




    public void insert (AssessmentEntity assessmentEntity) {
        lastAssessmentId = lastAssessmentId+1;
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
        lastCourseId = lastCourseId+1;
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
        lastTermId = lastTermId+1;
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
        lastNoteId = lastNoteId+1;
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

    public static void deleteAssessmentById (int assessmentId){
//        lastAssessmentId = lastAssessmentId-1;
        new deleteAssessmentByIdAsyncTask(assessmentDao).execute(assessmentId);
    }
    private static class deleteAssessmentByIdAsyncTask extends AsyncTask<Integer, Void, Void> {

        private AssessmentDao mAsyncTaskDao;

        deleteAssessmentByIdAsyncTask(AssessmentDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(Integer... integers) {
            mAsyncTaskDao.deleteById(integers[0]);
            return null;
        }
    }


    public void delete (AssessmentEntity assessmentEntity){
//        lastAssessmentId = lastAssessmentId-1;
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


    public static void delete (CourseEntity courseEntity){
//        lastCourseId = lastCourseId-1;
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

    public static void deleteCourseById(int courseId){
//        lastCourseId = lastCourseId-1;
        new deleteCourseByIdAsyncTask(courseDao).execute(courseId);
    }

    private static class deleteCourseByIdAsyncTask extends AsyncTask<Integer, Void, Void> {

        private CourseDao mAsyncTaskDao;

        deleteCourseByIdAsyncTask(CourseDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(Integer... integers) {
            mAsyncTaskDao.deleteCourseById(integers[0]);
            return null;
        }
    }





    public static void deleteTermById(int termId){
//        lastTermId = lastTermId -1;
       new deleteTermByIdAsyncTask(termDao).execute(termId);
    }

    private static class deleteTermByIdAsyncTask extends AsyncTask<Integer, Void, Void> {

        private TermDao mAsyncTaskDao;

        deleteTermByIdAsyncTask(TermDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(Integer... integers) {
            mAsyncTaskDao.deleteById(integers[0]);
            return null;
        }
    }



    public void delete (TermEntity termEntity){
//        lastTermId = lastTermId -1;
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
//        lastNoteId = lastNoteId-1;
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
