package com.freddy.sample.mpesa.ViewHolder;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.freddy.sample.mpesa.Interface.ItemClickListener;
import com.freddy.sample.mpesa.R;

public class hearsefilterViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
    public TextView txtbno, txtbname,txtbhn,txtbd;
    public ItemClickListener listner;
    public hearsefilterViewHolder(@NonNull View itemView) {
        super(itemView);
        txtbno = (TextView) itemView.findViewById(R.id.tvdbookingnumber);
        txtbname = (TextView) itemView.findViewById(R.id.tvbookingname);
        txtbhn = (TextView) itemView.findViewById(R.id.tvhearsename);
        txtbd = (TextView) itemView.findViewById(R.id.tvbookingstartdate);
    }

    @Override
    public void onClick(View view) {
        listner.onClick(view, getAdapterPosition(), false);

    }
    public void setItemClickListner(ItemClickListener listner) {
        this.listner = listner;
    }
}
