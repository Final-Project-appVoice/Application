package com.example.sarah.myproject;

import android.app.Activity;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

import java.util.Locale;


public class MyProject extends Activity
{
    DalPatient dalPatient;
    Patient patient;
    EditText userName_editText;
    EditText password_editText;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        //Locale.forLanguageTag("he");

        userName_editText = (EditText)findViewById(R.id.userName);
        password_editText = (EditText)findViewById(R.id.password);

        Locale locale = new Locale("he");
        locale.setDefault(locale);
        Configuration c = new Configuration();
        c.locale = locale;
        getBaseContext().getResources().updateConfiguration(c, getBaseContext().getResources().getDisplayMetrics());
        MyProject.this.setContentView(R.layout.activity_my_project);

        setContentView(R.layout.activity_my_project);
        dalPatient = new DalPatient();
        patient = new Patient("328786462", "David", "Cohen", "davidco@gmail.com", "Jerusalem", "0525234327", "Clalit");
        dalPatient.addNewPatient(patient, "password", this);
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
        String username = userName_editText.toString();
        String password = password_editText.toString();

    }
}
