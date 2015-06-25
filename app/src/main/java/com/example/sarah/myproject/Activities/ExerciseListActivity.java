package com.example.sarah.myproject.Activities;

import android.app.Activity;
import android.content.Intent;
import android.hardware.Camera;
import android.hardware.camera2.CameraManager;
import android.media.MediaRecorder;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.SurfaceView;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.sarah.myproject.Class.CameraPreview;
import com.example.sarah.myproject.Class.SessionManager;
import com.example.sarah.myproject.R;
import com.example.sarah.myproject.Tasks.ExerciseListTask;

import java.io.File;

public class ExerciseListActivity extends Activity
{
    private TextView folderTitle, folderDescription;
    private String folderName, patientId, patientName, folderID;
    private SessionManager session;
    private File mediaFile;
    public static final int MEDIA_TYPE_VIDEO = 2;
    private static final String TAG = "Recorder";
    private SurfaceView mSurfaceView;
    private CameraPreview mPreview;
    private Camera mCamera;
    private CameraManager cameraManager;
    private ImageButton startRecorder, stopRecorder;
    private String[] patientArray;
    private Handler mHandler = new Handler();
    private MediaRecorder mMediaRecorder;
    private boolean isRecording;
    private static String videoPath;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exercise_list);

        folderTitle = (TextView)findViewById(R.id.folderTitle);
        folderDescription = (TextView)findViewById(R.id.folderDescription);
        startRecorder = (ImageButton)findViewById(R.id.button_start_recorder);
        stopRecorder = (ImageButton)findViewById(R.id.button_stop_recorder);
        mSurfaceView = (SurfaceView)findViewById(R.id.surfaceView);

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

        ExerciseListTask exerciseTask = new ExerciseListTask(this);      // calling to task in order to pull from db the folders assigned
        exerciseTask.execute(patientId, folderID+"");       // execute task and add buttons for each folder assigned
        Log.w("Check", "AFTER");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.common_menu, menu);
        getActionBar().setTitle(folderName);
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
                this.finish();
                break;
            }
            case R.id.messages_icon:
            {
                Intent i = new Intent(this, MessagesActivity.class);
                this.startActivity(i);
                this.finish();
                break;
            }
            case R.id.account_icon:
            {
                Intent i = new Intent(this, AccountActivity.class);
                this.startActivity(i);
                this.finish();
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
                ExerciseListActivity.this.finish();
                break;
            }

        }



        return super.onOptionsItemSelected(item);
    }

}
