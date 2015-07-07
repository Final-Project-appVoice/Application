package com.example.sarah.myproject.Tasks;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.sarah.myproject.Adapters.FoldersAdapter;
import com.example.sarah.myproject.Class.Message;
import com.example.sarah.myproject.Class.SessionManager;
import com.example.sarah.myproject.Dal.DalConstant;
import com.example.sarah.myproject.R;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

/**
 * Created by Sarah on 29-Jun-15.
 */
public class GetMessageTask extends AsyncTask<String, Boolean, Message> {
    // constant to log in the DB
    public static final String DB_URL = DalConstant.DB_URL;
    public static final String USER = DalConstant.USER;
    public static final String PASS = DalConstant.PASS;

    private ProgressDialog dialog;
    public LinearLayout bar;
    private Context context;
    private FoldersAdapter foldersAdapter;

    private SessionManager session;    // Session Manager Class


    public GetMessageTask(Context context)
    {
        this.context = context;

        // Session Manager
        session = new SessionManager(context);

        bar = (LinearLayout) ((Activity) context).findViewById(R.id.ProgressBar);
    }

    protected void onPreExecute() {
        bar.setVisibility(View.VISIBLE);            // set progress bar visible
    }

    @Override
    protected Message doInBackground(String... params)
    {
        String messageId = params[0];
        Message message = null;
        try
        {
            // Connecting to the database
            Class.forName("com.mysql.jdbc.Driver").newInstance();
            Connection con = DriverManager.getConnection(DB_URL, USER, PASS);

            Statement statement1 = con.createStatement();

            String query1 = "SELECT * FROM Messages WHERE MessageId = '" + messageId + "'";
            ResultSet rs1 = statement1.executeQuery(query1);
            if (rs1.next())
            {
                Log.i("DB MESSAGE", "IN");
                String messageTo = rs1.getString("MessageTo");
                String messageFrom = rs1.getString("MessageFrom");
                String messageText = rs1.getString("Message");
                int isRead = rs1.getInt("IsRead");
                message = new Message(Integer.parseInt(messageId), messageFrom, messageTo, messageText, isRead);
                Log.i("DB MESSAGE", message.toString());
            }
            con.close();
        } catch (Exception e)
        {
            e.printStackTrace();
            Log.i("DB MESSAGE", "ERROR");
            return null;
        }
        return message;
    }

    @Override
    protected void onPostExecute(Message message)
    {
        super.onPostExecute(message);
        bar.setVisibility(View.GONE);
        TextView messageFrom = (TextView)((Activity)context).findViewById(R.id.messageFrom);
        TextView messageText = (TextView)((Activity)context).findViewById(R.id.messageText);

        messageFrom.setText(message.getMessageFrom());
        messageText.setText(message.getMessageText());

    }
}
