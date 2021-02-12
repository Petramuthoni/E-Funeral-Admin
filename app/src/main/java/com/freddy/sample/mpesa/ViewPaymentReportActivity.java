package com.freddy.sample.mpesa;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
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
import java.util.List;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

////import com.github.mikephil.charting.charts.LineChart;
////import com.github.mikephil.charting.data.Entry;
//import com.github.mikephil.charting.data.LineData;
//import com.github.mikephil.charting.data.LineDataSet;
//import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;

public class ViewPaymentReportActivity extends AppCompatActivity {
    ListView lv;
    List<funeralpayments> paymentl;
    DatabaseReference paymentRef,funeralpaymentRef;
    TextView texv;
//    LineChart linechart;
//    LineDataSet lineDataSet=new LineDataSet(null,null);
//    ArrayList<ILineDataSet> iLineDataSets=new ArrayList<>();
//    LineData lineData;
    AnyChartView anyChartView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_payment_report);
        lv=findViewById(R.id.mylistview);
        anyChartView = findViewById(R.id.linegraph);
       // linechart=(LineChart)findViewById(R.id.linegraph);
        texv=(TextView)findViewById(R.id.showtotalpaidvalue);
        paymentl=new ArrayList<>();
        paymentRef = FirebaseDatabase.getInstance().getReference().child("funeral requests payments admin");
        funeralpaymentRef = FirebaseDatabase.getInstance().getReference().child("funeral requests payments admin");

        paymentRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                paymentl.clear();

                for (DataSnapshot paymentds : dataSnapshot.getChildren()){
                    funeralpayments fpmts=paymentds.getValue(funeralpayments.class);
                    paymentl.add(fpmts);
                }
                ListAdapter adapter = new ListAdapter(ViewPaymentReportActivity.this,paymentl);
                lv.setAdapter(adapter);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        funeralpaymentRef.addValueEventListener(new ValueEventListener() {
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
        funeralpaymentRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String date;
             int amount;
                Cartesian cartesian = AnyChart.line();

                cartesian.animation(true);

                cartesian.padding(10d, 20d, 5d, 20d);

                cartesian.crosshair().enabled(true);
                cartesian.crosshair()
                        .yLabel(true)
                        // TODO ystroke
                        .yStroke((Stroke) null, null, null, (String) null, (String) null);

                cartesian.tooltip().positionMode(TooltipPositionMode.POINT);

                cartesian.title("Morgue Payment Representation.");

                cartesian.yAxis(0).title("Amount Generated in(Shillings)");
                cartesian.xAxis(0).labels().padding(5d, 5d, 5d, 5d);
                List<DataEntry> seriesData = new ArrayList<>();
                if (dataSnapshot.hasChildren()) {
                        for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                            funeralpayments fp = dataSnapshot1.getValue(funeralpayments.class);
                            date = (fp.getDate());
                           amount = Integer.parseInt(fp.getAmountpaid());

                            seriesData.add(new CustomDataEntry(date, amount));


                        }

                    }
//                seriesData.add(new CustomDataEntry("1986", 3.6));
//                seriesData.add(new CustomDataEntry("1995", 12.0));
//                seriesData.add(new CustomDataEntry("1996", 3.2));
//                seriesData.add(new CustomDataEntry("1997", 4.1));
//                seriesData.add(new CustomDataEntry("1998", 6.3));
//                seriesData.add(new CustomDataEntry("2008", 15.7));
//                seriesData.add(new CustomDataEntry("2009", 12.0));
                Set set = Set.instantiate();
                set.data(seriesData);
                Mapping series1Mapping = set.mapAs("{ x: 'x', value: 'value' }");

                Line series1 = cartesian.line(series1Mapping);
                series1.name("Amount Representation");
                series1.hovered().markers().enabled(true);
                series1.hovered().markers()
                        .type(MarkerType.CIRCLE)
                        .size(4d);
                series1.tooltip()
                        .position("right")
                        .offsetX(5d)
                        .offsetY(5d);

                cartesian.legend().enabled(true);
                cartesian.legend().fontSize(13d);
                cartesian.legend().padding(0d, 0d, 10d, 0d);

                anyChartView.setChart(cartesian);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }
    private class CustomDataEntry extends ValueDataEntry {

        CustomDataEntry(String x, Number value) {
            super(x, value);
//            setValue("value2", value2);
//            setValue("value3", value3);
        }

    }


//    private void showChart(ArrayList<Entry> dataVals) {
//        lineDataSet.setValues(dataVals);
//        lineDataSet.setLabel("Morgue Income Analysis");
//        iLineDataSets.clear();
//        iLineDataSets.add(lineDataSet);
//        lineData=new LineData(iLineDataSets);
//        linechart.clear();
//        linechart.setData(lineData);
//        linechart.invalidate();
//
//
//    }
}
