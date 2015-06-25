package com.example.sarah.myproject.Service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

public class VideoRecorderService extends Service
{
    public VideoRecorderService()
    {

        //VideoRecorder videoRecorder = new VideoRecorder();
    }

    @Override
    public IBinder onBind(Intent intent)
    {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }
}
