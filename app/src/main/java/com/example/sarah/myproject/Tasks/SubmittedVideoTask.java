package com.example.sarah.myproject.Tasks;

import android.content.Context;
import android.os.AsyncTask;

import com.example.sarah.myproject.Class.SessionManager;
import com.example.sarah.myproject.Dal.DalConstant;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Created by Sarah on 07-Jul-15.
 */
public class SubmittedVideoTask extends AsyncTask<String, Void, Void> // <Params, Progress, Result>
{
    // constant to log in the DB
    public static final String DB_URL = DalConstant.DB_URL;
    public static final String USER = DalConstant.USER;
    public static final String PASS = DalConstant.PASS;

    public String exerciseId, exerciseName, patientId, therapistId, imagePath;
    public Context context;
    private SessionManager session;
    private String[] patientArray;

    public SubmittedVideoTask(Context context)
    {
        this.context = context;
        session = new SessionManager(context.getApplicationContext());
        patientArray = session.getPatientDetails();
        if (patientArray.length > 0)
        {
            patientId = patientArray[0];        // get patient id from session
            therapistId = patientArray[2];
        }
    }

    @Override
    protected Void doInBackground(String... params)
    {
        exerciseId = params[0];
        exerciseName = params[1];
        imagePath = params[2];

        try
        {
            // Connecting to the database
            Class.forName("com.mysql.jdbc.Driver").newInstance();
            Connection con = DriverManager.getConnection(DB_URL, USER, PASS);

            Statement statement1 = con.createStatement();
            Statement statement2 = con.createStatement();

            // After connection
            String query1 = "INSERT INTO SubmittedExercise (ExerciseId, ExerciseName, PatientId, TherapistId, IsDone, VideoLink) VALUES (?, ?, ?, ?, ?, ?)";
            String query2 = "SELECT * FROM SubmittedExercise WHERE ExerciseId = '" + exerciseId + "'";
            String query3 = "UPDATE SubmittedExercise SET ImagePath = ? WHERE ExerciseId = '" + exerciseId + "'";

            try
            {
                ResultSet rs2 = statement2.executeQuery(query2);
                if (rs2.next())     // if submitted exercise exists
                {
                    PreparedStatement preparedStmt = con.prepareStatement(query3);      // update exercise is done
                    preparedStmt.setString(1, imagePath);
                }
                else
                {
                    //String query1 = "INSERT OR REPLACE INTO SubmittedExercise (ExerciseId, ExerciseName, PatientId, TherapistId) VALUES (" + Integer.parseInt(exerciseId) + ", '" + exerciseName + "', '" + patientId + "', '" + therapistId + "')";       // get exercise description
                    PreparedStatement preparedStmt = con.prepareStatement(query1);
                    preparedStmt.setInt(1, Integer.parseInt(exerciseId));
                    preparedStmt.setString(2, exerciseName);
                    preparedStmt.setString(3, patientId);
                    preparedStmt.setString(4, therapistId);
                    preparedStmt.setInt(5, 0);
                    preparedStmt.setString(6, imagePath);
                    // execute the preparedstatement
                    preparedStmt.execute();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
            con.close();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);
    }
}
