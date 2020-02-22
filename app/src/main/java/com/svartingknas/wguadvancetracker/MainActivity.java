package com.svartingknas.wguadvancetracker;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private Button termsBtn;
    private Button coursesBtn;
    private Button assessmentsBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        termsBtn = findViewById(R.id.main_termsbtn);
        coursesBtn = findViewById(R.id.main_coursesBtn);
        assessmentsBtn = findViewById(R.id.main_assessmentsBtn);

        termsBtn.setOnClickListener(this);
        coursesBtn.setOnClickListener(this);
        assessmentsBtn.setOnClickListener(this);
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
//        if (id == R.id.action_settings) {
//            return true;
//        }

        return super.onOptionsItemSelected(item);
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.main_termsbtn:
                Intent TermsIntent = new Intent(MainActivity.this, TermListActivity.class);
                startActivity(TermsIntent);
                break;
            case R.id.main_coursesBtn:
                Intent CourseIntent = new Intent(MainActivity.this, CourseListActivity.class);
                getIntent().putExtra("termId", Integer.valueOf(-1));
                startActivity(CourseIntent);
                break;
            case R.id.main_assessmentsBtn:
                Intent AssessmentIntent = new Intent(MainActivity.this, AssessmentListActivity.class);
                startActivity(AssessmentIntent);

        }
    }
}
