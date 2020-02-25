package com.svartingknas.wguadvancetracker;

import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.svartingknas.wguadvancetracker.entities.AssessmentEntity;
import com.svartingknas.wguadvancetracker.ui.AssessmentAdapter;
import com.svartingknas.wguadvancetracker.viewmodel.AssessmentViewModel;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.NotificationCompat;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
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

    long assessmentDate;


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
        final int currentCourseId = getIntent().getIntExtra("courseId", -1);


        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AssessmentListActivity.this, NewAssessmentActivity.class);
                intent.putExtra("assessmentCourseId", currentCourseId);
                startActivityForResult(intent, NEW_ASSESSMENT_REQUEST_CODE);
            }
        });

        RecyclerView assessmentRecyclerView = findViewById(R.id.assessment_list_rv);
        final AssessmentAdapter assessmentAdapter = new AssessmentAdapter(this);
        assessmentRecyclerView.setAdapter(assessmentAdapter);
        assessmentRecyclerView.setLayoutManager(new LinearLayoutManager(this));


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
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_notifications, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_enable_notifications) {

            Intent intent = new Intent(AssessmentListActivity.this, MyReceiver.class);
            intent.putExtra("channel", "webinar");
            intent.putExtra("startAssessment", "Your assessment is today");
            PendingIntent sender = PendingIntent.getBroadcast(AssessmentListActivity.this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
            AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
//            assessmentDate=Long.parseLong(mills.getText().toString());
            assessmentDate = System.currentTimeMillis() + 500;
            alarmManager.set(AlarmManager.RTC_WAKEUP, assessmentDate, sender);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            String pattern = "MM/dd/yyyy";
            DateFormat dateFormat = new SimpleDateFormat(pattern);
            try {
                int assessmentId = data.getIntExtra("assessmentId", -1);
                if (assessmentId == -1){
                    assessmentId = assessmentViewModel.lastID()+1;
                }
                AssessmentEntity assessmentEntity = new AssessmentEntity(
                        assessmentId,
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