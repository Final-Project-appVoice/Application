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

import com.example.sarah.myproject.Class.AsyncResponse;
import com.example.sarah.myproject.Class.CameraPreview;
import com.example.sarah.myproject.Class.DropboxSession;
import com.example.sarah.myproject.Class.Exercise;
import com.example.sarah.myproject.Class.SessionManager;
import com.example.sarah.myproject.R;
import com.example.sarah.myproject.Tasks.ExerciseTask;
import com.example.sarah.myproject.Tasks.UploadVideo;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ExerciseActivity extends Activity implements AsyncResponse
{
    private TextView exerciseTitle;
    private String patientId, patientName;

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
    private static String videoPath;
    public static final int MEDIA_TYPE_VIDEO = 2;
    private static final String TAG = "Recorder";
    public static Exercise returnedExercise;

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


        mSurfaceView = (SurfaceView)findViewById(R.id.surfaceView);
        exerciseDescription = (TextView)findViewById(R.id.exerciseDescription);
        imageView = (ImageView)findViewById(R.id.imageView);
        linkButton = (ImageButton)findViewById(R.id.button_link);
        fileButton = (Button)this.findViewById(R.id.button_file);
        startRecorder = (ImageButton)findViewById(R.id.button_start_recorder);
        stopRecorder = (ImageButton)findViewById(R.id.button_stop_recorder);
        layoutButtons = (LinearLayout)findViewById(R.id.layout_buttons);

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
        }

//        mSurfaceView.setVisibility(View.VISIBLE);


            mCamera = getCameraInstance();
            mCamera.setDisplayOrientation(90);
            mPreview = new CameraPreview(ExerciseActivity.this, mCamera, mSurfaceView);
            isRecording = false;
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

    @Override
    public void processFinish(Exercise exercise)      // getting th exercise from asyncTask
    {
//        Log.d("BEFORE", exercise.toString());
//        returnedExercise = exercise;
//        Log.d("AFTER   ", returnedExercise.toString());
//        final String link = exercise.getLink();
//        final Exercise copyExercise = exercise;
//        TextView exerciseDescription = (TextView)this.findViewById(R.id.exerciseDescription);
//        ImageView imageView = (ImageView)this.findViewById(R.id.imageView);
//        ImageButton linkButton = (ImageButton)this.findViewById(R.id.button_link);
//        Button fileButton = (Button)this.findViewById(R.id.button_file);
//        ImageButton startRecorder = (ImageButton)this.findViewById(R.id.button_start_recorder);
//        ImageButton stopRecorder = (ImageButton)this.findViewById(R.id.button_stop_recorder);
//        mSurfaceView = (SurfaceView)this.findViewById(R.id.surfaceView);
        // if the exercise contains a description
//        if(exercise.getDescription()!=null)
//        {
//            exerciseDescription.setText(exercise.getDescription());
//        }
//        else
//        {
//            exerciseDescription.setVisibility(View.GONE);
//        }
//        //  if the exercise contains a link to follow
//        if(exercise.getLink() != null)
//        {
//            linkButton.setOnClickListener(new View.OnClickListener() {
//
//                public void onClick(View v)
//                {
//                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(link)));
//                    Log.i("Video", "Video Playing....");
//
//                }
//            });
//        }
//        else
//        {
//            linkButton.setVisibility(View.GONE);
//        }
//        // if the exercise contains a file to open
//        if(exercise.getFilePath()!=null)
//        {
//            fileButton.setVisibility(View.VISIBLE);
//            fileButton.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v)
//                {
////                    DownloadFileTask downloadFileTask = new DownloadFileTask(ExerciseActivity.this);
////                    downloadFileTask.execute(copyExercise);
//                }
//            });
//        }
//        else
//        {
//            fileButton.setVisibility(View.GONE);
//        }

       //  if the exercise is requiring a video record
//        if(exercise.getIsVideo() == 1)
//        {
//            Log.i("Video", "YES VIDEO");
//            mSurfaceView.setVisibility(View.VISIBLE);
//
//            mCamera = getCameraInstance();
//            mCamera.setDisplayOrientation(90);
//            mPreview = new CameraPreview(ExerciseActivity.this, mCamera, mSurfaceView);
//            isRecording = false;
//            VideoTask videoTask = new VideoTask(this, surfaceView, startRecorder, stopRecorder, patientName);
//            videoTask.execute();
           // VideoRecorder videoRecorder = new VideoRecorder(this, surfaceView, startRecorder, stopRecorder, patientName);
     //   }
//        else
//        {
//            Log.i("Video", "NO VIDEO");
//            mSurfaceView.setVisibility(View.GONE);
//        }
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
