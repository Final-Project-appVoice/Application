package com.example.sarah.myproject.Activities;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.hardware.Camera;
import android.media.CamcorderProfile;
import android.media.MediaRecorder;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.SurfaceView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.sarah.myproject.Class.CameraPreview;
import com.example.sarah.myproject.Class.DropboxSession;
import com.example.sarah.myproject.Class.Exercise;
import com.example.sarah.myproject.Class.SessionManager;
import com.example.sarah.myproject.R;
import com.example.sarah.myproject.Tasks.DownloadImageTask;
import com.example.sarah.myproject.Tasks.UploadVideo;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ExerciseVideoActivity extends Activity
{

    private Context context;
    private ImageButton startRecorder, stopRecorder;
    private SurfaceView mSurfaceView;
    private TextView exerciseDescription;
    private ImageView imageView;
    private ImageButton linkButton;
    private Button fileButton;
    private LinearLayout layoutButtons;

    private MediaRecorder mMediaRecorder;
    private CameraPreview mPreview;
    private File mediaFile;
    private Camera mCamera;
    private boolean isRecording;
    private String patientId, patientName, videoPath;
    public static final int MEDIA_TYPE_VIDEO = 2;
    private static final String TAG = "Recorder";
    public static Exercise returnedExercise;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exercise_video);

        startRecorder = (ImageButton)findViewById(R.id.button_start_recorder);
        stopRecorder = (ImageButton)findViewById(R.id.button_stop_recorder);
        mSurfaceView = (SurfaceView)findViewById(R.id.surfaceView);
        stopRecorder.setVisibility(View.GONE);
        // Session class instance
        SessionManager session = new SessionManager(getApplicationContext());
        session.checkLogin();

        Intent i = getIntent();
        String exerciseId = i.getStringExtra("ExerciseId");

        Exercise selectedExercise = Exercise.getExerciseById(Integer.parseInt(exerciseId));

        // get user ID from session
        String[] patientArray = session.getPatientDetails();
        if (patientArray.length > 0)
        {
            patientId = patientArray[0];        // get patient id
            patientName = patientArray[1];      // get patient name
        }

        mCamera = getCameraInstance();

          mCamera.setDisplayOrientation(90);
            mPreview = new CameraPreview(this, mCamera, mSurfaceView);
            isRecording = false;
            Log.d("ExerciseActivity", "HERE1");

            if (selectedExercise != null)
            {
                if(selectedExercise.getImagePath()!=null)
                {
                    if(!selectedExercise.getImagePath().equals(""))
                    {
                        DownloadImageTask downloadImageTask = new DownloadImageTask(this);
                        downloadImageTask.execute(selectedExercise.getImagePath());
                    }
                }
            }
//        }
//        else
//        {
//            Toast.makeText(this, "Error in opening camera", Toast.LENGTH_LONG).show();
//        }
    }


    @Override
    public void onPause()
    {
        super.onPause();
        Log.i("On pause", "HERE");
        stopMediaRecorder();
        releaseCamera();              // release the camera immediately on pause event
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_exercise_video, menu);
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
    private Uri getOutputMediaFileUri(int type){
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
        videoPath = mediaStorageDir.getPath() + File.separator + "VID_"+ timeStamp + ".mp4";
        return mediaFile;
    }
}
