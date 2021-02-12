package com.freddy.sample.mpesa;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.telephony.SmsManager;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.freddy.sample.mpesa.Model.Notify;
import com.freddy.sample.mpesa.ViewHolder.notificationViewHolder;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class MorticianViewFuneralNotification extends AppCompatActivity {
    private RecyclerView paymentRecyclerView ;
    RecyclerView.LayoutManager layoutManager;
    private DatabaseReference paymentRef;
    private FirebaseRecyclerAdapter<Notify, notificationViewHolder> adapter;
    public static final int MY_PERMISSION_REQUEST_SEND_SMS = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mortician_view_funeral_notification);
        paymentRef = FirebaseDatabase.getInstance().getReference().child("funeralnotification");
        paymentRecyclerView = findViewById(R.id.recycler_notification_menu);
        paymentRecyclerView.setHasFixedSize(false);
        layoutManager = new LinearLayoutManager(this);
        paymentRecyclerView.setLayoutManager(layoutManager);
    }
    @Override
    protected void onStart() {
        super.onStart();
        FirebaseRecyclerOptions<Notify> options =
                new FirebaseRecyclerOptions.Builder<Notify>()
                        .setQuery(paymentRef, Notify.class)
                        .build();

        adapter =new FirebaseRecyclerAdapter<Notify, notificationViewHolder>(options) {


            @Override
            protected void onBindViewHolder(@NonNull notificationViewHolder holder, int position, @NonNull final Notify model) {
                holder.notificationName.setText("Bereaved Name: " + model.getBereavedname());
                holder.notificationPhoneNumber.setText("Phone Number: " + model.getPhonenumber() );
                holder.notificationMessage.setText("Notification Message: " +"I Request that you prepare the body of "+model.getDeceasedname()+", by adding the following embalming activities: "+model.getDeceasedhairdo()+","+model.getDeceasedsmell()+" and add  "+model.getDeceasedfacials()+". The body will be picked on "+model.getPickupdate()+"." );
                holder.notificationDateTime.setText("Sent at: " + model.getDate() + " " + model.getTime() );
                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        CharSequence options [] = new CharSequence[]{
                                "Delete",
                                "Reply",
                                "Cancel"

                        };
                        AlertDialog.Builder builder=new AlertDialog.Builder(MorticianViewFuneralNotification.this);
                        builder.setTitle("Do You Want To Delete/Reply thisnotification?");
                        builder.setItems(options, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                if (i == 0) {
                                    // String uID = getRef(position).getKey();
                                    // RemoveOrder(uID);
                                    //  ordersRef.removeValue();

                                    // */
                                    paymentRef.child(model.getPid()).removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            if (task.isSuccessful()) {
                                                Toast.makeText(MorticianViewFuneralNotification.this, "Notification Deleted successfully", Toast.LENGTH_SHORT).show();
                                                //Intent intent = new Intent(AdminNewOrdersActivity.this, MainActivity.class);
                                                // startActivity(intent);
                                            }

                                        }
                                    });
                                }
                                else if(i == 1){
//                                    Intent intent=new Intent(AdminCheckFuneralRequests.this,AdminBillingActivity.class);
//                                    intent.putExtra("bn",model.getBereavedname());
//                                    intent.putExtra("bno",model.getPhonenumber());
//                                    intent.putExtra("dn",model.getDeceasedname());
//                                    intent.putExtra("dg",model.getDeceasedgender());
//                                    intent.putExtra("rn",model.getRqn());
//                                    intent.putExtra("st",model.getStartdate());
//
//                                    startActivity(intent);



                                    String namehearse,descriptionhearse,username,userphone,datebooking,message;
//        namehearse=hearseName.getText().toString();
//        descriptionhearse=hearseDescription.getText().toString();
//        username=userBookingName.getText().toString();
//        userphone=userBookingPhone.getText().toString();
//        datebooking=bookingDate.getText().toString();
                                    //message="Dear "+ username + ",Thank you for booking a hearse with us.The hearse " + namehearse + "," + descriptionhearse+ "will be available on " + datebooking + ". The pricing depends on the distance to be covered and it is negotiable";
                                    message="Dear "+ model.getBereavedname() + ",We have received your embalming request,We will prepare your loved one body as per your wishes.Thank you." ;
                                    SmsManager smsManager = SmsManager.getDefault();
                                    smsManager.sendTextMessage(model.getPhonenumber(), null, message, null, null);
                                    Toast.makeText(getApplicationContext(), "message sent Successfully", Toast.LENGTH_SHORT).show();

//                                    if (TextUtils.isEmpty(userphone) || TextUtils.isEmpty(datebooking)||TextUtils.isEmpty(username)) {
//                                        userBookingPhone.setError("phone number required");
//                                        userBookingName.setError("your name is required");
//                                        bookingDate.setError("Date is required");
//                                    }
//                                     else {
//                                        if (!TextUtils.isDigitsOnly(userphone)) {
//                                            Toast.makeText(getApplicationContext(), "Enter valid phone number", Toast.LENGTH_SHORT).show();
//
//                                        } else {
//                                            SmsManager smsManager = SmsManager.getDefault();
//                                            smsManager.sendTextMessage(userphone, null, message, null, null);
//                                            //Toast.makeText(getApplicationContext(), "message sent", Toast.LENGTH_SHORT).show();
//                                        }
//                                    }

                                }
                                else{
                                    finish();

                                }

                            }
                        });
                        builder.show();

                    }
                });

            }


            @NonNull
            @Override
            public notificationViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.notification_item_layout, parent, false);
                notificationViewHolder holder = new notificationViewHolder(view);
                return holder;
            }

        };
        paymentRecyclerView.setAdapter(adapter);
        adapter.startListening();
    }
//    private void myMessage() {
//        String namehearse,descriptionhearse,username,userphone,datebooking,message;
////        namehearse=hearseName.getText().toString();
////        descriptionhearse=hearseDescription.getText().toString();
////        username=userBookingName.getText().toString();
////        userphone=userBookingPhone.getText().toString();
////        datebooking=bookingDate.getText().toString();
//        //message="Dear "+ username + ",Thank you for booking a hearse with us.The hearse " + namehearse + "," + descriptionhearse+ "will be available on " + datebooking + ". The pricing depends on the distance to be covered and it is negotiable";
//        message="Dear "+ username + ",Thank you for booking a hearse." + namehearse + " will be available on " + datebooking + ". ";
//
//        if (TextUtils.isEmpty(userphone) || TextUtils.isEmpty(datebooking)||TextUtils.isEmpty(username)) {
//            userBookingPhone.setError("phone number required");
//            userBookingName.setError("your name is required");
//            bookingDate.setError("Date is required");
//        } else {
//            if (!TextUtils.isDigitsOnly(userphone)) {
//                Toast.makeText(getApplicationContext(), "Enter valid phone number", Toast.LENGTH_SHORT).show();
//
//            } else {
//                SmsManager smsManager = SmsManager.getDefault();
//                smsManager.sendTextMessage(userphone, null, message, null, null);
//                //Toast.makeText(getApplicationContext(), "message sent", Toast.LENGTH_SHORT).show();
//            }
//        }
//    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode)
        {
            case MY_PERMISSION_REQUEST_SEND_SMS:
            {
                if ( grantResults.length >=0 && grantResults[0] == PackageManager.PERMISSION_GRANTED)
                {
                   // myMessage();
                }
                else
                {
                    Toast.makeText(getApplicationContext(),"No required permission granted",Toast.LENGTH_SHORT).show();
                }
            }
        }
    }


}
