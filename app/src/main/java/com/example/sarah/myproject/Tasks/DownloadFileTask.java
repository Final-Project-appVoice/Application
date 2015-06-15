package com.example.sarah.myproject.Tasks;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import com.dropbox.client2.DropboxAPI;
import com.dropbox.client2.exception.DropboxException;
import com.example.sarah.myproject.Class.DropboxSession;
import com.example.sarah.myproject.Class.FileOpen;
import com.example.sarah.myproject.Class.Task;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created by Sarah on 10-Jun-15.
 * Here we show getting metadata for a directory and downloading a file in a
 * background thread, trying to show typical exception handling and flow of
 * control for an app that downloads a file from Dropbox.
 */

public class DownloadFileTask extends AsyncTask<Task, Void, Void>  // <Params, Progress, Result>
{

    private Context context;
    ProgressDialog progressDialog;

    public DownloadFileTask(Context context)
    {
        // We set the context this way so we don't accidentally leak activities
        this.context = context;

    }

    @Override
    protected void onPreExecute()
    {
        super.onPreExecute();
        progressDialog = ProgressDialog.show(context, "Wait", "Downloading...");
    }

    @Override
    protected Void doInBackground(Task... params)
    {
        Task actualTask = params[0];

        File file = new File("/sdcard/" + actualTask.getImagePath().trim());
        Log.d("File", actualTask.getImagePath());
        FileOutputStream outputStream = null;
        try {
            outputStream = new FileOutputStream(file);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        DropboxAPI.DropboxFileInfo info = null;
        try {

            Log.w("API", DropboxSession.getDropboxSession().toString());
            info = (DropboxSession.getDropboxSession()).getFile("/" + actualTask.getImagePath().trim(), null, outputStream, null);
            Log.i("DbExampleLog", "The file's rev is: " + info.getMetadata().rev);
            //Thread.sleep(10000);
            FileOpen.openFile(context, file.getPath().toString());      // open new activity and open file

        } catch (DropboxException e) {
            Toast.makeText(context, "Error in connecting to the server", Toast.LENGTH_LONG);
            Log.d("ERROR", "e");
            e.printStackTrace();
        } catch (IOException e)
        {
            e.printStackTrace();
            Toast.makeText(context, "Error in opening the file", Toast.LENGTH_LONG);
        }
        return null;
    }
    protected void onPostExecute(Void object)
    {
        super.onPostExecute(object);
        progressDialog.dismiss();
    }
}
