package com.example.sarah.myproject.Class;

import java.util.Hashtable;

/**
 * Created by Sarah on 10-Jan-15.
 *
 * VoiceHygiene describes all the values that will be on that page.
 * The class contains:
 *      * List of String = the pdf files path
 *      * Hasthable - The hashtable represents the list of notifications the patient may want to add to his application
 *                  - Key = Integer - the priority of the notification
 *                  - Value = ToDoNotification - the text of the notification and its details
 */
public class VoiceHygiene// extends Patient
{

    //String patientId;
    //public List<String> listPdfPath;
    public Hashtable<Integer, String> pdfPathHashtable;
//    public List<ToDoNotifications> listNotification;
    public Hashtable<Integer, ToDoNotifications> toDoNotificationsHashtable;
//    public Hashtable<Integer, String> toDoNotificationsHashtable;


    public VoiceHygiene(Hashtable<Integer, String> pdfPathHashtable, Hashtable<Integer, ToDoNotifications> toDoNotificationsHashtable)
    {
        this.pdfPathHashtable = pdfPathHashtable;
        this.toDoNotificationsHashtable = toDoNotificationsHashtable;
    }

    public Hashtable<Integer, String> getPdfPathHashtable() {
        return pdfPathHashtable;
    }

    public void setPdfPathHashtable(Hashtable<Integer, String> pdfPathHashtable) {
        this.pdfPathHashtable = pdfPathHashtable;
    }

    public Hashtable<Integer, ToDoNotifications> getToDoNotificationsHashtable() {
        return toDoNotificationsHashtable;
    }

    public void setToDoNotificationsHashtable(Hashtable<Integer, ToDoNotifications> toDoNotificationsHashtable) {
        this.toDoNotificationsHashtable = toDoNotificationsHashtable;
    }
}
