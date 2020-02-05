package com.svartingknas.wguadvancetracker;

import android.os.Bundle;


import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;

import com.svartingknas.wguadvancetracker.entities.AssessmentEntity;
import com.svartingknas.wguadvancetracker.ui.AssessmentAdapter;
import com.svartingknas.wguadvancetracker.viewmodel.AssessmentViewModel;

import java.util.List;

public class CourseDetail extends AppCompatActivity {
    private AssessmentViewModel assessmentViewModel;
    private LayoutInflater inflater;

    int courseId = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_detail);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        assessmentViewModel = ViewModelProviders.of(this).get(AssessmentViewModel.class);

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
