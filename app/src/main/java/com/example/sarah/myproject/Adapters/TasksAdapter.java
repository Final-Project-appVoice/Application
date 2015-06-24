package com.example.sarah.myproject.Adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.sarah.myproject.Class.Task;
import com.example.sarah.myproject.R;

import java.util.List;

/**
 * Created by Sarah on 08-Jun-15.
 */
public class TasksAdapter extends ArrayAdapter<Task> implements AdapterView.OnItemClickListener
{
    private LayoutInflater inflater;
    private List<Task> tasks;       // list of exercise names
    Context context;



    public TasksAdapter(Context context, int resource, int textViewResourceId, List<Task> items)
    {
        super(context, resource, textViewResourceId, items);
        this.tasks = items;
        this.context = context;
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
        ImageView imageView = (ImageView)convertView.findViewById(R.id.read_task);

        final Task actualTask = tasks.get(position);
        Log.d("Actual Task", actualTask.toString());
        taskTitle.setText(actualTask.getTaskTitle());
        taskId.setText(actualTask.getTaskId() + "");
        taskDescription.setText(actualTask.getDescription());
        taskComment.setText(actualTask.getComment());
        taskUrl.setText(actualTask.getImagePath());

        if(actualTask.isDone())
        {
            imageView.setVisibility(View.VISIBLE);
        }
        else
        {
            imageView.setVisibility(View.INVISIBLE);
        }

//        buttonFile.setOnClickListener(new View.OnClickListener()
//        {
//            @Override
//            public void onClick(View v)
//            {
////                Log.d("Dropbox session", DropboxSession.getDropboxSession().toString());
//                DownloadFileTask downloadFileTask = new DownloadFileTask(context);
//                downloadFileTask.execute(actualTask);
//
//            }
//        });
        return convertView;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id)
    {

    }


}
