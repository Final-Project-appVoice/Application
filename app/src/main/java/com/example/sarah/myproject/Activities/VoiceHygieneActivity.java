package com.example.sarah.myproject.Activities;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.sarah.myproject.Class.Patient;
import com.example.sarah.myproject.Class.SessionManager;
import com.example.sarah.myproject.Dal.DalPatient;
import com.example.sarah.myproject.R;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
public class VoiceHygieneActivity extends Activity
{
    SessionManager session;
    private DalPatient dalPatient;
    private ListView listViewPdf, listViewNotification;
    private TextView welcome_voiceHygiene;
    private Button createPDF, openPDF;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_voice_hygiene);

        session = new SessionManager(getApplicationContext());
        dalPatient = new DalPatient();

        welcome_voiceHygiene = (TextView)findViewById(R.id.welcome_voiceHygiene);
        listViewPdf = (ListView)findViewById(R.id.listView_pdf_voiceHygiene);
        listViewNotification = (ListView)findViewById(R.id.listView_notification_voiceHygiene);

        String patientId = session.getUserDetails();

        Toast.makeText(getApplicationContext(), "User Login Status: " + session.isLoggedIn(), Toast.LENGTH_LONG).show();

        Patient p = dalPatient.patientDetails(patientId, this);

        fillListViewPDF();
        //welcome_voiceHygiene.setText("Hello " + p.getFName());

        openPDF = (Button)findViewById(R.id.button_test_pdf);

    }

    private void fillListViewPDF()
    {

        // Instanciating an array list
        List<String> pdf_list = new ArrayList<String>();
        for (int i=1; i<=3; i++)
        {
            pdf_list.add("File" + i);
        }

        List<String> notification_list = new ArrayList<String>();
        for (int i=1; i<=4; i++)
        {
            notification_list.add("Notification"+i);
        }
//        your_array_list.add("File1");
//        your_array_list.add("File2");
//        your_array_list.add("File3");

        // This is the array adapter, it takes the context of the activity as a
        // first parameter, the type of list view as a second parameter and your
        // array as a third parameter.
        ArrayAdapter<String> pdfArrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, pdf_list);
        ArrayAdapter<String> notificationArrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, notification_list);

        listViewPdf.setAdapter(pdfArrayAdapter);
        listViewNotification.setAdapter(notificationArrayAdapter);


    }
    void openPdf()
    {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        String path = Environment.getExternalStorageDirectory().getAbsolutePath() + "/PDF";

        File file = new File(path, "sarah.pdf");

        intent.setDataAndType( Uri.fromFile(file), "application/pdf" );
        startActivity(intent);
    }

    void openPDF(String filename)
    {
       // File file = new File(Environment.getExternalStorageDirectory().getAbsolutePath() +"/"+ filename);
        File file = new File("C:/Users/Sarah/Desktop/Sarah/סמסטר ז/אנדרואיד/MyProject/app/src/main/res/raw/sarah.pdf");

        Log.d("file",Environment.getExternalStorageDirectory().getAbsolutePath() +"/"+ filename );
        Intent target = new Intent(Intent.ACTION_VIEW);
        target.setDataAndType(Uri.fromFile(file),"application/pdf");
        target.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);

        Intent intent = Intent.createChooser(target, "Open File");
        try {
            startActivity(intent);
        } catch (ActivityNotFoundException e) {
            // Instruct the user to install a PDF reader here, or something
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_voice_hygiene, menu);
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

    public void onClick_button_test_pdf(View view)
    {
        openPDF("sarah");
//        File file = new File(Environment.getExternalStorageDirectory().getAbsolutePath() +"/"+ "sarah.pdf");
//        Uri path = Uri.fromFile(file);
//        Intent intent = new Intent(Intent.ACTION_VIEW);
//        intent.setDataAndType(path, "application/pdf");
//        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//        startActivity(intent);
    }
}
