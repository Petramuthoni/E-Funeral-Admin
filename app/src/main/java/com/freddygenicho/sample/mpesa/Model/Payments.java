package com.freddygenicho.sample.mpesa.Model;

public class Payments {
   public String  pid;
    public String date;
    public String time;
    public String  name;
    public String phonenumber;
    public String amountpaid;

 public Payments() {
 }

 public Payments(String pid, String date, String time, String name, String phonenumber, String amountpaid) {
  this.pid = pid;
  this.date = date;
  this.time = time;
  this.name = name;
  this.phonenumber = phonenumber;
  this.amountpaid = amountpaid;
 }

 public String getPid() {
  return pid;
 }

 public void setPid(String pid) {
  this.pid = pid;
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

 public String getAmountpaid() {
  return amountpaid;
 }

 public void setAmountpaid(String amountpaid) {
  this.amountpaid = amountpaid;
 }

 @Override
 public String toString() {
  return "Payments{" +
          "pid='" + pid + '\'' +
          ", date='" + date + '\'' +
          ", time='" + time + '\'' +
          ", name='" + name + '\'' +
          ", phonenumber='" + phonenumber + '\'' +
          ", amountpaid='" + amountpaid + '\'' +
          '}';
 }
}

