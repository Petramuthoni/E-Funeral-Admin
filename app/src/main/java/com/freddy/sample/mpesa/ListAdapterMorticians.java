package com.freddy.sample.mpesa;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.freddy.sample.mpesa.Model.hearsepayments;
import com.freddy.sample.mpesa.Model.morticians;

import java.util.List;

public class ListAdapterMorticians extends ArrayAdapter {
    private Activity mContext;
    List<morticians> paymentlist;
    public ListAdapterMorticians(Activity mContext, List<morticians> paymentlist) {
        super(mContext, R.layout.mortician_layout, paymentlist);
        this.mContext = mContext;
        this.paymentlist = paymentlist;
    }
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater=mContext.getLayoutInflater();
        View listItemView = inflater.inflate(R.layout.mortician_layout,null,true);
        TextView tvn=listItemView.findViewById(R.id.tvmorticianname);
        TextView tvd=listItemView.findViewById(R.id.tvmorticianphone);
        TextView tva=listItemView.findViewById(R.id.tvmorticianemail);


        morticians fp=paymentlist.get(position);
        tvn.setText(fp.getMorticianname());
        tvd.setText(fp.getMorticianPhone());
        tva.setText(fp.getMorticianEmail());


        return listItemView;
    }

}
