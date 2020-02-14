package com.svartingknas.wguadvancetracker;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.svartingknas.wguadvancetracker.entities.AssessmentEntity;
import com.svartingknas.wguadvancetracker.ui.AssessmentAdapter;
import com.svartingknas.wguadvancetracker.viewmodel.AssessmentViewModel;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

public class AssessmentListActivity extends AppCompatActivity {

    private static final int NEW_ASSESSMENT_REQUEST_CODE = 1;
    private AssessmentViewModel assessmentViewModel;
    private LayoutInflater layoutInflater;
    private ImageButton deleteAssessment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_assessment_list);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_black_24dp);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        assessmentViewModel = ViewModelProviders.of(this).get(AssessmentViewModel.class);
        final int assessmentId = getIntent().getIntExtra("assessmentId", -1);


        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AssessmentListActivity.this, NewAssessmentActivity.class);
                intent.putExtra("assessmentId", assessmentViewModel.lastID()+1);
                startActivityForResult(intent, NEW_ASSESSMENT_REQUEST_CODE);
            }
        });

        RecyclerView assessmentRecyclerView = findViewById(R.id.assessment_list_rv);
        final AssessmentAdapter assessmentAdapter = new AssessmentAdapter(this);
        assessmentRecyclerView.setAdapter(assessmentAdapter);
        assessmentRecyclerView.setLayoutManager(new LinearLayoutManager(this));

//        deleteAssessment = findViewById(R.id.delete_assessment_btn);
//        deleteAssessment.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                    InventoryManagementRepository.deleteAssessmentById(assessmentId);
//            }
//        });


        int currentCourseId = getIntent().getIntExtra("courseId", -1);
        if (currentCourseId == -1){
            assessmentViewModel.getAllAssessments().observe(this, new Observer<List<AssessmentEntity>>() {
                @Override
                public void onChanged(List<AssessmentEntity> assessmentEntities) {
                    assessmentAdapter.setAssessments(assessmentEntities);
                }
            });
        } else {
            assessmentViewModel.getAssociatedAssessments(currentCourseId).observe(this, new Observer<List<AssessmentEntity>>() {
                @Override
                public void onChanged(List<AssessmentEntity> assessment) {
                    assessmentAdapter.setAssessments(assessment);
                }
            });
        }



    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            String pattern = "MM/dd/yyyy";
            DateFormat dateFormat = new SimpleDateFormat(pattern);
            try {
                AssessmentEntity assessmentEntity = new AssessmentEntity(
                        assessmentViewModel.lastID() + 1,
                        data.getStringExtra("assessmentName"),
                        dateFormat.parse(data.getStringExtra("assessmentDate")),
                        data.getStringExtra("assessmentType"),
                        data.getIntExtra("assessmentCourseId", -1)
                        );
                assessmentViewModel.insert(assessmentEntity);
            }
            catch (ParseException pe){
                // maybe do something?
            }
        }else {
            Toast.makeText(this, R.string.empty_not_saved, Toast.LENGTH_LONG)
                    .show();
        }
    }

    @Override
    public boolean onSupportNavigateUp(){
        onBackPressed();
        return true;
    }

}