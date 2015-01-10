package com.example.sarah.myproject.Class;

import java.util.Hashtable;

/**
 * Created by Sarah on 10-Jan-15.
 */
public class Exercises extends Patient
{
    Boolean isVideo;
    Boolean isSequence;
    Hashtable<Integer, Instructions> instructionsHashtable;
    Hashtable<Integer, String> pdfPathHashtable;

    public Exercises(String id, String fName, String lName, String mail, String address, String phone, String HMO, Boolean isVideo, Boolean isSequence, Hashtable<Integer, Instructions> instructionsHashtable, Hashtable<Integer, String> pdfPathHashtable)
    {
        super(id, fName, lName, mail, address, phone, HMO);
        this.isVideo = isVideo;
        this.isSequence = isSequence;
        this.instructionsHashtable = instructionsHashtable;
        this.pdfPathHashtable = pdfPathHashtable;
    }


    public Boolean getIsVideo() {
        return isVideo;
    }

    public void setIsVideo(Boolean isVideo) {
        this.isVideo = isVideo;
    }

    public Boolean getIsSequence() {
        return isSequence;
    }

    public void setIsSequence(Boolean isSequence) {
        this.isSequence = isSequence;
    }

    public Hashtable<Integer, Instructions> getInstructionsHashtable() {
        return instructionsHashtable;
    }

    public void setInstructionsHashtable(Hashtable<Integer, Instructions> instructionsHashtable) {
        this.instructionsHashtable = instructionsHashtable;
    }

    public Hashtable<Integer, String> getPdfPathHashtable() {
        return pdfPathHashtable;
    }

    public void setPdfPathHashtable(Hashtable<Integer, String> pdfPathHashtable) {
        this.pdfPathHashtable = pdfPathHashtable;
    }
}
