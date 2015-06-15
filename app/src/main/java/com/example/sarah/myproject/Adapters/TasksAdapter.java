package com.example.sarah.myproject.Adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.dropbox.client2.DropboxAPI;
import com.dropbox.client2.exception.DropboxException;
import com.example.sarah.myproject.Class.DropboxSession;
import com.example.sarah.myproject.Class.Task;
import com.example.sarah.myproject.R;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.List;

/**
 * Created by Sarah on 08-Jun-15.
 */
public class TasksAdapter extends ArrayAdapter<Task> implements AdapterView.OnItemClickListener
{
    private LayoutInflater inflater;
    private List<Task> tasks;       // list of exercise names




    public TasksAdapter(Context context, int resource, int textViewResourceId, List<Task> items)
    {
        super(context, resource, textViewResourceId, items);
        this.tasks = items;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        Log.d("TasksAdapter", "HERE2");



    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent)       // what happens in each row of list
    {
        Log.d("TasksAdapter", "HERE1");
        View view = inflater.inflate(R.layout.task_adapter, null);      // calling to the relevant layout (exercise adapter)
        convertView = view;
        TextView taskTitle = (TextView)convertView.findViewById(R.id.taskTitle);
        TextView taskDescription = (TextView)convertView.findViewById(R.id.taskDescription);
        TextView taskComment = (TextView)convertView.findViewById(R.id.taskComment);
        TextView taskId = (TextView)convertView.findViewById(R.id.taskId);
        TextView taskUrl = (TextView)convertView.findViewById(R.id.urlTitle);
        Button buttonFile = (Button)convertView.findViewById(R.id.buttonTaskFile);

        final Task actualTask = tasks.get(position);
        Log.d("Actual Task", actualTask.toString());
        taskTitle.setText(actualTask.getTaskTitle());
        taskId.setText(actualTask.getTaskId() + "");
        taskDescription.setText(actualTask.getDescription());
        taskComment.setText(actualTask.getComment());
        taskUrl.setText(actualTask.getImagePath());

        buttonFile.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
//                Log.d("Dropbox session", DropboxSession.getDropboxSession().toString());
//                DownloadFileTask download = new DownloadFileTask(v.getContext(), DropboxSession.getDropboxSession(), "/");
//                download.execute();
                File file = new File("/Applications/AppVoice/"+actualTask.getImagePath().trim());
                Log.d("File", actualTask.getImagePath());
                FileOutputStream outputStream = null;
                try
                {
                    outputStream = new FileOutputStream(file);
                } catch (FileNotFoundException e)
                {
                    e.printStackTrace();
                }
                DropboxAPI.DropboxFileInfo info = null;
                try
                {

                    Log.w("API",DropboxSession.getDropboxSession().toString());
                    info = (DropboxSession.getDropboxSession()).getFile("/Applications/AppVoice/"+actualTask.getImagePath().trim(), null, outputStream, null);
                    Log.i("DbExampleLog", "The file's rev is: " + info.getMetadata().rev);
                }
                catch (DropboxException e)
                {
                    Log.d("ERROR", "e");
                    e.printStackTrace();
                }



            }
        });
        return convertView;
    }

//    private boolean downloadDropboxFile(String dbPath, File localFile) throws IOException {
//
//        BufferedInputStream br = null;
//        BufferedOutputStream bw = null;
//
//        try {
//            if (!localFile.exists()) {
//                localFile.createNewFile(); //otherwise dropbox client will fail silently
//            }
//
//            FileDownload fd = DropboxSession.getDropboxSession().getFileStream("dropbox", dbPath, null);
//            br = new BufferedInputStream(fd.is);
//            bw = new BufferedOutputStream(new FileOutputStream(localFile));
//
//            byte[] buffer = new byte[4096];
//            int read;
//            while (true) {
//                read = br.read(buffer);
//                if (read <= 0) {
//                    break;
//                }
//                bw.write(buffer, 0, read);
//            }
//        } finally {
//            //in finally block:
//            if (bw != null) {
//                bw.close();
//            }
//            if (br != null) {
//                br.close();
//            }
//        }
//
//        return true;
//    }
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id)
    {

    }


}
