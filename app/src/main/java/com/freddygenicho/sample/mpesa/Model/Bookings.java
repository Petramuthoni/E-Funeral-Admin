package com.freddygenicho.sample.mpesa.Model;

public class Bookings {
    private String bookingdate,date,hearsedescription,hearsename,name,phone,pid,time;

    public Bookings() {
    }

    @Override
    public String toString() {
        return "Bookings{" +
                "bookingdate='" + bookingdate + '\'' +
                ", date='" + date + '\'' +
                ", hearsedescription='" + hearsedescription + '\'' +
                ", hearsename='" + hearsename + '\'' +
                ", name='" + name + '\'' +
                ", phone='" + phone + '\'' +
                ", pid='" + pid + '\'' +
                ", time='" + time + '\'' +
                '}';
    }

    public Bookings(String bookingdate, String date, String hearsedescription, String hearsename, String name, String phone, String pid, String time) {
        this.bookingdate = bookingdate;
        this.date = date;
        this.hearsedescription = hearsedescription;
        this.hearsename = hearsename;
        this.name = name;
        this.phone = phone;
        this.pid = pid;
        this.time = time;
    }

    public String getBookingdate() {
        return bookingdate;
    }

    public void setBookingdate(String bookingdate) {
        this.bookingdate = bookingdate;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getHearsedescription() {
        return hearsedescription;
    }

    public void setHearsedescription(String hearsedescription) {
        this.hearsedescription = hearsedescription;
    }

    public String getHearsename() {
        return hearsename;
    }

    public void setHearsename(String hearsename) {
        this.hearsename = hearsename;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
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
