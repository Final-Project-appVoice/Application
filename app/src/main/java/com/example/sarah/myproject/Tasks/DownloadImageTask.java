package com.example.sarah.myproject.Tasks;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ImageView;
import android.widget.Toast;

import com.dropbox.client2.DropboxAPI;
import com.dropbox.client2.exception.DropboxException;
import com.example.sarah.myproject.Class.DropboxSession;
import com.example.sarah.myproject.R;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

/**
 * Created by Sarah on 25-Jun-15.
 * <p/>
 * Task to download picture from dropbox and upload it to image view on activity
 */
public class DownloadImageTask extends AsyncTask<String, Void, Void>  // <Params, Progress, Result>
{
    private Context context;
    private Drawable mDrawable;
    private ImageView imageView;

    public DownloadImageTask(Context context)
    {
        this.context = context;
    }

    @Override
    protected Void doInBackground(String... params)
    {
        String fileName = params[0];
        File file = new File("/sdcard/AppVoice" + fileName.trim());
        Log.d("File", fileName);
        FileOutputStream outputStream = null;
        try
        {
            outputStream = new FileOutputStream(file);

        }
        catch (FileNotFoundException e)
        {
            e.printStackTrace();
            Toast.makeText(context, "Error in creating new file", Toast.LENGTH_LONG);
        }
        DropboxAPI.DropboxFileInfo info = null;     // get dropbox info to connect to it
        try
        {

            Log.w("API", DropboxSession.getDropboxSession().toString());

            // connect to dropbox and get file we need
            info = (DropboxSession.getDropboxSession()).getFile("/" + fileName.trim(), null, outputStream, null);
            Log.i("DbExampleLog", "The file's rev is: " + info.getMetadata().rev);
            mDrawable = Drawable.createFromPath(file.getAbsolutePath());
        }
        catch (DropboxException e)
        {
            Toast.makeText(context, "Error in connecting to the server", Toast.LENGTH_LONG).show();

            Log.d("ERROR", "e");
            e.printStackTrace();
            return null;
        }

        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid)
    {
        super.onPostExecute(aVoid);

        imageView = (ImageView)((Activity)context).findViewById(R.id.imageView);
        imageView.setImageDrawable(mDrawable);
    }
// mDrawable = Drawable.createFromPath(cachePath);
}
