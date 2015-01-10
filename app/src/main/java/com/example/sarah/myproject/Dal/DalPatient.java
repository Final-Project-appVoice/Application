package com.example.sarah.myproject.Dal;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.sarah.myproject.DB.MyDbHelper;
import com.example.sarah.myproject.Class.Patient;
import com.example.sarah.myproject.DB.Patients_db;

/**
 * Created by Sarah on 29-Dec-14.
 */
public class DalPatient
{
    MyDbHelper dbHelper;
    SQLiteDatabase db;

    //Patient patient;
    public DalPatient()
    {

    }
    public void addNewPatient(Patient patient, String password, Context context)
    {

        dbHelper = new MyDbHelper(context);
        db = dbHelper.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(Patients_db.COLUMN_NAME_ENTRY_ID, patient.getId());
        values.put(Patients_db.COLUMN_NAME_FIRST_NAME, patient.getFName());
        values.put(Patients_db.COLUMN_NAME_LAST_NAME, patient.getLName());
        values.put(Patients_db.COLUMN_NAME_MAIL, patient.getMail());
        values.put(Patients_db.COLUMN_NAME_PASSWORD, password);
        values.put(Patients_db.COLUMN_NAME_ADDRESS, patient.getAddress());
        values.put(Patients_db.COLUMN_NAME_PHONE, patient.getPhone());
        values.put(Patients_db.COLUMN_NAME_HMO, patient.getHMO());
        db.insertOrThrow(Patients_db.TABLE_NAME, null, values);
        db.close();


    }

    public Cursor findPatientByIdAndPwd(String username, String password, Context context)
    {
        SQLiteDatabase db1 = dbHelper.getReadableDatabase();

        // SELECT COLUMN_NAME_ENTRY_ID, COLUMN_NAME_PASSWORD FROM TABLE_NAME
        // WHERE COLUMN_NAME_ENTRY_ID = username AND COLUMN_NAME_PASSWORD = password
        String[] cols = {Patients_db.COLUMN_NAME_ENTRY_ID, Patients_db.COLUMN_NAME_PASSWORD};
        Cursor c = db1.query(
                Patients_db.TABLE_NAME,
                cols,
                Patients_db.COLUMN_NAME_ENTRY_ID + "=? AND " + Patients_db.COLUMN_NAME_PASSWORD + "=?",
                new String[]{username, password},
                null,null,null);

        if (!(c.moveToFirst()) || c.getCount() == 0)
        {
            //cursor is empty
            return null;
        }
        return c;
    }
    public Cursor findPatientById(String username, Context context)
    {
        SQLiteDatabase db1 = dbHelper.getReadableDatabase();

        // SELECT COLUMN_NAME_ENTRY_ID, COLUMN_NAME_PASSWORD FROM TABLE_NAME
        // WHERE COLUMN_NAME_ENTRY_ID = username AND COLUMN_NAME_PASSWORD = password
        String[] cols = {Patients_db.COLUMN_NAME_ENTRY_ID};
        Cursor c = db1.query(
                Patients_db.TABLE_NAME,
                cols,
                Patients_db.COLUMN_NAME_ENTRY_ID + "=?",
                new String[]{username},
                null,null,null);

        if (!(c.moveToFirst()) || c.getCount() == 0)
        {
            //cursor is empty
            return null;
        }
        return c;
    }
    public boolean isPatientExists(Cursor c)
    {
        if (c == null)
        {
            return false;
        }
        return true;
    }

    public String showDetails(String username, Context context)
    {
        dbHelper = new MyDbHelper(context);
        db = dbHelper.getWritableDatabase();

        Cursor c = db.rawQuery("SELECT * FROM patients WHERE id='"+username+"'", null);
    String str = "";
        Patient p;

        if(c.moveToFirst())
        {
            Log.w("DataBase:", c.getString(1)+"\n"+ c.getString(2)+"\n"+ c.getString(3)+"\n"+ c.getString(4)+"\n"+ c.getString(5)+"\n"+ c.getString(6)+"\n"+ c.getString(7)+"\n"+ c.getString(8));
            p = new Patient(c.getString(1), c.getString(2), c.getString(3), c.getString(4), c.getString(6), c.getString(7), c.getString(8));
            str += p.toString();
//            editName.setText();
//            editMarks.setText(c.getString(2));
           // patient_textView.setText(p.toString());
        }
        return str;
    }

    public Patient patientDetails(String username, Context context)
    {
        dbHelper = new MyDbHelper(context);
        db = dbHelper.getWritableDatabase();

        Cursor c = db.rawQuery("SELECT * FROM patients WHERE id='"+username+"'", null);
        String str = "";
        Patient p;

        if(c.moveToFirst())
        {
            Log.w("DataBAse:", c.getString(1) + "\n" + c.getString(2) + "\n" + c.getString(3) + "\n" + c.getString(4) + "\n" + c.getString(5) + "\n" + c.getString(6) + "\n" + c.getString(7) + "\n" + c.getString(8));
            p = new Patient(c.getString(1), c.getString(2), c.getString(3), c.getString(4), c.getString(6), c.getString(7), c.getString(8));
            return p;
        }
        return null;
    }
}
