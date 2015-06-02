package com.example.sarah.myproject.Dal;

import android.app.Activity;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.TextView;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

/**
 * Created by Sarah on 26-Apr-15.
 */
public class MySqlDal extends AsyncTask<String, String, String>  // <Params, Progress, Result>
{
    private final Activity activity;
    private final Context context;
    private int branchId;
    TextView sqltry;
    public static final String DB_URL = DalConstant.DB_URL;
    public static final String USER = DalConstant.USER;
    public static final String PASS = DalConstant.PASS;

    public MySqlDal(Activity activity, Context context, int branchId)
    {
        this.activity = activity;
        this.context = context;
        this.branchId=branchId;
        Log.d("MY SQL ", "HEREEEE");
        //averageTextView = (TextView) activity.findViewById(R.id.timeTextView);
    }

    @Override
    protected String doInBackground(String... params)
    {
        int response = 0;
        String responseName = "";
        Log.d("BACKGROUND ", "HEREE");
        try {
            boolean running = true;
            Class.forName("com.mysql.jdbc.Driver").newInstance();
            Connection con = DriverManager.getConnection(DB_URL, USER, PASS);

            String result = "\nDatabase connection success\n";
            Statement st = con.createStatement();
            Log.d("AFTER CONNECTION ", "HEREE");
           // Statement st2 = con.createStatement();
           while(running)
           {
                String query = "SELECT FirstName FROM SpeechTherapist WHERE LicenseId = '123'";
                String query1 = "SELECT Patientcol FROM Patient WHERE idPatient = '1'";
                Log.d("RUNNING ", "HEREE");
                ResultSet rs = st.executeQuery(query1);
                while (rs.next()){
                   // String name = rs.getString("FirstName");
                    String name = rs.getString("Patientcol");
                    Log.d("WHILE ", "HEREE");
                    Log.d("NAME ", name);

                    responseName = name;
                    publishProgress(name);
                }
                if(isCancelled())
                {
                    running = false;
                }
           }
            Log.d("RESULT ", result);
        } catch (Exception e) {
            Log.d("EXCEPTION ", "HERRREE");
            e.printStackTrace();
        }
        return responseName;
    }

    protected void onProgressUpdate(String... progress)
    {
        Log.v("ON PROGRESS", progress[0]);
        sqltry.setText(progress[0]);
    }
}


