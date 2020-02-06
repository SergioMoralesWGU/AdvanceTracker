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

import com.svartingknas.wguadvancetracker.database.InventoryManagementRepository;
import com.svartingknas.wguadvancetracker.entities.AssessmentEntity;
import com.svartingknas.wguadvancetracker.ui.AssessmentAdapter;
import com.svartingknas.wguadvancetracker.viewmodel.AssessmentViewModel;
import com.svartingknas.wguadvancetracker.viewmodel.CourseViewModel;
import com.svartingknas.wguadvancetracker.viewmodel.NoteViewModel;

import java.util.List;

public class CourseDetail extends AppCompatActivity {
    private static final int NEW_CLASS_DETAIL_REQUEST_CODE = 1;
    private AssessmentViewModel assessmentViewModel;
    private NoteViewModel noteViewModel;
    private CourseViewModel courseViewModel;
    private LayoutInflater inflater;
    private Button addNoteBtn;
    private Button addAssessmentBtn;
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

//        courseId = findViewById(R.id.)
        courseName = findViewById(R.id.tv_course_detail_name);
        courseStartDate = findViewById(R.id.tv_course_detail_start_date);
        courseEndDate = findViewById(R.id.tv_course_detail_end_date);
        courseStatus = findViewById(R.id.tv_course_detail_status);
        mentorName = findViewById(R.id.tv_course_detail_mentor_name);
        mentorPhone = findViewById(R.id.tv_course_detail_mentor_phone);
        mentorEmail = findViewById(R.id.tv_course_detail_mentor_email);

        if (getIntent().getStringExtra("courseTitle")!=null){
            InventoryManagementRepository.setCurrentCourseId(getIntent().getIntExtra("id", -1));
            courseName.setText(getIntent().getStringExtra("courseTitle"));
            courseStartDate.setText(getIntent().getStringExtra("courseStartDate"));
            courseEndDate.setText(getIntent().getStringExtra("courseEndDate"));
            courseStatus.setText(getIntent().getStringExtra("courseStatus"));
            mentorName.setText(getIntent().getStringExtra("mentorName"));
            mentorPhone.setText(getIntent().getStringExtra("mentorPhone"));
            mentorEmail.setText(getIntent().getStringExtra("mentorEmail"));
        }


        RecyclerView assessmentRecyclerView = findViewById(R.id.assessment_list_rv);
        final AssessmentAdapter assessmentAdapter = new AssessmentAdapter(this);
        assessmentRecyclerView.setAdapter(assessmentAdapter);
        assessmentRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        assessmentViewModel.getAllAssessments().observe(this, new Observer<List<AssessmentEntity>>() {
            @Override
            public void onChanged(List<AssessmentEntity> assessmentEntities) {
                assessmentAdapter.setAssessments(assessmentEntities);
            }
        });
    }

//        intent.putExtra("id", current.getId());
//        intent.putExtra("courseTitle", current.getCourseTitle());
//        intent.putExtra("courseStatus", current.getCourseStatus());
//        intent.putExtra("courseStartDate", current.getCourseStartDate());
//        intent.putExtra("courseEndDate", current.getCourseEndDate());
//        intent.putExtra("mentorName", current.getMentorName());
//        intent.putExtra("mentorPhone", current.getMentorPhone());
//        intent.putExtra("mentorEmail", current.getMentorEmail());
//        intent.putExtra("courseTermId", current.getCourseTermId());
//        intent.putExtra("position", position);
//
//        addNoteBtn = findViewById(R.id.btn_add_note);
//        addAssessmentBtn = findViewById(R.id.btn_add_assessment);
//        deleteCourse = findViewById(R.id.btn_delete_course);
//        editCourse = findViewById(R.id.btn_edit_course);
//
//
//        addNoteBtn.setOnClickListener(this);
//        addAssessmentBtn.setOnClickListener(this);
//        deleteCourse.setOnClickListener(this);
//        editCourse.setOnClickListener(this);


        assessmentViewModel = ViewModelProviders.of(this).get(AssessmentViewModel.class);
        noteViewModel = ViewModelProviders.of(this).get(NoteViewModel.class);


        RecyclerView assessmentRecyclerView = findViewById(R.id.courseview_assessment_rv);
        final AssessmentAdapter assessmentAdapter = new AssessmentAdapter(this);
        assessmentRecyclerView.setAdapter(assessmentAdapter);
        assessmentRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        assessmentViewModel.getAssociatedAssessments(courseId).observe(this, new Observer<List<AssessmentEntity>>() {
            @Override
            public void onChanged(List<AssessmentEntity> assessmentEntities) {
                assessmentAdapter.setAssessments(assessmentEntities);
            }
        });

    }

}

//
//    public void onClick(View v) {
//        switch (v.getId()) {
////                case R.id.btn_delete_course:
////                    Intent DeleteCourseIntent = new Intent(CourseDetail.this, NewAssessmentActivity.class);
////                    startActivity(DeleteCourseIntent);
////                    break;
////                case R.id.btn_edit_course:
////                    Intent EditCourseIntent = new Intent(CourseDetail.this, NewCourseActivity.class);
////                    startActivity(EditCourseIntent);
////                    break;
//            case R.id.btn_add_assessment:
//                Intent AddAssessmentIntent = new Intent(CourseDetail.this, NewAssessmentActivity.class);
//                startActivity(AddAssessmentIntent);
//                break;
//            case R.id.btn_add_note:
//                Intent AddNoteIntent = new Intent(CourseDetail.this, NewNoteActivity.class);
//                startActivity(AddNoteIntent);
//                break;
//        }
//    }