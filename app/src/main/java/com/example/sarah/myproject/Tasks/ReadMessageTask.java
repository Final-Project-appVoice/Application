package com.example.sarah.myproject.Tasks;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.example.sarah.myproject.Activities.MessageActivity;
import com.example.sarah.myproject.Dal.DalConstant;
import com.example.sarah.myproject.R;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.Statement;

/**
 * Created by Sarah on 29-Jun-15.
 */
public class ReadMessageTask extends AsyncTask<Integer, Boolean, Boolean>
{

    // constant to log in the DB
    public static final String DB_URL = DalConstant.DB_URL;
    public static final String USER = DalConstant.USER;
    public static final String PASS = DalConstant.PASS;

    private Context context;
    private View view;

    public ReadMessageTask(Context context, View view)
    {
        this.context = context;
        this.view = view;

    }
    @Override
    protected Boolean doInBackground(Integer... params)
    {
        int messageId = params[0];      // get message id

        try
        {
            // Connecting to the database
            Class.forName("com.mysql.jdbc.Driver").newInstance();
            Connection con = DriverManager.getConnection(DB_URL, USER, PASS);

            Statement statement1 = con.createStatement();
            //Statement statement2 = con.createStatement();

            // After connection
            String query1 = "UPDATE Messages SET IsRead = ? WHERE MessageId = '" + messageId + "'";
            PreparedStatement preparedStmt = con.prepareStatement(query1);
            preparedStmt.setInt(1, 1);

            // execute the java preparedstatement
            preparedStmt.executeUpdate();
            con.close();

        }
        catch (Exception e)         // if connection to db didn't succeed
        {
            e.printStackTrace();
            Log.e("READ MESSAGE", "Error in update");
            return false;
        }
        return true;
    }

    @Override
    protected void onPostExecute(Boolean aBoolean)
    {
        super.onPostExecute(aBoolean);

        Intent i = new Intent(context, MessageActivity.class);

        TextView messageId = (TextView)view.findViewById(R.id.messageId);

        i.putExtra("MessageId", messageId.getText());
        context.startActivity(i);
    }
}
