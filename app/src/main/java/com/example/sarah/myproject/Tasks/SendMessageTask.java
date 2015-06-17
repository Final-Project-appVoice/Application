package com.example.sarah.myproject.Tasks;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.sarah.myproject.Adapters.FoldersAdapter;
import com.example.sarah.myproject.Class.SessionManager;
import com.example.sarah.myproject.Dal.DalConstant;
import com.example.sarah.myproject.R;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.Statement;

/**
 * Created by Sarah on 17-Jun-15.
 */
public class SendMessageTask extends AsyncTask<String, Boolean, Boolean>
{
    // constant to log in the DB
    public static final String DB_URL = DalConstant.DB_URL;
    public static final String USER = DalConstant.USER;
    public static final String PASS = DalConstant.PASS;

    private ProgressDialog dialog;
    public LinearLayout bar;
    private Context context;
    private FoldersAdapter foldersAdapter;


    public SessionManager session;    // Session Manager Class

    public SendMessageTask(Context context)
    {
        this.context = context;

        // Session Manager
        session = new SessionManager(context);

        bar = (LinearLayout)((Activity)context).findViewById(R.id.ProgressBar);
    }

    protected void onPreExecute()
    {
        bar.setVisibility(View.VISIBLE);            // set progress bar visible
    }

    @Override
    protected Boolean doInBackground(String... params)
    {
        String patientId = params[0];
        String therapistId = params[1];
        String message = params[2];
        try
        {
            // Connecting to the database
            Class.forName("com.mysql.jdbc.Driver").newInstance();
            Connection con = DriverManager.getConnection(DB_URL, USER, PASS);

            Statement statement1 = con.createStatement();
            //Statement statement2 = con.createStatement();

            // After connection
            String query1 = "INSERT INTO Messages (MessageFrom, MessageTo, Message, IsRead) VALUES (?, ?, ?, ?)";
            //String query1 = "INSERT OR REPLACE INTO SubmittedExercise (ExerciseId, ExerciseName, PatientId, TherapistId) VALUES (" + Integer.parseInt(exerciseId) + ", '" + exerciseName + "', '" + patientId + "', '" + therapistId + "')";       // get exercise description
            PreparedStatement preparedStmt = con.prepareStatement(query1);
            preparedStmt.setString (1, patientId);
            preparedStmt.setString(2, therapistId);
            preparedStmt.setString(3, message);
            preparedStmt.setInt(4, 1);  // isRead = false

            // execute the preparedstatement
            if(preparedStmt.execute())
            {
                return true;
            }
        }
        catch (Exception e)         // if connection to db didn't succeed
        {
            e.printStackTrace();
            return false;
        }
        return false;
    }
    @Override
    protected void onPostExecute(Boolean result)      // The result of the operation computed by doInBackground(Params...)
    {
        super.onPostExecute(result);
        bar.setVisibility(View.GONE);
        if(result)
        {
            Toast.makeText(context, "Message sent successfully", Toast.LENGTH_SHORT).show();
            ((Activity) context).finish();
        }
        else
        {

            Toast.makeText(context, "Error in sending message", Toast.LENGTH_LONG).show();
        }

    }




}
