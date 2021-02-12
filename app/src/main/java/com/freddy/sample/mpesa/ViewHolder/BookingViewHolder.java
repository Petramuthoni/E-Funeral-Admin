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

public class BookingViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
    public TextView usersName, usersPhoneNumber,usersBookingDate,usersDateTime,usersBookedHearseDescription;
    public Button ShowOrdersBtn;
    public CardView cardView;
    public ItemClickListener listner;
    public BookingViewHolder(@NonNull View itemView) {
        super(itemView);
        usersName=itemView.findViewById(R.id.person_booking_name);
        cardView=itemView.findViewById(R.id.card_bookings);
        usersPhoneNumber= itemView.findViewById(R.id.person_booking_number);
        usersBookingDate=itemView.findViewById(R.id.hearse_booking_date);
        usersDateTime=itemView.findViewById(R.id.hearsebooking_date_time);
        usersBookedHearseDescription=itemView.findViewById(R.id.hearse_name_description);
    }
    public void setItemClickListner(ItemClickListener listner) {
        this.listner = listner;
    }

    @Override
    public void onClick(View view) {
        listner.onClick(view, getAdapterPosition(), false);

    }
}
