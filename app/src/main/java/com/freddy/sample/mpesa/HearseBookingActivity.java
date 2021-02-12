package com.freddy.sample.mpesa;

import android.Manifest;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

//import com.example.e_funeral.Model.Hearses;
//import com.example.e_funeral.Model.Products;
import com.freddy.sample.mpesa.Model.Hearses;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Locale;

public class HearseBookingActivity extends AppCompatActivity {
    private EditText hearseName,hearseDescription,userBookingName,userBookingPhone,bookingDate;
    Button submitBookings,smsMessage;
    private ImageView hearseimage;
    private String productID="";
    String namehearse,descriptionhearse,username,userphone,datebooking,bookingRandomKey,saveCurrentDate, saveCurrentTime;
    private DatabaseReference hearseRef,bookingRef;
    public static final int MY_PERMISSION_REQUEST_SEND_SMS = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hearse_booking);
        hearseRef = FirebaseDatabase.getInstance().getReference().child("Hearses");
        bookingRef = FirebaseDatabase.getInstance().getReference().child("Bookings");
        productID = getIntent().getStringExtra("pid");
        hearseName = (EditText) findViewById(R.id.hearse_name_booking);
        hearseDescription = (EditText) findViewById(R.id.hearse_description_booking);
        userBookingName = (EditText) findViewById(R.id.user_booking_name);
        userBookingPhone = (EditText) findViewById(R.id.user_booking_phone);
        bookingDate = (EditText) findViewById(R.id.hearse_date_booking);

        final Calendar myCalendar = Calendar.getInstance();
        final DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                // TODO Auto-generated method stub
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                String myFormat = "MM/dd/yy"; //In which you need put here
                SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

                bookingDate.setText(sdf.format(myCalendar.getTime()));

            }
        };



        submitBookings=(Button)findViewById(R.id.hearse_booking_btn);
        hearseimage=(ImageView)findViewById(R.id.hearse_image_booking);
        displaySpecificHearseInfo();
        submitBookings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Check();
               /* int permissioncheck= ContextCompat.checkSelfPermission(HearseBookingActivity.this, Manifest.permission.SEND_SMS);
                if(permissioncheck == PackageManager.PERMISSION_GRANTED){
                   // mymessage();
                    Check();
                }else{
                    ActivityCompat.requestPermissions(HearseBookingActivity.this,new String[]{Manifest.permission.SEND_SMS},MY_PERSSIONS_request_send_sms);
                }*/

            }
        });

        bookingDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new DatePickerDialog(HearseBookingActivity.this, date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();

            }
        });

    }
