package com.example.sarah.myproject;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Sarah on 28-Dec-14.
 */

public class MyDbHelper extends SQLiteOpenHelper
{

    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "dataBaseFile.db";


    public MyDbHelper(Context context)
    {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);

    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL("CREATE TABLE "+ Patients_db.TABLE_NAME+ "("+
                        Patients_db._ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                        Patients_db.COLUMN_NAME_ENTRY_ID + " TEXT_TYPE NOT NULL," +
                        Patients_db.COLUMN_NAME_FIRST_NAME + " TEXT_TYPE," +
                        Patients_db.COLUMN_NAME_LAST_NAME  + " TEXT_TYPE," +
                        Patients_db.COLUMN_NAME_MAIL + " TEXT_TYPE," +
                        Patients_db.COLUMN_NAME_PASSWORD + " TEXT_TYPE," +
                        Patients_db.COLUMN_NAME_ADDRESS + " TEXT_TYPE," +
                        Patients_db.COLUMN_NAME_PHONE + " TEXT_TYPE," +
                        Patients_db.COLUMN_NAME_HMO + " TEXT_TYPE);"


        );
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
    {
        db.execSQL("DROP TABLE IF EXISTS " + Patients_db.TABLE_NAME);

    }

}
