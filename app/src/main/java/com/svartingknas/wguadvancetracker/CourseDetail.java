package com.svartingknas.wguadvancetracker;

import android.app.AlarmManager;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.NotificationCompat;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.format.DateUtils;
import android.util.Log;
import android.view.LayoutInflater;

import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.svartingknas.wguadvancetracker.database.DateTypeConverter;
import com.svartingknas.wguadvancetracker.database.InventoryManagementRepository;
import com.svartingknas.wguadvancetracker.entities.AssessmentEntity;
import com.svartingknas.wguadvancetracker.entities.CourseEntity;
import com.svartingknas.wguadvancetracker.entities.NoteEntity;
import com.svartingknas.wguadvancetracker.entities.TermEntity;
import com.svartingknas.wguadvancetracker.ui.AssessmentAdapter;
import com.svartingknas.wguadvancetracker.ui.NoteAdapter;
import com.svartingknas.wguadvancetracker.viewmodel.AssessmentViewModel;
import com.svartingknas.wguadvancetracker.viewmodel.CourseViewModel;
import com.svartingknas.wguadvancetracker.viewmodel.NoteViewModel;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class CourseDetail extends AppCompatActivity{
    private static final int NEW_CLASS_DETAIL_REQUEST_CODE = 1;
    private static final int NEW_NOTE_ACTIVITY_REQUEST_CODE = 1;
    private static final int NEW_ASSESSMENT_ACTIVITY_REQUEST_CODE = 1;
    public static final String EXTRA_REPLY = "com.svartingknas.wguadvancetracker.REPLY";
    private static final int EDIT_CLASS_REQUEST_CODE = 2;

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

    long startDate;
    long endDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_detail);
        Toolbar toolbar = findViewById(R.id.toolbar);
        courseViewModel = ViewModelProviders.of(this).get(CourseViewModel.class);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        final int courseId = getIntent().getIntExtra("courseId", -2);
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
                intent.putExtra("courseId", courseId);
                startActivityForResult(intent, NEW_NOTE_ACTIVITY_REQUEST_CODE);
            }
        });

        displayNotesBtn = findViewById(R.id.btn_display_notes);
        displayNotesBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CourseDetail.this, NoteListActivity.class);
                intent.putExtra("courseId", courseId);
                startActivityForResult(intent, NEW_ASSESSMENT_ACTIVITY_REQUEST_CODE);
            }
        });

        deleteCourse = findViewById(R.id.btn_delete_course);
        deleteCourse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CourseDetail.this, TermListActivity.class);
                    InventoryManagementRepository.deleteCourseById(courseId);
                    Toast.makeText(getApplicationContext(),"Course Deleted",Toast.LENGTH_LONG).show();
                startActivity(intent);

            }
        });

        editCourse = findViewById(R.id.btn_edit_course);
        editCourse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CourseDetail.this, NewCourseActivity.class);
                intent.putExtra("termId", getIntent().getIntExtra("termId", -1));
                intent.putExtra("courseId", courseId);
                intent.putExtra("courseTitle", courseName.getText());
                intent.putExtra("courseStartDate", courseStartDate.getText());
                intent.putExtra("courseEndDate", courseEndDate.getText());
                intent.putExtra("courseStatus", courseStatus.getText());
                intent.putExtra("mentorName", mentorName.getText());
                intent.putExtra("mentorEmail", mentorEmail.getText());
                intent.putExtra("mentorPhone", mentorPhone.getText());
                startActivityForResult(intent, EDIT_CLASS_REQUEST_CODE);
            }
        });


        if (getIntent().getStringExtra("courseTitle") != null) {
            courseName.setText(getIntent().getStringExtra("courseTitle"));

            String courseStartDateString = getIntent().getStringExtra("courseStartDate");
            if(courseStartDateString != null){
                String pattern = "MM/dd/yyyy";
                DateFormat dateFormat = new SimpleDateFormat(pattern);
                try {
                    startDate = dateFormat.parse(courseStartDateString).getTime();
                } catch (ParseException e) {
                    startDate = -1;
                }
            }
            courseStartDate.setText(getIntent().getStringExtra("courseStartDate"));

            String courseEndDateString = getIntent().getStringExtra("courseEndDate");
            if(courseEndDateString != null){
                String pattern = "MM/dd/yyyy";
                DateFormat dateFormat = new SimpleDateFormat(pattern);
                try {
                    endDate = dateFormat.parse(courseEndDateString).getTime();
                } catch (ParseException e) {
                    endDate = -1;
                }
            }
            courseEndDate.setText(getIntent().getStringExtra("courseEndDate"));

            courseStatus.setText(getIntent().getStringExtra("courseStatus"));
            mentorName.setText(getIntent().getStringExtra("mentorName"));
            mentorPhone.setText(getIntent().getStringExtra("mentorPhone"));
            mentorEmail.setText(getIntent().getStringExtra("mentorEmail"));
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

            Intent startIntent=new Intent(CourseDetail.this,MyReceiver.class);
            startIntent.putExtra("TitleKey", "Course Start!");
            startIntent.putExtra("ContentKey", "Your class starts today");
            PendingIntent senderStart= PendingIntent.getBroadcast(CourseDetail.this,0,startIntent, PendingIntent.FLAG_UPDATE_CURRENT);

            AlarmManager startAlarmManager=(AlarmManager)getSystemService(Context.ALARM_SERVICE);
            startAlarmManager.set(AlarmManager.RTC_WAKEUP, startDate, senderStart);


            Intent endIntent=new Intent(CourseDetail.this,MyReceiver.class);
            endIntent.putExtra("TitleKey", "End of class");
            endIntent.putExtra("ContentKey", "Your class ends today");
            PendingIntent senderEnd= PendingIntent.getBroadcast(CourseDetail.this,0,endIntent, PendingIntent.FLAG_UPDATE_CURRENT);

            AlarmManager endAlarmManager=(AlarmManager)getSystemService(Context.ALARM_SERVICE);
            endAlarmManager.set(AlarmManager.RTC_WAKEUP, endDate, senderEnd);


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
                CourseEntity courseEntity = new CourseEntity(
                        data.getIntExtra("courseId", -1),
                        data.getStringExtra("courseTitle"),
                        dateFormat.parse(data.getStringExtra("courseStartDate")),
                        dateFormat.parse(data.getStringExtra("courseEndDate")),
                        data.getStringExtra("courseStatus"),
                        data.getStringExtra("mentorName"),
                        data.getStringExtra("mentorEmail"),
                        data.getStringExtra("mentorPhone"),
                        data.getIntExtra("termId", -1));
                courseViewModel.insert(courseEntity);
            } catch (ParseException pe){
                //do stuff
            }
            courseName.setText(data.getStringExtra("courseTitle"));
            courseStartDate.setText(data.getStringExtra("courseStartDate"));
            courseEndDate.setText(data.getStringExtra("courseEndDate"));
            courseStatus.setText(data.getStringExtra("courseStatus"));
            mentorName.setText(data.getStringExtra("mentorName"));
            mentorEmail.setText(data.getStringExtra("mentorEmail"));
            mentorPhone.setText(data.getStringExtra("mentorPhone"));


        }else {
            Toast.makeText(this, R.string.empty_not_saved, Toast.LENGTH_LONG)
                    .show();
        }
    }

}