/*
    private void mymessage() {
        String myNumber=userBookingPhone.getText().toString();
        String myUser=userBookingName.getText().toString();
        String hearse=hearseName.getText().toString();
        String hDescription= hearseDescription.getText().toString();
        String date=bookingDate.getText().toString();
        if(myNumber==null || myNumber.equals("")){
            Toast.makeText(HearseBookingActivity.this,"Phone Field cannot be empty",Toast.LENGTH_SHORT).show();
        }

        else if(TextUtils.isDigitsOnly(myNumber)){
            SmsManager smsManager=SmsManager.getDefault();
            smsManager.sendTextMessage(myNumber,null,  "Thank you for booking a hearse with us.The hearse will be available during the booked date.The pricing depends on the distance to be covered and it is negotiable.",null,null);
            Toast.makeText(HearseBookingActivity.this,"hearse booked successfully ",Toast.LENGTH_SHORT).show();

        }else{
            Toast.makeText(HearseBookingActivity.this,"Please Enter Integer Only ",Toast.LENGTH_SHORT).show();


        }
    }
*/

   /* @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch(requestCode){
            case MY_PERSSIONS_request_send_sms:
            {
                if(grantResults.length>=0 && grantResults[0]== PackageManager.PERMISSION_GRANTED){
                    mymessage();
                }else{
                    Toast.makeText(HearseBookingActivity.this,"No permission",Toast.LENGTH_SHORT).show();

                }
            }
        }
    }
*/

    private void Check () {
        namehearse = hearseName.getText().toString();
        descriptionhearse = hearseDescription.getText().toString();
        username = userBookingName.getText().toString();
        userphone = userBookingPhone.getText().toString();
        datebooking = bookingDate.getText().toString();
        if (TextUtils.isEmpty(namehearse)) {
            Toast.makeText(HearseBookingActivity.this, "Please provide the Hearse name", Toast.LENGTH_SHORT).show();

        } else if (TextUtils.isEmpty(descriptionhearse)) {
            Toast.makeText(HearseBookingActivity.this, "Please provide the Hearse description", Toast.LENGTH_SHORT).show();

        } else if (TextUtils.isEmpty(username)) {
            Toast.makeText(HearseBookingActivity.this, "Please provide your Name", Toast.LENGTH_SHORT).show();

        } else if (TextUtils.isEmpty(userphone)) {
            Toast.makeText(HearseBookingActivity.this, "Please provide your Phone Number", Toast.LENGTH_SHORT).show();

        }
        else if (TextUtils.isEmpty(datebooking)) {
            Toast.makeText(HearseBookingActivity.this, "Please provide the intended service date", Toast.LENGTH_SHORT).show();

        }
        else {
            submitBooking();
        }
    }






    private void submitBooking() {
        Calendar calendar = Calendar.getInstance();

        SimpleDateFormat currentDate = new SimpleDateFormat("MMM dd, yyyy");
        saveCurrentDate = currentDate.format(calendar.getTime());

        SimpleDateFormat currentTime = new SimpleDateFormat("HH:mm:ss a");
        saveCurrentTime = currentTime.format(calendar.getTime());
        bookingRandomKey = saveCurrentDate + saveCurrentTime;


        HashMap<String, Object> ordersMap = new HashMap<>();
        ordersMap.put("pid", bookingRandomKey);
        ordersMap.put("name",username) ;
        ordersMap.put("phone", userphone);
        ordersMap.put("bookingdate", datebooking);
        ordersMap.put("hearsename", namehearse);
        ordersMap.put("hearsedescription", descriptionhearse);
        ordersMap.put("date", saveCurrentDate);
        ordersMap.put("time", saveCurrentTime);
        //ordersMap.put("state", "not shipped");

        bookingRef.child(bookingRandomKey).updateChildren(ordersMap).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    //lest out products child


                    Toast.makeText(HearseBookingActivity.this, "Your Hearse Booking Has Been Made Successfully", Toast.LENGTH_LONG).show();
                    myMessage();
                    Intent intent = new Intent(HearseBookingActivity.this, MainActivityHearse.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);
                    finish();
                }

            }
        });





    }

    private void myMessage() {
        String namehearse,descriptionhearse,username,userphone,datebooking,message;
        namehearse=hearseName.getText().toString();
        descriptionhearse=hearseDescription.getText().toString();
        username=userBookingName.getText().toString();
        userphone=userBookingPhone.getText().toString();
        datebooking=bookingDate.getText().toString();
        //message="Dear "+ username + ",Thank you for booking a hearse with us.The hearse " + namehearse + "," + descriptionhearse+ "will be available on " + datebooking + ". The pricing depends on the distance to be covered and it is negotiable";
        message="Dear "+ username + ",Thank you for booking a hearse." + namehearse + " will be available on " + datebooking + ". ";

        if (TextUtils.isEmpty(userphone) || TextUtils.isEmpty(datebooking)||TextUtils.isEmpty(username)) {
            userBookingPhone.setError("phone number required");
            userBookingName.setError("your name is required");
            bookingDate.setError("Date is required");
        } else {
            if (!TextUtils.isDigitsOnly(userphone)) {
                Toast.makeText(getApplicationContext(), "Enter valid phone number", Toast.LENGTH_SHORT).show();

            } else {
                SmsManager smsManager = SmsManager.getDefault();
                smsManager.sendTextMessage(userphone, null, message, null, null);
                //Toast.makeText(getApplicationContext(), "message sent", Toast.LENGTH_SHORT).show();
            }
        }
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode)
        {
            case MY_PERMISSION_REQUEST_SEND_SMS:
            {
                if ( grantResults.length >=0 && grantResults[0] == PackageManager.PERMISSION_GRANTED)
                {
                    myMessage();
                }
                else
                {
                    Toast.makeText(getApplicationContext(),"No required permission granted",Toast.LENGTH_SHORT).show();
                }
            }
        }
    }

    private void displaySpecificHearseInfo() {
        hearseRef.child(productID).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()){
                    Hearses hearses=dataSnapshot.getValue(Hearses.class);
                    hearseName.setText(hearses.getPname());
                    hearseDescription.setText(hearses.getDescription());
                    Picasso.get().load(hearses.getImage()).into(hearseimage);
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    public void sendingtext(View view) {
        int permissionCheck = ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.SEND_SMS);
        if (permissionCheck == PackageManager.PERMISSION_GRANTED) {
            myMessage();
        } else {
            ActivityCompat.requestPermissions(this, new String[]
                    {Manifest.permission.SEND_SMS}, MY_PERMISSION_REQUEST_SEND_SMS);
        }
    }
}
