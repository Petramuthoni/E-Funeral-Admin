package com.freddygenicho.sample.mpesa.Model;

public class Donations {
    private String date,dname,pid,pleadescription,time,donateto,image;

    public Donations() {
    }

    @Override
    public String toString() {
        return "Donations{" +
                "date='" + date + '\'' +
                ", dname='" + dname + '\'' +
                ", pid='" + pid + '\'' +
                ", pleadescription='" + pleadescription + '\'' +
                ", time='" + time + '\'' +
                ", donateto='" + donateto + '\'' +
                ", image='" + image + '\'' +
                '}';
    }

    public Donations(String date, String dname, String pid, String pleadescription, String time, String donateto, String image) {
        this.date = date;
        this.dname = dname;
        this.pid = pid;
        this.pleadescription = pleadescription;
        this.time = time;
        this.donateto = donateto;
        this.image = image;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getDname() {
        return dname;
    }

    public void setDname(String dname) {
        this.dname = dname;
    }

    public String getPid() {
        return pid;
    }

    public void setPid(String pid) {
        this.pid = pid;
    }

    public String getPleadescription() {
        return pleadescription;
    }

    public void setPleadescription(String pleadescription) {
        this.pleadescription = pleadescription;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getDonateto() {
        return donateto;
    }

    public void setDonateto(String donateto) {
        this.donateto = donateto;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
