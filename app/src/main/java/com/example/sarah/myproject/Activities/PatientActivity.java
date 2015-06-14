package com.example.sarah.myproject.Activities;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.dropbox.client2.DropboxAPI;
import com.dropbox.client2.android.AndroidAuthSession;
import com.dropbox.client2.session.AccessTokenPair;
import com.dropbox.client2.session.AppKeyPair;
import com.example.sarah.myproject.Adapters.FoldersAdapter;
import com.example.sarah.myproject.Class.DropboxSession;
import com.example.sarah.myproject.Class.Patient;
import com.example.sarah.myproject.Class.SessionManager;
import com.example.sarah.myproject.DB.Constant;
import com.example.sarah.myproject.Dal.DalPatient;
import com.example.sarah.myproject.R;
import com.example.sarah.myproject.Tasks.FolderTask;

import java.util.Set;


public class PatientActivity extends Activity {

    private TextView patient_textView;
    private Intent i;
    private Patient p;
    private DalPatient dalPatient;
    private DisplayMetrics displayMetrics;
    private int screenHeightPx, screenWidthPx;
    private Button button1, button2, button3;
    private LinearLayout linearLayout_home;
    private SessionManager session;
    private Set<String> patientDetails;
    private String[] patientArray;
    private String patientId, patientName;
    private ListView listView;
    private FoldersAdapter foldersAdapter;
    // In the class declaration section:
    private DropboxAPI<AndroidAuthSession> mDBApi;


    private SharedPreferences pref;     // Shared Preferences
    private SharedPreferences.Editor editor;      // Editor for Shared preferences
    private Context context;       // Context

    int PRIVATE_MODE = 0;       // Shared pref mode

    public static final String MyPREFERENCES = "MyPrefsFile" ;
    public static final String DROPBOX = "Dropbox";
    private static final String ACCOUNT_PREFS_NAME = "prefs";
    private static final String ACCESS_KEY_NAME = "ACCESS_KEY";
    private static final String ACCESS_SECRET_NAME = "ACCESS_SECRET";
    private static final String TAG = "AppVoice";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
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

        // get user ID from session
        patientArray = session.getPatientDetails();
        if (patientArray.length > 0)
        {
            patientId = patientArray[0];        // get patient id from session
            patientName = patientArray[1];      // get patient name from session
        }

        Toast.makeText(getApplicationContext(), "User Login Status: " + session.isLoggedIn(), Toast.LENGTH_LONG).show();

        patient_textView = (TextView) findViewById(R.id.patientNameTextView);       // patient name to fill

        i = getIntent();

        fieldTextView();        // fill fields

        Log.w("Check", "BEFORE");
        FolderTask folderTask = new FolderTask(this);      // calling to task in order to pull from db the folders assigned
        folderTask.execute(patientId);       // execute task and add buttons for each folder assigned
        Log.w("Check", "AFTER");

        // DROPBOX session
//        AppKeyPair appKeys = new AppKeyPair(Constant.APP_KEY, Constant.APP_SECRET);     // initialize key for dropbox folders
//        AndroidAuthSession session = new AndroidAuthSession(appKeys);       // create authentication
        AndroidAuthSession session = buildSession();
        mDBApi = new DropboxAPI<AndroidAuthSession>(session);       // new dropbox session
        DropboxSession dropboxSession = new DropboxSession(mDBApi);
        Log.d("Dropbox session", mDBApi.toString());

        SharedPreferences prefs = getSharedPreferences(ACCOUNT_PREFS_NAME, 0);
        String key = prefs.getString(ACCESS_KEY_NAME, null);
        // Log.d("preferences - key", key);

        String secret = prefs.getString(ACCESS_SECRET_NAME, null);
        //  Log.d("preferences - secret", secret);
//        if (key == null || secret == null || key.length() == 0 || secret.length() == 0)
//        {
//            mDBApi.getSession().startOAuth2Authentication(PatientActivity.this);     // wait for authorization and go back to specific activity
//        }
//        else
//        {
        loadAuth(session);
//        }
    }

    private void fieldTextView()
    {
        patient_textView.setText(getString(R.string.welcome_user) + " " + patientName);

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.common_menu, menu);
        getActionBar().setTitle(patientName);
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
        }

        return super.onOptionsItemSelected(item);
    }


    public void onClick_action_logout(MenuItem item) {
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
        // String id = i.getStringExtra(session.PATIENT_ID);
        Log.e("exit", patientName);
        moveTaskToBack(true);
        PatientActivity.this.finish();
    }
    protected void onResume()

    {
        super.onResume();
        AndroidAuthSession session = mDBApi.getSession();

        // The next part must be inserted in the onResume() method of the
        // activity from which session.startAuthentication() was called, so
        // that Dropbox authentication completes properly.
        if (session.authenticationSuccessful())
        {
            try {
                // Mandatory call to complete the auth
                session.finishAuthentication();
                // Store it locally in our app for later use
                storeAuth(session);
                //setLoggedIn(true);
            }
            catch (IllegalStateException e)
            {
                showToast("Couldn't authenticate with Dropbox:" + e.getLocalizedMessage());
                Log.i(TAG, "Error authenticating", e);
            }
        }
    }

    private void showToast(String msg) {
        Toast error = Toast.makeText(this, msg, Toast.LENGTH_LONG);
        error.show();
    }
    //
