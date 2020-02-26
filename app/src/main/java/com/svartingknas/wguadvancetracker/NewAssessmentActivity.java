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

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.NotificationCompat;
import androidx.lifecycle.ViewModelProviders;

import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.svartingknas.wguadvancetracker.entities.AssessmentEntity;
import com.svartingknas.wguadvancetracker.viewmodel.AssessmentViewModel;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;

public class NewAssessmentActivity extends AppCompatActivity {
    public static final String EXTRA_REPLY = "com.svartingknas.c196Sergiomorales.reply";
    private AssessmentViewModel assessmentViewModel;
    private EditText assessmentTitle;
    private EditText assessmentType;
    private EditText assessmentDueDate;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_assessment);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        assessmentTitle = findViewById(R.id.et_assessment_name);
        assessmentType = findViewById(R.id.et_assessment_type);
        assessmentDueDate = findViewById(R.id.et_assessment_due);

        assessmentViewModel = ViewModelProviders.of(this).get(AssessmentViewModel.class);


        final int assessmentId  = getIntent().getIntExtra("assessmentId", -1);
        if (assessmentId != -1){
            // this means we are editing
            assessmentTitle.setText(getIntent().getStringExtra("assessmentName"));
            assessmentType.setText(getIntent().getStringExtra("assessmentType"));
            assessmentDueDate.setText(getIntent().getStringExtra("assessmentDate"));

        }

        final Button assessmentSaveButton = findViewById(R.id.btn_save_assessment);
        assessmentSaveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String pattern = "MM/dd/yyyy";
                DateFormat dateFormat = new SimpleDateFormat(pattern);

                Intent replyIntent = new Intent();
                if (TextUtils.isEmpty(assessmentTitle.getText())) {
                    setResult(RESULT_CANCELED, replyIntent);
                } else if ((TextUtils.isEmpty(assessmentTitle.getText()))) {
                    setResult(RESULT_CANCELED, replyIntent);
                }   else if ((TextUtils.isEmpty(assessmentType.getText()))) {
                    setResult(RESULT_CANCELED, replyIntent);
                } else if ((TextUtils.isEmpty(assessmentDueDate.getText()))) {
                    setResult(RESULT_CANCELED, replyIntent);
                } else {
                    String assessmentTitleString = assessmentTitle.getText().toString();
                    String assessmentTypeString = assessmentType.getText().toString();
                    String assessmentDueDateString = assessmentDueDate.getText().toString();

                    replyIntent.putExtra("assessmentCourseId", getIntent().getIntExtra("assessmentCourseId", -1));
                    replyIntent.putExtra("assessmentName", assessmentTitleString);
                    replyIntent.putExtra("assessmentType", assessmentTypeString);
                    replyIntent.putExtra("assessmentDate", assessmentDueDateString);
                    replyIntent.putExtra("assessmentId", assessmentId);

                    setResult(RESULT_OK, replyIntent);
                }
                finish();
            }
        });

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
            Intent intent = new Intent(NewAssessmentActivity.this, MyReceiver.class);
            intent.putExtra("TitleKey", "Assessment Alert");
            intent.putExtra("ContentKey", "Your assessment is today");
            PendingIntent sender = PendingIntent.getBroadcast(NewAssessmentActivity.this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
            AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
            long assessmentDate;
            try {
                assessmentDate = dateFormat.parse(data.getStringExtra("assessmentDate")).getTime();
                alarmManager.set(AlarmManager.RTC_WAKEUP, assessmentDate, sender);
            } catch (ParseException e) {
                e.printStackTrace();
            }

        }else {
            Toast.makeText(this, R.string.empty_not_saved, Toast.LENGTH_LONG)
                    .show();
        }
    }
}
