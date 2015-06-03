package com.example.sarah.myproject.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.sarah.myproject.R;

import java.util.List;

/**
 * Created by Sarah on 03-Jun-15.
 */
public class ExercisesAdapter extends ArrayAdapter<String> implements AdapterView.OnItemClickListener
{
    private LayoutInflater inflater;
    private List<String> exercises;       // list of exercise names

    public ExercisesAdapter(Context context, int resource, int textViewResourceId, List<String> items)
    {
        super(context, resource, textViewResourceId, items);
        this.exercises = items;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent)       // what happens in each row of list
    {
        View view = inflater.inflate(R.layout.exercise_adapter, null);      // calling to the relevant layout (exercise adapter)
        convertView = view;
        TextView exerciseTitle = (TextView)view.findViewById(R.id.exerciseTitle);
        String title = position+1 + ". " + exercises.get(position);
        exerciseTitle.setText(title);

        return convertView;
    }


    @Override
    public void onItemClick(AdapterView<?> parent, android.view.View view, int position, long id)
    {
    }
}

