package com.example.sarah.myproject.Tasks;

import android.app.Activity;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.example.sarah.myproject.Adapters.FoldersAdapter;
import com.example.sarah.myproject.Class.SessionManager;
import com.example.sarah.myproject.Dal.DalConstant;
import com.example.sarah.myproject.R;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Sarah on 31-May-15.
 */
public class FolderTask extends AsyncTask<String, List<String>, List<String>> implements AdapterView.OnItemClickListener  // <Params, Progress, Result>
{
    // constant to log in the DB
    public static final String DB_URL = DalConstant.DB_URL;
    public static final String USER = DalConstant.USER;
    public static final String PASS = DalConstant.PASS;
    public LinearLayout bar;
    private Context context;
    private FoldersAdapter foldersAdapter;


    public SessionManager session;    // Session Manager Class

    public FolderTask(Context context)
    {
        this.context = context;

        // Session Manager
        session = new SessionManager(context);
    }


    @Override
    protected List<String> doInBackground(String... params)      // the params we send from execute()
    {
        String patientId = params[0];    // patientId

        List<String> folders = new ArrayList<String>();
        try {
            // Connecting to the database
            Class.forName("com.mysql.jdbc.Driver").newInstance();
            Connection con = DriverManager.getConnection(DB_URL, USER, PASS);
            Statement statement1 = con.createStatement();
            Statement statement2 = con.createStatement();
            // After connection
            String query = "SELECT FolderId FROM AssignedExercise WHERE PatientId = '" + patientId + "'";

            ResultSet rs1 = statement1.executeQuery(query);
            Log.d("SIZE DB FOLDER", rs1.getFetchSize()+"");
            while (rs1.next())
            {
                // User exist
                int folderId = rs1.getInt("FolderId");
                String query1 = "SELECT Name FROM Folder WHERE FolderId = '" + folderId + "'";
                ResultSet rs2 = statement2.executeQuery(query1);
                if(rs2.next())
                {
                    String folderName = folderId + "," + rs2.getString("Name");
                    if (!folders.contains(folderName))
                    {
                        folders.add(folderName);
                    }
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

        foldersAdapter = new FoldersAdapter(context, android.R.layout.activity_list_item, R.id.folderList, folders);       // calling to the adapter list -- folderList = list without adapter
        ListView listView = (ListView)((Activity)context).findViewById(R.id.folderList);     // the list of folders
        listView.setAdapter(foldersAdapter);        // adapting to the folder list the adapter list


    }



    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id)
    {

    }
}
