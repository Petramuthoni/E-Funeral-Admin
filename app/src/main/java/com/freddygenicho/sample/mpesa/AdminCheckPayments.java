package com.freddygenicho.sample.mpesa;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.freddy.sample.mpesa.R;
import com.freddygenicho.sample.mpesa.Model.Bookings;
import com.freddygenicho.sample.mpesa.Model.Payments;
import com.freddygenicho.sample.mpesa.ViewHolder.BookingViewHolder;
import com.freddygenicho.sample.mpesa.ViewHolder.PaymentsViewHolder;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class AdminCheckPayments extends AppCompatActivity {
    private RecyclerView paymentRecyclerView ;
    RecyclerView.LayoutManager layoutManager;
    private DatabaseReference paymentRef;
    private FirebaseRecyclerAdapter<Payments, PaymentsViewHolder> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_check_payments);
        paymentRef = FirebaseDatabase.getInstance().getReference().child("Payments");
        paymentRecyclerView = findViewById(R.id.recycler_payments_menu);
        paymentRecyclerView.setHasFixedSize(false);
        layoutManager = new LinearLayoutManager(this);
        paymentRecyclerView.setLayoutManager(layoutManager);
    }

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseRecyclerOptions<Payments> options =
                new FirebaseRecyclerOptions.Builder<Payments>()
                        .setQuery(paymentRef, Payments.class)
                        .build();

        adapter =new FirebaseRecyclerAdapter<Payments, PaymentsViewHolder>(options) {


            @Override
            protected void onBindViewHolder(@NonNull PaymentsViewHolder holder, int position, @NonNull Payments model) {
                holder.paymentName.setText("Name: " + model.getName() );
                holder.paymentPhoneNumber.setText("Phone Number: " + model.getPhonenumber() );
                holder.paymentTotalAmount.setText("Paid Amount: " + model.getAmountpaid() );
                holder.paymentDateTime.setText("Paid at: " + model.getDate() + " " + model.getTime() );

            }


            @NonNull
            @Override
            public PaymentsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.payments_item_layout, parent, false);
                PaymentsViewHolder holder = new PaymentsViewHolder(view);
                return holder;
            }

        };
        paymentRecyclerView.setAdapter(adapter);
        adapter.startListening();
    }
}
