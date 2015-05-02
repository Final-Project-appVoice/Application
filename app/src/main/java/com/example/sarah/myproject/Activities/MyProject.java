package com.example.sarah.myproject.Activities;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import com.example.sarah.myproject.Class.AlertDialogManager;
import com.example.sarah.myproject.Class.Patient;
import com.example.sarah.myproject.Class.Patients;
import com.example.sarah.myproject.Class.SessionManager;
import com.example.sarah.myproject.Dal.DalPatient;
import com.example.sarah.myproject.Dal.MySqlDal;
import com.example.sarah.myproject.R;
import com.microsoft.windowsazure.mobileservices.MobileServiceClient;
import com.microsoft.windowsazure.mobileservices.MobileServiceTable;

public class MyProject extends Activity
{
    DalPatient dalPatient;
    MySqlDal mySqlDal;
    Patient patient;
    EditText userName_editText;
    EditText password_editText;
    public static boolean isLoggedIn = false;
    SharedPreferences settings;
    AlertDialogManager alert = new AlertDialogManager();     // Alert Dialog Manager
    SessionManager session;    // Session Manager Class
    private MobileServiceClient mClient;
    private MobileServiceTable<Patients> mToDoTable;



    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_project);
        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }
        //StorageService storage = new StorageService(this);


        setupUI(findViewById(R.id.main_layout));
        // Session Manager
        session = new SessionManager(getApplicationContext());

        //Toast.makeText(getApplicationContext(), "User Login Status: " + session.isLoggedIn(), Toast.LENGTH_LONG).show();

        userName_editText = (EditText)findViewById(R.id.username);
        password_editText = (EditText)findViewById(R.id.pwd);
        //patient_textView = (TextView)findViewById(R.id.patientTextView);
        settings = getSharedPreferences(session.MyPREFERENCES, MODE_PRIVATE);
        addNewPatient();
        mySqlDal = new MySqlDal(this, this, getTaskId());
        mySqlDal.execute();

    }


    private void addNewPatient()
    {

        dalPatient = new DalPatient();
        patient = new Patient("3", "David", "Cohen", "davidco@gmail.com", "Jerusalem", "0525234327", "Clalit");
        Log.w("warning:", patient.toString());
        Patients mPatient = new Patients("3", "David", "Levy");
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
//    Dismiss keyboard
    public static void hideSoftKeyboard(Activity activity)
    {
        InputMethodManager inputMethodManager = (InputMethodManager)  activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(activity.getCurrentFocus().getWindowToken(), 0);
    }

    public void setupUI(View view) {

        //Set up touch listener for non-text box views to hide keyboard.
        if(!(view instanceof EditText)) {

            view.setOnTouchListener(new View.OnTouchListener() {

                public boolean onTouch(View v, MotionEvent event) {
                    hideSoftKeyboard(MyProject.this);
                    return false;
                }

            });
        }

        //If a layout container, iterate over children and seed recursion.
        if (view instanceof ViewGroup) {

            for (int i = 0; i < ((ViewGroup) view).getChildCount(); i++) {

                View innerView = ((ViewGroup) view).getChildAt(i);

                setupUI(innerView);
            }
        }
    }
}
