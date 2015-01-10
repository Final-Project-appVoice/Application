package com.example.sarah.myproject.Class;

/**
 * Created by Sarah on 29-Dec-14.
 */
public class Patient
{
    String id;
    String fName;
    String lName;
    String mail;
    String address;
    String phone;
    String HMO;

    public Patient(String id, String fName, String lName, String mail, String address, String phone, String HMO)
    {
        this.id = id;
        this.fName = fName;
        this.lName = lName;
        this.mail = mail;
        this.address = address;
        this.phone = phone;
        this.HMO = HMO;
    }

    public String getId()
    {
        return id;
    }

    public void setId(String id)
    {
        this.id = id;
    }

    public String getFName()
    {
        return fName;
    }

    public void setFName(String fName)
    {
        this.fName = fName;
    }

    public String getLName()
    {
        return lName;
    }

    public void setLName(String lName)
    {
        this.lName = lName;
    }

    public String getMail()
    {
        return mail;
    }

    public void setMail(String mail)
    {
        this.mail = mail;
    }

    public String getAddress()
    {
        return address;
    }

    public void setAddress(String address)
    {
        this.address = address;
    }

    public String getPhone()
    {
        return phone;
    }

    public void setPhone(String phone)
    {
        this.phone = phone;
    }

    public String getHMO()
    {
        return HMO;
    }

    public void setHMO(String HMO)
    {
        this.HMO = HMO;
    }

    @Override
    public String toString()
    {
        return "Patient{" +
                "id=" + id +
                ", fName='" + fName + '\'' +
                ", lName='" + lName + '\'' +
                ", mail='" + mail + '\'' +
                ", address='" + address + '\'' +
                ", phone=" + phone +
                ", HMO='" + HMO + '\'' +
                '}';
    }
}
