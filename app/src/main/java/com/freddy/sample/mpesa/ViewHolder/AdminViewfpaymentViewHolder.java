package com.freddy.sample.mpesa.ViewHolder;

import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.freddy.sample.mpesa.Interface.ItemClickListener;
import com.freddy.sample.mpesa.R;


public class AdminViewfpaymentViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
    public TextView payName, payPhoneNumber,payTotalAmount,payDateTime,paidTotalAmount,payTotalBalance;
    public CardView cardViews;
    public Button showi;
    public ItemClickListener listner;

    public AdminViewfpaymentViewHolder(@NonNull View itemView) {
        super(itemView);
        paidTotalAmount=itemView.findViewById(R.id.amp);
        payPhoneNumber=itemView.findViewById(R.id.pay_phone_number);
        payName=itemView.findViewById(R.id.person_paymentfuneral_name);
        cardViews=itemView.findViewById(R.id.card_view_funeral_payments);
        payTotalAmount= itemView.findViewById(R.id.total_amount_tobepaid);
        payTotalBalance=itemView.findViewById(R.id.blc);
        payDateTime=itemView.findViewById(R.id.pay_date_time);
//        showi=itemView.findViewById(R.id.show_invoice);
    }

    @Override
    public void onClick(View view) {

    }
}
