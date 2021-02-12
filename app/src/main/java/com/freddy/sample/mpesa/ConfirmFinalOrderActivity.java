package com.freddy.sample.mpesa;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;

public class ConfirmFinalOrderActivity extends AppCompatActivity {
    private EditText nameEditText,phoneEditText,addressEditText,cityEditText;
    private Button submitOrderButton;
    private String totalAmounts,orderRandomKey,saveCurrentDate, saveCurrentTime,names,phones,addresses,cities;
    private DatabaseReference ordersRef;
    private FirebaseAuth mAuth;
    private String currentUserId;
   // String productID="";




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mAuth=FirebaseAuth.getInstance();
        currentUserId=mAuth.getCurrentUser().getUid();
        ordersRef = FirebaseDatabase.getInstance().getReference().child("Orders");
        setContentView(R.layout.activity_confirm_final_order);
        submitOrderButton=(Button)findViewById(R.id.submit_final_order);
        nameEditText=(EditText)findViewById(R.id.Shippment_name);
        phoneEditText=(EditText)findViewById(R.id.shippment_Phone_Number);
        addressEditText=(EditText)findViewById(R.id.shippment_Address);
        cityEditText=(EditText)findViewById(R.id.shippment_City);
        totalAmounts=getIntent().getStringExtra("Total Price");

        submitOrderButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Check();
            }
        });

    }

    private void Check() {
        names=nameEditText.getText().toString();
        phones=phoneEditText.getText().toString();
        addresses=addressEditText.getText().toString();
        cities=cityEditText.getText().toString();
        if(TextUtils.isEmpty(nameEditText.getText().toString()))
        {
            Toast.makeText(ConfirmFinalOrderActivity.this,"Please provide your name", Toast.LENGTH_SHORT).show();

        }
        else if(TextUtils.isEmpty(phoneEditText.getText().toString()))
        {
            Toast.makeText(ConfirmFinalOrderActivity.this,"Please provide your phone number", Toast.LENGTH_SHORT).show();

        }
        else if(TextUtils.isEmpty(addressEditText.getText().toString()))
        {
            Toast.makeText(ConfirmFinalOrderActivity.this,"Please provide your Address", Toast.LENGTH_SHORT).show();

        }
        else if(TextUtils.isEmpty(cityEditText.getText().toString()))
        {
            Toast.makeText(ConfirmFinalOrderActivity.this,"Please provide your City", Toast.LENGTH_SHORT).show();

        }
        else{
            ConfirmOrder();
            Toast.makeText(ConfirmFinalOrderActivity.this, "Your Final Order Has Been Placed Successfully", Toast.LENGTH_LONG).show();
            Intent intent = new Intent(ConfirmFinalOrderActivity.this, MainActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
            finish();
        }

    }

    private void ConfirmOrder() {

        Calendar calendar = Calendar.getInstance();

        SimpleDateFormat currentDate = new SimpleDateFormat("MMM dd, yyyy");
        saveCurrentDate = currentDate.format(calendar.getTime());

        SimpleDateFormat currentTime = new SimpleDateFormat("HH:mm:ss a");
        saveCurrentTime = currentTime.format(calendar.getTime());
        orderRandomKey = saveCurrentDate + saveCurrentTime;


        HashMap<String, Object> ordersMap = new HashMap<>();
        ordersMap.put("pid", orderRandomKey);
        ordersMap.put("uid", currentUserId);
        ordersMap.put("totalAmount", totalAmounts);
        ordersMap.put("name",names) ;
        ordersMap.put("phone", phones);
        ordersMap.put("address", addresses);
        ordersMap.put("city", cities);
        ordersMap.put("date", saveCurrentDate);
        ordersMap.put("time", saveCurrentTime);
        //ordersMap.put("state", "not shipped");

        ordersRef.child(orderRandomKey).updateChildren(ordersMap).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    //lest out products child
                    FirebaseDatabase.getInstance().getReference().child("Cart List").child("User View").child(currentUserId).removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {

                                Toast.makeText(ConfirmFinalOrderActivity.this, "Your Final Order Has Been Placed Successfully", Toast.LENGTH_LONG).show();
                                Intent intent = new Intent(ConfirmFinalOrderActivity.this, MainActivity.class);
                                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                startActivity(intent);
                                finish();
                            }

                        }
                    });


                }



            }
        });



    }



}
