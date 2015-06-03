package com.example.sarah.myproject.Activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.example.sarah.myproject.Class.SessionManager;
import com.example.sarah.myproject.R;
import com.example.sarah.myproject.Tasks.ExerciseTask;

public class ExerciseListActivity extends Activity
{
    private TextView folderTitle, folderDescription;
    private String folderName, patientId, patientName, folderID;
    private SessionManager session;
    private String[] patientArray;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exercise_list);

        folderTitle = (TextView)findViewById(R.id.folderTitle);
        folderDescription = (TextView)findViewById(R.id.folderDescription);

        Intent i = getIntent();         // get information from intent
        folderName = i.getStringExtra("folderName");    // get folder name
        folderID = i.getStringExtra("folderId");    // get folder id

        Log.w("FOLDER NAME", folderName);
        Log.w("FOLDER ID", folderID +"");

        folderTitle.setText(folderName);        // set title of page to be folder name

        // Session class instance
        session = new SessionManager(getApplicationContext());
        session.checkLogin();

        // get user ID from session
        patientArray = session.getPatientDetails();
        if (patientArray.length > 0)
        {
            patientId = patientArray[0];        // get patient id
            patientName = patientArray[1];      // get patient name
        }
        Log.w("Check", "BEFORE");
        Log.w("Patient ID", patientId);
        Log.w("Folder ID", folderName);

        ExerciseTask exerciseTask = new ExerciseTask(this);      // calling to task in order to pull from db the folders assigned
        exerciseTask.execute(patientId, folderID+"");       // execute task and add buttons for each folder assigned
        Log.w("Check", "AFTER");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_exercise_list, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}