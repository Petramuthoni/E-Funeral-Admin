package com.freddygenicho.sample.mpesa.ViewHolder;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

//import com.example.e_funeral.Interface.ItemClickListener;
//import com.example.e_funeral.R;
import com.freddy.sample.mpesa.R;
import com.freddygenicho.sample.mpesa.Interface.ItemClickListener;


public class ProductViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
    public TextView txtProductName, txtProductDescription, txtProductPrice;
    public ImageView imageView;
    public CardView cardView;
    public ItemClickListener listner;


    public ProductViewHolder(View itemView) {
        super(itemView);
        cardView=itemView.findViewById(R.id.products_Card);


        imageView = (ImageView) itemView.findViewById(R.id.product_image);
        txtProductName = (TextView) itemView.findViewById(R.id.product_name);
        txtProductDescription = (TextView) itemView.findViewById(R.id.product_description);
        txtProductPrice = (TextView) itemView.findViewById(R.id.product_price);
    }

    public void setItemClickListner(ItemClickListener listner) {
        this.listner = listner;
    }

    @Override
    public void onClick(View view) {
        listner.onClick(view, getAdapterPosition(), false);
    }
}
