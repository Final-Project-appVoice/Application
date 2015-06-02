package com.example.sarah.myproject.Adapters;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;

import com.example.sarah.myproject.Activities.ExerciseListActivity;
import com.example.sarah.myproject.R;

import java.util.List;

/**
 * Created by Sarah on 02-Jun-15.
 *
 * The list of buttons that will be on the home screen of each patient
 */
public class FoldersAdapter extends ArrayAdapter<String> implements AdapterView.OnItemClickListener
{
    private LayoutInflater inflater;
    private List<String> folders;       // list of folder names

    public FoldersAdapter(Context context, int resource, int textViewResourceId, List<String> items)
    {
        super(context, resource, textViewResourceId, items);
        this.folders = items;
        inflater = (LayoutInflater) context.getSystemService( Context.LAYOUT_INFLATER_SERVICE );
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent)       // what happens in each row of list
    {
        View view = inflater.inflate(R.layout.folder_adapter, null);      // calling to the relevant layout
        convertView = view;

        Button button = (Button)view.findViewById(R.id.folderButton);
        button.setText(folders.get(position));
        button.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)     // on folder click
            {
                Button button = (Button)v.findViewById(R.id.folderButton);
                String folderName = button.getText().toString();
                Log.d("ON CLICK", folderName);

                Intent i = new Intent(v.getContext(), ExerciseListActivity.class);
                i.putExtra("folderName", folderName);
                v.getContext().startActivity(i);
            }
        });
        return convertView;
    }


    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id)
    {
    }
}
