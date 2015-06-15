package com.example.sarah.myproject.Tasks;

import android.app.Activity;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.sarah.myproject.Adapters.ExercisesAdapter;
import com.example.sarah.myproject.Class.Patient;
import com.example.sarah.myproject.Dal.DalConstant;
import com.example.sarah.myproject.R;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

/**
 * Created by Sarah on 15-Jun-15.
 */
public class AccountTask extends AsyncTask<String, Patient, Patient> implements AdapterView.OnItemClickListener  // <Params, Progress, Result>
{
    public static final String DB_URL = DalConstant.DB_URL;
    public static final String USER = DalConstant.USER;
    public static final String PASS = DalConstant.PASS;
    public LinearLayout bar;
    private String patientId;
    private Context context;
    private ExercisesAdapter exercisesAdapter;
    private EditText id, mail, address, phone, hmo, therapist;
    private TextView name;

    public AccountTask(Context context)
    {
        this.context = context;
        bar = (LinearLayout)((Activity)context).findViewById(R.id.ProgressBar);
        name = (TextView)((Activity)context).findViewById(R.id.patientNameTextView);
        id = (EditText)((Activity)context).findViewById(R.id.id_editText);
        mail = (EditText)((Activity)context).findViewById(R.id.mail_editText);
        address = (EditText)((Activity)context).findViewById(R.id.address_editText);
        phone = (EditText)((Activity)context).findViewById(R.id.phone_editText);
        hmo = (EditText)((Activity)context).findViewById(R.id.hmo_editText);
        therapist = (EditText)((Activity)context).findViewById(R.id.therapist_editText);
    }

    @Override
    protected Patient doInBackground(String... params)
    {
        patientId = params[0];

        Patient patient = null;

        try {
            // Connecting to the database
            Class.forName("com.mysql.jdbc.Driver").newInstance();
            Connection con = DriverManager.getConnection(DB_URL, USER, PASS);

            Statement statement1 = con.createStatement();
            Statement statement2 = con.createStatement();
            String query1 = "SELECT * FROM Patient WHERE PatientId = '" + patientId + "'";

            ResultSet rs1 = statement1.executeQuery(query1);
            if (rs1.next())
            {
                patient = new Patient(patientId, rs1.getString("FirstName"), rs1.getString("LastName"), rs1.getString("Mail"), rs1.getString("Address"), rs1.getString("PhoneNumber"), rs1.getString("Hmo"), rs1.getString("TherapistId"));
                Log.d("PATIENT", patient.toString());
                String query2 = "SELECT * FROM SpeechTherapist WHERE LicenseId = '" + patient.getTherapistId() + "'";
                ResultSet rs2 = statement2.executeQuery(query2);
                if (rs2.next())
                {
                    patient.setTherapistId(rs2.getString(1) + " " + rs2.getString(2));
                }

            }
        }
            catch (Exception e)         // if connection to db didn't succeed
            {
                e.printStackTrace();
            }


        return patient;
    }

    protected void onPreExecute()
    {
        bar.setVisibility(View.VISIBLE);            // set progress bar visible
    }

    @Override
    protected void onPostExecute(Patient patient)      // The result of the operation computed by doInBackground(Params...)
    {
        super.onPostExecute(patient);

        bar.setVisibility(View.GONE);            // set progress bar not visible

        therapist.setText(patient.getTherapistId());
        name.setText(patient.getFName() + " " + patient.getLName());
        id.setText(patient.getId());
        mail.setText(patient.getMail());
        address.setText(patient.getAddress());
        phone.setText(patient.getPhone());
        hmo.setText(patient.getHMO());
    }


    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id)
    {

    }
}
