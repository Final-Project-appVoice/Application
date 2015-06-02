package com.example.sarah.myproject.Tasks;

import android.content.Context;
import android.os.AsyncTask;
import android.widget.LinearLayout;

import com.example.sarah.myproject.Class.SessionManager;
import com.example.sarah.myproject.Dal.DalConstant;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Sarah on 31-May-15.
 */
public class PatientFolderTask extends AsyncTask<String, List<String>, List<String>>  // <Params, Progress, Result>
{
    // constant to log in the DB
    public static final String DB_URL = DalConstant.DB_URL;
    public static final String USER = DalConstant.USER;
    public static final String PASS = DalConstant.PASS;
    public LinearLayout bar;
    private Context context;

    public SessionManager session;    // Session Manager Class

    public PatientFolderTask(Context context) {
        this.context = context;

        // Session Manager
        session = new SessionManager(context);
    }


    @Override
    protected List<String> doInBackground(String... params)      // the params we send from execute()
    {
        String patientId = params[0];    // patientId
        String password = params[1];    // password
        List<String> folders = new ArrayList<String>();
        try {
            // Connecting to the database
            Class.forName("com.mysql.jdbc.Driver").newInstance();
            Connection con = DriverManager.getConnection(DB_URL, USER, PASS);
            Statement statement = con.createStatement();

            // After connection
            String query = "SELECT FolderName FROM AssignedExercise WHERE PatientId = '" + patientId + "' AND Password = '" + password + "'";

            ResultSet rs = statement.executeQuery(query);

            while (rs.next()) {
                // User exist
                String folderName = rs.getString("FolderName");
                if (!folders.contains(folderName))
                {
                    folders.add(folderName);
                }

            }

        } catch (Exception e)         // if connection to db didn't succeed
        {
            e.printStackTrace();
        }
        return folders;
    }


    @Override
    protected void onPostExecute(List<String> folders)      // The result of the operation computed by doInBackground(Params...)
    {
        super.onPostExecute(folders);

        for(int i = 0; i < folders.size(); i++)
        {

        }
    }


}
