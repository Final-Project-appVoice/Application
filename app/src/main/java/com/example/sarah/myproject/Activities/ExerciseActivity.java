package com.example.sarah.myproject.Activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.sarah.myproject.Class.AsyncResponse;
import com.example.sarah.myproject.Class.Exercise;
import com.example.sarah.myproject.Class.SessionManager;
import com.example.sarah.myproject.R;
import com.example.sarah.myproject.Tasks.ExerciseTask;

public class ExerciseActivity extends Activity implements AsyncResponse
{
    private TextView exerciseTitle;
    private String patientId, patientName;
    private ImageView imageView;
    private TextView exerciseDescription;

    public static Exercise returnedExercise;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exercise);

        Intent i = getIntent();
        String exerciseId = i.getStringExtra("ExerciseId");
        Exercise selectedExercise = Exercise.getExerciseById(Integer.parseInt(exerciseId));

        exerciseTitle = (TextView)findViewById(R.id.exerciseTitle);
        if(selectedExercise!=null)
        {
            exerciseTitle.setText(selectedExercise.getTitle());       // set exercise name to be the exercise name we got from the intent
        }
        // Session class instance
        SessionManager session = new SessionManager(getApplicationContext());
        session.checkLogin();


        exerciseDescription = (TextView)findViewById(R.id.exerciseDescription);
        imageView = (ImageView)findViewById(R.id.imageView);
        //linkButton = (ImageButton)findViewById(R.id.button_link);
        //fileButton = (Button)this.findViewById(R.id.button_file);
       // startRecorder = (ImageButton)findViewById(R.id.button_start_recorder);
       // stopRecorder = (ImageButton)findViewById(R.id.button_stop_recorder);
       // layoutButtons = (LinearLayout)findViewById(R.id.layout_buttons);

        // get user ID from session
        String[] patientArray = session.getPatientDetails();
        if (patientArray.length > 0)
        {
            patientId = patientArray[0];        // get patient id
            patientName = patientArray[1];      // get patient name
        }
        Log.d("ExercAcity - patientId", patientId+"");
        Log.d("ExercAcity - exerciseId", exerciseId+"");

        //Log.d("ExerciseActivity", );

        ExerciseTask exerciseTask = new ExerciseTask(this);
        exerciseTask.delegate = this;
        exerciseTask.execute(patientId, exerciseId.trim());

        if(returnedExercise!=null)
        {
            if(returnedExercise.getDescription()!=null)
            {
                exerciseDescription.setText(returnedExercise.getDescription());
            }
            if(returnedExercise.getImagePath()!=null)
            {

            }
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_exercise, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
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
    }


}
