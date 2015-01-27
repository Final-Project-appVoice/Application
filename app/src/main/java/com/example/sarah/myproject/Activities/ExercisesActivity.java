package com.example.sarah.myproject.Activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import com.example.sarah.myproject.R;

public class ExercisesActivity extends Activity
{
    private Button button1, button2;
    private DisplayMetrics displayMetrics;
    private int screenHeightPx, screenWidthPx;
    private LinearLayout linearLayoutButtons;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exercises);

        button1 = (Button)findViewById(R.id.button_exercise1);
        button2 = (Button)findViewById(R.id.button_exercise2);
        linearLayoutButtons = (LinearLayout)findViewById(R.id.linearLayout_buttons_exercise);

        // get the size of the screen in order to set buttons' size relative to the screen
        displayMetrics = this.getResources().getDisplayMetrics();
        screenHeightPx = (int)(Math.round(displayMetrics.heightPixels));        // height on screen
        screenWidthPx = (int)(Math.round(displayMetrics.widthPixels));          //width of screen

        button1.setHeight((int) Math.round(0.15 * screenHeightPx));
        button1.setWidth((int) Math.round(0.25 * screenWidthPx));
        button2.setHeight((int) Math.round(0.15 * screenHeightPx));
        button2.setWidth((int) Math.round(0.25*screenWidthPx));
        linearLayoutButtons.setPadding(0, (int)Math.round(0.1*screenHeightPx), 0, 0);
        //button2.setPadding(0, (int)Math.round(0.1*screenHeightPx), 0, 0);



    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_exercises, menu);
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

//    onClick Relaxation
    public void onClick_button1(View view)
    {
        Intent i = new Intent(this, RelaxationActivity.class);
        startActivity(i);
    }

    public void onClick_button2(View view)
    {
        Intent i = new Intent(this, BreathingActivity.class);
        startActivity(i);
    }
}
