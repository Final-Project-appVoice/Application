package com.example.sarah.myproject.DB;

import android.provider.BaseColumns;

/**
 * Created by Sarah on 28-Dec-14.
 */

public class Patients_db implements BaseColumns
{

    public static final String TABLE_NAME = "patients" ;
    public static final String COLUMN_NAME_ENTRY_ID = "id";
    public static final String COLUMN_NAME_FIRST_NAME = "first_name";
    public static final String COLUMN_NAME_LAST_NAME = "last_name";
    public static final String COLUMN_NAME_MAIL = "mail";
    public static final String COLUMN_NAME_PASSWORD = "password";
    public static final String COLUMN_NAME_ADDRESS = "address";
    public static final String COLUMN_NAME_PHONE = "phone";
    public static final String COLUMN_NAME_HMO = "HMO";

}


