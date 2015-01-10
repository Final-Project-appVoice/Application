package com.example.sarah.myproject.Class;

import java.util.Collection;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

/**
 * Created by Sarah on 10-Jan-15.
 */
public class VoiceHygiene extends Patient
{
    //String patientId;
    public List<String> listPdfPath;
    public List<ToDoNotifications> listNotification;
    public Hashtable<Integer, ToDoNotifications> toDoNotificationsHashtable;


    public VoiceHygiene(String id, String fName, String lName, String mail, String address, String phone, String HMO, List<String> listPdfPath, List<ToDoNotifications> listNotification, Hashtable<Integer, ToDoNotifications> toDoNotificationsHashtable)
    {
        super(id, fName, lName, mail, address, phone, HMO);
        this.listPdfPath = new List<String>()
        {
            @Override
            public void add(int location, String object) {

            }

            @Override
            public boolean add(String object) {
                return false;
            }

            @Override
            public boolean addAll(int location, Collection<? extends String> collection) {
                return false;
            }

            @Override
            public boolean addAll(Collection<? extends String> collection) {
                return false;
            }

            @Override
            public void clear() {

            }

            @Override
            public boolean contains(Object object) {
                return false;
            }

            @Override
            public boolean containsAll(Collection<?> collection) {
                return false;
            }

            @Override
            public String get(int location) {
                return null;
            }

            @Override
            public int indexOf(Object object) {
                return 0;
            }

            @Override
            public boolean isEmpty() {
                return false;
            }

            @Override
            public Iterator<String> iterator() {
                return null;
            }

            @Override
            public int lastIndexOf(Object object) {
                return 0;
            }

            @Override
            public ListIterator<String> listIterator() {
                return null;
            }

            @Override
            public ListIterator<String> listIterator(int location) {
                return null;
            }

            @Override
            public String remove(int location) {
                return null;
            }

            @Override
            public boolean remove(Object object) {
                return false;
            }

            @Override
            public boolean removeAll(Collection<?> collection) {
                return false;
            }

            @Override
            public boolean retainAll(Collection<?> collection) {
                return false;
            }

            @Override
            public String set(int location, String object) {
                return null;
            }

            @Override
            public int size() {
                return 0;
            }

            @Override
            public List<String> subList(int start, int end) {
                return null;
            }

            @Override
            public Object[] toArray() {
                return new Object[0];
            }

            @Override
            public <T> T[] toArray(T[] array) {
                return null;
            }


        };

        this.listNotification = new List<ToDoNotifications>()
        {
            @Override
            public void add(int location, ToDoNotifications object)
            {

            }

            @Override
            public boolean add(ToDoNotifications object) {
                return false;
            }

            @Override
            public boolean addAll(int location, Collection<? extends ToDoNotifications> collection) {
                return false;
            }

            @Override
            public boolean addAll(Collection<? extends ToDoNotifications> collection) {
                return false;
            }

            @Override
            public void clear() {

            }

            @Override
            public boolean contains(Object object) {
                return false;
            }

            @Override
            public boolean containsAll(Collection<?> collection) {
                return false;
            }

            @Override
            public ToDoNotifications get(int location) {
                return null;
            }

            @Override
            public int indexOf(Object object) {
                return 0;
            }

            @Override
            public boolean isEmpty() {
                return false;
            }

            @Override
            public Iterator<ToDoNotifications> iterator() {
                return null;
            }

            @Override
            public int lastIndexOf(Object object) {
                return 0;
            }

            @Override
            public ListIterator<ToDoNotifications> listIterator() {
                return null;
            }

            @Override
            public ListIterator<ToDoNotifications> listIterator(int location) {
                return null;
            }

            @Override
            public ToDoNotifications remove(int location) {
                return null;
            }

            @Override
            public boolean remove(Object object) {
                return false;
            }

            @Override
            public boolean removeAll(Collection<?> collection) {
                return false;
            }

            @Override
            public boolean retainAll(Collection<?> collection) {
                return false;
            }

            @Override
            public ToDoNotifications set(int location, ToDoNotifications object) {
                return null;
            }

            @Override
            public int size() {
                return 0;
            }

            @Override
            public List<ToDoNotifications> subList(int start, int end) {
                return null;
            }

            @Override
            public Object[] toArray() {
                return new Object[0];
            }

            @Override
            public <T> T[] toArray(T[] array) {
                return null;
            }
        };

        this.toDoNotificationsHashtable = toDoNotificationsHashtable;
    }

    public List<String> getListPdfPath() {
        return listPdfPath;
    }

    public void setListPdfPath(List<String> listPdfPath) {
        this.listPdfPath = listPdfPath;
    }

    public List<ToDoNotifications> getListNotification()
    {
        return listNotification;
    }

    public void setListNotification(List<ToDoNotifications> listNotification)
    {
        this.listNotification = listNotification;
    }

    public Hashtable<Integer, ToDoNotifications> getToDoNotificationsHashtable() {
        return toDoNotificationsHashtable;
    }

    public void setToDoNotificationsHashtable(Hashtable<Integer, ToDoNotifications> toDoNotificationsHashtable) {
        this.toDoNotificationsHashtable = toDoNotificationsHashtable;
    }
}
