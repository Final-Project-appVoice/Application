package com.example.sarah.myproject.Tasks;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ImageView;
import android.widget.Toast;

import com.dropbox.client2.DropboxAPI;
import com.dropbox.client2.DropboxAPI.Entry;
import com.dropbox.client2.exception.DropboxException;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by Sarah on 10-Jun-15.
 * Here we show getting metadata for a directory and downloading a file in a
 * background thread, trying to show typical exception handling and flow of
 * control for an app that downloads a file from Dropbox.
 */

public class DownloadFileTask extends AsyncTask<Void, Long, ArrayList<String>> {


    private Context mContext;
    private final ProgressDialog mDialog;
    private DropboxAPI<?> mApi;
    private String mPath;
    private ImageView mView;
    private Drawable mDrawable;
    private String cachePath = "";
    private FileOutputStream mFos;

    private boolean mCanceled;
    private Long mFileLen;
    private String mErrorMsg;
    private String mDropboxPath;
    // Note that, since we use a single file name here for simplicity, you
    // won't be able to use this code for two simultaneous downloads.
    private final static String IMAGE_FILE_NAME = "dbroulette.png";

    public DownloadFileTask(Context context, DropboxAPI<?> api, String dropboxPath)
    {
        // We set the context this way so we don't accidentally leak activities
        mContext = context.getApplicationContext();
        mDropboxPath = dropboxPath;
        mApi = api;
        Log.d("DropboxPath", dropboxPath);
        mPath = dropboxPath;
        //mView = view;

        mDialog = new ProgressDialog(context);
        mDialog.setMessage("Downloading Image");
        mDialog.setButton(ProgressDialog.BUTTON_POSITIVE, "Cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                mCanceled = true;
                mErrorMsg = "Canceled";

                // This will cancel the getThumbnail operation by closing
                // its stream
                if (mFos != null) {
                    try {
                        mFos.close();
                    } catch (IOException e) {
                    }
                }
            }
        });

        mDialog.show();
    }

    @Override
    protected ArrayList<String> doInBackground(Void... params)
    {

          if (mCanceled)
            {
                return null;
            }

            ArrayList<String> files = new ArrayList<String>();
            try {
                Entry directory = mApi.metadata(mPath, 1000, null, true, null);
                for (Entry entry : directory.contents) {
                    files.add(entry.fileName());
                }
            } catch (DropboxException e) {
                e.printStackTrace();
            }

            return files;

    }

          //  return files;
