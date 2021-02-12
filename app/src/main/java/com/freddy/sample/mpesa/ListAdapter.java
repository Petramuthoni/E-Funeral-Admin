package com.freddy.sample.mpesa;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.freddy.sample.mpesa.Model.funeralpayments;


import java.util.List;

public class ListAdapter extends ArrayAdapter {
    private Activity mContext;
    List<funeralpayments> paymentlist;
    public ListAdapter(Activity mContext, List<funeralpayments> paymentlist) {
        super(mContext,R.layout.listitem,paymentlist);
        this.mContext=mContext;
        this.paymentlist=paymentlist;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater=mContext.getLayoutInflater();
        View listItemView = inflater.inflate(R.layout.listitem,null,true);
        TextView tvn=listItemView.findViewById(R.id.tvname);
        TextView tvd=listItemView.findViewById(R.id.tvdate);
        TextView tva=listItemView.findViewById(R.id.tvamountpaid);

        funeralpayments fp=paymentlist.get(position);
        tvn.setText(fp.getName());
        tvd.setText(fp.getDate());
        tva.setText(fp.getAmountpaid());

        return listItemView;
    }
}
