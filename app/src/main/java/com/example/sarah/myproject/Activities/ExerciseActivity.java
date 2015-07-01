package com.example.sarah.myproject.Activities;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.sarah.myproject.Class.AsyncResponse;
import com.example.sarah.myproject.Class.Exercise;
import com.example.sarah.myproject.Class.SessionManager;
import com.example.sarah.myproject.R;
import com.example.sarah.myproject.Tasks.SubmittedExerciseTask;

public class ExerciseActivity extends Activity implements AsyncResponse
{
    private TextView exerciseTitle;
    private String patientId, patientName;
    private ImageView imageView;
    private TextView exerciseDescription;
    private Button doneButton;
    private SessionManager session;

    Exercise selectedExercise;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exercise);

        // Session class instance
        SessionManager session = new SessionManager(getApplicationContext());
        session.checkLogin();

        Intent i = getIntent();
        String exerciseId = i.getStringExtra("ExerciseId");
        String isDone = i.getStringExtra("IsDone");

        selectedExercise = Exercise.getExerciseById(Integer.parseInt(exerciseId));

        exerciseTitle = (TextView)findViewById(R.id.exerciseTitle);
        if(selectedExercise!=null)
        {
            Log.i("ExerciseActivity", "EXERCISE NOT NULL");
            Log.i("ExerciseActivity", selectedExercise.toString());
            exerciseTitle.setText(selectedExercise.getTitle());       // set exercise name to be the exercise name we got from the intent
        }


        exerciseDescription = (TextView)findViewById(R.id.exerciseDescription);
        imageView = (ImageView)findViewById(R.id.imageView);
        doneButton = (Button)findViewById(R.id.button_tasks_done);

        // get user ID from session
        String[] patientArray = session.getPatientDetails();
        if (patientArray.length > 0)
        {
            patientId = patientArray[0];        // get patient id
            patientName = patientArray[1];      // get patient name
        }
        if(isDone.equals("true"))
        {
            doneButton.setBackgroundColor(Color.GRAY);
            doneButton.setClickable(false);
        }
        else
        {
            doneButton.setBackgroundColor(Color.RED);
            doneButton.setClickable(true);
        }
        Log.d("ExercAcity - patientId", patientId+"");
        Log.d("ExercAcity - exerciseId", exerciseId+"");

        if(selectedExercise!=null)
        {

            if(selectedExercise.getDescription()!=null)
            {
                exerciseDescription.setText(selectedExercise.getDescription());
            }
         /*  if(selectedExercise.getImagePath()!=null)
            {
                Log.i("ExerciseActivity", "BEFORE IMAGE");
                DownloadImageTask downloadImageTask = new DownloadImageTask(this);
                downloadImageTask.execute(selectedExercise.getImagePath());
                Log.i("ExerciseActivity", "AFTER IMAGE");
            }
            else
            {
                imageView.setVisibility(View.GONE);
            } */
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.common_menu, menu);
        getActionBar().setTitle(selectedExercise.getTitle());
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        switch(id)
        {
            case R.id.folders_icon:
            {
                Intent i = new Intent(this, PatientActivity.class);
                this.startActivity(i);
                break;
            }
            case R.id.messages_icon:
            {
                Intent i = new Intent(this, MessagesActivity.class);
                this.startActivity(i);
                break;
            }
            case R.id.account_icon:
            {
                Intent i = new Intent(this, AccountActivity.class);
                this.startActivity(i);
                break;
            }
            case R.id.action_logout:
            {
                session.logoutUser();
                break;
            }
            case  R.id.action_exit:
            {
                moveTaskToBack(true);
                ExerciseActivity.this.finish();
                break;
            }

        }


        return super.onOptionsItemSelected(item);
    }



    @Override
    public void processFinish(Exercise exercise)      // getting th exercise from asyncTask
    {
    }


    public void onClick_button_exercise_done(View view)
    {
        SubmittedExerciseTask submittedExerciseTask = new SubmittedExerciseTask(this);
        submittedExerciseTask.execute(selectedExercise.getId()+"", selectedExercise.getTitle());
        this.finish();
    }
}
