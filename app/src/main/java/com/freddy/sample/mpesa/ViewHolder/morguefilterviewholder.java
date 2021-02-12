package com.freddy.sample.mpesa.ViewHolder;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.freddy.sample.mpesa.Interface.ItemClickListener;
import com.freddy.sample.mpesa.R;


public class morguefilterviewholder extends RecyclerView.ViewHolder implements View.OnClickListener {
    public TextView txtdno, txtdname,txtdge,txtst;
    public ItemClickListener listner;

    public morguefilterviewholder(@NonNull View itemView) {
        super(itemView);
        txtdno = (TextView) itemView.findViewById(R.id.tvdeceasedadmissionnumber);
        txtdname = (TextView) itemView.findViewById(R.id.tvdeceasedname);
        txtdge = (TextView) itemView.findViewById(R.id.tvdeceasedgender);
        txtst = (TextView) itemView.findViewById(R.id.tvdeceasedstartdate);
    }

    @Override
    public void onClick(View view) {
        listner.onClick(view, getAdapterPosition(), false);

    }
    public void setItemClickListner(ItemClickListener listner) {
        this.listner = listner;
    }
}
