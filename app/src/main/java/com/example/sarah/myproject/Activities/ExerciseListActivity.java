package com.example.sarah.myproject.Activities;

import android.app.Activity;
import android.content.Intent;
import android.hardware.Camera;
import android.hardware.camera2.CameraManager;
import android.media.CamcorderProfile;
import android.media.MediaRecorder;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.SurfaceView;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.sarah.myproject.Class.CameraPreview;
import com.example.sarah.myproject.Class.DropboxSession;
import com.example.sarah.myproject.Class.SessionManager;
import com.example.sarah.myproject.R;
import com.example.sarah.myproject.Tasks.ExerciseListTask;
import com.example.sarah.myproject.Tasks.UploadVideo;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

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
        stopRecorder.setVisibility(View.GONE);
        startRecorder.setVisibility(View.VISIBLE);


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

//        mCamera = getCameraInstance();
//        mCamera.setDisplayOrientation(90);
//        mPreview = new CameraPreview(this, mCamera, mSurfaceView);
//        isRecording = false;

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
    public void onClick_buttonStartRecorder(View view)
    {
        startRecorder.setVisibility(View.GONE);
        stopRecorder.setVisibility(View.VISIBLE);

        startMediaRecorder();
    }

    public void onClick_buttonStopRecorder(View view)
    {
        stopRecorder.setVisibility(View.GONE);
        startRecorder.setVisibility(View.VISIBLE);

        stopMediaRecorder();
    }

    /**
     * A safe way to get an instance of the Camera object.
     */
    public static Camera getCameraInstance() {
        Camera c = null;
        try {
            c = Camera.open(1); // attempt to get a Camera instance
        } catch (Exception e) {
            // Camera is not available (in use or does not exist)
        }
        return c; // returns null if camera is unavailable
    }

    private boolean prepareVideoRecorder()
    {

        mMediaRecorder = new MediaRecorder();

        // Step 1: Unlock and set camera to MediaRecorder
        mCamera.unlock();
        mMediaRecorder.setCamera(mCamera);

        // Step 2: Set sources
        mMediaRecorder.setAudioSource(MediaRecorder.AudioSource.CAMCORDER);
        mMediaRecorder.setVideoSource(MediaRecorder.VideoSource.CAMERA);

        // Step 3: Set a CamcorderProfile (requires API Level 8 or higher)
        mMediaRecorder.setProfile(CamcorderProfile.get(1, CamcorderProfile.QUALITY_HIGH));

        // Step 4: Set output file
        mMediaRecorder.setOutputFile(getOutputMediaFile(MEDIA_TYPE_VIDEO).toString());



        // Step 5: Set the preview output
        mMediaRecorder.setPreviewDisplay(mSurfaceView.getHolder().getSurface());//mPreview.getHolder().getSurface());

        // Step 6: Prepare configured MediaRecorder
        try {
            mMediaRecorder.prepare();
        } catch (IllegalStateException e)
        {
            Log.d(TAG, "IllegalStateException preparing MediaRecorder: " + e.getMessage());
            releaseMediaRecorder();
            return false;
        } catch (IOException e) {
            Log.d(TAG, "IOException preparing MediaRecorder: " + e.getMessage());
            releaseMediaRecorder();
            return false;
        }
        return true;
    }

    private void startMediaRecorder()
    {
        if (!isRecording)
        {
            //initialize video camera
            if (prepareVideoRecorder())
            {
                // Camera is available and unlocked, MediaRecorder is prepared,
                // now you can start recording
                mMediaRecorder.start();
                isRecording = true;
            }
            else
            {
                // prepare didn't work, release the camera
                Log.i(TAG, "prepare is false");
                releaseMediaRecorder();
            }
        }
    }

    private void stopMediaRecorder()
    {
        if (isRecording)
        {
            // stop recording and release camera
            mMediaRecorder.stop();  // stop the recording
            releaseMediaRecorder(); // release the MediaRecorder object
            mCamera.lock();         // take camera access back from MediaRecorder
            isRecording = false;
            UploadVideo upload = new UploadVideo(this, DropboxSession.getDropboxSession(), "/", mediaFile);
            upload.execute();
        }
    }

    private void releaseMediaRecorder() {
        if (mMediaRecorder != null) {
            mMediaRecorder.reset();   // clear recorder configuration
            mMediaRecorder.release(); // release the recorder object
            mMediaRecorder = null;
            mCamera.lock();           // lock camera for later use
        }
    }

    private void releaseCamera() {
        if (mCamera != null) {
            mCamera.release();        // release the camera for other applications
            mCamera = null;
        }
    }


    /** Create a file Uri for saving an image or video */
    private  Uri getOutputMediaFileUri(int type){
        return Uri.fromFile(getOutputMediaFile(type));
    }

    /** Create a File for saving an image or video */
    private File getOutputMediaFile(int type) {
        // To be safe, you should check that the SDCard is mounted
        // using Environment.getExternalStorageState() before doing this.

        File mediaStorageDir = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES), "MyCameraApp");

        // This location works best if you want the created images to be shared
        // between applications and persist after your app has been uninstalled.

        // Create the storage directory if it does not exist
        if (! mediaStorageDir.exists()){
            if (! mediaStorageDir.mkdirs()){
                Log.d("MyCameraApp", "failed to create directory");
                return null;
            }
        }

        // Create a media file name
        String timeStamp = new SimpleDateFormat("ddMMyyyy_HHmmss").format(new Date());
        String copyPatientName = patientName;
        copyPatientName = copyPatientName.replace(" ", "_");
        if(type == MEDIA_TYPE_VIDEO)
        {
            Log.d(TAG, "save media");
            String mediaName = File.separator +
                    "VID_"+ copyPatientName +"_"+ timeStamp + ".mp4";
            Log.d("Media Name", mediaName);
            mediaFile = new File(mediaStorageDir.getPath() + File.separator +
                    "VID_"+ copyPatientName + "_" + timeStamp + ".mp4");
//            FileInputStream inputStream = null;
//            try
//            {
//                inputStream = new FileInputStream(mediaFile);
//            } catch (FileNotFoundException e)
//            {
//                e.printStackTrace();
//                Log.d(TAG, "FILE INPUT STREAM ERROR");
//            }
//            try {
//                KeyStore.Entry response = (KeyStore.Entry) DropboxSession.getDropboxSession().putFile(mediaName, inputStream,
//                        mediaFile.length(), null, null);
//            } catch (DropboxException e)
//            {
//                e.printStackTrace();
//                Log.d(TAG, "FILE KEY ENTRY STREAM ERROR");
//            }


            Log.d(TAG, mediaFile.getAbsolutePath().toString());
        }
        else
        {
            return null;
        }
        videoPath = mediaStorageDir.getPath() + File.separator +
                "VID_"+ timeStamp + ".mp4";
        return mediaFile;
    }

}
