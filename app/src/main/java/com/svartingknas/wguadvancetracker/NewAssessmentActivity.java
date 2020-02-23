package com.svartingknas.wguadvancetracker;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;

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

                    //notification stuff

//                    onReceive(NewAssessmentActivity.this, new Intent());

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
        }else {
            Toast.makeText(this, R.string.empty_not_saved, Toast.LENGTH_LONG)
                    .show();
        }
    }


    // experimenting with notifications

    static int notificationID;
    String channel_id="test";


    private void onReceive(Context context, Intent intent) {
//        Toast.makeText(context,intent.getStringExtra("key"),Toast.LENGTH_LONG).show();
        createNotificationChannel(context,channel_id);
  /*      Notification n=new Notification.Builder(context)
                .setSmallIcon(R.drawable.ic_launcher_foreground)
                .setChannelId(channel_id)
                .setContentTitle("Test Notification with an id of:"+Integer.toString(notificationID))
                .setContentText("This is a test").build();*/
        Notification n= new NotificationCompat.Builder(context, channel_id)
                .setSmallIcon(R.drawable.ic_launcher_foreground)
//                .setContentText(intent.getStringExtra("key"))
                .setContentText("test notification")
                .setContentTitle("Test of Notification with an id of :"+Integer.toString(notificationID)).build();

        NotificationManager notificationManager=(NotificationManager)context.getSystemService(NOTIFICATION_SERVICE);
        notificationManager.notify(notificationID++,n);

        //Put a notification her aka Vogella Tutorial

        // TODO: This method is called when the BroadcastReceiver is receiving
        // an Intent broadcast.
        // throw new UnsupportedOperationException("Not yet implemented");
    }


    private void createNotificationChannel(Context context, String CHANNEL_ID) {
        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is new and not in the support library

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = context.getResources().getString(R.string.channel_name);
            String description = context.getString(R.string.channel_description);
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, name, importance);
            channel.setDescription(description);
            // Register the channel with the system; you can't change the importance
            // or other notification behaviors after this
            NotificationManager notificationManager = context.getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }

}
