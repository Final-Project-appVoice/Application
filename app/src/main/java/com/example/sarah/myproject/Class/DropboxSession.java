package com.example.sarah.myproject.Class;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.dropbox.client2.DropboxAPI;
import com.dropbox.client2.android.AndroidAuthSession;
import com.dropbox.client2.session.AccessTokenPair;
import com.dropbox.client2.session.AppKeyPair;
import com.example.sarah.myproject.DB.Constant;

/**
 * Created by Sarah on 11-Jun-15.
 */
public class DropboxSession
{
    private static DropboxAPI<AndroidAuthSession> mDBApi;
    private Context mContext;
    private SharedPreferences mPrefs;
    public static final String MyPREFERENCES = "MyPrefsFile" ;
    public static final String DROPBOX = "Dropbox";
    private static final String ACCOUNT_PREFS_NAME = "prefs";
    private static final String ACCESS_KEY_NAME = "ACCESS_KEY";
    private static final String ACCESS_SECRET_NAME = "ACCESS_SECRET";
    private static final String TAG = "AppVoice";
    Activity mActivity;

    public DropboxSession(DropboxAPI<AndroidAuthSession> DBApi)
    {
        this.mDBApi = DBApi;
    }


    public AndroidAuthSession buildSession()
    {
        AppKeyPair appKeyPair = new AppKeyPair(Constant.APP_KEY, Constant.APP_SECRET);

        AndroidAuthSession session = new AndroidAuthSession(appKeyPair);
        loadAuth(session);
        return session;
    }

    public void loadAuth(AndroidAuthSession session)
    {
        Log.d("DROPBOX SESSION", "HERE4");
        // Log.d("DROPBOX SESSION", mContext.getPackageName());
        if(mContext!=null)
        {
            Log.d("DROPBOX SESSION", "HERE5");

            SharedPreferences prefs = mContext.getSharedPreferences(ACCOUNT_PREFS_NAME, 0);
            String key = prefs.getString(ACCESS_KEY_NAME, null);
            String secret = prefs.getString(ACCESS_SECRET_NAME, null);
            if (key == null || secret == null || key.length() == 0 || secret.length() == 0) return;

            if (key.equals("oauth2:")) {
                // If the key is set to "oauth2:", then we can assume the token is for OAuth 2.
                session.setOAuth2AccessToken(secret);
            } else {
                // Still support using old OAuth 1 tokens.
                session.setAccessTokenPair(new AccessTokenPair(key, secret));
            }
        }
    }

    /**
     * Shows keeping the access keys returned from Trusted Authenticator in a local
     * store, rather than storing user name & password, and re-authenticating each
     * time (which is not to be done, ever).
     */
    public void storeAuth(AndroidAuthSession session)
    {
        // Store the OAuth 2 access token, if there is one.
        String oauth2AccessToken = session.getOAuth2AccessToken();
        Log.d("DROPBOX SESSION", "HERE1");
        if (oauth2AccessToken != null)
        {
            Log.d("DROPBOX SESSION", "HERE2");
            SharedPreferences prefs = mContext.getSharedPreferences(ACCOUNT_PREFS_NAME, 0);
            SharedPreferences.Editor edit = prefs.edit();
            edit.putString(ACCESS_KEY_NAME, "oauth2:");
            edit.putString(ACCESS_SECRET_NAME, oauth2AccessToken);
            edit.commit();
            return;
        }
        // Store the OAuth 1 access token, if there is one.  This is only necessary if
        // you're still using OAuth 1.
        AccessTokenPair oauth1AccessToken = session.getAccessTokenPair();
        if (oauth1AccessToken != null)
        {
            Log.d("DROPBOX SESSION", "HERE3");
            SharedPreferences prefs = mContext.getSharedPreferences(ACCOUNT_PREFS_NAME, 0);
            SharedPreferences.Editor edit = prefs.edit();
            edit.putString(ACCESS_KEY_NAME, oauth1AccessToken.key);
            edit.putString(ACCESS_SECRET_NAME, oauth1AccessToken.secret);
            edit.commit();
            return;
        }
    }

    public static DropboxAPI<AndroidAuthSession> getDropboxSession()
    {
        return mDBApi;
    }
}