//    protected void onResume()
//    {
//        super.onResume();
//
////        if (session.isLoggedIn())
////        {
////            session.createLoginSession(p.getFName(), p.getMail());
////        }
//        SharedPreferences settings = getSharedPreferences("MyPrefsFile", MODE_PRIVATE);
//        SharedPreferences.Editor editor;
//        String accessPreference = settings.getString(DROPBOX, null);           // Get the last sms sent
//        String accessToken;
//
//        if(accessPreference != null)
//        {
//            AccessTokenPair accessTokenPair = mDBApi.getSession().getAccessTokenPair();
//            mDBApi.getSession().setAccessTokenPair(accessTokenPair);
//        }
//        else if (mDBApi.getSession().authenticationSuccessful())
//        {
//            try {
////                // Required to complete auth, sets the access token on the session
//                mDBApi.getSession().finishAuthentication();
////
//                accessToken = mDBApi.getSession().getOAuth2AccessToken();
//                storeAuth(mDBApi.getSession());
////
//            } catch (IllegalStateException e) {
//                Log.i("DbAuthLog", "Error authenticating", e);
//            }
//        }
//        //AccessTokenPair tokens = new AccessTokenPair(mDBApi.getSession().)
//
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

    //  @Override
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


    private void loadAuth(AndroidAuthSession session)
    {
        session.setOAuth2AccessToken(Constant.ACCESS_SECRET);
        SharedPreferences prefs = getSharedPreferences(ACCOUNT_PREFS_NAME, 0);
        String key = prefs.getString(ACCESS_KEY_NAME, null);
        String secret = prefs.getString(ACCESS_SECRET_NAME, null);
        if (key == null || secret == null || key.length() == 0 || secret.length() == 0) return;

        if (key.equals("oauth2:"))
        {
            // If the key is set to "oauth2:", then we can assume the token is for OAuth 2.
            // session.setOAuth2AccessToken(secret);

            Log.d("load secret", secret);
        } else
        {
            // Still support using old OAuth 1 tokens.
            session.setAccessTokenPair(new AccessTokenPair(key, secret));
        }
    }

    /**
     * Shows keeping the access keys returned from Trusted Authenticator in a local
     * store, rather than storing user name & password, and re-authenticating each
     * time (which is not to be done, ever).
     */
    private void storeAuth(AndroidAuthSession session)
    {

        // Store the OAuth 2 access token, if there is one.
        String oauth2AccessToken = session.getOAuth2AccessToken();
        if (oauth2AccessToken != null)
        {
            SharedPreferences prefs = getSharedPreferences(ACCOUNT_PREFS_NAME, 0);
            SharedPreferences.Editor edit = prefs.edit();
            edit.putString(ACCESS_KEY_NAME, "oauth2:");
            edit.putString(ACCESS_SECRET_NAME, oauth2AccessToken);
            Log.d("Store key", oauth2AccessToken);
            edit.commit();
            return;
        }
        // Store the OAuth 1 access token, if there is one.  This is only necessary if
        // you're still using OAuth 1.
        AccessTokenPair oauth1AccessToken = session.getAccessTokenPair();
        if (oauth1AccessToken != null) {
            SharedPreferences prefs = getSharedPreferences(ACCOUNT_PREFS_NAME, 0);
            SharedPreferences.Editor edit = prefs.edit();
            edit.putString(ACCESS_KEY_NAME, oauth1AccessToken.key);
            edit.putString(ACCESS_SECRET_NAME, oauth1AccessToken.secret);
            edit.commit();
            return;
        }
    }

    private AndroidAuthSession buildSession()
    {
        AppKeyPair appKeyPair = new AppKeyPair(Constant.APP_KEY, Constant.APP_SECRET);

        AndroidAuthSession session = new AndroidAuthSession(appKeyPair);
        loadAuth(session);
        return session;
    }

}
