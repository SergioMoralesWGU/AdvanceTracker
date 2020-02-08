package com.svartingknas.wguadvancetracker;

import android.content.Intent;
import android.os.Bundle;


import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;

import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.svartingknas.wguadvancetracker.database.InventoryManagementRepository;
import com.svartingknas.wguadvancetracker.entities.AssessmentEntity;
import com.svartingknas.wguadvancetracker.entities.NoteEntity;
import com.svartingknas.wguadvancetracker.ui.AssessmentAdapter;
import com.svartingknas.wguadvancetracker.ui.NoteAdapter;
import com.svartingknas.wguadvancetracker.viewmodel.AssessmentViewModel;
import com.svartingknas.wguadvancetracker.viewmodel.CourseViewModel;
import com.svartingknas.wguadvancetracker.viewmodel.NoteViewModel;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

public class CourseDetail extends AppCompatActivity{
    private static final int NEW_CLASS_DETAIL_REQUEST_CODE = 1;
    private static final int NEW_NOTE_ACTIVITY_REQUEST_CODE = 1;
    private static final int NEW_ASSESSMENT_ACTIVITY_REQUEST_CODE = 1;
    public static final String EXTRA_REPLY = "com.svartingknas.wguadvancetracker.REPLY";

    private AssessmentViewModel assessmentViewModel;
    private NoteViewModel noteViewModel;
    private CourseViewModel courseViewModel;
    private LayoutInflater inflater;
    private Button displayAssessmentBtn;
    private Button displayNotesBtn;
    private ImageButton deleteCourse;
    private ImageButton editCourse;

    //COURSE DETAIL ITEMS
    private TextView courseName;
    private TextView courseStartDate;
    private TextView courseEndDate;
    private TextView courseStatus;
    private TextView mentorName;
    private TextView mentorPhone;
    private TextView mentorEmail;

    int courseId = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_detail);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        courseName = findViewById(R.id.tv_course_detail_name);
        courseStartDate = findViewById(R.id.tv_course_detail_start_date);
        courseEndDate = findViewById(R.id.tv_course_detail_end_date);
        courseStatus = findViewById(R.id.tv_course_detail_status);
        mentorName = findViewById(R.id.tv_course_detail_mentor_name);
        mentorPhone = findViewById(R.id.tv_course_detail_mentor_phone);
        mentorEmail = findViewById(R.id.tv_course_detail_mentor_email);


        displayAssessmentBtn = findViewById(R.id.btn_display_assessment);
        displayAssessmentBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CourseDetail.this, AssessmentListActivity.class);
                startActivityForResult(intent, NEW_NOTE_ACTIVITY_REQUEST_CODE);
            }
        });


        displayNotesBtn = findViewById(R.id.btn_display_notes);
        displayNotesBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CourseDetail.this, NoteListActivity.class);
                startActivityForResult(intent, NEW_ASSESSMENT_ACTIVITY_REQUEST_CODE);
            }
        });

        deleteCourse = findViewById(R.id.btn_delete_course);

        editCourse = findViewById(R.id.btn_edit_course);



        if (getIntent().getStringExtra("courseTitle") != null) {
            InventoryManagementRepository.setCurrentCourseId(getIntent().getIntExtra("id", -1));
            courseName.setText(getIntent().getStringExtra("courseTitle"));
            courseStartDate.setText(getIntent().getStringExtra("courseStartDate"));
            courseEndDate.setText(getIntent().getStringExtra("courseEndDate"));
            courseStatus.setText(getIntent().getStringExtra("courseStatus"));
            mentorName.setText(getIntent().getStringExtra("mentorName"));
            mentorPhone.setText(getIntent().getStringExtra("mentorPhone"));
            mentorEmail.setText(getIntent().getStringExtra("mentorEmail"));
        }




//        RecyclerView assessmentRecyclerView = findViewById(R.id.courseview_assessment_rv);
//        final AssessmentAdapter assessmentAdapter = new AssessmentAdapter(this);
//        assessmentRecyclerView.setAdapter(assessmentAdapter);
//        assessmentRecyclerView.setLayoutManager(new LinearLayoutManager(this));
//
//        assessmentViewModel.getAssociatedAssessments(courseId).observe(this, new Observer<List<AssessmentEntity>>() {
//            @Override
//            public void onChanged(List<AssessmentEntity> assessmentEntities) {
//                assessmentAdapter.setAssessments(assessmentEntities);
//            }
//        });

//        RecyclerView notesRecyclerView = findViewById(R.id.courseview_notes_rv);
//        final NoteAdapter notesAdapter = new NoteAdapter(this);
//        notesRecyclerView.setAdapter(notesAdapter);
//        notesRecyclerView.setLayoutManager(new LinearLayoutManager(this));
//
//        noteViewModel.getAssociatedNotes(courseId).observe(this, new Observer<List<NoteEntity>>() {
//            @Override
//            public void onChanged(List<NoteEntity> noteEntities) {
//                notesAdapter.setNotes(noteEntities);
//            }
//        });
    }

//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);

//        if (resultCode == RESULT_OK) {
//            String pattern = "MM/dd/yyyy";
//            DateFormat dateFormat = new SimpleDateFormat(pattern);
//            try {
//                AssessmentEntity assessmentEntity = new AssessmentEntity(
//                        assessmentViewModel.lastID() + 1,
//                        data.getStringExtra("assessment_title"),
//                        dateFormat.parse(data.getStringExtra("assessment_due_date")),
//                        data.getStringExtra("assessment_type"),
//                        data.getIntExtra("assessmentCourseId", -1)
//                );
//                assessmentViewModel.insert(assessmentEntity);
//            }
//            catch (ParseException pe){
//                // maybe do something?
//            }
//        }else {
//            Toast.makeText(this, R.string.empty_not_saved, Toast.LENGTH_LONG)
//                    .show();
//        }
//    }

}
