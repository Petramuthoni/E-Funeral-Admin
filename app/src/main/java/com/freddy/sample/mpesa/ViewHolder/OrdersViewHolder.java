package com.freddy.sample.mpesa.ViewHolder;

import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.freddy.sample.mpesa.Interface.ItemClickListener;
import com.freddy.sample.mpesa.R;


//import com.example.e_funeral.Interface.ItemClickListener;
//import com.example.e_funeral.R;

public class OrdersViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
    public TextView userName, userPhoneNumber,userTotalPrice,userDateTime,userShippingAddress;
    public Button ShowOrdersBtn;
    public CardView cardView;
    public ItemClickListener listner;

    public OrdersViewHolder(@NonNull View itemView) {
        super(itemView);
        userName=itemView.findViewById(R.id.person_booking_name);
        cardView=itemView.findViewById(R.id.card_orders);
        userPhoneNumber= itemView.findViewById(R.id.order_phone_number);
        userTotalPrice=itemView.findViewById(R.id.order_total_price);
        userDateTime=itemView.findViewById(R.id.order_date_time);
        userShippingAddress=itemView.findViewById(R.id.order_address_city);
        ShowOrdersBtn=itemView.findViewById(R.id.show_all_products_btn);
    }
    public void setItemClickListner(ItemClickListener listner) {
        this.listner = listner;
    }

    @Override
    public void onClick(View view) {
        listner.onClick(view, getAdapterPosition(), false);

    }
}
