package com.example.sarah.myproject.Tasks;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.sarah.myproject.Class.AsyncResponse;
import com.example.sarah.myproject.Class.Exercise;
import com.example.sarah.myproject.Dal.DalConstant;
import com.example.sarah.myproject.R;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
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
                exercise = new Exercise(exerciseId, title, folderId, rs1.getString("TherapistId"), rs1.getString("Description"), rs1.getString("ImagePath"), rs1.getString("FilePath"), Boolean.parseBoolean(rs1.getInt("IsVideo")+""), rs1.getString("Link"));
                Log.i("DB EXERCISE", exercise.toString());
            }
            else
            {
                Log.i("DB EXERCISE", "OUT");
            }
            String query2 = "INSERT INTO SubmittedExercise (ExerciseId, ExerciseName, PatientId, TherapistId) VALUES (?, ?, ?, ?)";
            PreparedStatement preparedStmt = con.prepareStatement(query2);
            preparedStmt.setInt(1, exercise.getId());
            preparedStmt.setString(2, exercise.getTitle());
            preparedStmt.setString(3, patientId);
            preparedStmt.setString(4, exercise.getTherapistId());
            // execute the preparedstatement
            preparedStmt.execute();
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
        bar.setVisibility(View.GONE);            // set progress bar not visible

//        final String filePath = exercise.getFilePath();

    }
}
