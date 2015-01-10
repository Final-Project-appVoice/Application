package com.example.sarah.myproject.Activities;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.example.sarah.myproject.Class.Patient;
import com.example.sarah.myproject.Class.SessionManager;
import com.example.sarah.myproject.Dal.DalPatient;
import com.example.sarah.myproject.R;

public class VoiceHygieneActivity extends Activity
{
    SessionManager session;
    DalPatient dalPatient;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_voice_hygiene);

        session = new SessionManager(getApplicationContext());
        dalPatient = new DalPatient();

        TextView welcome_voiceHygiene = (TextView)findViewById(R.id.welcome_voiceHygiene);
        String patientId = session.getUserDetails();

        Toast.makeText(getApplicationContext(), "User Login Status: " + session.isLoggedIn(), Toast.LENGTH_LONG).show();

        Patient p = dalPatient.patientDetails(patientId, this);

        welcome_voiceHygiene.setText("Hello " + p.getFName());
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_voice_hygiene, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
