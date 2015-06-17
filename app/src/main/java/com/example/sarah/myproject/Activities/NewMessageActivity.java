package com.example.sarah.myproject.Activities;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.sarah.myproject.Class.AlertDialogManager;
import com.example.sarah.myproject.Class.SessionManager;
import com.example.sarah.myproject.R;
import com.example.sarah.myproject.Tasks.GetTherapistDetailsTask;
import com.example.sarah.myproject.Tasks.SendMessageTask;

import java.util.List;

public class NewMessageActivity extends Activity
{
    private SessionManager sessionManager;
    private String patientId, patientName, therapistId;
    private Button buttonSendMessage;
    private EditText messageEditText;
    public static List<String> therapistDetails;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_message);

        sessionManager = new SessionManager(this);
        String[] patientDetails = sessionManager.getPatientDetails();
        buttonSendMessage = (Button)this.findViewById(R.id.button_sendMessage);
        messageEditText = (EditText)this.findViewById(R.id.message_editText);

        buttonSendMessage.setEnabled(true);
        patientId = patientDetails[0];
        patientName = patientDetails[1];

        therapistId = patientDetails[2];

        GetTherapistDetailsTask getTherapistDetailsTask = new GetTherapistDetailsTask(this);
        getTherapistDetailsTask.execute(therapistId);



    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_new_message, menu);
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

    public void onClick_sendMessage(View view)
    {
        String message = messageEditText.getText().toString();
        if(message.isEmpty())
        {
            AlertDialogManager alertDialogManager = new AlertDialogManager();
            alertDialogManager.showAlertDialog(this, "Message Empty", "You cannot send an empty message", false);
        }
        else
        {
            buttonSendMessage.setEnabled(false);
            SendMessageTask sendMessageTask = new SendMessageTask(this);
            sendMessageTask.execute(patientId, therapistId, message);
        }
    }
}
