package com.example.sarah.myproject.Tasks;

import android.os.AsyncTask;

import com.example.sarah.myproject.Class.Exercise;

/**
 * Created by Sarah on 25-Jun-15.
 *
 *
 */
public class DownloadImageTask extends AsyncTask<Exercise, Void, Void>  // <Params, Progress, Result>
{
    @Override
    protected Void doInBackground(Exercise... params)
    {
        return null;
    }

    // mDrawable = Drawable.createFromPath(cachePath);
}
