package com.svartingknas.wguadvancetracker;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.svartingknas.wguadvancetracker.entities.NoteEntity;
import com.svartingknas.wguadvancetracker.ui.NoteAdapter;
import com.svartingknas.wguadvancetracker.viewmodel.CourseViewModel;
import com.svartingknas.wguadvancetracker.viewmodel.NoteViewModel;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

public class NoteListActivity extends AppCompatActivity {
    private static final int NEW_NOTE_LIST_REQUEST_CODE = 1;
    private NoteViewModel noteViewModel;
    private TextView tvNoteCourseId;
    private TextView tvNoteName;
    private TextView tvNoteText;
    int noteCourseId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note_list);
        Toolbar toolbar = findViewById(R.id.toolbar);
        noteViewModel = ViewModelProviders.of(this).get(NoteViewModel.class);
        setSupportActionBar(toolbar);

        tvNoteCourseId = findViewById(R.id.note_course_id);
        tvNoteName = findViewById(R.id.note_text_tv);
        tvNoteText = findViewById(R.id.note_name_tv);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(NoteListActivity.this, NewNoteActivity.class);
                intent.putExtra("Id", noteViewModel.lastID()+1); //TODO not sure if this is supposed to be just ID or noteCourseId
                startActivityForResult(intent, NEW_NOTE_LIST_REQUEST_CODE);
            }
        });


        RecyclerView noteRecyclerView = findViewById(R.id.note_list_rv);
        final NoteAdapter noteAdapter = new NoteAdapter(this);
        noteRecyclerView.setAdapter(noteAdapter);
        noteRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        int noteCourseId = getIntent().getIntExtra("courseId", -2);
            noteViewModel.getAssociatedNotes(noteCourseId).observe(this, new Observer<List<NoteEntity>>() {
                @Override
                public void onChanged(List<NoteEntity> courses) {
                    noteAdapter.setNotes(courses);
                }
            });
        }

    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
             NoteEntity noteEntity = new NoteEntity(
                        noteViewModel.lastID() + 1,
                        data.getStringExtra("note_title"),
                        data.getStringExtra("note_text"),
                        data.getIntExtra("note_course_id", 1));
                noteViewModel.insert(noteEntity);

        } else {
            Toast.makeText(this, R.string.empty_not_saved, Toast.LENGTH_LONG)
                    .show();
        }
    }
}
