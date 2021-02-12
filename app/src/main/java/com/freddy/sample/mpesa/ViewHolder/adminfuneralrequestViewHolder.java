package com.freddy.sample.mpesa.ViewHolder;

import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.freddy.sample.mpesa.Interface.ItemClickListener;
import com.freddy.sample.mpesa.R;

public class adminfuneralrequestViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
    public TextView name, phonenumber,deceasedname,deceasedgender,fromto,duration,userdatetime,admitted;
    //  public Button cancel,accept;
    public CardView cardView;
    public ItemClickListener listner;

    public adminfuneralrequestViewHolder(@NonNull View itemView) {
        super(itemView);
        name=itemView.findViewById(R.id.person_bookingf_name);
        cardView=itemView.findViewById(R.id.card_requests);
        phonenumber= itemView.findViewById(R.id.bookingf_phone_number);
        deceasedname=itemView.findViewById(R.id.booking_deceased_name);
        deceasedgender=itemView.findViewById(R.id.deceased_booking_gender);
        fromto=itemView.findViewById(R.id.fromto);
        // duration=itemView.findViewById(R.id.booking_duration);
        userdatetime=itemView.findViewById(R.id.request_date_time);
        admitted=itemView.findViewById(R.id.request_admittedby);
        // cancel=itemView.findViewById(R.id.Cancel_btn);
        // accept=itemView.findViewById(R.id.Accept_btn);

    }
    public void setItemClickListner(ItemClickListener listner) {
        this.listner = listner;
    }

    @Override
    public void onClick(View view) {
        listner.onClick(view, getAdapterPosition(), false);
    }
}
