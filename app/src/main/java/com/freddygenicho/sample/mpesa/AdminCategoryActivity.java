package com.freddygenicho.sample.mpesa;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.freddy.sample.mpesa.R;

public class AdminCategoryActivity extends AppCompatActivity {
    private ImageView coffins, flowers, crosses;
    private ImageView bodybags, linens, perfumes,hearses;
    private Button maintain,CheckOrderBtn,CheckHearseBooking,CheckPayments;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_category);
        coffins = (ImageView) findViewById(R.id.coffins);
        flowers = (ImageView) findViewById(R.id.flowers);
        crosses = (ImageView) findViewById(R.id.cross);
        bodybags = (ImageView) findViewById(R.id.bodybag);
        hearses = (ImageView) findViewById(R.id.hearse);
        maintain=(Button) findViewById(R.id.maintain_btn);
        CheckOrderBtn=(Button) findViewById(R.id.check_orders_btn);
        CheckPayments=(Button) findViewById(R.id.check_payments_btn);
        CheckHearseBooking=(Button) findViewById(R.id.check_hearse_booking_btn);




        perfumes = (ImageView) findViewById(R.id.perfume);
        coffins.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                Intent intent = new Intent(AdminCategoryActivity.this, AdminHomeActivity.class);
                intent.putExtra("category", "coffins");
                startActivity(intent);
            }
        });


        flowers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                Intent intent = new Intent(AdminCategoryActivity.this, AdminHomeActivity.class);
                intent.putExtra("category", "flowers");
                startActivity(intent);
            }
        });


        crosses.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                Intent intent = new Intent(AdminCategoryActivity.this, AdminHomeActivity.class);
                intent.putExtra("category", "crosses");
                startActivity(intent);
            }
        });


        bodybags.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                Intent intent = new Intent(AdminCategoryActivity.this, AdminHomeActivity.class);
                intent.putExtra("category", "bodybags");
                startActivity(intent);
            }
        });





        perfumes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                Intent intent = new Intent(AdminCategoryActivity.this, AdminHomeActivity.class);
                intent.putExtra("category", "perfumes");
                startActivity(intent);
            }
        });
        maintain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(AdminCategoryActivity.this,MainActivity2.class);
                intent.putExtra("Admin", "Admin");
                startActivity(intent);
            }
        });
        CheckOrderBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(AdminCategoryActivity.this,AdminNewOrdersActivity.class);
                startActivity(intent);

            }
        });
        CheckHearseBooking.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(AdminCategoryActivity.this,AdminCheckHearseBookingsActivity.class);
                startActivity(intent);

            }
        });
        CheckPayments.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(AdminCategoryActivity.this,AdminCheckPayments.class);
                startActivity(intent);

            }
        });
        hearses.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(AdminCategoryActivity.this,AdminAddHearse.class);
                startActivity(intent);

            }
        });






    }
}
