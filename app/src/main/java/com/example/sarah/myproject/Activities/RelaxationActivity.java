package com.example.sarah.myproject.Activities;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.sarah.myproject.R;

import java.util.ArrayList;
import java.util.List;

public class RelaxationActivity extends Activity
{
    private ListView listView_exercises;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_relaxation);

        listView_exercises = (ListView)findViewById(R.id.listView_exercises_relaxation);
        fillListViewExercises();
    }

    private void fillListViewExercises()
    {

        // Instanciating an array list
        List<String> exercises_list = new ArrayList<String>();
        for (int i=1; i<=3; i++)
        {
            exercises_list.add("Exercise" + i);
        }


        // This is the array adapter, it takes the context of the activity as a
        // first parameter, the type of list view as a second parameter and your
        // array as a third parameter.
        ArrayAdapter<String> exerciseArrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, exercises_list);

        listView_exercises.setAdapter(exerciseArrayAdapter);


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_relaxation, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
