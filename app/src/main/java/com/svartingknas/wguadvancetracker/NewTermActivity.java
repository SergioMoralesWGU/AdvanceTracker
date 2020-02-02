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



        final Button termSaveButton = findViewById(R.id.term_save_button);
        termSaveButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                String pattern = "MM/dd/yyyy";
                DateFormat dateFormat = new SimpleDateFormat(pattern);

                Intent replyIntent = new Intent();
                String name = termName.getText().toString();
//                Date termStart = dateFormat.parse(termStartDate(getText().toString()));
//                Date termEnd = termEndDate.getText().toString();

                replyIntent.putExtra("termName", name);
//                replyIntent.putExtra("termStartDate", termStart);
//                replyIntent.putExtra("termEndDate", termEnd);

                if (getIntent().getStringExtra("termName")!= null){
                    int id = getIntent().getIntExtra("id", 0);
                    int courseId = getIntent().getIntExtra("courseId", 0);
//                    TermEntity termEntity = new TermEntity(id, name, termStart, termEnd);
                }
                    setResult(RESULT_OK, replyIntent);
                finish();
            }
        });


    }

}
