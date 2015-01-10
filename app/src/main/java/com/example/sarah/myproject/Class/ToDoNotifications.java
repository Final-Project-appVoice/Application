package com.example.sarah.myproject.Class;

import java.sql.Time;

/**
 * Created by Sarah on 10-Jan-15.
 */
public class ToDoNotifications
{
    String titleOfNotification;
    String textOfNotification;
    Time time;
    int priority;

    public ToDoNotifications(String titleOfNotification, String textOfNotification, Time time, int priority)
    {
        this.titleOfNotification = titleOfNotification;
        this.textOfNotification = textOfNotification;
        this.time = time;
        this.priority = priority;
    }

    public String getTitleOfNotification()
    {
        return titleOfNotification;
    }

    public void setTitleOfNotification(String titleOfNotification)
    {
        this.titleOfNotification = titleOfNotification;
    }

    public String getTextOfNotification()
    {
        return textOfNotification;
    }

    public void setTextOfNotification(String textOfNotification)
    {
        this.textOfNotification = textOfNotification;
    }

    public Time getTime()
    {
        return time;
    }

    public void setTime(Time time)
    {
        this.time = time;
    }

    public int getPriority()
    {
        return priority;
    }

    public void setPriority(int priority)
    {
        this.priority = priority;
    }
}
