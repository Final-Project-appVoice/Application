package com.example.sarah.myproject.Adapters;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.sarah.myproject.Activities.ExerciseActivity;
import com.example.sarah.myproject.Activities.ExerciseVideoActivity;
import com.example.sarah.myproject.Class.Exercise;
import com.example.sarah.myproject.R;
import com.example.sarah.myproject.Tasks.DownloadFileTask;

import java.util.List;

/**
 * Created by Sarah on 03-Jun-15.
 */
public class ExercisesAdapter extends ArrayAdapter<String> implements AdapterView.OnItemClickListener
{
    private LayoutInflater inflater;
    private List<String> exercises;       // list of exercise names
    private Context context;
    private String TAG="ExerciseAdapter";
    private DisplayMetrics displayMetrics;
    private int screenHeightPx, screenWidthPx;

    public ExercisesAdapter(Context context, int resource, int textViewResourceId, List<String> items)
    {
        super(context, resource, textViewResourceId, items);
        this.exercises = items;
        this.context = context;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        // get the size of the screen in order to set buttons' size relative to the screen
        displayMetrics = context.getResources().getDisplayMetrics();
        screenHeightPx = (int)(Math.round(displayMetrics.heightPixels));        // height on screen
        screenWidthPx = (int)(Math.round(displayMetrics.widthPixels));          //width of screen


        //button2.setPadding(0, (int)Math.round(0.1*screenHeightPx), 0, 0);

    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    @Override
    public View getView(int position, View convertView, ViewGroup parent)       // what happens in each row of list
    {
        View view = inflater.inflate(R.layout.exercise_adapter, null);      // calling to the relevant layout (exercise adapter)
        convertView = view;
        TextView exerciseTitle = (TextView)view.findViewById(R.id.exerciseTitle);
        TextView exerciseIdTextView = (TextView)view.findViewById(R.id.exerciseId);
        ImageView exerciseDone = (ImageView)view.findViewById(R.id.exercise_done);

        //ImageView exerciseVideo = (ImageView)view.findViewById(R.id.video_icon);
        LinearLayout layoutExercise = (LinearLayout)view.findViewById(R.id.LinearDescription);
        LinearLayout layoutVideo = (LinearLayout)view.findViewById(R.id.LinearVideo);
        LinearLayout layoutFile = (LinearLayout)view.findViewById(R.id.LinearFile);
        LinearLayout layoutLink = (LinearLayout)view.findViewById(R.id.LinearLink);

        ImageButton exerciseButton = (ImageButton)view.findViewById(R.id.button_exercise);
        ImageView videoButton = (ImageView)view.findViewById(R.id.button_video);
        ImageView fileButton = (ImageView)view.findViewById(R.id.button_file);
        ImageView linkButton = (ImageView)view.findViewById(R.id.button_link);

        exerciseButton.getLayoutParams().height = (int) Math.round(0.075 * screenHeightPx);
        exerciseButton.getLayoutParams().width = (int) Math.round(0.075 * screenHeightPx);
        videoButton.getLayoutParams().height = (int) Math.round(0.075 * screenHeightPx);
        videoButton.getLayoutParams().width = (int) Math.round(0.075 * screenHeightPx);
        fileButton.getLayoutParams().height = (int) Math.round(0.075 * screenHeightPx);
        fileButton.getLayoutParams().width = (int) Math.round(0.075 * screenHeightPx);
        linkButton.getLayoutParams().height = (int) Math.round(0.075 * screenHeightPx);
        linkButton.getLayoutParams().width = (int) Math.round(0.075 * screenHeightPx);

        String exercise = exercises.get(position);
        String[] exerciseSplit = exercise.split(",");       // get exercise details from the list using the position
        final String exerciseName = exerciseSplit[0];     // split to get title
        final String exerciseId = exerciseSplit[1];       // split to get id
        final String isDone = exerciseSplit[2];           // split to know if IsDone
        String isVideo = exerciseSplit[3];           // split to know if IsVideo
        String title = position+1 + ". " + exerciseName;
        exerciseTitle.setText(title);       // fill text view to be the title of the exercise
        exerciseIdTextView.setText(exerciseId);     // fill text view to be the id of the exercise
        Log.i("LIST EXERCISE", Exercise.getAllExercises());
        Exercise selectedExercise = Exercise.getExerciseById(Integer.parseInt(exerciseId));       // get exercise using exerciseId

        final Exercise copyExercise = selectedExercise;

        if(isDone.equals("true"))     // the exercise is already done
        {
            exerciseDone.setVisibility(View.VISIBLE);
        }
        else        // the exercise is not done yet
        {
            exerciseDone.setVisibility(View.INVISIBLE);
        }

        if(selectedExercise!=null)
        {
            Log.i(TAG, "exercise not null");
            Log.i(TAG, selectedExercise.toString());
            exerciseButton.setVisibility(View.VISIBLE);
            exerciseButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {           // onClick = open activity to do the exercise
                    Intent i = new Intent(v.getContext(), ExerciseActivity.class);
                    i.putExtra("ExerciseId", exerciseId);
                    i.putExtra("IsDone", isDone);
                    v.getContext().startActivity(i);
                }
            });
            if(selectedExercise.getIsVideo() == 1)          // if exercise is requiring video record
            {
                Log.i(TAG, "video not null");
                layoutVideo.setVisibility(View.VISIBLE);           // enable button to record video
                linkButton.setClickable(true);

                videoButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v)     // onClick = open activity to record video
                    {

                        Intent i = new Intent(v.getContext(), ExerciseVideoActivity.class);            // opening relevant activity
                        i.putExtra("ExerciseId", exerciseId);           // send data to activity
                        v.getContext().startActivity(i);
                    }
                });
            }
            else
            {
                layoutVideo.setVisibility(View.INVISIBLE);
//                videoButton.setClickable(false);
            }
            if(selectedExercise.getFilePath()!=null)        // if exercise contains file to open
            {
                Log.i(TAG, "file not null");
//                fileButton.setClickable(true);

                layoutFile.setVisibility(View.VISIBLE);            // enable button to open the file
                fileButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v)     // onClick = open file using DownloadFileTask
                    {
                        DownloadFileTask downloadFileTask = new DownloadFileTask(v.getContext());
                        downloadFileTask.execute(copyExercise);
                    }
                });
            }
            else            // if exercise doesn't contain file
            {
                layoutFile.setVisibility(View.INVISIBLE);       // set button not enabled
//                fileButton.setClickable(false);

            }
            if(selectedExercise.getLink()!=null)        // if exercise contains link to follow
            {
                Log.i(TAG, "link not null");
                layoutLink.setVisibility(View.VISIBLE);            // enable button to follow the link
//                linkButton.setClickable(true);
                linkButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v)     // onClick = open link using relevant app
                    {
                        v.getContext().startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(copyExercise.getLink())));
                        Log.i("Video", "Video Playing....");
                    }
                });
            }
            else                // if exercise doesn't contain link
            {
                layoutLink.setVisibility(View.INVISIBLE);       // set button not enables
//                linkButton.setClickable(false);

            }
        }


        return convertView;
    }


    @Override
    public void onItemClick(AdapterView<?> parent, android.view.View view, int position, long id)
    {
        view.getTag(3);

//        TextView exerciseIdTextView = (TextView)view.findViewById(R.id.exerciseId);
//        TextView exerciseTitleTextView = (TextView)view.findViewById(R.id.exerciseTitle);
//
//        String exerciseTitle = exerciseTitleTextView.getText().toString();        // getting exercise name from exercise selected
//        String exerciseId = exerciseIdTextView.getText().toString();       // getting exercise id from exercise selected
//        Log.d("ON CLICK", exerciseTitle + " " + exerciseId);
//
//        Intent i = new Intent(view.getContext(), ExerciseActivity.class);      // opening selected exercise
//        i.putExtra("exerciseTitle", exerciseTitle);       // passing folderName to the new intent
//        i.putExtra("exerciseId", exerciseId);       // passing folderId to the new intent
//        view.getContext().startActivity(i);
    }
}

