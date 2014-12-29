package com.example.sarah.myproject;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

/**
 * Created by Sarah on 29-Dec-14.
 */
public class DalPatient
{
    //Patient patient;
    public DalPatient()
    {

    }
    public void addNewPatient(Patient patient, String password, Context context)
    {

        MyDbHelper dbHelper = new MyDbHelper(context);
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(Patients_db.COLUMN_NAME_ENTRY_ID, patient.getId());
        values.put(Patients_db.COLUMN_NAME_FIRST_NAME, patient.getFName());
        values.put(Patients_db.COLUMN_NAME_LAST_NAME, patient.getLName());
        values.put(Patients_db.COLUMN_NAME_MAIL, patient.getMail());

        values.put(Patients_db.COLUMN_NAME_ADDRESS, patient.getAddress());
        values.put(Patients_db.COLUMN_NAME_PHONE, patient.getPhone());
        values.put(Patients_db.COLUMN_NAME_HMO, patient.getHMO());
        values.put(Patients_db.COLUMN_NAME_PASSWORD, password);
        db.insertOrThrow(Patients_db.TABLE_NAME, null, values);
        db.close();
    }
}
