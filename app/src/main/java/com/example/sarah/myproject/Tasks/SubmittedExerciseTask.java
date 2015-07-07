package com.example.sarah.myproject.Tasks;

import android.content.Context;
import android.os.AsyncTask;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.sarah.myproject.Adapters.TasksAdapter;
import com.example.sarah.myproject.Class.SessionManager;
import com.example.sarah.myproject.Class.Task;
import com.example.sarah.myproject.Dal.DalConstant;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

/**
 * Created by Sarah on 15-Jun-15.
 */
public class SubmittedExerciseTask extends AsyncTask<String, List<Task>, List<Task>> implements AdapterView.OnItemClickListener  // <Params, Progress, Result>
{

    // constant to log in the DB
    public static final String DB_URL = DalConstant.DB_URL;
    public static final String USER = DalConstant.USER;
    public static final String PASS = DalConstant.PASS;


    public LinearLayout bar;
    private Context context;
    private String[] patientArray;
    private TasksAdapter tasksAdapter;
    private TextView exerciseDescription;
    private String exerciseName, exerciseId, patientId, therapistId;
    private SessionManager session;
    private List<Task> taskList;

    public SubmittedExerciseTask(Context context) {
        this.context = context;
        session = new SessionManager(context.getApplicationContext());
        patientArray = session.getPatientDetails();
        if (patientArray.length > 0) {
            patientId = patientArray[0];        // get patient id from session
            therapistId = patientArray[2];
        }
    }

    @Override
    protected List<Task> doInBackground(String... params) {
        exerciseId = params[0];
        exerciseName = params[1];

        try {
            // Connecting to the database
            Class.forName("com.mysql.jdbc.Driver").newInstance();
            Connection con = DriverManager.getConnection(DB_URL, USER, PASS);

            Statement statement1 = con.createStatement();
            Statement statement2 = con.createStatement();

            // After connection
            String query1 = "INSERT INTO SubmittedExercise (ExerciseId, ExerciseName, PatientId, TherapistId, IsDone) VALUES (?, ?, ?, ?, ?)";
            String query2 = "SELECT * FROM SubmittedExercise WHERE ExerciseId = '" + exerciseId + "'";
            String query3 = "UPDATE SubmittedExercise SET IsDone = ? WHERE ExerciseId = '" + exerciseId + "'";

            try {
                ResultSet rs2 = statement2.executeQuery(query2);
                if (rs2.next())     // if submitted exercise exists
                {
                    PreparedStatement preparedStmt = con.prepareStatement(query3);
                    preparedStmt.setInt(1, 1);
                } else {
                    //String query1 = "INSERT OR REPLACE INTO SubmittedExercise (ExerciseId, ExerciseName, PatientId, TherapistId) VALUES (" + Integer.parseInt(exerciseId) + ", '" + exerciseName + "', '" + patientId + "', '" + therapistId + "')";       // get exercise description
                    PreparedStatement preparedStmt = con.prepareStatement(query1);
                    preparedStmt.setInt(1, Integer.parseInt(exerciseId));
                    preparedStmt.setString(2, exerciseName);
                    preparedStmt.setString(3, patientId);
                    preparedStmt.setString(4, therapistId);
                    preparedStmt.setInt(5, 1);
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
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

    }
}
