package com.example.sarah.myproject.Activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.sarah.myproject.Class.Patient;
import com.example.sarah.myproject.Class.SessionManager;
import com.example.sarah.myproject.Dal.DalPatient;
import com.example.sarah.myproject.R;


public class PatientActivity extends Activity
{

    private TextView patient_textView;
    private Intent i;
    private Patient p;
    private DalPatient dalPatient;
    private DisplayMetrics displayMetrics;
    private int screenHeightPx, screenWidthPx;
    private Button button1, button2, button3;
    private LinearLayout linearLayout_home;
    private SessionManager session;
    String patientId;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient);

        // Session class instance
        session = new SessionManager(getApplicationContext());

        /**
         * Call this function whenever you want to check user login
         * This will redirect user to LoginActivity is he is not
         * logged in
         * */
        session.checkLogin();

        // get user data from session
        patientId = session.getUserDetails();

        Toast.makeText(getApplicationContext(), "User Login Status: " + session.isLoggedIn(), Toast.LENGTH_LONG).show();

        // get the size of the screen in order to set buttons' size relative to the screen
        displayMetrics = this.getResources().getDisplayMetrics();
        screenHeightPx = (int)(Math.round(displayMetrics.heightPixels));        // height on screen
        screenWidthPx = (int)(Math.round(displayMetrics.widthPixels));          //width of screen

        dalPatient = new DalPatient();

        patient_textView = (TextView)findViewById(R.id.patientNameTextView);
        button1 = (Button)findViewById(R.id.imageButton);
        button2 = (Button)findViewById(R.id.imageButton2);
        button3 = (Button)findViewById(R.id.imageButton3);
        linearLayout_home = (LinearLayout)findViewById(R.id.linearLayout_hello);

        i = getIntent();
        p = dalPatient.patientDetails(patientId, this);

        if(p != null)
        {
            fieldTextView();
        }


        button1.setHeight((int)Math.round(0.15*screenHeightPx));
        button1.setWidth((int) Math.round(0.25*screenWidthPx));
        button2.setHeight((int)Math.round(0.15*screenHeightPx));
        button2.setWidth((int) Math.round(0.25*screenWidthPx));
        button3.setHeight((int)Math.round(0.15*screenHeightPx));
        button3.setWidth((int) Math.round(0.25*screenWidthPx));

        linearLayout_home.setPadding(0, 0, 0, (int)Math.round(0.1*screenHeightPx)); // left, top, right, bottom

    }

    private void fieldTextView()
    {
        patient_textView.setText(getString(R.string.welcome_user)+ " " + p.getFName());

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_patient, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
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

    public void onClick_voiceHygiene(View view)
    {
        Intent i = new Intent(this, VoiceHygieneActivity.class);
        startActivity(i);           // go to activity
    }

    public void onClick_theory(View view)
    {
        Intent i = new Intent(this, TheoreticalBackgroundActivity.class);
        startActivity(i);           // go to activity
    }

    public void onClick_exercises(View view)
    {
        Intent i = new Intent(this, ExercisesActivity.class);
        startActivity(i);           // go to activity
    }


    public void onClick_action_logout(MenuItem item)
    {
//        SharedPreferences sharedpreferences = getSharedPreferences(MyProject.MyPREFERENCES, Context.MODE_PRIVATE);
//        Editor editor = sharedpreferences.edit();
//        editor.clear();
//        editor.commit();
//        MyProject.isLoggedIn = false;
//        moveTaskToBack(true);
//        PatientActivity.this.finish();

        // Clear the session data
        // This will clear all session data and
        // redirect user to LoginActivity
        session.logoutUser();
    }

    public void onClick_action_exit(MenuItem item)
    {
        //String id = i.getStringExtra(MyProject.PATIENT_ID);
        String id = i.getStringExtra(session.PATIENT_ID);
        Log.e("exit", id);
        moveTaskToBack(true);
        PatientActivity.this.finish();
    }

//    @Override
//    protected void onPause()
//    {
//        super.onPause();
//    }
//
//    @Override
//    protected void onStop()
//    {
//        super.onStop();
//        session.logoutUser();
//    }

 //   @Override
//    protected void onResume()
//    {
//        super.onResume();
//
//        if(session.isLoggedIn())
//        {
//            session.createLoginSession(p.getFName(), p.getMail());
//        }
//    }


}
