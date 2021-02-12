package com.freddy.sample.mpesa.ViewHolder;

import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.freddy.sample.mpesa.Interface.ItemClickListener;
import com.freddy.sample.mpesa.R;

public class hearsepaymentsViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
    public TextView hearsepayName, hearsepayPhoneNumber,hearsepayTotalAmount,hearsepayDateTime;
    public CardView cardViews;
    public ItemClickListener listner;

    public hearsepaymentsViewHolder(@NonNull View itemView) {
        super(itemView);
        hearsepayName=itemView.findViewById(R.id.person_paymenthearse_name);
        hearsepayPhoneNumber=itemView.findViewById(R.id.hearse_phone_number);
        hearsepayTotalAmount=itemView.findViewById(R.id.amphearse);
        cardViews=itemView.findViewById(R.id.card_view_hearse_payments);
        hearsepayDateTime= itemView.findViewById(R.id.hearsepay_date_time);

    }

    @Override
    public void onClick(View view) {
        listner.onClick(view, getAdapterPosition(), false);

    }
    public void setItemClickListner(ItemClickListener listner) {
        this.listner = listner;
    }
}
