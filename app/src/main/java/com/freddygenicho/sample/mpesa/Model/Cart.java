package com.freddygenicho.sample.mpesa.Model;

public class Cart {
    public String pid,pname,price,discount,quantity,uid;

    public Cart() {
    }

    public Cart(String pid, String pname, String price, String discount, String quantity, String uid) {
        this.pid = pid;
        this.pname = pname;
        this.price = price;
        this.discount = discount;
        this.quantity = quantity;
        this.uid = uid;
    }



    @Override
    public String toString() {
        return "Cart{" +
                "pid='" + pid + '\'' +
                ", pname='" + pname + '\'' +
                ", price='" + price + '\'' +
                ", discount='" + discount + '\'' +
                ", quantity='" + quantity + '\'' +
                ", uid='" + uid + '\'' +
                '}';
    }

   /* public Cart(String pid, String pname, String price, String discount, String quantity) {
        this.pid = pid;
        this.pname = pname;
        this.price = price;
        this.discount = discount;
        this.quantity = quantity;
    }*/
   public String getUid() {
       return uid;
   }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getPid() {
        return pid;
    }

    public void setPid(String pid) {
        this.pid = pid;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public String getQuantity() {
        return quantity;
    }

    public String getPname() {
        return pname;
    }

    public void setPname(String pname) {
        this.pname = pname;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getDiscount() {
        return discount;
    }

    public void setDiscount(String discount) {
        this.discount = discount;
    }
}
