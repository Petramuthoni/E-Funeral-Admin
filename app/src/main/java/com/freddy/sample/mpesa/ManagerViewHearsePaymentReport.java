package com.freddy.sample.mpesa;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.TextView;

import com.anychart.AnyChart;
import com.anychart.AnyChartView;
import com.anychart.chart.common.dataentry.DataEntry;
import com.anychart.chart.common.dataentry.ValueDataEntry;
import com.anychart.charts.Cartesian;
import com.anychart.core.cartesian.series.Line;
import com.anychart.data.Mapping;
import com.anychart.data.Set;
import com.anychart.enums.MarkerType;
import com.anychart.enums.TooltipPositionMode;
import com.anychart.graphics.vector.Stroke;
import com.freddy.sample.mpesa.Model.funeralpayments;
import com.freddy.sample.mpesa.Model.hearsepayments;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ManagerViewHearsePaymentReport extends AppCompatActivity {
    ListView listv;
    List<hearsepayments> paymentl1;
    DatabaseReference paymentRef,funeralpaymentRef;
    TextView texv;
    //    LineChart linechart;
//    LineDataSet lineDataSet=new LineDataSet(null,null);
//    ArrayList<ILineDataSet> iLineDataSets=new ArrayList<>();
//    LineData lineData;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manager_view_hearse_payment_report);
        listv=findViewById(R.id.listview);
        // linechart=(LineChart)findViewById(R.id.linegraph);
        texv=(TextView)findViewById(R.id.showtotalpaid);
        paymentl1=new ArrayList<>();
        paymentRef = FirebaseDatabase.getInstance().getReference().child("hearsepayments");
        funeralpaymentRef = FirebaseDatabase.getInstance().getReference().child("funeral requests payments admin");

        paymentRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                paymentl1.clear();

                for (DataSnapshot paymentds : dataSnapshot.getChildren()){
                    hearsepayments fpmts=paymentds.getValue(hearsepayments.class);
                    paymentl1.add(fpmts);
                }
                ListAdapter2 adapter = new ListAdapter2(ManagerViewHearsePaymentReport.this,paymentl1);
                listv.setAdapter(adapter);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        paymentRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                int sum=0;
//                for(DataSnapshot ds : dataSnapshot.getChildrenCount()){
//
//                }
                for( DataSnapshot ds :dataSnapshot.getChildren()) {
                    Map<String,Object> map=(Map<String,Object>) ds.getValue();
                    Object totalpayment=map.get("amountpaid");
                    int pValue=Integer.parseInt(String.valueOf(totalpayment));
                    sum += pValue;
                    texv.setText(String.valueOf(sum));



                }


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
//        funeralpaymentRef.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                int date;
//                int amount;
//                try {
//                    ArrayList<Entry> dataVals = new ArrayList<Entry>();
//                    if (dataSnapshot.hasChildren()) {
//                        for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
//                            funeralpayments fp = dataSnapshot1.getValue(funeralpayments.class);
//                            date = Integer.parseInt(fp.getPreservationstart());
//                           amount = Integer.parseInt(fp.getAmountpaid());
//
//                            dataVals.add(new Entry(date, amount));
//
//
//                        }
//                        showChart(dataVals);
//                    } else {
//                        linechart.clear();
//                        linechart.invalidate();
//                    }
//                }catch (Exception e){
//                    e.printStackTrace();
//                }
//
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError databaseError) {
//
//            }
//        });

    }

}
