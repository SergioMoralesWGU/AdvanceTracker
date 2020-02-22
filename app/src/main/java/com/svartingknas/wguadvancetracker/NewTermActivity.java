package com.svartingknas.wguadvancetracker;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;


import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.svartingknas.wguadvancetracker.entities.TermEntity;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;


public class NewTermActivity extends AppCompatActivity {
    public static final String EXTRA_REPLY = "com.svartingknas.c196Sergiomorales.reply";
    private EditText termName;
    private EditText termStartDate;
    private EditText termEndDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_term);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        termName = findViewById(R.id.et_term_name);
        termStartDate = findViewById(R.id.et_term_start_date);
        termEndDate = findViewById(R.id.et_term_end_date);

        final int termId = getIntent().getIntExtra("termId", -1);
        if (termId != -1){
            // this means we are editing
            termName.setText(getIntent().getStringExtra("termTitle"));
            termStartDate.setText(getIntent().getStringExtra("termStartDate"));
            termEndDate.setText(getIntent().getStringExtra("termEndDate"));
        }




        final Button termSaveButton = findViewById(R.id.term_save_button);
        termSaveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String pattern = "MM/dd/yyyy";
                DateFormat dateFormat = new SimpleDateFormat(pattern);

                Intent replyIntent = new Intent();
                if (TextUtils.isEmpty(termName.getText())) {
                    setResult(RESULT_CANCELED, replyIntent);
                } else if ((TextUtils.isEmpty(termStartDate.getText()))) {
                    setResult(RESULT_CANCELED, replyIntent);
                } else if (TextUtils.isEmpty(termEndDate.getText())) {
                    setResult(RESULT_CANCELED, replyIntent);
                } else {
                    replyIntent.putExtra("termId", termId);
                    String termNameString = termName.getText().toString();
                    String termStartDateString = termStartDate.getText().toString();
                    String termEndDateString = termEndDate.getText().toString();
                    replyIntent.putExtra("term_name", termNameString);
                    replyIntent.putExtra("term_start_date", termStartDateString);
                    replyIntent.putExtra("term_end_date", termEndDateString);
                    setResult(RESULT_OK, replyIntent);
                }

                finish();
            }
        });


    }

}
