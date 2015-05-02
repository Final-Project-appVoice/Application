package com.example.sarah.myproject.Dal;

import android.app.Activity;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.TextView;

import com.example.sarah.myproject.R;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

/**
 * Created by Sarah on 26-Apr-15.
 */
public class MySqlDal extends AsyncTask<String,String,String>
{
    private final Activity activity;
    private final Context context;
    private int branchId;
    TextView sqltry;
    private static final String DB_URL = "jdbc:mysql://259a2693-7bcb-46e5-adb2-a47a0101d17f.mysql.sequelizer.com:3306/db259a26937bcb46e5adb2a47a0101d17f";
    private static final String USER = "oosbszkpqgotoeqb";
    private static final String PASS = "4Cu2nEaWFcZNZVKsx2vbuSYdTw46T5wipApPhfTdG4FQeSSZKpwPWKPjLrhXJcdJ";

    public MySqlDal(Activity activity,Context context,int branchId)
    {
        this.activity = activity;
        this.context=context;
        this.branchId=branchId;
        sqltry = (TextView) activity.findViewById(R.id.sql_try);
        Log.d("MY SQL ", "HEREEEE");
        //averageTextView = (TextView) activity.findViewById(R.id.timeTextView);
    }

    @Override
    protected String doInBackground(String... params) {
        int response = 0;
        String responseName = "";
        Log.d("BACKGROUND ", "HEREE");
        try {
            boolean running = true;
            Class.forName("com.mysql.jdbc.Driver").newInstance();
            Connection con = DriverManager.getConnection(DB_URL, USER, PASS);
           // Connection con = DriverManager.getConnection(connectionString);

            String result = "\nDatabase connection success\n";
            Statement st = con.createStatement();
            Log.d("AFTER CONNECTION ", "HEREE");
            Statement st2 = con.createStatement();
//            while(running) {
                String query = "SELECT FirstName FROM SpeechTherapist WHERE LicenseId = '123'";
                //String query2 = "SELECT AverageTime FROM Queue WHERE BusinessId = '" + branchId + "'";
                Log.d("RUNNING ", "HEREE");
                ResultSet rs = st.executeQuery(query);
               // ResultSet rs2 = st2.executeQuery(query2);
                while (rs.next()){//&& rs2.next()) {
                    //int currentQueue = rs.getInt("CurrentQueue");
                    String name = rs.getString("FirstName");
                    Log.d("WHILE ", "HEREE");
                    Log.d("NAME ", name);
                    //Time t=rs2.getTime("AverageTime");
                    //System.out.println("The Time From DB Id : "+ t );
                    responseName = name;
                    publishProgress(name);
                }
                if(isCancelled())
                    running=false;
         //   }
            Log.d("RESULT ", result);
        } catch (Exception e) {
            Log.d("EXCEPTION ", "HEREE");
            e.printStackTrace();
        }
        return responseName;
    }

    protected void onProgressUpdate(String... progress)
    {
        Log.v("ON PROGRESS", progress[0]);
        sqltry.setText(progress[0]);
        //averageTextView.setText(progress[1]);
    }
}


