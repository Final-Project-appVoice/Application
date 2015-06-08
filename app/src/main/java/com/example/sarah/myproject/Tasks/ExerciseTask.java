package com.example.sarah.myproject.Tasks;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.example.sarah.myproject.Activities.TaskExerciseActivity;
import com.example.sarah.myproject.Adapters.ExercisesAdapter;
import com.example.sarah.myproject.Dal.DalConstant;
import com.example.sarah.myproject.R;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Sarah on 02-Jun-15.
 *
 * Getting the list of all exercises using folderId and patientId, and folder description.
 */
public class ExerciseTask extends AsyncTask<String, List<String>, List<String>> implements AdapterView.OnItemClickListener  // <Params, Progress, Result>
{
    // constant to log in the DB
    public static final String DB_URL = DalConstant.DB_URL;
    public static final String USER = DalConstant.USER;
    public static final String PASS = DalConstant.PASS;
    public LinearLayout bar;
    private Context context;
    private ExercisesAdapter exercisesAdapter;
    private TextView folderDescription;
    private String description;

    public ExerciseTask(Context context)
    {
        this.context = context;


    }


    @Override
    protected List<String> doInBackground(String... params)      // the params we send from execute()
    {
        String patientId = params[0].trim();    // patientId
        String folderId = params[1].trim();
        //int folderId = Integer.getInteger(params[1].trim());  // folderId

        List<String> exercises = new ArrayList<String>();

        try
        {
            // Connecting to the database
            Class.forName("com.mysql.jdbc.Driver").newInstance();
            Connection con = DriverManager.getConnection(DB_URL, USER, PASS);

            folderDescription = (TextView)((Activity) context).findViewById(R.id.folderDescription);

            Statement statement1 = con.createStatement();
            Statement statement2 = con.createStatement();
            Statement statement3 = con.createStatement();

            Log.d("ExerciseTask - patienId", "*"+patientId+"*");
            Log.d("ExerciseTask = folderId", "*"+folderId+"*");
            // After connection
            String query = "SELECT ExerciseId FROM AssignedExercise WHERE PatientId = '" + patientId + "' AND FolderId = '" + folderId + "'";
            String query3 = "SELECT Description FROM Folder WHERE FolderId = '" + folderId + "'";

            ResultSet rs3 = statement3.executeQuery(query3);
            if(rs3.next())
            {
                description = rs3.getString("Description");

            }
            ResultSet rs1 = statement1.executeQuery(query);
            while (rs1.next())
            {
                Log.d("ExerciseTask", "IN WHILE");
                // User exist
                int exerciseId = rs1.getInt("ExerciseId");
                Log.w("EXERCISE id", exerciseId + "");
                String query2 = "SELECT Title FROM Exercise WHERE ExerciseId = '" + exerciseId + "'";

                ResultSet rs2 = statement2.executeQuery(query2);
                if (rs2.next())
                {
                    Log.d("ExerciseTask", "IN IF");
                    String exerciseName = rs2.getString("Title");
                    if(!exercises.contains(exerciseName))
                    {
                        Log.d("ExerciseTask", "IN LIST CONTAINS");
                        Log.d("Exercise Title", exerciseName);
                        exercises.add(exerciseName + "," + exerciseId);     // return exercise details: <exerciseName,exerciseId>
                    }
                }
            }
            Log.d("ExerciseTask", "AFTER");
        } catch (Exception e)         // if connection to db didn't succeed
        {
            e.printStackTrace();
        }
        return exercises;
    }


    @Override
    protected void onPostExecute(List<String> exercises)      // The result of the operation computed by doInBackground(Params...)
    {
        super.onPostExecute(exercises);

        folderDescription.setText(description);
        exercisesAdapter = new ExercisesAdapter(context, android.R.layout.activity_list_item, R.id.exerciseList, exercises);       // calling to the adapter list -- folderList = list without adapter
        ListView listView = (ListView) ((Activity) context).findViewById(R.id.exerciseList);     // the list of folders
        listView.setAdapter(exercisesAdapter);        // adapting to the folder list the adapter list
        listView.setOnItemClickListener(this);

    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id)
    {
        TextView exerciseIdTextView = (TextView)view.findViewById(R.id.exerciseId);
        TextView exerciseTitleTextView = (TextView)view.findViewById(R.id.exerciseTitle);

        String exerciseTitle = exerciseTitleTextView.getText().toString();        // getting exercise name from exercise selected
        String exerciseId = exerciseIdTextView.getText().toString();       // getting exercise id from exercise selected
        Log.d("ON CLICK", exerciseTitle + " " + exerciseId);

        Intent i = new Intent(view.getContext(), TaskExerciseActivity.class);      // opening selected exercise
        i.putExtra("exerciseTitle", exerciseTitle);       // passing folderName to the new intent
        i.putExtra("exerciseId", exerciseId);       // passing folderId to the new intent
        view.getContext().startActivity(i);
    }
}