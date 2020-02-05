package com.svartingknas.wguadvancetracker;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.svartingknas.wguadvancetracker.entities.TermEntity;
import com.svartingknas.wguadvancetracker.ui.TermAdapter;
import com.svartingknas.wguadvancetracker.viewmodel.TermViewModel;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.widget.Toast;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

public class TermListActivity extends AppCompatActivity {
    private TermViewModel termViewModel;
    private static final int NEW_WORD_ACTIVITY_REQUEST_CODE = 1;
    private LayoutInflater layoutInflater;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_term_list);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        termViewModel = ViewModelProviders.of(this).get(TermViewModel.class);// TODO bicycle project does this differently


        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(TermListActivity.this, NewTermActivity.class);
                intent.putExtra("Id", termViewModel.lastID()+1);

                startActivityForResult(intent, NEW_WORD_ACTIVITY_REQUEST_CODE);
            }
        });
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        RecyclerView recyclerView = findViewById(R.id.recyclerView_termList);
        final TermAdapter termListAdapter = new TermAdapter(this);
        recyclerView.setAdapter(termListAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        termViewModel.getAllTerms().observe(this, new Observer<List<TermEntity>>() {
            @Override
            public void onChanged(@Nullable final List<TermEntity> terms) {
                //update the cached copy of nodos in the adapter
                termListAdapter.setTerms(terms);
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
                TermEntity term = new TermEntity(
                        termViewModel.lastID() + 1,
                        data.getStringExtra("term_name"),
                        dateFormat.parse(data.getStringExtra("term_start_date")),
                        dateFormat.parse(data.getStringExtra("term_end_date"))
                );
                termViewModel.insert(term);
            }
            catch (ParseException pe){
                // maybe do something?
            }
        }else {
            Toast.makeText(this, R.string.empty_not_saved, Toast.LENGTH_LONG)
                    .show();
        }
    }

}