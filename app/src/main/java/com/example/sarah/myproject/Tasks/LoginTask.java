package com.example.sarah.myproject.Tasks;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import com.example.sarah.myproject.Activities.PatientActivity;
import com.example.sarah.myproject.Class.AlertDialogManager;
import com.example.sarah.myproject.Class.Patient;
import com.example.sarah.myproject.Class.SessionManager;
import com.example.sarah.myproject.Dal.DalConstant;
import com.example.sarah.myproject.R;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

/**
 * Created by Sarah on 31-May-15.
 */
public class LoginTask extends AsyncTask<String, Patient, Patient>  // <Params, Progress, Result>
{
    // constant to log in the DB
    public static final String DB_URL = DalConstant.DB_URL;
    public static final String USER = DalConstant.USER;
    public static final String PASS = DalConstant.PASS;
    public LinearLayout bar;
    private Context context;
    private Button loginButton;
    public AlertDialogManager alert;
    public SessionManager session;    // Session Manager Class

    public LoginTask(Context context)
    {
        this.context = context;
        bar = (LinearLayout)((Activity)context).findViewById(R.id.ProgressBar);
        loginButton = (Button)((Activity)context).findViewById(R.id.button);
        // Session Manager
        session = new SessionManager(context);

        alert = new AlertDialogManager();     // Alert Dialog Manager
    }

    protected void onPreExecute()
    {
        bar.setVisibility(View.VISIBLE);            // set progress bar visible
        loginButton.setClickable(false);
    }
    @Override
    protected Patient doInBackground(String... params)      // the params we send from execute()
    {
        String username = params[0];    // username
        String password = params[1];    // password
        try
        {
            // Connecting to the database
            Class.forName("com.mysql.jdbc.Driver").newInstance();
            Connection con = DriverManager.getConnection(DB_URL, USER, PASS);
            Statement statement = con.createStatement();

            // After connection
            String query = "SELECT * FROM Patient WHERE PatientId = '" + username + "' AND Password = '" + password + "'";

            ResultSet rs = statement.executeQuery(query);
            if (rs.next())
            {
                // User exist
                String patientId = rs.getString("PatientId");
                String firstName = rs.getString("FirstName");
                String lastName = rs.getString("LastName");
                String mail = rs.getString("Mail");
                String phone = rs.getString("PhoneNumber");
                String address = rs.getString("Address");
                String hmo = rs.getString("Hmo");
                String pwd = rs.getString("Password");
                String therapistId = rs.getString("TherapistId");

                Patient patient = new Patient(patientId, firstName, lastName, mail, address, phone, hmo, therapistId);
                return patient;

            }
            else
            {
                // TODO: user doesnt exist
                alert.showAlertDialog(context, "Login failed..", "Please enter username and password", false);
            }
        } catch (Exception e)         // if connection to db didn't succeed
        {
            e.printStackTrace();
        }
        return null;
    }


    @Override
    protected void onPostExecute(Patient patient)      // The result of the operation computed by doInBackground(Params...)
    {
        super.onPostExecute(patient);
        bar.setVisibility(View.GONE);
        loginButton.setClickable(true);

        if (patient != null)
        {
            //session.createPatientLoginSession(patient);
            session.createLoginSession(patient.getId(), patient.getFName()+ " " + patient.getLName());

            // Staring MainActivity
            Intent i = new Intent(context, PatientActivity.class);
            Activity activity = (Activity) context;

            activity.startActivity(i);
            activity.finish();
        }
    }


}
