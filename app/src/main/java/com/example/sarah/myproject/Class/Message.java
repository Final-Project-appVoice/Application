package com.example.sarah.myproject.Class;

import java.util.Date;

/**
 * Created by Sarah on 29-Jun-15.
 */
public class Message
{
    int messageId, isRead;
    String messageFrom, messageTo, messageText;
    Date messageDate;

    public Message(int messageId, String messageFrom, String messageTo, String messageText, Date messageDate, int isRead)
    {
        this.isRead = isRead;
        this.messageFrom = messageFrom;
        this.messageId = messageId;
        this.messageText = messageText;
        this.messageDate = messageDate;
        this.messageTo = messageTo;
    }

    public int isRead() {
        return isRead;
    }

    public String getMessageFrom() {
        return messageFrom;
    }

    public int getMessageId() {
        return messageId;
    }

    public String getMessageText() {
        return messageText;
    }

    public Date getMessageDate() {
        return messageDate;
    }

    public String getMessageTo() {
        return messageTo;
    }

    @Override
    public String toString() {
        return "Message{" +
                "isRead=" + isRead +
                ", messageId=" + messageId +
                ", messageFrom='" + messageFrom + '\'' +
                ", messageTo='" + messageTo + '\'' +
                ", messageText='" + messageText + '\'' +
                '}';
    }
}
