package com.svartingknas.wguadvancetracker;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
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
import android.view.Menu;
import android.view.MenuItem;
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
                intent.putExtra("Id", noteViewModel.lastID()+1);
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



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.sharing) {
            Intent sendIntent = new Intent();
            sendIntent.setAction(Intent.ACTION_SEND);
            sendIntent.putExtra(Intent.EXTRA_TEXT, "This is my text to send.");
            // (Optional) Here we're setting the title of the content
            sendIntent.putExtra(Intent.EXTRA_TITLE, "Send message title");
            sendIntent.setType("text/plain");

            Intent shareIntent = Intent.createChooser(sendIntent, null);
            startActivity(shareIntent);
            return true;
        }
//        if (id == R.id.notifications) {
//            Intent intent=new Intent(NoteListActivity.this,MyReceiver.class);
//            intent.putExtra("key","This is a short message");
//            PendingIntent sender= PendingIntent.getBroadcast(NoteListActivity.this,0,intent,0);
//            AlarmManager alarmManager=(AlarmManager)getSystemService(Context.ALARM_SERVICE);
//            date=Long.parseLong(mills.getText().toString());
//            alarmManager.set(AlarmManager.RTC_WAKEUP, date, sender);
//            return true;
//        }

        return super.onOptionsItemSelected(item);
    }


    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
             NoteEntity noteEntity = new NoteEntity(
                        noteViewModel.lastID() + 1,
                        data.getStringExtra("noteTitle"),
                        data.getStringExtra("noteText"),
                        data.getIntExtra("noteCourseId", 1));
                noteViewModel.insert(noteEntity);

        } else {
            Toast.makeText(this, R.string.empty_not_saved, Toast.LENGTH_LONG)
                    .show();
        }
    }
}
