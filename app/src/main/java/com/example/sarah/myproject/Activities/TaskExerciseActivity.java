package com.example.sarah.myproject.Activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.example.sarah.myproject.Class.SessionManager;
import com.example.sarah.myproject.R;
import com.example.sarah.myproject.Tasks.SubmittedExerciseTask;
import com.example.sarah.myproject.Tasks.TaskTask;

public class TaskExerciseActivity extends Activity
{
    public TextView exercsieTitleTextView, descriptionTextView;
    public String exerciseName, exerciseId, patientId, patientName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_exercise);

        exercsieTitleTextView = (TextView) findViewById(R.id.exerciseTitle);

        Intent i = getIntent();         // get information from intent
        exerciseName = i.getStringExtra("exerciseTitle");    // get exercise name
        exerciseId = i.getStringExtra("exerciseId");    // get exercise id

        //String[] titleSplit = exerciseName.split(".");      // getting exercise name like: 1. Exercise1 -- split it to get {1, Exercise1}
        //exerciseTitle = titleSplit[1];      // get exercise name

        exercsieTitleTextView.setText(exerciseName);       // set exercise name to be the exercise name we got from the intent

        // Session class instance
        SessionManager session = new SessionManager(getApplicationContext());
        session.checkLogin();

        // get user ID from session
        String[] patientArray = session.getPatientDetails();
        if (patientArray.length > 0) {
            patientId = patientArray[0];        // get patient id
            patientName = patientArray[1];      // get patient name
        }

        TaskTask taskTask = new TaskTask(this);      // calling to task in order to pull from db the folders assigned
        taskTask.execute(patientId, exerciseId);       // execute task and add buttons for each folder assigned
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.common_menu, menu);
        getActionBar().setTitle(exerciseName);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        Intent i = null;
        switch (id)
        {
            case R.id.folders_icon:
            {
                i = new Intent(this, PatientActivity.class);
                break;
            }
            case R.id.messages_icon:
            {
                i = new Intent(this, MessagesActivity.class);
                break;
            }
            case R.id.account_icon:
            {
                i = new Intent(this, AccountActivity.class);
                break;
            }


        }
        this.startActivity(i);
        this.finish();


        return super.onOptionsItemSelected(item);
    }

    public void onClick_button_exercise_done(View view)
    {
        SubmittedExerciseTask submittedExerciseTask = new SubmittedExerciseTask(this);
        submittedExerciseTask.execute(exerciseId, exerciseName);
        this.finish();
    }
}
