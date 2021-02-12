package com.freddy.sample.mpesa.Model;

public class hearsepayments {
    String amountpaid,date,name,phonenumber,pid,time,bid,hpid,bookedhearse;

    public hearsepayments() {
    }

    public hearsepayments(String amountpaid, String date, String name, String phonenumber, String pid, String time, String bid, String hpid, String bookedhearse) {
        this.amountpaid = amountpaid;
        this.date = date;
        this.name = name;
        this.phonenumber = phonenumber;
        this.pid = pid;
        this.time = time;
        this.bid = bid;
        this.hpid = hpid;
        this.bookedhearse = bookedhearse;
    }

//    public hearsepayments(String amountpaid, String date, String name, String phonenumber, String pid, String time, String bid, String hpid) {
//        this.amountpaid = amountpaid;
//        this.date = date;
//        this.name = name;
//        this.phonenumber = phonenumber;
//        this.pid = pid;
//        this.time = time;
//        this.bid = bid;
//        this.hpid = hpid;
//    }


    public String getBookedhearse() {
        return bookedhearse;
    }

    public void setBookedhearse(String bookedhearse) {
        this.bookedhearse = bookedhearse;
    }

    public String getHpid() {
        return hpid;
    }

    @Override
    public String toString() {
        return "hearsepayments{" +
                "amountpaid='" + amountpaid + '\'' +
                ", date='" + date + '\'' +
                ", name='" + name + '\'' +
                ", phonenumber='" + phonenumber + '\'' +
                ", pid='" + pid + '\'' +
                ", time='" + time + '\'' +
                ", bid='" + bid + '\'' +
                ", hpid='" + hpid + '\'' +
                '}';
    }

    public void setHpid(String hpid) {
        this.hpid = hpid;
    }

    public void setAmountpaid(String amountpaid) {
        this.amountpaid = amountpaid;
    }

    public String getBid() {
        return bid;
    }

    public void setBid(String bid) {
        this.bid = bid;
    }

    public String getAmountpaid() {
        return amountpaid;
    }

    public void setAmountpadaid(String amountpadaid) {
        this.amountpaid = amountpaid;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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
}
