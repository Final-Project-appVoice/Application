package com.example.sarah.myproject.Activities;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.VideoView;

import com.example.sarah.myproject.R;

import java.util.ArrayList;
import java.util.List;

public class TheoreticalBackgroundActivity extends Activity
{
    private String link_path = "https://www.youtube.com/watch?v=moSFlvxnbgk";
    private VideoView videoView;
    private ListView listViewPdf;
    //private Button btnvideo;
    private ImageButton btnvideo;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_theoretical_background);
       // btnvideo = (Button)findViewById(R.id.button_video);
        btnvideo = (ImageButton)findViewById(R.id.button_video);
        listViewPdf = (ListView)findViewById(R.id.listView_pdf_theoreticalBackground);
        btnvideo.setOnClickListener(new View.OnClickListener()
        {

            public void onClick(View v)
            {
                startActivity(new Intent(Intent.ACTION_VIEW,Uri.parse(link_path)));
                Log.i("Video", "Video Playing....");

            }
        });

        fillListViewPDF();

    }

    private void fillListViewPDF() {

        // Instanciating an array list
        List<String> pdf_list = new ArrayList<String>();
        for (int i = 1; i <= 3; i++) {
            pdf_list.add("File" + i);
        }

        ArrayAdapter<String> pdfArrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, pdf_list);

        listViewPdf.setAdapter(pdfArrayAdapter);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_theorical_background, menu);
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
