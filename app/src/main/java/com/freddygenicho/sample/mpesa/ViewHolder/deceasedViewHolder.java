package com.freddygenicho.sample.mpesa.ViewHolder;

import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

//import com.example.e_funeral.Interface.ItemClickListener;
//import com.example.e_funeral.R;
import com.freddy.sample.mpesa.R;
import com.freddygenicho.sample.mpesa.Interface.ItemClickListener;


public class deceasedViewHolder  extends RecyclerView.ViewHolder implements View.OnClickListener {
    public TextView txtDeceasedName, txtDeacesedDonationDescription, txtDonateToPrice;
    public ImageView imageViews;
    public CardView cardViews;
    public ItemClickListener listner;

    public deceasedViewHolder(@NonNull View itemView) {
        super(itemView);
        cardViews=itemView.findViewById(R.id.Deceased_Card);


        imageViews = (ImageView) itemView.findViewById(R.id.deceased_image_layout);
        txtDeceasedName = (TextView) itemView.findViewById(R.id.deceased_name_layout);
        txtDeacesedDonationDescription = (TextView) itemView.findViewById(R.id.deceased_plea_description);
        txtDonateToPrice = (TextView) itemView.findViewById(R.id.deceased_donation_lines);
    }
    public void setItemClickListner(ItemClickListener listner) {
        this.listner = listner;
    }

    @Override
    public void onClick(View view) {
        listner.onClick(view, getAdapterPosition(), false);

    }
}
