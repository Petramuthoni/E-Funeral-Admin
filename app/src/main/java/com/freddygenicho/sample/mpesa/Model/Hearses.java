package com.freddygenicho.sample.mpesa.Model;

public class Hearses  {
    private String date,description,image,pid,pname,time;

    public Hearses() {
    }

    public Hearses(String date, String description, String image, String pid, String pname, String time) {
        this.date = date;
        this.description = description;
        this.image = image;
        this.pid = pid;
        this.pname = pname;
        this.time = time;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getPid() {
        return pid;
    }

    public void setPid(String pid) {
        this.pid = pid;
    }

    public String getPname() {
        return pname;
    }

    public void setPname(String pname) {
        this.pname = pname;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
