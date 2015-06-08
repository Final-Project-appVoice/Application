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
import android.widget.TextView;

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
    public View getView(int position, View convertView, final ViewGroup parent)       // what happens in each row of list
    {
        final View view = inflater.inflate(R.layout.folder_adapter, null);      // calling to the relevant layout
        convertView = view;

        Button button = (Button)view.findViewById(R.id.folderButton);
        TextView folderIdTextView = (TextView)view.findViewById(R.id.folderId);

        Log.d("FOLDERS", folders.get(position).toString());
        String[] parseFolders = folders.get(position).split(",");       // getting folders details and parse it

        String folderId = parseFolders[0].trim();     // getting folder id
        String folderName = parseFolders[1].trim();        // getting folder name
        Log.d("FOLDER ID", folderId);
        Log.d("FOLDERS NAME", folderName);
        button.setText(folderName);     // set button title to be the folder name
        folderIdTextView.setText(folderId);     // set textview invisible to keep the folder id

        button.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)     // on folder click
            {
                Button button = (Button)v.findViewById(R.id.folderButton);
                TextView folderIdTextView = (TextView)view.findViewById(R.id.folderId);

                String folderName = button.getText().toString();        // getting folder name from folder selected
                String folderID = folderIdTextView.getText().toString();       // getting folder id from folder selected
                Log.d("ON CLICK", folderName + " " + folderID);

                Intent i = new Intent(v.getContext(), ExerciseListActivity.class);      // opening selected folder
                i.putExtra("folderName", folderName);       // passing folderName to the new intent
                i.putExtra("folderId", folderID);       // passing folderId to the new intent
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
