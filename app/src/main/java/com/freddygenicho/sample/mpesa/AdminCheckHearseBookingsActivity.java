package com.freddygenicho.sample.mpesa;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

//import com.example.e_funeral.Model.Bookings;
//import com.example.e_funeral.ViewHolder.BookingViewHolder;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.freddy.sample.mpesa.R;
import com.freddygenicho.sample.mpesa.Model.Bookings;
import com.freddygenicho.sample.mpesa.ViewHolder.BookingViewHolder;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class AdminCheckHearseBookingsActivity extends AppCompatActivity {
    private RecyclerView hearsebookingRecyclerView ;
    RecyclerView.LayoutManager layoutManager;
    private DatabaseReference bookingRef;
    private FirebaseRecyclerAdapter<Bookings, BookingViewHolder> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_check_hearse_bookings);
        bookingRef = FirebaseDatabase.getInstance().getReference().child("Bookings");
        hearsebookingRecyclerView = findViewById(R.id.recycler_hearse_menu);
        hearsebookingRecyclerView.setHasFixedSize(false);
        layoutManager = new LinearLayoutManager(this);
        hearsebookingRecyclerView.setLayoutManager(layoutManager);
    }

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseRecyclerOptions<Bookings> options =
                new FirebaseRecyclerOptions.Builder<Bookings>()
                        .setQuery(bookingRef, Bookings.class)
                        .build();

        adapter =new FirebaseRecyclerAdapter<Bookings, BookingViewHolder>(options) {


            @Override
            protected void onBindViewHolder(@NonNull BookingViewHolder holder, int position, @NonNull Bookings model) {
                holder.usersName.setText("Name: " + model.getName() );
                holder.usersPhoneNumber.setText("Phone: " + model.getPhone() );
                holder.usersBookingDate.setText("Booking Date:" + model.getBookingdate());
                holder.usersDateTime.setText("Booked at: " + model.getDate() + " " + model.getTime() );
                holder.usersBookedHearseDescription.setText("Hearse Name " + model.getHearsename() + " , " + model.getHearsedescription() );
            }


            @NonNull
            @Override
            public BookingViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.hearse_bookings_layout, parent, false);
                BookingViewHolder holder = new BookingViewHolder(view);
                return holder;
            }

        };
        hearsebookingRecyclerView.setAdapter(adapter);
        adapter.startListening();
    }
}
