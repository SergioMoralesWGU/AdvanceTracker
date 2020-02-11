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

public class NewAssessmentActivity extends AppCompatActivity {
    public static final String EXTRA_REPLY = "com.svartingknas.c196Sergiomorales.reply";
    private EditText assessmentCourseId;
    private EditText assessmentTitle;
    private EditText assessmentType;
    private EditText assessmentDueDate;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_assessment);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        assessmentCourseId = findViewById(R.id.et_assessment_courseId);
        assessmentTitle = findViewById(R.id.et_assessment_name);
        assessmentType = findViewById(R.id.et_assessment_type);
        assessmentDueDate = findViewById(R.id.et_assessment_due);

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
                    String assessmentCourseIdString = assessmentCourseId.getText().toString();
                    String assessmentTitleString = assessmentTitle.getText().toString();
                    String assessmentTypeString = assessmentType.getText().toString();
                    String assessmentDueDateString = assessmentDueDate.getText().toString();
                    replyIntent.putExtra("assessmentCourseId", assessmentCourseIdString);
                    replyIntent.putExtra("assessmentName", assessmentTitleString);
                    replyIntent.putExtra("assessmentType", assessmentTypeString);
                    replyIntent.putExtra("assessmentDate", assessmentDueDateString);


                    setResult(RESULT_OK, replyIntent);
                }

                finish();
            }
        });

    }

}
