package com.example.sarah.myproject.Activities;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.sarah.myproject.Class.AsyncResponse;
import com.example.sarah.myproject.Class.Exercise;
import com.example.sarah.myproject.Class.SessionManager;
import com.example.sarah.myproject.R;
import com.example.sarah.myproject.Tasks.DownloadFileTask;
import com.example.sarah.myproject.Tasks.ExerciseTask;

public class ExerciseActivity extends Activity implements AsyncResponse
{
    private TextView exerciseTitle;
    private String patientId, patientName;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exercise);

        Intent i = getIntent();
        String exerciseName = i.getStringExtra("exerciseTitle");
        String exerciseId = i.getStringExtra("exerciseId");

        exerciseTitle = (TextView)findViewById(R.id.exerciseTitle);
        exerciseTitle.setText(exerciseName);       // set exercise name to be the exercise name we got from the intent

        // Session class instance
        SessionManager session = new SessionManager(getApplicationContext());
        session.checkLogin();

        // get user ID from session
        String[] patientArray = session.getPatientDetails();
        if (patientArray.length > 0)
        {
            patientId = patientArray[0];        // get patient id
            patientName = patientArray[1];      // get patient name
        }
        Log.d("ExercAcity - patientId", patientId+"");
        Log.d("ExercAcity - exerciseId", exerciseId+"");

        Log.d("ExerciseActivity", "HERE");
//        TaskTask taskTask = new TaskTask(this);      // calling to task in order to pull from db the folders assigned
//        taskTask.execute(patientId, exerciseId);       // execute task and add buttons for each folder assigned
        ExerciseTask exerciseTask = new ExerciseTask(this);
        exerciseTask.delegate = this;
        exerciseTask.execute(patientId, exerciseId.trim());
        Log.d("ExerciseActivity", "HERE1");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_exercise, menu);
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

    @Override
    public void processFinish(Exercise exercise)      // getting th exercise from asyncTask
    {
        final String link = exercise.getLink();
        final Exercise copyExercise = exercise;
        TextView exerciseDescription = (TextView)this.findViewById(R.id.exerciseDescription);
        ImageView imageView = (ImageView)this.findViewById(R.id.imageView);
        ImageButton linkButton = (ImageButton)this.findViewById(R.id.button_link);
        Button fileButton = (Button)this.findViewById(R.id.button_file);

        if(exercise.getDescription()!=null)
        {
            exerciseDescription.setText(exercise.getDescription());
        }
        else
        {
            exerciseDescription.setVisibility(View.GONE);
        }
        if(exercise.getLink() != null)
        {
            linkButton.setOnClickListener(new View.OnClickListener() {

                public void onClick(View v)
                {
                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(link)));
                    Log.i("Video", "Video Playing....");

                }
            });
        }
        else
        {
            linkButton.setVisibility(View.GONE);
        }
        if(exercise.getFilePath()!=null)
        {
            fileButton.setVisibility(View.VISIBLE);
            fileButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v)
                {
                    DownloadFileTask downloadFileTask = new DownloadFileTask(ExerciseActivity.this);
                    downloadFileTask.execute(copyExercise);
                }
            });
        }
        else
        {
            fileButton.setVisibility(View.GONE);
        }
    }
}
