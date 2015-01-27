//package com.example.sarah.myproject.Adapters;
//
//import android.content.Context;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.ArrayAdapter;
//import android.widget.Button;
//import android.widget.TextView;
//
//import com.example.sarah.myproject.R;
//
//import java.util.ArrayList;
//import java.util.List;
//import java.util.zip.Inflater;
//
///**
// * Created by Sarah on 26-Jan-15.
// */
//public class ExerciseAdapter extends ArrayAdapter<String>
//{
//    private LayoutInflater inflater;
//
//    private String[] exercises;
//    private List<String> exerciseList;
//    private Button button_exercise;
//
//    public ExerciseAdapter(Context context, int resource, int textViewRId, String[] items) {
//        super(context, resource, textViewRId, items);
//        this.exercises = items;
//
//        exerciseList = new ArrayList<String>();
//
//        exerciseList.add("ראש וכתפיים");
//        exerciseList.add("גב ועמוד השדרה");
//        exerciseList.add("פנים פה ולסת");
//        exerciseList.add("לשון");
//        exerciseList.add("חשובים");
//
//    }
//
//    @Override
//    public View getView(int position, View convertView, ViewGroup parent)
//    {
//        View buttonView = (View)getLayoutInflater().inflate(R.layout.item_as_button, null);
//
//        String itemText = exercises[position];
//
//        ((Button) buttonView.findViewById(R.id.button_exercise_adapter)).setText(itemText);
//        convertView = buttonView;
//
//
//        return convertView;
//
//    }
//}
//
//
//
