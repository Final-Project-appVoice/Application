package com.example.sarah.myproject.Tasks;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.dropbox.client2.DropboxAPI;
import com.dropbox.client2.exception.DropboxException;
import com.example.sarah.myproject.Class.DropboxSession;
import com.example.sarah.myproject.Class.FileOpen;
import com.example.sarah.myproject.Class.SessionManager;
import com.example.sarah.myproject.Class.Task;
import com.example.sarah.myproject.Dal.DalConstant;
import com.example.sarah.myproject.R;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.Statement;

/**
 * Created by Sarah on 10-Jun-15.
 * Here we show getting metadata for a directory and downloading a file in a
 * background thread, trying to show typical exception handling and flow of
 * control for an app that downloads a file from Dropbox.
 */

public class DownloadFileTask extends AsyncTask<Task, Void, Void>  // <Params, Progress, Result>
{

    private Context context;
    private ProgressDialog progressDialog;
    private SessionManager session;
    private String patientId, therapistId;

    // constant to log in the DB
    private static final String DB_URL = DalConstant.DB_URL;
    private static final String USER = DalConstant.USER;
    private static final String PASS = DalConstant.PASS;

    public DownloadFileTask(Context context)
    {
        // We set the context this way so we don't accidentally leak activities
        this.context = context;
        session = new SessionManager(context);
        String[] patientDetails = session.getPatientDetails();
        patientId = patientDetails[0];
        therapistId = patientDetails[2];
    }

    @Override
    protected void onPreExecute()
    {
        super.onPreExecute();
        progressDialog = ProgressDialog.show(context, "Wait", "Downloading...");
    }

    @Override
    protected Void doInBackground(Task... params)
    {
        Task actualTask = params[0];

        // create new file to save the one we are downloading
        File file = new File("/sdcard/AppVoice" + actualTask.getImagePath().trim());
        Log.d("File", actualTask.getImagePath());
        FileOutputStream outputStream = null;
        try
        {
            outputStream = new FileOutputStream(file);

        }
        catch (FileNotFoundException e)
        {
            e.printStackTrace();
            Toast.makeText(context, "Error in creating new file", Toast.LENGTH_LONG);
        }
        DropboxAPI.DropboxFileInfo info = null;     // get dropbox info to connect to it
        try
        {

            Log.w("API", DropboxSession.getDropboxSession().toString());
            // connect to dropbox and get file we need
            info = (DropboxSession.getDropboxSession()).getFile("/" + actualTask.getImagePath().trim(), null, outputStream, null);
            Log.i("DbExampleLog", "The file's rev is: " + info.getMetadata().rev);
            // opening the downloaded file
            FileOpen.openFile(context, file.getPath().toString());      // open new activity and open file

            try
            {
                // Connecting to the database
                Class.forName("com.mysql.jdbc.Driver").newInstance();
                Connection con = DriverManager.getConnection(DB_URL, USER, PASS);

                Statement statement1 = con.createStatement();

                // After connection
                String query1 = "INSERT INTO SubmittedTask (TaskId, PatientId, TherapistId) VALUES (?, ?, ?, ?)";
                PreparedStatement preparedStmt = con.prepareStatement(query1);
                preparedStmt.setInt(1, actualTask.getTaskId());
                preparedStmt.setString(2, patientId);
                preparedStmt.setString(3, therapistId);
                preparedStmt.setInt(4, actualTask.getExerciseId());

                // execute the preparedstatement
                preparedStmt.execute();
            }
            catch (Exception e)         // if connection to db didn't succeed
            {
                e.printStackTrace();
            }

        }
        catch (DropboxException e)
        {
            Toast.makeText(context, "Error in connecting to the server", Toast.LENGTH_LONG).show();

            Log.d("ERROR", "e");
            e.printStackTrace();
            return null;
        } catch (IOException e)
        {
            e.printStackTrace();
            Toast.makeText(context, "Error in opening the file", Toast.LENGTH_LONG).show();
            return null;
        }
        return null;
    }
    protected void onPostExecute(Void object)
    {
        super.onPostExecute(object);
        progressDialog.dismiss();

        ImageView image = (ImageView)((Activity)context).findViewById(R.id.read_task);
        image.setVisibility(View.VISIBLE);


    }
}
