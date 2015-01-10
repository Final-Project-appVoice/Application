package com.example.sarah.myproject.Class;

import java.util.Hashtable;

/**
 * Created by Sarah on 10-Jan-15.
 */
public class TheoreticalBackground extends Patient
{
    String pdfPath;
    Hashtable<Integer, Instructions> hashtable;

    public TheoreticalBackground(String id, String fName, String lName, String mail, String address, String phone, String HMO, String pdfPath, Hashtable<Integer, Instructions> hashtable)
    {
        super(id, fName, lName, mail, address, phone, HMO);
        this.pdfPath = pdfPath;
        this.hashtable = hashtable;
    }


    public String getPdfPath() {
        return pdfPath;
    }

    public void setPdfPath(String pdfPath) {
        this.pdfPath = pdfPath;
    }

    public Hashtable<Integer, Instructions> getHashtable() {
        return hashtable;
    }

    public void setHashtable(Hashtable<Integer, Instructions> hashtable) {
        this.hashtable = hashtable;
    }
}
