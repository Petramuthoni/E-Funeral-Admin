package com.freddy.sample.mpesa;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class ManagerDashboard extends AppCompatActivity {
    ImageView occupancy,payments,bookings,  hearsepayments,morticinas;
    RelativeLayout relativeLayout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manager_dashboard);
        try {
            relativeLayout = (RelativeLayout) findViewById(R.id.r56);
            occupancy = (ImageView) findViewById(R.id.occupancybtn);
            payments = (ImageView) findViewById(R.id.funeralhomepayment);
            hearsepayments = (ImageView) findViewById(R.id.hearsepayment);
            morticinas = (ImageView) findViewById(R.id.morticnasreport);
            bookings = (ImageView) findViewById(R.id.hearsereport);
            occupancy.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(ManagerDashboard.this, ManagerOccupancyReport.class);
                    startActivity(intent);
                }
            });
            morticinas.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(ManagerDashboard.this,ManagerViewMorticiansActivity.class);
                    startActivity(intent);

                }
            });
            payments.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(ManagerDashboard.this, ViewPaymentReportActivity.class);
                    startActivity(intent);
                }
            });
            hearsepayments.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(ManagerDashboard.this, ManagerViewHearsePaymentReport.class);
                    startActivity(intent);
                }
            });
            bookings.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(ManagerDashboard.this, ManagerCheckHearseBookings.class);
                    startActivity(intent);
                }
            });


        }catch(Exception e){
            e.printStackTrace();
            Toast.makeText(ManagerDashboard.this,e.getMessage(),Toast.LENGTH_LONG).show();
        }
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.managerdashboardoptions,menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        super.onOptionsItemSelected(item);
        if (item.getItemId()== R.id.forMortician){
            Intent intent=new Intent(ManagerDashboard.this,MnagerRegisterMorticiansActivity.class);
            startActivity(intent);

        }


        return super.onOptionsItemSelected(item);

    }






}

