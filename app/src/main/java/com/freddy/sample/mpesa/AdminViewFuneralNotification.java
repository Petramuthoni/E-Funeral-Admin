package com.freddy.sample.mpesa;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.freddy.sample.mpesa.Model.Notify;
import com.freddy.sample.mpesa.ViewHolder.notificationViewHolder;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class AdminViewFuneralNotification extends AppCompatActivity {
    private RecyclerView paymentRecyclerView ;
    RecyclerView.LayoutManager layoutManager;
    private DatabaseReference paymentRef;
    private FirebaseRecyclerAdapter<Notify, notificationViewHolder> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_view_funeral_notification);
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
            protected void onBindViewHolder(@NonNull notificationViewHolder holder, int position, @NonNull Notify model) {
//              holder.notificationName.setText("Bereaved Name: " + model.getName() );
//                holder.notificationPhoneNumber.setText("Phone Number: " + model.getPhonenumber() );
//                holder.notificationMessage.setText("Notification Message: " + model.getNotificationmessage() );
//                holder.notificationDateTime.setText("Sent at: " + model.getDate() + " " + model.getTime() );

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
}
