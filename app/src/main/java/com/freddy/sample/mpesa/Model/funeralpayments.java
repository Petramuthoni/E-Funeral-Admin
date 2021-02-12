package com.freddy.sample.mpesa.Model;

public class funeralpayments {
    private String amountpaid,amounttobepaid,balance,rnumber,name,phonenumber,paymentnumber,date,time,pid,preservationstart,preservationend,deceasedfullnames,deceasedgenders,preservationduration;
    public funeralpayments() {
    }





//
//    public funeralpayments(String amountpaid, String amounttobepaid, String balance, String rnumber, String name, String phonenumber, String paymentnumber, String date, String time, String pid, String preservationstart) {
//        this.amountpaid = amountpaid;
//        this.amounttobepaid = amounttobepaid;
//        this.balance = balance;
//        this.rnumber = rnumber;
//        this.name = name;
//        this.phonenumber = phonenumber;
//        this.paymentnumber = paymentnumber;
//        this.date = date;
//        this.time = time;
//        this.pid = pid;
//        this.preservationstart = preservationstart;
//    }


    public funeralpayments(String amountpaid, String amounttobepaid, String balance, String rnumber, String name, String phonenumber, String paymentnumber, String date, String time, String pid, String preservationstart, String preservationend, String deceasedfullnames, String deceasedgenders, String preservationduration) {
        this.amountpaid = amountpaid;
        this.amounttobepaid = amounttobepaid;
        this.balance = balance;
        this.rnumber = rnumber;
        this.name = name;
        this.phonenumber = phonenumber;
        this.paymentnumber = paymentnumber;
        this.date = date;
        this.time = time;
        this.pid = pid;
        this.preservationstart = preservationstart;
        this.preservationend = preservationend;
        this.deceasedfullnames = deceasedfullnames;
        this.deceasedgenders = deceasedgenders;
        this.preservationduration = preservationduration;
    }

    public String getPreservationduration() {
        return preservationduration;
    }

    public void setPreservationduration(String preservationduration) {
        this.preservationduration = preservationduration;
    }

    @Override
    public String toString() {
        return "funeralpayments{" +
                "amountpaid='" + amountpaid + '\'' +
                ", amounttobepaid='" + amounttobepaid + '\'' +
                ", balance='" + balance + '\'' +
                ", rnumber='" + rnumber + '\'' +
                ", name='" + name + '\'' +
                ", phonenumber='" + phonenumber + '\'' +
                ", paymentnumber='" + paymentnumber + '\'' +
                ", date='" + date + '\'' +
                ", time='" + time + '\'' +
                ", pid='" + pid + '\'' +
                ", preservationstart='" + preservationstart + '\'' +
                ", preservationend='" + preservationend + '\'' +
                ", deceasedfullnames='" + deceasedfullnames + '\'' +
                ", deceasedgenders='" + deceasedgenders + '\'' +
                ", preservationduration='" + preservationduration + '\'' +
                '}';
    }

    public String getPreservationend() {
        return preservationend;
    }

    public void setPreservationend(String preservationend) {
        this.preservationend = preservationend;
    }

    public String getDeceasedfullnames() {
        return deceasedfullnames;
    }

    public void setDeceasedfullnames(String deceasedfullnames) {
        this.deceasedfullnames = deceasedfullnames;
    }

    public String getDeceasedgenders() {
        return deceasedgenders;
    }

    public void setDeceasedgenders(String deceasedgenders) {
        this.deceasedgenders = deceasedgenders;
    }

    public String getPreservationstart() {
        return preservationstart;
    }

    public void setPreservationstart(String preservationstart) {
        this.preservationstart = preservationstart;
    }

    public String getPid() {
        return pid;
    }

    public void setPid(String pid) {
        this.pid = pid;
    }

    public String getAmountpaid() {
        return amountpaid;
    }

    public void setAmountpaid(String amountpaid) {
        this.amountpaid = amountpaid;
    }

    public String getAmounttobepaid() {
        return amounttobepaid;
    }

    public void setAmounttobepaid(String amounttobepaid) {
        this.amounttobepaid = amounttobepaid;
    }

    public String getBalance() {
        return balance;
    }

    public void setBalance(String balance) {
        this.balance = balance;
    }

    public String getRnumber() {
        return rnumber;
    }

    public void setRnumber(String rnumber) {
        this.rnumber = rnumber;
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

    public String getPaymentnumber() {
        return paymentnumber;
    }

    public void setPaymentnumber(String paymentnumber) {
        this.paymentnumber = paymentnumber;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
