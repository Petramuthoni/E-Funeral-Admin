package com.freddy.sample.mpesa.Model;

public class funeralrequests {
    public String bereavedname,did,phonenumber,startdate,deceasedgender,enddate,durationindays,deceasedname,currentdate,currenttime,rqn,admittedby;
    public funeralrequests(){}

//    public funeralrequests(String bereavedname, String did, String phonenumber, String startdate, String deceasedgender, String enddate, String durationindays, String deceasedname, String currentdate, String currenttime, String rqn) {
//        this.bereavedname = bereavedname;
//        this.did = did;
//        this.phonenumber = phonenumber;
//        this.startdate = startdate;
//        this.deceasedgender = deceasedgender;
//        this.enddate = enddate;
//        this.durationindays = durationindays;
//        this.deceasedname = deceasedname;
//        this.currentdate = currentdate;
//        this.currenttime = currenttime;
//        this.rqn = rqn;
//    }

    public funeralrequests(String bereavedname, String did, String phonenumber, String startdate, String deceasedgender, String enddate, String durationindays, String deceasedname, String currentdate, String currenttime, String rqn, String admittedby) {
        this.bereavedname = bereavedname;
        this.did = did;
        this.phonenumber = phonenumber;
        this.startdate = startdate;
        this.deceasedgender = deceasedgender;
        this.enddate = enddate;
        this.durationindays = durationindays;
        this.deceasedname = deceasedname;
        this.currentdate = currentdate;
        this.currenttime = currenttime;
        this.rqn = rqn;
        this.admittedby = admittedby;
    }

    public String getAdmittedby() {
        return admittedby;
    }

    public void setAdmittedby(String admittedby) {
        this.admittedby = admittedby;
    }

    public String getRqn() {
        return rqn;
    }

    public void setRqn(String rqn) {
        this.rqn = rqn;
    }

    public String getBereavedname() {
        return bereavedname;
    }

    public void setBereavedname(String bereavedname) {
        this.bereavedname = bereavedname;
    }

    public String getDid() {
        return did;
    }

    public void setDid(String did) {
        this.did = did;
    }

    public String getPhonenumber() {
        return phonenumber;
    }

    public void setPhonenumber(String phonenumber) {
        this.phonenumber = phonenumber;
    }

    public String getStartdate() {
        return startdate;
    }

    public void setStartdate(String startdate) {
        this.startdate = startdate;
    }

    public String getDeceasedgender() {
        return deceasedgender;
    }

    public void setDeceasedgender(String deceasedgender) {
        this.deceasedgender = deceasedgender;
    }

    public String getEnddate() {
        return enddate;
    }

    public void setEnddate(String enddate) {
        this.enddate = enddate;
    }

    public String getDurationindays() {
        return durationindays;
    }

    public void setDurationindays(String durationindays) {
        this.durationindays = durationindays;
    }

    public String getDeceasedname() {
        return deceasedname;
    }

    public void setDeceasedname(String deceasedname) {
        this.deceasedname = deceasedname;
    }

    public String getCurrentdate() {
        return currentdate;
    }

    public void setCurrentdate(String currentdate) {
        this.currentdate = currentdate;
    }

    public String getCurrenttime() {
        return currenttime;
    }

    public void setCurrenttime(String currenttime) {
        this.currenttime = currenttime;
    }

    @Override
    public String toString() {
        return "funeralrequests{" +
                "bereavedname='" + bereavedname + '\'' +
                ", did='" + did + '\'' +
                ", phonenumber='" + phonenumber + '\'' +
                ", startdate='" + startdate + '\'' +
                ", deceasedgender='" + deceasedgender + '\'' +
                ", enddate='" + enddate + '\'' +
                ", durationindays='" + durationindays + '\'' +
                ", deceasedname='" + deceasedname + '\'' +
                ", currentdate='" + currentdate + '\'' +
                ", currenttime='" + currenttime + '\'' +
                '}';
    }
}
