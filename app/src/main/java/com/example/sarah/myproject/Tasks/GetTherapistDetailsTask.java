package com.example.sarah.myproject.Tasks;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.LinearLayout;
import android.widget.TextView;

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
 * Created by Sarah on 17-Jun-15.
 */
public class GetTherapistDetailsTask extends AsyncTask<String, List<String>, List<String>>
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

    public GetTherapistDetailsTask(Context context)
    {
        this.context = context;
    }
    @Override
    protected List<String> doInBackground(String... params)
    {
        String therapistId = params[0];

        List<String> therapist = new ArrayList<String>();
        try
        {
            // Connecting to the database
            Class.forName("com.mysql.jdbc.Driver").newInstance();
            Connection con = DriverManager.getConnection(DB_URL, USER, PASS);
            Statement statement1 = con.createStatement();

            // After connection
            String query = "SELECT * FROM SpeechTherapist WHERE LicenseId = '" + therapistId + "'";

            ResultSet rs1 = statement1.executeQuery(query);
            if (rs1.next())
            {
                // User exist
                String firstName = rs1.getString("FirstName");
                String lastName = rs1.getString("LastName");


                therapist.add(firstName);
                therapist.add(lastName);
                therapist.add(therapistId);
            }

        } catch (Exception e)         // if connection to db didn't succeed
        {
            e.printStackTrace();
        }
        return therapist;
    }

    @Override
    protected void onPostExecute(List<String> strings)
    {
        super.onPostExecute(strings);
        Log.i("SIZE STR", strings.size()+"");
        //NewMessageActivity.therapistDetails = strings;
        TextView therapistNameTextView = (TextView)((Activity)context).findViewById(R.id.therapist_textView);
        therapistNameTextView.setText(strings.get(0) + " " + strings.get(1));
    }
}
