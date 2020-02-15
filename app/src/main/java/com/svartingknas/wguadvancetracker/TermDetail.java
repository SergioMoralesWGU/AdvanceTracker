package com.svartingknas.wguadvancetracker;

import android.content.Intent;
import android.os.Bundle;


import com.svartingknas.wguadvancetracker.database.InventoryManagementRepository;
import com.svartingknas.wguadvancetracker.entities.TermEntity;
import com.svartingknas.wguadvancetracker.viewmodel.TermViewModel;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.ViewModelProviders;

import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;

public class TermDetail extends AppCompatActivity {
    private static final int NEW_WORD_ACTIVITY_REQUEST_CODE = 1 ;
    public static final String EXTRA_REPLY = "com.svartingknas.wguadvancetracker.REPLY";

    private TermViewModel termViewModel;
    private TextView termName;
    private TextView termStartDate;
    private TextView termEndDate;
    private TextView termId;
    private Button associatedCourses;
    private ImageButton deleteTermBtn;
    private int position;
    public static int numCourses;
    public int getTermId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        termViewModel = ViewModelProviders.of(this).get(TermViewModel.class);
        setContentView(R.layout.activity_term_detail);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        getTermId = getIntent().getIntExtra("Id", -1);

        termId = findViewById(R.id.term_id);
        termName = findViewById(R.id.tv_term_name);
        termStartDate = findViewById(R.id.term_start_date);
        termEndDate = findViewById(R.id.term_end_date);
        final int termInt = getIntent().getIntExtra("Id", -1);
        final int courseTermId = getIntent().getIntExtra("courseTermId", -1);
        if (getIntent().getStringExtra("termTitle")!=null){
            termId.setText(termInt+ "");
            termName.setText(getIntent().getStringExtra("termTitle"));
            termStartDate.setText(getIntent().getStringExtra("termStartDate"));
            termEndDate.setText(getIntent().getStringExtra("termEndDate"));
        }

        deleteTermBtn = findViewById(R.id.delete_term_btn);
        deleteTermBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TermDetail.this, TermListActivity.class);
                if (InventoryManagementRepository.hasAssociatedCourses(termInt)){
                    Toast.makeText(TermDetail.this, "You cannot delete a term with courses", Toast.LENGTH_LONG).show();
                }
                InventoryManagementRepository.deleteTermById(termInt);
                Toast.makeText(TermDetail.this, "Term Deleted", Toast.LENGTH_LONG).show();
//                if (courseViewModel.getAssociatedCourses(courseTermId) != null) {
//                } else {
//                }
                startActivityForResult(intent, NEW_WORD_ACTIVITY_REQUEST_CODE);
            }
        });


        associatedCourses = findViewById(R.id.btn_term_associated_courses);
        associatedCourses.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TermDetail.this, CourseListActivity.class);
                try {
                    intent.putExtra("termId", getIntent().getIntExtra("Id", -1));

                }catch (Exception ex){
                    //stuff
                }
                startActivityForResult(intent, NEW_WORD_ACTIVITY_REQUEST_CODE);
            }
        });


    }



    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            String pattern = "MM/dd/yyyy";
            DateFormat dateFormat = new SimpleDateFormat(pattern);
            try {
                TermEntity termEntity = new TermEntity(
                        termViewModel.lastID() + 1,
                        data.getStringExtra("term_name"),
                        dateFormat.parse(data.getStringExtra("term_start_date")),
                        dateFormat.parse(data.getStringExtra("term_end_date"))
                );
                termViewModel.insert(termEntity);
            } catch (ParseException pe) {
                // maybe do something?
            }
        } else {
            Toast.makeText(this, R.string.empty_not_saved, Toast.LENGTH_LONG)
                    .show();
        }
    }

}












