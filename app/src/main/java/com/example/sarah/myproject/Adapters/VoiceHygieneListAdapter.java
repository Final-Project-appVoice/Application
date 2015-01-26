package com.example.sarah.myproject.Adapters;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.TextView;

import com.example.sarah.myproject.R;

/**
 * Created by Sarah on 26-Jan-15.
 */
public class VoiceHygieneListAdapter extends CursorAdapter
{
    private LayoutInflater inflater;
    public VoiceHygieneListAdapter(Context context, Cursor c)
    {
        super(context, c, 0);
        inflater = LayoutInflater.from(context);

    }
    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent)
    {
        return inflater.inflate(R.layout.row_listview_voicehygiene, parent,false);
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor)
    {
        TextView line_number = (TextView) view.findViewById(R.id.row_number_pdf);
        TextView pdf_path = (TextView) view.findViewById(R.id.path_pdf);

        line_number.setText(cursor.getString(0) + " ");
        pdf_path.setText(cursor.getString(1) + " ");
    }
}
