package com.freddy.sample.mpesa.ViewHolder;

import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.freddy.sample.mpesa.Interface.ItemClickListener;
import com.freddy.sample.mpesa.R;


public class notificationViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener  {
    public TextView notificationName, notificationPhoneNumber,notificationMessage,notificationDateTime;
    public CardView cardView;
    public ItemClickListener listner;
    public notificationViewHolder(@NonNull View itemView) {
        super(itemView);
        notificationName=itemView.findViewById(R.id.person_notification_name);
        cardView=itemView.findViewById(R.id.card_notifications);
        notificationPhoneNumber= itemView.findViewById(R.id.notification_phone_number);
        notificationMessage=itemView.findViewById(R.id.notification_message);
        notificationDateTime=itemView.findViewById(R.id.notification_date_time);
    }

    @Override
    public void onClick(View view) {
        listner.onClick(view, getAdapterPosition(), false);

    }
    public void setItemClickListner(ItemClickListener listner) {
        this.listner = listner;
    }
}
