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
import com.example.sarah.myproject.Adapters.TasksAdapter;
import com.example.sarah.myproject.Class.Task;
import com.example.sarah.myproject.Dal.DalConstant;
import com.example.sarah.myproject.R;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Sarah on 08-Jun-15.
 *
 * Get all tasks from exercise using exercise id and patient id, get description exercise.
 */
public class TaskTask extends AsyncTask<String, List<Task>, List<Task>> implements AdapterView.OnItemClickListener  // <Params, Progress, Result>
{
    // constant to log in the DB
    public static final String DB_URL = DalConstant.DB_URL;
    public static final String USER = DalConstant.USER;
    public static final String PASS = DalConstant.PASS;


    public LinearLayout bar;
    private Context context;
    private TasksAdapter tasksAdapter;
    private TextView exerciseDescription;
    private String description;
    private List<Task> taskList;

    public TaskTask(Context context)
    {
        this.context = context;
        bar = (LinearLayout)((Activity)context).findViewById(R.id.ProgressBar);

    }


    @Override
    protected List<Task> doInBackground(String... params)      // the params we send from execute()
    {
        String patientId = params[0].trim();    // patientId
        String exerciseId = params[1].trim();       //exerciseId

        List<String> tasks = new ArrayList<String>();       // list of all tasks in exercise

        try
        {
            // Connecting to the database
            Class.forName("com.mysql.jdbc.Driver").newInstance();
            Connection con = DriverManager.getConnection(DB_URL, USER, PASS);

            exerciseDescription = (TextView)((Activity) context).findViewById(R.id.exerciseDescription);        // get text view of exercise description

            Statement statement1 = con.createStatement();
            Statement statement2 = con.createStatement();
            Statement statement3 = con.createStatement();

            Log.d("Task - patienId", "*" + patientId + "*");
            Log.d("Task = exId", "*"+exerciseId+"*");
            // After connection
            String query1 = "SELECT Description FROM Exercise WHERE ExerciseId = '" + exerciseId + "'";       // get exercise description
            String query2 = "SELECT * FROM Task WHERE ExerciseId = '" + exerciseId + "'";   // get all tasks

            ResultSet rs1 = statement1.executeQuery(query1);
            if(rs1.next())
            {
                description = rs1.getString("Description");

            }
            ResultSet rs2 = statement2.executeQuery(query2);
            taskList = new ArrayList<>();
            while (rs2.next())      // run on all the list of tasks
            {
                Log.d("Task", "IN WHILE");
                int taskId = rs2.getInt("TaskId");      // gte task id from db
                Log.w("TASK id", taskId + "");
                String query3 = "SELECT TaskId FROM SubmittedTask WHERE TaskId = '" + taskId + "' AND PatientId = '" + patientId + "'";   // get all tasks
                ResultSet rs3 = statement3.executeQuery(query3);
                Task task;
                if(rs3.next())      // the task is submitted
                {
                    task = new Task(taskId, rs2.getString("TaskTitle"), rs2.getString("Comment"), rs2.getString("Description"), rs2.getInt("ExerciseId"), rs2.getString("ImagePath"), true);
                }
                else        // the task is not submitted
                {
                    task = new Task(taskId, rs2.getString("TaskTitle"), rs2.getString("Comment"), rs2.getString("Description"), rs2.getInt("ExerciseId"), rs2.getString("ImagePath"), false);
                }

                taskList.add(task);
                Log.d("Task", task.toString());
            }
            Log.d("ExerciseTask", "AFTER");
        } catch (Exception e)         // if connection to db didn't succeed
        {
            e.printStackTrace();
        }

        return taskList;
    }

    @Override
    protected void onPreExecute()
    {
        bar.setVisibility(View.VISIBLE);            // set progress bar visible
    }

    @Override
    protected void onPostExecute(List<Task> tasks)      // The result of the operation computed by doInBackground(Params...)
    {
        super.onPostExecute(tasks);
        bar.setVisibility(View.GONE);            // set progress bar not visible
        Log.d("TaskTask", "HERE1");
        exerciseDescription.setText(description);       // set description exercise
        tasksAdapter = new TasksAdapter(context, android.R.layout.activity_list_item, R.id.tasksList, tasks);       // calling to the adapter list -- folderList = list without adapter
        ListView listView = (ListView) ((Activity) context).findViewById(R.id.tasksList);     // the list of folders
        listView.setAdapter(tasksAdapter);        // adapting to the folder list the adapter list
        listView.setOnItemClickListener(this);
        Log.d("TaskTask", "HERE2");
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
