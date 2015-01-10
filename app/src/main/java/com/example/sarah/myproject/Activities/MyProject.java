package com.example.sarah.myproject.Activities;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.sarah.myproject.Class.AlertDialogManager;
import com.example.sarah.myproject.Class.Patient;
import com.example.sarah.myproject.Class.SessionManager;
import com.example.sarah.myproject.Dal.DalPatient;
import com.example.sarah.myproject.R;


public class MyProject extends Activity
{
    DalPatient dalPatient;
    Patient patient;
    EditText userName_editText;
    EditText password_editText;
//    public static final String MyPREFERENCES = "MyPrefsFile" ;
//    public static final String PATIENT_ID = "PatientId";
//    //public static final String PATIENT_PWD = "PatientPwd";
    public static boolean isLoggedIn = false;
    SharedPreferences settings;
    // Alert Dialog Manager
    AlertDialogManager alert = new AlertDialogManager();

    // Session Manager Class
    SessionManager session;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_project);

        // Session Manager
        session = new SessionManager(getApplicationContext());

        Toast.makeText(getApplicationContext(), "User Login Status: " + session.isLoggedIn(), Toast.LENGTH_LONG).show();

        userName_editText = (EditText)findViewById(R.id.username);
        password_editText = (EditText)findViewById(R.id.pwd);
        //patient_textView = (TextView)findViewById(R.id.patientTextView);
        settings = getSharedPreferences(session.MyPREFERENCES, MODE_PRIVATE);
        addNewPatient();

    }


//    protected void onResume()
//    {
//        settings = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
//        if (settings.contains(PATIENT_ID))
//        {
//            Log.w("sharePref: ", "settings CONTAINS!");
//            if(settings.contains(PATIENT_PWD))
//            {
//                Intent i = new Intent(this, PatientActivity.class);
//                startActivity(i);
//            }
//        }
//        super.onResume();
//    }


    private void addNewPatient()
    {
        dalPatient = new DalPatient();
        patient = new Patient("3", "David", "Cohen", "davidco@gmail.com", "Jerusalem", "0525234327", "Clalit");
        Log.w("warning:", patient.toString());
        dalPatient.addNewPatient(patient, "p", this);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.my_project, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void login_button(View view)
    {
        // the editText
        String username = userName_editText.getText().toString();
        String password = password_editText.getText().toString();

        Log.w("Check", username +" "+ password);

//        if(username.trim().length() > 0 && password.trim().length() > 0)
//        {
//            // check if patient exists and keep the patient details in Cursor c
//            Cursor c = dalPatient.findPatientByIdAndPwd(username, password, this);
//            SharedPreferences.Editor editor = settings.edit();
//
//            //if exists, change activity
//            if (dalPatient.isPatientExists(c)) {
//                Log.w("exist", "the patient exists!!");
//                Intent i = new Intent(this, PatientActivity.class);            //change to PatientActivity
//                String str = dalPatient.showDetails(username, this);           // save patient details in str
//                i.putExtra("PatientText", str);                                 //save preference
//               // i.putExtra("PatientId", username);
//                i.putExtra(session.PATIENT_ID, username);
//                //i.putExtra("PatientPwd", password);
//                //isLoggedIn = true;
//
//                //editor.commit();
//                editor.apply();            // commit with the preferences
//                startActivity(i);           // go to activity
//            }
//            else
//            {
//                Log.w("exist", "the patient NOT exists!!");
//                // username / password doesn't match
//                alert.showAlertDialog(MyProject.this, "Login failed..", "Username/Password is incorrect", false);
//            }
//
//        }
//        else
//        {
//            alert.showAlertDialog(MyProject.this, "Login failed..", "Please enter username and password", false);
//        }


        if(username.trim().length() > 0 && password.trim().length() > 0)
        {

            // check if patient exists and keep the patient details in Cursor c
            Cursor c = dalPatient.findPatientByIdAndPwd(username, password, this);

            //if exists, change activity
            if(dalPatient.isPatientExists(c))
            {
                Patient p = dalPatient.patientDetails(username, this);
                session.createLoginSession(p.getId(), p.getMail());

                // Staring MainActivity
                Intent i = new Intent(getApplicationContext(), PatientActivity.class);
                startActivity(i);
                finish();
            }
            else
            {
                // username / password doesn't match
                alert.showAlertDialog(MyProject.this, "Login failed..", "Username/Password is incorrect", false);
            }
        }
        else
        {
            alert.showAlertDialog(MyProject.this, "Login failed..", "Please enter username and password", false);
        }

    }
}
