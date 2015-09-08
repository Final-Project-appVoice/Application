package com.example.sarah.myproject.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.sarah.myproject.Class.Message;
import com.example.sarah.myproject.R;
import com.example.sarah.myproject.Tasks.GetTherapistDetailsTask;
import com.example.sarah.myproject.Tasks.ReadMessageTask;

import java.util.List;

/**
 * Created by Sarah on 29-Jun-15.
 */
public class MessageAdapter extends ArrayAdapter<Message> implements AdapterView.OnItemClickListener
{
    private LayoutInflater inflater;
    private List<Message> messages;       // list of folder names

    public MessageAdapter(Context context, int resource, int textViewResourceId, List<Message> items)
    {
        super(context, resource, textViewResourceId, items);
        this.messages = items;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public View getView(int position, View convertView, final ViewGroup parent)       // what happens in each row of list
    {
        View view = inflater.inflate(R.layout.message_adapter, null);      // calling to the relevant layout (exercise adapter)
        convertView = view;

        TextView messageText = (TextView)view.findViewById(R.id.messageText);
        TextView messageId = (TextView)view.findViewById(R.id.messageId);
        TextView messageDate = (TextView)view.findViewById(R.id.date_textView);

        Message message = getItem(position);

        GetTherapistDetailsTask getTherapistDetailsTask = new GetTherapistDetailsTask(view.getContext());
        getTherapistDetailsTask.execute(message.getMessageFrom()+"");



        messageText.setText(message.getMessageText());
        messageId.setText(message.getMessageId()+"");
        messageDate.setText(message.getMessageDate()+"");

        return convertView;
    }



    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id)
    {
        ReadMessageTask readMessageTask = new ReadMessageTask(parent.getContext(), view);
        readMessageTask.execute(getItem(position).getMessageId());

    }
}
