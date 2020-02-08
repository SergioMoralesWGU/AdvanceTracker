package com.svartingknas.wguadvancetracker;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.svartingknas.wguadvancetracker.database.InventoryManagementRepository;
import com.svartingknas.wguadvancetracker.entities.CourseEntity;
import com.svartingknas.wguadvancetracker.ui.CourseAdapter;
import com.svartingknas.wguadvancetracker.viewmodel.CourseViewModel;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.widget.Toast;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

public class CourseListActivity extends AppCompatActivity {
    private static final int NEW_CLASS_LIST_REQUEST_CODE = 1;
    private CourseViewModel courseViewModel;
    private LayoutInflater layoutInflater;
    int courseTermId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_list);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        courseViewModel = ViewModelProviders.of(this).get(CourseViewModel.class);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CourseListActivity.this, NewCourseActivity.class);
                intent.putExtra("id", courseViewModel.lastID()+1);
                startActivityForResult(intent, NEW_CLASS_LIST_REQUEST_CODE);
            }
        });
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        RecyclerView courseRecyclerView = findViewById(R.id.content_course_list_rv);
        final CourseAdapter courseAdapter = new CourseAdapter(this);
        courseRecyclerView.setAdapter(courseAdapter);
        courseRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        int currentTermId = getIntent().getIntExtra("termId", -2);
        if (currentTermId == -1) {
            courseViewModel.getAllCourses().observe(this, new Observer<List<CourseEntity>>() {
                @Override
                public void onChanged(List<CourseEntity> courses) {
                    courseAdapter.setCourses(courses);
                }
            });
        } else {
            courseViewModel.getAssociatedCourses(currentTermId).observe(this, new Observer<List<CourseEntity>>() {
                @Override
                public void onChanged(List<CourseEntity> courses) {
                    courseAdapter.setCourses(courses);
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
                CourseEntity courseEntity = new CourseEntity(
                        courseViewModel.lastID() + 1,
                        data.getStringExtra("course_title"),
                        dateFormat.parse(data.getStringExtra("course_start_date")),
                        dateFormat.parse(data.getStringExtra("course_end_date")),
                        data.getStringExtra("course_status"),
                        data.getStringExtra("mentor_name"),
                        data.getStringExtra("mentor_phone"),
                        data.getStringExtra("mentor_email"),
                        data.getIntExtra("courseTermId", -1)
                );
                courseViewModel.insert(courseEntity);
            } catch (ParseException pe) {
                // maybe do something?
            }
        } else {
            Toast.makeText(this, R.string.empty_not_saved, Toast.LENGTH_LONG)
                    .show();
        }
    }
}
