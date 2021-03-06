package com.example.sarah.myproject.Class;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

import com.example.sarah.myproject.Activities.MyProject;

import java.util.Set;
import java.util.TreeSet;

/**
 * Created by Sarah on 07-Jan-15.
 */
public class SessionManager
{
    SharedPreferences pref;     // Shared Preferences
    Editor editor;      // Editor for Shared preferences
    Context _context;       // Context

    int PRIVATE_MODE = 0;       // Shared pref mode
//    private static final String PREF_NAME = "AndroidHivePref";      // Sharedpref file name
//    private static final String IS_LOGIN = "IsLoggedIn";        // All Shared Preferences Keys
//    public static final String KEY_ID = "PatientId";       // User name (make variable public to access from outside)
//    public static final String KEY_EMAIL = "PatientEmail";     // Email address (make variable public to access from outside)
    public static final String MyPREFERENCES = "MyPrefsFile" ;
    public static final String PATIENT_ID = "PatientId";
    public static final String PATIENT_SET = "PatientSet";
    public static final String PATIENT_NAME = "PatientName";
    private static final String IS_LOGIN = "IsLoggedIn";        // All Shared Preferences Keys
    public static final String THERAPIST_ID = "TherapistId";

    private static final Patient LOGIN_PATIENT = null;
    //public static final String PATIENT_PWD = "PatientPwd";

    // Constructor
    public SessionManager(Context context)
    {
        this._context = context;
        //pref = _context.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
        pref = _context.getSharedPreferences(MyPREFERENCES, PRIVATE_MODE);
        editor = pref.edit();
    }

    /**
     * Create login session
     * */
    public void createLoginSession(String patientId, String name, String therapistId)
    {
        // Storing login value as TRUE
        editor.putBoolean(IS_LOGIN, true);

        // Storing name in pref
        //editor.putString(KEY_ID, name);
        editor.putString(PATIENT_ID, patientId);

        // Storing name in pref
        editor.putString(PATIENT_NAME, name);

        // Storing therapist id in pref
        editor.putString(THERAPIST_ID, therapistId);

        // commit changes
        editor.commit();
    }

    public void createPatientLoginSession(Patient patient)
    {
        // Storing login value as TRUE
        editor.putBoolean(IS_LOGIN, true);

        Set<String> set = new TreeSet<>();
        set.add(patient.getId());
        set.add(patient.getFName());
        set.add(patient.getLName());
        set.add(patient.getMail());
        set.add(patient.getPhone());
        set.add(patient.getAddress());
        set.add(patient.getHMO());
        set.add(patient.getTherapistId());

        editor.putStringSet(PATIENT_SET, set);
        editor.commit();
    }
    public String getUserDetails()
    {
        String user = pref.getString(PATIENT_ID, null);
        // user name
        //user.put(KEY_ID, pref.getString(KEY_ID, null));
        //user.put(PATIENT_ID, pref.getString(PATIENT_ID, null));

        // user email id
       //user.put(KEY_EMAIL, pref.getString(KEY_EMAIL, null));

        // return user
        return user;
    }

    public String[] getPatientDetails()
    {
        String patientId = pref.getString(PATIENT_ID, null);
        String patientName = pref.getString(PATIENT_NAME, null);
        String patientTherapistId = pref.getString(THERAPIST_ID, null);
        String[] array = {patientId, patientName, patientTherapistId};

        return array;
    }

    public void checkLogin() {
        // Check login status
        if (!this.isLoggedIn())
        {
            // user is not logged in redirect him to Login Activity
            Intent i = new Intent(_context, MyProject.class);
            // Closing all the Activities
            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

            // Add new Flag to start new Activity
            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

            // Staring Login Activity
            _context.startActivity(i);
        }

    }
    /**
     * Clear session details
     * */
    public void logoutUser(){
        // Clearing all data from Shared Preferences
        editor.clear();
        editor.commit();

        // Storing login value as TRUE
        editor.putBoolean(IS_LOGIN, false);
        // After logout redirect user to Loing Activity
        Intent i = new Intent(_context, MyProject.class);
        // Closing all the Activities
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

        // Add new Flag to start new Activity
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

        // Staring Login Activity
        _context.startActivity(i);

    }

    public boolean isLoggedIn()
    {
        return pref.getBoolean(IS_LOGIN, false);
    }


}
