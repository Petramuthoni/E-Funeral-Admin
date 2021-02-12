package com.freddy.sample.mpesa;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.anychart.AnyChart;
import com.anychart.AnyChartView;
import com.anychart.chart.common.dataentry.DataEntry;
import com.anychart.chart.common.dataentry.ValueDataEntry;
import com.anychart.charts.Pie;

import java.util.ArrayList;
import java.util.List;

public class PieChartActivity extends AppCompatActivity {
    AnyChartView anyChartView;
   // public int pendingNotifications;
    String number;

    //int badgeCounter=Integer.valueOf(number);
    //private DatabaseReference paymentReference;
    String[] months;
   // int[] values={badgeCounter,30};
    int[] values;
    TextView text,unoc,text2;
    int diff;
    int totalcapacity=50;
    String result;
    AdminCheckFuneralRequests adminCheckFuneralRequests;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pie_chart);
        anyChartView=(AnyChartView)findViewById(R.id.chart);
        number = getIntent().getStringExtra("count");
        text2 = (TextView) findViewById(R.id.txtoccupiedvalue);
        text2.setText(number);
        diff=totalcapacity-Integer.valueOf(number);
        result=String.valueOf(diff);
        unoc = (TextView) findViewById(R.id.txtunoccupiedvalue);
        unoc.setText(result);



        try {
            adminCheckFuneralRequests=new AdminCheckFuneralRequests();
           number = getIntent().getStringExtra("count");
            //number=pendingNotifications;
            int badgeCounter = Integer.valueOf(number);
            String[] months = {"Occupied Space", "Unoccupied Space"};
            // int[] values={badgeCounter,30};
            int[] values = {badgeCounter, (50-badgeCounter)};
            text = (TextView) findViewById(R.id.tc);
//     text2 = (TextView) findViewById(R.id.txtoccupiedvalue);
//        unoc = (TextView) findViewById(R.id.txtunoccupiedvalue);
//           unoc.setText((50-badgeCounter));
           // text.setText(number);
 //text2.setText(number);
            Pie pie= AnyChart.pie();
            List<DataEntry> dataEntryList=new ArrayList<>();
            for(int i=0;i<months.length;i++){
                dataEntryList.add(new ValueDataEntry(months[i],values[i]));

            }
            pie.data(dataEntryList);
            anyChartView.setChart(pie);
        }
        catch(Exception e)
            {
               e.printStackTrace();
            }






        //drawchart();
    }



    private void drawchart() {
        Pie pie= AnyChart.pie();
        List<DataEntry> dataEntryList=new ArrayList<>();
        for(int i=0;i<months.length;i++){
            dataEntryList.add(new ValueDataEntry(months[i],values[i]));

        }
        pie.data(dataEntryList);
        anyChartView.setChart(pie);


    }
}
//    float number1 = ...; // parse your EditText input here
//// other numbers ...
//
//        ArrayList<Entry> vals = new ArrayList<Entry>();
//        vals.add(new Entry(number1, 0));
//        vals.add(new Entry(number2, 1));
//        vals.add(new Entry(number3, 2));
//        vals.add(new Entry(number4, 3));
//
//        String[] xVals = new String[] { "User1", "User2", "User3", "User4" };
//
//        PieDataSet dataSet = new PieDataSet(vals, "User Values");
//        PieData data = new PieData(xVals, dataSet);
//
//        pieChart.setData(data);
//// refresh
//        pieChart.invalidate();
