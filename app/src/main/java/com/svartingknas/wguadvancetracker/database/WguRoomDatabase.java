package com.svartingknas.wguadvancetracker.database;

import android.content.Context;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.svartingknas.wguadvancetracker.entities.AssessmentEntity;
import com.svartingknas.wguadvancetracker.entities.CourseEntity;
import com.svartingknas.wguadvancetracker.entities.NoteEntity;
import com.svartingknas.wguadvancetracker.entities.TermEntity;

import java.util.Date;

@Database(entities = {AssessmentEntity.class, CourseEntity.class, NoteEntity.class, TermEntity.class}, version = 1, exportSchema = false)
public abstract class WguRoomDatabase extends RoomDatabase {

    //volatile used so that the var is not cached, and disappears with the program important to use because of memory sync
    //Whenever the memory is out this variable also disappears, this is important because this variable represents the instance of the database
    public abstract AssessmentDao assessmentDao();
    public abstract CourseDao courseDao();
    public abstract NoteDao noteDao();
    public abstract TermDao termDao();

    private static volatile WguRoomDatabase INSTANCE;

    //I am going to create a singleton, in order to ensure that we are not creating
    //databases where we do not need as the db is a very expensive.
    //this ensures that we only use one database when needed
    public static WguRoomDatabase getDatabase(final Context context){
        if (INSTANCE == null){
            synchronized (WguRoomDatabase.class){
                if (INSTANCE == null){
                    //create db
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            WguRoomDatabase.class, "wgu_database")
                            .fallbackToDestructiveMigration()
                            .addCallback(roomDatabaseCallback)
                            .build();
                }
            }
        }
        return INSTANCE;
    }

    private static RoomDatabase.Callback roomDatabaseCallback = new RoomDatabase.Callback(){
        @Override
        public void onOpen(@NonNull SupportSQLiteDatabase db) {
            super.onOpen(db);
            new PopulateDbAsync(INSTANCE).execute();
        }
    };


    private static class PopulateDbAsync extends AsyncTask<Void, Void, Void> {
        private final AssessmentDao assessmentDao;
        private final CourseDao courseDao;
        private final NoteDao noteDao;
        private final TermDao termDao;

        //this used to be public.
        PopulateDbAsync(WguRoomDatabase db) {
            assessmentDao = db.assessmentDao();
            courseDao = db.courseDao();
            noteDao = db.noteDao();
            termDao = db.termDao();

        }

        //everything done here is for testing, the db can be put together.
        @Override
        protected Void doInBackground(Void... voids) {
//            termDao.deleteAllTerms();//removes all terms from the table
//            courseDao.deleteAllCourses();
//            assessmentDao.deleteAllAssessments();
//            noteDao.deleteAllNotes();
//              for testing

            TermEntity term = new TermEntity(1, "First Term", new Date("12/12/2020"), new Date("06/01/2021"));
            termDao.insert(term);
            term = new TermEntity(2, "second Term", new Date("07/01/2021"), new Date("12/01/2021"));
            termDao.insert(term);

            CourseEntity course = new CourseEntity(1,"Spanish", new Date("02/01/2019"), new Date("04/01/2020"), "Not Started","Jaime", "8011234567", "jaime@wgu.edu", 1);
            courseDao.insert(course);
            course = new CourseEntity(2,"english", new Date("02/01/2019"), new Date("04/01/2020"), "Not Started","Che", "8017654321", "che@wgu.edu",2);
            courseDao.insert(course);

            AssessmentEntity assessment = new AssessmentEntity(1,"Preterito", new Date("01/01/2020"), "Objective", 1);
            assessmentDao.insert(assessment);
            assessment = new AssessmentEntity(2,"Pluscuamperfecto", new Date ("02/01/2020"), "Objective", 2);
            assessmentDao.insert(assessment);

            NoteEntity note = new NoteEntity(1, "Note One", "This is the first note", 1);
            noteDao.insert(note);
            note = new NoteEntity(2, "Note Two", "This is the second note", 2);
            noteDao.insert(note);

            return null;
        }
    }
}