//            // Get the metadata for a directory
//            DropboxAPI.Entry dirent = mApi.metadata(mPath, 1000, null, true, null);
//            Log.d("metadata", dirent.fileName());
//            if (!dirent.isDir || dirent.contents == null) {
//                // It's not a directory, or there's nothing in it
//                mErrorMsg = "File or empty directory";
//                return false;
//            }
//
//            // Make a list of everything in it that we can get a thumbnail for
//            ArrayList<DropboxAPI.Entry> thumbs = new ArrayList<DropboxAPI.Entry>();
//            for (DropboxAPI.Entry ent: dirent.contents)
//            {
//                if (ent.thumbExists) {
//                    // Add it to the list of thumbs we can choose from
//                    thumbs.add(ent);
//                }
//            }
//
//            if (mCanceled) {
//                return false;
//            }
//
//            if (thumbs.size() == 0) {
//                // No thumbs in that directory
//                mErrorMsg = "No pictures in that directory";
//                return false;
//            }
//            Log.d("Thmbs size", thumbs.size() + "");
//            // Now pick a random one
//            int index = (int)(Math.random() * thumbs.size());
////            DropboxAPI.Entry ent = thumbs.get(index);
////            String path = ent.path;
//            // mFileLen = ent.bytes;
//            Log.d("DropboxPath", mDropboxPath);
//
//           // ListDropboxFiles list = new ListDropboxFiles(dropbox, FILE_DIR, handler);
//            for(int i=0; i<thumbs.size(); i++)
//            {
//                Log.d("In for thumbs", i+"");
//
//                DropboxAPI.Entry ent = thumbs.get(index);
//                String path = ent.path;
//
//                if(path.contains(mDropboxPath))
//                {
//                    cachePath = mContext.getCacheDir().getAbsolutePath() + mDropboxPath;
//                    Log.d("In for thumbs cp", cachePath);
//                }
//            }
//
//            //String cachePath = mContext.getCacheDir().getAbsolutePath() + "/" + IMAGE_FILE_NAME;
//            try
//            {
//                mFos = new FileOutputStream(cachePath);
//            } catch (FileNotFoundException e) {
//                mErrorMsg = "Couldn't create a local file to store the image";
//                return false;
//            }
//
//            // This downloads a smaller, thumbnail version of the file.  The
//            // API to download the actual file is roughly the same.
////            mApi.getThumbnail(path, mFos, DropboxAPI.ThumbSize.BESTFIT_960x640,
////                    DropboxAPI.ThumbFormat.JPEG, null);
//            if (mCanceled) {
//                return false;
//            }
//
//            // mDrawable = Drawable.createFromPath(cachePath);
//            // We must have a legitimate picture
//            return true;
//
//        } catch (DropboxUnlinkedException e) {
//            // The AuthSession wasn't properly authenticated or user unlinked.
//        } catch (DropboxPartialFileException e) {
//            // We canceled the operation
//            mErrorMsg = "Download canceled";
//        } catch (DropboxServerException e) {
//            // Server-side exception.  These are examples of what could happen,
//            // but we don't do anything special with them here.
//            if (e.error == DropboxServerException._304_NOT_MODIFIED) {
//                // won't happen since we don't pass in revision with metadata
//            } else if (e.error == DropboxServerException._401_UNAUTHORIZED) {
//                // Unauthorized, so we should unlink them.  You may want to
//                // automatically log the user out in this case.
//            } else if (e.error == DropboxServerException._403_FORBIDDEN) {
//                // Not allowed to access this
//            } else if (e.error == DropboxServerException._404_NOT_FOUND) {
//                // path not found (or if it was the thumbnail, can't be
//                // thumbnailed)
//            } else if (e.error == DropboxServerException._406_NOT_ACCEPTABLE) {
//                // too many entries to return
//            } else if (e.error == DropboxServerException._415_UNSUPPORTED_MEDIA) {
//                // can't be thumbnailed
//            } else if (e.error == DropboxServerException._507_INSUFFICIENT_STORAGE) {
//                // user is over quota
//            } else {
//                // Something else
//            }
//            // This gets the Dropbox error, translated into the user's language
//            mErrorMsg = e.body.userError;
//            if (mErrorMsg == null) {
//                mErrorMsg = e.body.error;
//            }
//        } catch (DropboxIOException e) {
//            // Happens all the time, probably want to retry automatically.
//            mErrorMsg = "Network error.  Try again.";
//        } catch (DropboxParseException e) {
//            // Probably due to Dropbox server restarting, should retry
//            mErrorMsg = "Dropbox error.  Try again.";
//        } catch (DropboxException e) {
//            // Unknown error
//            mErrorMsg = "Unknown error.  Try again.";
//
//        }
//        return false;
   // }

    @Override
    protected void onProgressUpdate(Long... progress) {
        int percent = (int)(100.0*(double)progress[0]/mFileLen + 0.5);
        mDialog.setProgress(percent);
    }

    @Override
    protected void onPostExecute(ArrayList<String> result)
    {
        mDialog.dismiss();
       // TextView textView = (TextView)mContext.findViewById(R.id.filesTextView);
        String str = "";
        for(int i=0; i<result.size(); i++)
        {
            str += result.get(i) + "\n";
        }
       // textView.setText(str);
//        if (result)
//        {
//            // Set the image now that we have it
//            //mView.setImageDrawable(mDrawable);
//            FileOpen fileOpen = new FileOpen();
//            File file = new File(cachePath);
//            try {
//                fileOpen.openFile(mContext, file);
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        } else {
//            // Couldn't download it, so show an error
//            showToast(mErrorMsg);
//        }
    }

    private void showToast(String msg) {
        Toast error = Toast.makeText(mContext, msg, Toast.LENGTH_LONG);
        error.show();
    }


}
