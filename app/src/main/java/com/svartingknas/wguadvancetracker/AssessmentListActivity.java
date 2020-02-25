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

//alert imports:
//import android.app.AlarmManager ;
//import android.app.DatePickerDialog ;
//import android.app.Notification ;
//import android.app.PendingIntent ;
//import android.content.Context ;
//import android.content.Intent ;
//import android.os.Bundle ;
//import android.support.v4.app.NotificationCompat ;
//import android.support.v7.app.AppCompatActivity ;
//import android.view.View ;
//import android.widget.Button ;
//import android.widget.DatePicker ;
//import java.text.SimpleDateFormat ;
//import java.util.Calendar ;
//import java.util.Date ;
//import java.util.Locale ;

public class AssessmentListActivity extends AppCompatActivity {

    private static final int NEW_ASSESSMENT_REQUEST_CODE = 1;
    private AssessmentViewModel assessmentViewModel;
    private LayoutInflater layoutInflater;


    public static final String NOTIFICATION_CHANNEL_ID = "10001" ;
    private final static String default_notification_channel_id = "default" ;


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
            Intent intent=new Intent(AssessmentListActivity.this,MyReceiver.class);
            intent.putExtra("channel","webinar");
            intent.putExtra("startAssessment", "Your assessment starts soon");
            PendingIntent sender= PendingIntent.getBroadcast(AssessmentListActivity.this,0,intent, PendingIntent.FLAG_UPDATE_CURRENT);
            AlarmManager alarmManager=(AlarmManager)getSystemService(Context.ALARM_SERVICE);
//            date=Long.parseLong(mills.getText().toString());
            alarmManager.set(AlarmManager.RTC_WAKEUP, System.currentTimeMillis() + 1000, sender);
            return true;

//            notification.setSmallIcon(R.drawable.ic_event_note_black_24dp);
//            notification.setTicker("Class alert");
//            notification.setWhen(System.currentTimeMillis());
//            notification.setContentTitle("this is the title");
//            notification.setContentText("this is the body of the notification");
//
//            Intent intent = new Intent(this, CourseDetail.class);
//            PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
//            notification.setContentIntent(pendingIntent);
//
//
//            //builds notification and issues it
//            NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
//            notificationManager.notify(uniqueID, notification.build());
        }
        return super.onOptionsItemSelected(item);
    }


    //    private boolean enableNotifications(){
//        long now = System.currentTimeMillis();
//        if (now <= scheduleCourseAlarm(getApplicationContext(), courseId, DateUtil.get) )
//    }
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