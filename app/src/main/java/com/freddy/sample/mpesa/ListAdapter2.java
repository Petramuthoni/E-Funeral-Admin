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

import com.freddy.sample.mpesa.Model.funeralpayments;
import com.freddy.sample.mpesa.Model.hearsepayments;

import java.util.List;

public class ListAdapter2 extends ArrayAdapter {
    private Activity mContext;
    List<hearsepayments> paymentlist;
    public ListAdapter2(Activity mContext, List<hearsepayments> paymentlist) {
        super(mContext,R.layout.listitem2,paymentlist);
        this.mContext=mContext;
        this.paymentlist=paymentlist;
    }
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater=mContext.getLayoutInflater();
        View listItemView = inflater.inflate(R.layout.listitem2,null,true);
        TextView tvn=listItemView.findViewById(R.id.tvclientname);
        TextView tvd=listItemView.findViewById(R.id.tvhearsebooked);
        TextView tva=listItemView.findViewById(R.id.tvdatepaid);
        TextView tvam=listItemView.findViewById(R.id.tvamount);

        hearsepayments fp=paymentlist.get(position);
        tvn.setText(fp.getName());
        tvd.setText(fp.getBookedhearse());
        tva.setText(fp.getDate());
        tvam.setText(fp.getAmountpaid());

        return listItemView;
    }
}
