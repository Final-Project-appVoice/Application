package com.example.sarah.myproject.Adapters;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.sarah.myproject.Activities.ExerciseActivity;
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
        TextView exerciseIdTextView = (TextView)view.findViewById(R.id.exerciseId);
        ImageView exerciseDone = (ImageView)view.findViewById(R.id.exercise_done);
        ImageView exerciseVideo = (ImageView)view.findViewById(R.id.video_icon);


        String exercise = exercises.get(position);
        String[] exerciseSplit = exercise.split(",");       // get exercise details from the list using the position
        String exerciseName = exerciseSplit[0];     // split to get title
        String exerciseId = exerciseSplit[1];       // split to get id
        String isDone = exerciseSplit[2];           // split to know if IsDone
        String isVideo = exerciseSplit[3];           // split to know if IsVideo
        String title = position+1 + ". " + exerciseName;
        exerciseTitle.setText(title);       // fill text view to be the title of the exercise
        exerciseIdTextView.setText(exerciseId);     // fill text view to be the id of the exercise

        if(isDone.equals("true"))     // the exercise is already done
        {
            exerciseDone.setVisibility(View.VISIBLE);
        }
        else        // the exercise is not done yet
        {
            exerciseDone.setVisibility(View.INVISIBLE);
        }
        if(isVideo.equals("1"))
        {
            exerciseVideo.setVisibility(View.VISIBLE);
        }
        else
        {
            exerciseVideo.setVisibility(View.INVISIBLE);
        }

        return convertView;
    }


    @Override
    public void onItemClick(AdapterView<?> parent, android.view.View view, int position, long id)
    {
        TextView exerciseIdTextView = (TextView)view.findViewById(R.id.exerciseId);
        TextView exerciseTitleTextView = (TextView)view.findViewById(R.id.exerciseTitle);

        String exerciseTitle = exerciseTitleTextView.getText().toString();        // getting exercise name from exercise selected
        String exerciseId = exerciseIdTextView.getText().toString();       // getting exercise id from exercise selected
        Log.d("ON CLICK", exerciseTitle + " " + exerciseId);

        Intent i = new Intent(view.getContext(), ExerciseActivity.class);      // opening selected exercise
        i.putExtra("exerciseTitle", exerciseTitle);       // passing folderName to the new intent
        i.putExtra("exerciseId", exerciseId);       // passing folderId to the new intent
        view.getContext().startActivity(i);
    }
}

