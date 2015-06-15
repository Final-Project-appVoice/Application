package com.example.sarah.myproject.Activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.sarah.myproject.Class.SessionManager;
import com.example.sarah.myproject.R;
import com.example.sarah.myproject.Tasks.AccountTask;

public class AccountActivity extends Activity
{
    private SessionManager session;
    private String[] patientArray;
    private String patientId, patientName;
    private EditText mail, address, phone;
    private Button buttonUpdate, buttonSave;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account);

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

        mail = (EditText)findViewById(R.id.mail_editText);
        phone = (EditText)findViewById(R.id.phone_editText);
        address = (EditText)findViewById(R.id.address_editText);

        buttonSave = (Button)findViewById(R.id.button_save);
        buttonUpdate = (Button)findViewById(R.id.button_update);

        mail.setEnabled(false);
        phone.setEnabled(false);
        address.setEnabled(false);

        buttonUpdate.setVisibility(View.VISIBLE);
        buttonSave.setVisibility(View.GONE);

        AccountTask accountTask = new AccountTask(this);      // calling to task in order to pull from db the folders assigned
        accountTask.execute(patientId);       // execute task and add buttons for each folder assigned
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.common_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

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
                AccountActivity.this.finish();
                break;
            }

        }


        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed()
    {
        super.onBackPressed();
        this.finish();
    }

    public void onClick_buttonUpdate(View view)
    {
        mail.setEnabled(true);
        phone.setEnabled(true);
        address.setEnabled(true);

        buttonUpdate.setVisibility(View.GONE);
        buttonSave.setVisibility(View.VISIBLE);
    }

    public void onClick_buttonContact(View view)
    {
        Intent i = new Intent(this, MessagesActivity.class);
        startActivity(i);
        this.finish();
    }

    public void onClick_buttonSave(View view)
    {
        mail.setEnabled(false);
        phone.setEnabled(false);
        address.setEnabled(false);

        buttonUpdate.setVisibility(View.VISIBLE);
        buttonSave.setVisibility(View.GONE);
    }
}
