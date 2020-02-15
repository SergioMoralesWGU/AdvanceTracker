package com.svartingknas.wguadvancetracker;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

public class NewCourseActivity extends AppCompatActivity {
    public static final String EXTRA_REPLY = "com.svartingknas.c196Sergiomorales.reply";
    private EditText courseTitle;
    private EditText courseStartDate;
    private EditText courseEndDate;
    private EditText courseStatus;
    private EditText mentorName;
    private EditText mentorPhone;
    private EditText mentorEmail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_course);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        courseTitle = findViewById(R.id.et_course_title);
        courseStartDate = findViewById(R.id.et_course_start_date);
        courseEndDate = findViewById(R.id.et_course_end_date);
        courseStatus = findViewById(R.id.et_course_status);
        mentorName = findViewById(R.id.et_course_mentor_name);
        mentorPhone = findViewById(R.id.et_course_mentor_phone);
        mentorEmail = findViewById(R.id.et_course_mentor_email);

        final Button courseSaveButton = findViewById(R.id.btn_save_course);
        courseSaveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String pattern = "MM/dd/yyyy";
                DateFormat dateFormat = new SimpleDateFormat(pattern);

                Intent replyIntent = new Intent();
                if (TextUtils.isEmpty(courseTitle.getText())) {
                    setResult(RESULT_CANCELED, replyIntent);
                } else if ((TextUtils.isEmpty(courseStartDate.getText()))) {
                    setResult(RESULT_CANCELED, replyIntent);
                } else if ((TextUtils.isEmpty(courseEndDate.getText()))) {
                    setResult(RESULT_CANCELED, replyIntent);
                } else if ((TextUtils.isEmpty(courseStatus.getText()))) {
                    setResult(RESULT_CANCELED, replyIntent);
                } else if ((TextUtils.isEmpty(mentorName.getText()))) {
                    setResult(RESULT_CANCELED, replyIntent);
                } else if (TextUtils.isEmpty(mentorPhone.getText())) {
                    setResult(RESULT_CANCELED, replyIntent);
                } else if (TextUtils.isEmpty(mentorEmail.getText())) {
                    setResult(RESULT_CANCELED, replyIntent);
                } else {
                    String courseTitleString = courseTitle.getText().toString();
                    String courseStartDateString = courseStartDate.getText().toString();
                    String courseEndDateString = courseEndDate.getText().toString();
                    String courseStatusString = courseStatus.getText().toString();
                    String mentorNameString = mentorName.getText().toString();
                    String mentorPhoneString = mentorPhone.getText().toString();
                    String mentorEmailString = mentorEmail.getText().toString();

                    replyIntent.putExtra("termId", getIntent().getIntExtra("termId", -1));
                    replyIntent.putExtra("courseTitle", courseTitleString);
                    replyIntent.putExtra("courseStartDate", courseStartDateString);
                    replyIntent.putExtra("courseEndDate", courseEndDateString);
                    replyIntent.putExtra("courseStatus", courseStatusString);
                    replyIntent.putExtra("mentorName", mentorNameString);
                    replyIntent.putExtra("mentorPhone", mentorPhoneString);
                    replyIntent.putExtra("mentorEmail", mentorEmailString);

                    setResult(RESULT_OK, replyIntent);
                }

                finish();
            }
        });
    }

}
