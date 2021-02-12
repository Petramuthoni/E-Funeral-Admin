package com.freddy.sample.mpesa.ViewHolder;

import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.freddy.sample.mpesa.Interface.ItemClickListener;
import com.freddy.sample.mpesa.R;

public class MorticiansViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener  {
    public TextView MorticianName, MorticianPhoneNumber,MorticianEmail,hearsepayDateTime;
    public ItemClickListener listner;

    public MorticiansViewHolder(@NonNull View itemView) {
        super(itemView);
        MorticianName=itemView.findViewById(R.id.tvmorticianname);
        MorticianPhoneNumber=itemView.findViewById(R.id.tvmorticianphone);
        MorticianEmail=itemView.findViewById(R.id.tvmorticianemail);

    }

    @Override
    public void onClick(View view) {
        listner.onClick(view, getAdapterPosition(), false);

    }
    public void setItemClickListner(ItemClickListener listner) {
        this.listner = listner;
    }
}
