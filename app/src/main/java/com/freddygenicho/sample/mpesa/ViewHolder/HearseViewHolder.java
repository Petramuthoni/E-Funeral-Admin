package com.freddygenicho.sample.mpesa.ViewHolder;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

//import com.example.e_funeral.Interface.ItemClickListener;
//import com.example.e_funeral.R;
import com.freddy.sample.mpesa.R;
import com.freddygenicho.sample.mpesa.Interface.ItemClickListener;


public class HearseViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
    public TextView txtHearseName, txtHearseDescription;
    public ImageView HearseimageView;
    public ItemClickListener listner;
    public HearseViewHolder(@NonNull View itemView) {
        super(itemView);
        HearseimageView = (ImageView) itemView.findViewById(R.id.Hearse_image);
        txtHearseName = (TextView) itemView.findViewById(R.id.Hearse_name);
        txtHearseDescription = (TextView) itemView.findViewById(R.id.Hearse_Description);
    }
    public void setItemClickListner(ItemClickListener listner) {
        this.listner = listner;
    }

    @Override
    public void onClick(View view) {
        listner.onClick(view, getAdapterPosition(), false);
    }

}
