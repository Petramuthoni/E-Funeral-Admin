package com.freddy.sample.mpesa.Model;

public class Orders {
    public String address;
    public String city;
    public String date;
    public String name;
    public String phone;
    public String pid;
    public String uid;
    public String time;
    public String totalAmount;
    public String ordernumber;

    public Orders() {
    }

    @Override
    public String toString() {
        return "Orders{" +
                "address='" + address + '\'' +
                ", city='" + city + '\'' +
                ", date='" + date + '\'' +
                ", name='" + name + '\'' +
                ", phone='" + phone + '\'' +
                ", pid='" + pid + '\'' +
                ", uid='" + uid + '\'' +
                ", time='" + time + '\'' +
                ", totalAmount='" + totalAmount + '\'' +
                ", ordernumber='" + ordernumber + '\'' +
                '}';
    }

    public Orders(String address, String city, String date, String name, String phone, String pid, String uid, String time, String totalAmount, String ordernumber) {
        this.address = address;
        this.city = city;
        this.date = date;
        this.name = name;
        this.phone = phone;
        this.pid = pid;
        this.uid = uid;
        this.time = time;
        this.totalAmount = totalAmount;
        this.ordernumber = ordernumber;
    }

    public String getOrdernumber() {
        return ordernumber;
    }

    public void setOrdernumber(String ordernumber) {
        this.ordernumber = ordernumber;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }


    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
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

    public String getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(String totalAmount) {
        this.totalAmount = totalAmount;
    }
}
