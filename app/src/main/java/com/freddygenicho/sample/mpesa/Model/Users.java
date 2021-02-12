package com.freddygenicho.sample.mpesa.Model;

public class Users {
    public String name, Phone, Email, uid;

    public Users() {

    }

    @Override
    public String toString() {
        return "Users{" +
                "name='" + name + '\'' +
                ", Phone='" + Phone + '\'' +
                ", Email='" + Email + '\'' +
                ", uid='" + uid + '\'' +
                '}';
    }

    public Users(String name, String phone, String email, String uid) {
        this.name = name;
        Phone = phone;
        Email = email;
        this.uid = uid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return Phone;
    }

    public void setPhone(String phone) {
        Phone = phone;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }
}

