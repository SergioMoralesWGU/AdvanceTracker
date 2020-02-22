package com.svartingknas.wguadvancetracker;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class NewNoteActivity extends AppCompatActivity {
    public static final String EXTRA_REPLY = "com.svartingknas.c196Sergiomorales.reply";
    private EditText noteName;
    private EditText noteText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_note);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        noteName = findViewById(R.id.et_note_name);
        noteText = findViewById(R.id.et_note_text);

        final Button noteSaveButton = findViewById(R.id.btn_save_note);
        noteSaveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent replyIntent = new Intent();
                if (TextUtils.isEmpty(noteName.getText())) {
                    setResult(RESULT_CANCELED, replyIntent);
                } else if ((TextUtils.isEmpty(noteName.getText()))) {
                setResult(RESULT_CANCELED, replyIntent);
                }   else if ((TextUtils.isEmpty(noteText.getText()))) {
                    setResult(RESULT_CANCELED, replyIntent);
                } else {
                    String noteNameString = noteName.getText().toString();
                    String noteTextString = noteText.getText().toString();
                    replyIntent.putExtra("noteCourseId", getIntent().getIntExtra("courseId", -1));
                    replyIntent.putExtra("noteTitle", noteNameString);
                    replyIntent.putExtra("noteText", noteTextString);

                    setResult(RESULT_OK, replyIntent);
                }

                finish();
            }
        });
    }

}
