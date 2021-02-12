package com.freddy.sample.mpesa.Model;

public class morticians {
    String morticianEmail,morticianPhone,morticianname,uid;

    public morticians() {
    }

    public morticians(String morticianEmail, String morticianPhone, String morticianname, String uid) {
        this.morticianEmail = morticianEmail;
        this.morticianPhone = morticianPhone;
        this.morticianname = morticianname;
        this.uid = uid;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getMorticianEmail() {
        return morticianEmail;
    }

    public void setMorticianEmail(String morticianEmail) {
        this.morticianEmail = morticianEmail;
    }

    public String getMorticianPhone() {
        return morticianPhone;
    }

    public void setMorticianPhone(String morticianPhone) {
        this.morticianPhone = morticianPhone;
    }

    public String getMorticianname() {
        return morticianname;
    }

    public void setMorticianname(String morticianname) {
        this.morticianname = morticianname;
    }
}
