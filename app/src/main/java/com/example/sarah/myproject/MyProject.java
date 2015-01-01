package com.example.sarah.myproject;

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
import android.widget.TextView;


public class MyProject extends Activity
{
    DalPatient dalPatient;
    Patient patient;
    EditText userName_editText;
    EditText password_editText;
    TextView patient_textView;

    SharedPreferences settings;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_project);

        userName_editText = (EditText)findViewById(R.id.username);
        password_editText = (EditText)findViewById(R.id.pwd);
        patient_textView = (TextView)findViewById(R.id.patientTextView);
        settings = getSharedPreferences("MyPrefsFile", MODE_PRIVATE);
        String num = userName_editText.getText().toString();

        addNewPatient();

    }

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
    public boolean onOptionsItemSelected(MenuItem item) {
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
        String username = userName_editText.getText().toString();
        String password = password_editText.getText().toString();
        Log.w("Check", username +" "+ password);
        Cursor c = dalPatient.findPatientByIdAndPwd(username, password, this);

        SharedPreferences.Editor editor=settings.edit();


        if(dalPatient.isPatientExists(c))
        {
            Log.w("exist", "the patient exists!!");

            Intent i = new Intent(this, PatientActivity.class);
            String str = dalPatient.showDetails(username, this);
            //patient_textView.setText(str);
            i.putExtra("PatientText", str);

            editor.commit();
            startActivity(i);
        }
        else
        {
            Log.w("exist", "the patient NOT exists!!");
        }


    }
}
