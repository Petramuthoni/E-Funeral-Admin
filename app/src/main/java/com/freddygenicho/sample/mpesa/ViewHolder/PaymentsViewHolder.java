package com.freddygenicho.sample.mpesa.ViewHolder;

import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.freddy.sample.mpesa.R;
import com.freddygenicho.sample.mpesa.Interface.ItemClickListener;


public class PaymentsViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
    public TextView paymentName, paymentPhoneNumber,paymentTotalAmount,paymentDateTime;
    public CardView cardView;
    public ItemClickListener listner;

    public PaymentsViewHolder(@NonNull View itemView) {
        super(itemView);
        paymentName=itemView.findViewById(R.id.person_payment_name);
        cardView=itemView.findViewById(R.id.card_payments);
        paymentPhoneNumber= itemView.findViewById(R.id.payment_phone_number);
        paymentTotalAmount=itemView.findViewById(R.id.payment_total_amount);
        paymentDateTime=itemView.findViewById(R.id.payment_date_time);
    }

    @Override
    public void onClick(View view) {
        listner.onClick(view, getAdapterPosition(), false);

    }
    public void setItemClickListner(ItemClickListener listner) {
        this.listner = listner;
    }
}
