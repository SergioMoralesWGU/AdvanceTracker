package com.svartingknas.wguadvancetracker;

import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.svartingknas.wguadvancetracker.entities.CourseEntity;
import com.svartingknas.wguadvancetracker.entities.TermEntity;
import com.svartingknas.wguadvancetracker.ui.TermAdapter;
import com.svartingknas.wguadvancetracker.viewmodel.CourseViewModel;
import com.svartingknas.wguadvancetracker.viewmodel.TermViewModel;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.View;

import java.util.List;

public class TermCourseView extends AppCompatActivity {
    private CourseViewModel courseViewModel;
    private static final int NEW_TERM_ACTIVITY_REQUEST_CODE = 1;
    private int courseTermId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_term_course_view);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        courseViewModel = ViewModelProviders.of(this).get(CourseViewModel.class);


//        FloatingActionButton fab = findViewById(R.id.fab);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
//            }
//        });
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        RecyclerView recyclerView = findViewById(R.id.recyclerView_termList);
        final TermAdapter termListAdapter = new TermAdapter(this);
        recyclerView.setAdapter(termListAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

//        courseViewModel.getAssociatedCourses(courseTermId).observe(this, new Observer<List<TermEntity>>() {
//            @Override
//            public void onChanged(@Nullable final List<CourseEntity> courses) {
//                //update the cached copy of nodos in the adapter
//                courseListAdapter.setCourses(courses);
//            }
//
    }
}



