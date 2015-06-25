package com.example.sarah.myproject.Tasks;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.util.Log;
import android.view.SurfaceView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.sarah.myproject.Class.AsyncResponse;
import com.example.sarah.myproject.Class.Exercise;
import com.example.sarah.myproject.Dal.DalConstant;
import com.example.sarah.myproject.R;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

/**
 * Created by Sarah on 24-Jun-15.
 */
public class ExerciseTask extends AsyncTask<String, Exercise, Exercise>  // <Params, Progress, Result>
{
    public AsyncResponse delegate=null;

    public static final String DB_URL = DalConstant.DB_URL;
    public static final String USER = DalConstant.USER;
    public static final String PASS = DalConstant.PASS;

    private LinearLayout bar;
    private Context context;
    String filePath;

    public ExerciseTask(Context context)
    {
        this.context = context;
        bar = (LinearLayout)((Activity)context).findViewById(R.id.ProgressBar);
    }

    protected void onPreExecute()
    {
        bar.setVisibility(View.VISIBLE);            // set progress bar visible
    }

    @Override
    protected Exercise doInBackground(String... params)         // <patientId, exerciseId>
    {
        String patientId = params[0];
        int exerciseId = Integer.parseInt(params[1]);
        Exercise exercise = null;


        try {
            // Connecting to the database
            Class.forName("com.mysql.jdbc.Driver").newInstance();
            Connection con = DriverManager.getConnection(DB_URL, USER,PASS);


            Statement statement1 = con.createStatement();
            String query1 = "SELECT * FROM Exercise WHERE ExerciseId = '" + exerciseId + "'";
            ResultSet rs1 = statement1.executeQuery(query1);
            if(rs1.next())
            {
                Log.i("DB EXERCISE", "IN");
                String title = rs1.getString("Title");
                int folderId = rs1.getInt("FolderId");
                exercise = new Exercise(exerciseId, title, folderId, rs1.getString("TherapistId"), rs1.getString("Description"), rs1.getString("ImagePath"), rs1.getString("FilePath"), rs1.getInt("IsVideo"), rs1.getString("Link"));
                Log.i("DB EXERCISE", exercise.toString());
            }
            else
            {
                Log.i("DB EXERCISE", "OUT");
            }
//            String query2 = "INSERT INTO SubmittedExercise (ExerciseId, ExerciseName, PatientId, TherapistId) VALUES (?, ?, ?, ?)";
//            PreparedStatement preparedStmt = con.prepareStatement(query2);
//            preparedStmt.setInt(1, exercise.getId());
//            preparedStmt.setString(2, exercise.getTitle());
//            preparedStmt.setString(3, patientId);
//            preparedStmt.setString(4, exercise.getTherapistId());
//            // execute the preparedstatement
//            preparedStmt.execute();
        }
        catch (Exception e)         // if connection to db didn't succeed
        {
            e.printStackTrace();
            Log.i("DB EXERCISE", "ERROR");
        }
        return exercise;
    }

    @Override
    protected void onPostExecute(Exercise exercise)      // The result of the operation computed by doInBackground(Params...)
    {
        super.onPostExecute(exercise);
        delegate.processFinish(exercise);       // using interface to pass the Exercise we've received from doInBackground
        final Exercise copyExercise = exercise;
        final String link;
        ImageButton startRecorder = (ImageButton)((Activity)context).findViewById(R.id.button_start_recorder);
        ImageButton stopRecorder = (ImageButton)((Activity)context).findViewById(R.id.button_stop_recorder);
        SurfaceView surfaceView = (SurfaceView)((Activity)context).findViewById(R.id.surfaceView);
        TextView exerciseDescription = (TextView)((Activity)context).findViewById(R.id.exerciseDescription);
        ImageView imageView = (ImageView)((Activity)context).findViewById(R.id.imageView);
        ImageButton linkButton = (ImageButton)((Activity)context).findViewById(R.id.button_link);
        Button fileButton = (Button)((Activity)context).findViewById(R.id.button_file);
        LinearLayout layoutButtons = (LinearLayout)((Activity)context).findViewById(R.id.layout_buttons);

        bar.setVisibility(View.GONE);            // set progress bar not visible
        //ExerciseActivity.returnedExercise = exercise;
//        final String filePath = exercise.getFilePath();
        if(exercise.getIsVideo() == 1)
        {
            Log.i("Video", "YES VIDEO");
            surfaceView.setVisibility(View.VISIBLE);
            layoutButtons.setVisibility(View.VISIBLE);
        }
        else
        {
            Log.i("Video", "NO VIDEO");
            surfaceView.setVisibility(View.GONE);
            layoutButtons.setVisibility(View.GONE);
        }
        if(exercise.getDescription()!=null)
        {
            exerciseDescription.setText(exercise.getDescription());
        }
        else
        {
            exerciseDescription.setVisibility(View.GONE);
        }
        //  if the exercise contains a link to follow
        if(exercise.getLink() != null)
        {
            link = exercise.getLink();
            linkButton.setVisibility(View.VISIBLE);
            linkButton.setOnClickListener(new View.OnClickListener() {

                public void onClick(View v)
                {
                    context.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(link)));
                    Log.i("Video", "Video Playing....");

                }
            });
        }
        else
        {
            linkButton.setVisibility(View.GONE);
        }
        // if the exercise contains a file to open
        if(exercise.getFilePath()!=null)
        {
            fileButton.setVisibility(View.VISIBLE);
            fileButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v)
                {
                    DownloadFileTask downloadFileTask = new DownloadFileTask(context);
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
