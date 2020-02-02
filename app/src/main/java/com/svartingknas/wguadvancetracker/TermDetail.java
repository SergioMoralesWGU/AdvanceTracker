package com.svartingknas.wguadvancetracker;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.svartingknas.wguadvancetracker.entities.CourseEntity;
import com.svartingknas.wguadvancetracker.ui.CourseAdapter;
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
import android.widget.TextView;
import android.widget.Toast;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class TermDetail extends AppCompatActivity {
    private static final int NEW_WORD_ACTIVITY_REQUEST_CODE = 1 ;
    public static final String EXTRA_REPLY = "com.svartingknas.wguadvancetracker.REPLY";
    private TermViewModel termViewModel;
    private CourseViewModel courseViewModel;
    private TextView termName;
    private TextView termStartDate;
    private TextView termEndDate;
    private TextView termId;
    private int position;
    public static int numCourses;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        termViewModel = ViewModelProviders.of(this).get(TermViewModel.class);
        courseViewModel = ViewModelProviders.of(this).get(CourseViewModel.class);
        setContentView(R.layout.activity_term_detail);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        termName = findViewById(R.id.tv_term_name);
        termStartDate = findViewById(R.id.term_start_date);
        termEndDate = findViewById(R.id.term_end_date);
        termId = findViewById(R.id.term_id);

        if (getIntent().getStringExtra("termName")!=null){
            termId.setText(getIntent().getIntExtra("id", -1));
            termName.setText(getIntent().getStringExtra("termName"));
            termStartDate.setText(getIntent().getStringExtra("termStartDate"));
            termEndDate.setText(getIntent().getStringExtra("termEndDate"));
        }

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Intent intent = new Intent(TermDetail.this, NewCourseActivity.class);
                startActivityForResult(intent, NEW_WORD_ACTIVITY_REQUEST_CODE);

            }
        });
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        RecyclerView recyclerView = findViewById(R.id.associated_courses_rv);
        final CourseAdapter adapter = new CourseAdapter(this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        courseViewModel = ViewModelProviders.of(this).get(CourseViewModel.class);
        courseViewModel.getAssociatedCourses(1).observe(this, new Observer<List<CourseEntity>>() {
            @Override
            public void onChanged(List<CourseEntity> courseEntities) {
                List<CourseEntity> filteredCourses = new ArrayList<>();
                for (CourseEntity i:courseEntities)
                    if (i.getCourseTermId()==getIntent().getIntExtra("courseTermId", 0))filteredCourses.add(i);
                    adapter.setCourses(filteredCourses);
                    numCourses = filteredCourses.size();
            }
        });
    }

    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            String pattern = "MM/dd/yyyy";
            DateFormat dateFormat = new SimpleDateFormat(pattern);
            try {
                CourseEntity courseEntity = new CourseEntity(
                        courseViewModel.lastID() + 1,
                        data.getStringExtra("courseTitle"),
                        dateFormat.parse(data.getStringExtra("courseStartDate")),
                        dateFormat.parse(data.getStringExtra("courseEndDate")),
                        data.getStringExtra("courseStatus"),
                        data.getStringExtra("mentorName"),
                        data.getStringExtra("mentorEmail"),
                        data.getStringExtra("mentorPhone"),
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



















