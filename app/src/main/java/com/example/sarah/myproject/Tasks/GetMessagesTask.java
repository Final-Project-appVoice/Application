package com.example.sarah.myproject.Tasks;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.example.sarah.myproject.Adapters.FoldersAdapter;
import com.example.sarah.myproject.Adapters.MessageAdapter;
import com.example.sarah.myproject.Class.Message;
import com.example.sarah.myproject.Class.SessionManager;
import com.example.sarah.myproject.Dal.DalConstant;
import com.example.sarah.myproject.R;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Sarah on 29-Jun-15.
 */
public class GetMessagesTask extends AsyncTask<String, Boolean, List<Message>> implements AdapterView.OnItemClickListener {
    // constant to log in the DB
    public static final String DB_URL = DalConstant.DB_URL;
    public static final String USER = DalConstant.USER;
    public static final String PASS = DalConstant.PASS;

    private ProgressDialog dialog;
    public LinearLayout bar;
    private Context context;
    private FoldersAdapter foldersAdapter;

    private SessionManager session;    // Session Manager Class

    public GetMessagesTask(Context context)
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
    protected List<Message> doInBackground(String... params)
    {
        String patientId = params[0];

        List<Message> allMessages = new ArrayList<>();
        try {
            // Connecting to the database
            Class.forName("com.mysql.jdbc.Driver").newInstance();
            Connection con = DriverManager.getConnection(DB_URL, USER, PASS);

            Statement statement1 = con.createStatement();

            String query1 = "SELECT * FROM Messages WHERE MessageTo = '" + patientId + "'";
            ResultSet rs1 = statement1.executeQuery(query1);
            if (rs1.next())
            {
                Log.i("DB MESSAGE", "IN");
                int messageId = rs1.getInt("MessageId");
                String messageFrom = rs1.getString("MessageFrom");
                String messageText = rs1.getString("Message");
                int isRead = rs1.getInt("IsRead");
                Date messageDate = rs1.getDate("MessageDate");
                //DateFormat dateFormat = "dd/mm/yyyy";
                Message message = new Message(messageId, messageFrom, patientId, messageText, messageDate, isRead);
                allMessages.add(message);
                Log.i("DB MESSAGE", message.toString());
            }
            con.close();
        } catch (Exception e) {
            e.printStackTrace();
            Log.i("DB MESSAGE", "ERROR");
            return null;
        }
        return allMessages;
    }

    @Override
    protected void onPostExecute(List<Message> messages)
    {
        super.onPostExecute(messages);
        bar.setVisibility(View.GONE);
        if(messages!=null)
        {
            int count = 0;
            TextView numOfMessage = (TextView)((Activity)context).findViewById(R.id.message_num);
            for(int i=0; i<messages.size(); i++)
            {
                if(messages.get(i).isRead() == 0)
                {
                    count++;
                }
            }
            if(count > 0)
            {
                numOfMessage.setText("(" + count + ")");
            }
            MessageAdapter messageAdapter = new MessageAdapter(context, android.R.layout.activity_list_item, R.id.messages_list, messages);       // calling to the adapter list -- folderList = list without adapter
            ListView listView = (ListView) ((Activity) context).findViewById(R.id.messages_list);     // the list of folders
            listView.setAdapter(messageAdapter);        // adapting to the folder list the adapter list
            listView.setOnItemClickListener(this);
        }
    }

//    @Override
//    public void onClick(View v)
//    {
//        TextView messageIdTextView = (TextView)v.findViewById(R.id.messageId);
//        String messageId = (String) messageIdTextView.getText();
//        ReadMessageTask readMessageTask = new ReadMessageTask(context, v);
//        readMessageTask.execute(Integer.parseInt(messageId));
//    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id)
    {
        ReadMessageTask readMessageTask = new ReadMessageTask(parent.getContext(), view);
        readMessageTask.execute(((Message) parent.getItemAtPosition(position)).getMessageId());
    }
}
