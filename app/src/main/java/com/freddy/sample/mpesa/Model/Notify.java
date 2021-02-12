package com.freddy.sample.mpesa.Model;

public class Notify {
    String date,bereavedname,deceasedfacials,deceasedgender,deceasedhairdo,deceasedname,deceasedsmell,pickupdate,phonenumber,pid,time;

    public Notify() {
    }

    public Notify(String date, String bereavedname, String deceasedfacials, String deceasedgender, String deceasedhairdo, String deceasedname, String deceasedsmell, String pickupdate, String phonenumber, String pid, String time) {
        this.date = date;
        this.bereavedname = bereavedname;
        this.deceasedfacials = deceasedfacials;
        this.deceasedgender = deceasedgender;
        this.deceasedhairdo = deceasedhairdo;
        this.deceasedname = deceasedname;
        this.deceasedsmell = deceasedsmell;
        this.pickupdate = pickupdate;
        this.phonenumber = phonenumber;
        this.pid = pid;
        this.time = time;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }









    public String getPhonenumber() {
        return phonenumber;
    }

    public void setPhonenumber(String phonenumber) {
        this.phonenumber = phonenumber;
    }

    public String getPid() {
        return pid;
    }

    public void setPid(String pid) {
        this.pid = pid;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getBereavedname() {
        return bereavedname;
    }

    public void setBereavedname(String bereavedname) {
        this.bereavedname = bereavedname;
    }

    public String getDeceasedfacials() {
        return deceasedfacials;
    }

    public void setDeceasedfacials(String deceasedfacials) {
        this.deceasedfacials = deceasedfacials;
    }

    public String getDeceasedgender() {
        return deceasedgender;
    }

    public void setDeceasedgender(String deceasedgender) {
        this.deceasedgender = deceasedgender;
    }

    public String getDeceasedhairdo() {
        return deceasedhairdo;
    }

    public void setDeceasedhairdo(String deceasedhairdo) {
        this.deceasedhairdo = deceasedhairdo;
    }

    public String getDeceasedname() {
        return deceasedname;
    }

    public void setDeceasedname(String deceasedname) {
        this.deceasedname = deceasedname;
    }

    public String getDeceasedsmell() {
        return deceasedsmell;
    }

    public void setDeceasedsmell(String deceasedsmell) {
        this.deceasedsmell = deceasedsmell;
    }

    public String getPickupdate() {
        return pickupdate;
    }

    public void setPickupdate(String pickupdate) {
        this.pickupdate = pickupdate;
    }

    @Override
    public String toString() {
        return "Notify{" +
                "date='" + date + '\'' +
                ", bereavedname='" + bereavedname + '\'' +
                ", deceasedfacials='" + deceasedfacials + '\'' +
                ", deceasedgender='" + deceasedgender + '\'' +
                ", deceasedhairdo='" + deceasedhairdo + '\'' +
                ", deceasedname='" + deceasedname + '\'' +
                ", deceasedsmell='" + deceasedsmell + '\'' +
                ", pickupdate='" + pickupdate + '\'' +
                ", phonenumber='" + phonenumber + '\'' +
                ", pid='" + pid + '\'' +
                ", time='" + time + '\'' +
                '}';
    }
}
