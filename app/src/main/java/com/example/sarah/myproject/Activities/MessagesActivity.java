package com.example.sarah.myproject.Activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.example.sarah.myproject.Class.SessionManager;
import com.example.sarah.myproject.R;

public class MessagesActivity extends Activity
{
    private SessionManager session;
    private String[] patientArray;
    private String patientId, patientName;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_messages);

        session = new SessionManager(getApplicationContext());

        /**
         * Call this function whenever you want to check user login
         * This will redirect user to LoginActivity is he is not
         * logged in
         * */
        session.checkLogin();

        // get user ID from session
        patientArray = session.getPatientDetails();
        if (patientArray.length > 0)
        {
            patientId = patientArray[0];        // get patient id from session
            patientName = patientArray[1];      // get patient name from session
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.common_menu, menu);
        getActionBar().setDisplayHomeAsUpEnabled(true);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        switch(id)
        {
            case R.id.folders_icon:
            {
                Intent i = new Intent(this, PatientActivity.class);
                this.startActivity(i);
                this.finish();
                break;
            }
            case R.id.messages_icon:
            {
                Intent i = new Intent(this, MessagesActivity.class);
                this.startActivity(i);
                this.finish();
                break;
            }
            case R.id.account_icon:
            {
                Intent i = new Intent(this, AccountActivity.class);
                this.startActivity(i);
                this.finish();
                break;
            }
            case R.id.action_logout:
            {
                session.logoutUser();
                break;
            }
            case  R.id.action_exit:
            {
                moveTaskToBack(true);
                MessagesActivity.this.finish();
                break;
            }
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed()
    {
        super.onBackPressed();
        this.finishActivity(MODE_PRIVATE);
    }
}
