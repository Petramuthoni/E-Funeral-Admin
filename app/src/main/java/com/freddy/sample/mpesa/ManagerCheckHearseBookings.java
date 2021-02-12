package com.freddy.sample.mpesa;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.freddy.sample.mpesa.Model.Bookings;
import com.freddy.sample.mpesa.ViewHolder.BookingViewHolder;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

public class ManagerCheckHearseBookings extends AppCompatActivity {
    private RecyclerView hearsebookingRecyclerView ;
    EditText hearsebookingserching;
    RecyclerView.LayoutManager layoutManager;
    private DatabaseReference bookingRef;
    private FirebaseRecyclerAdapter<Bookings, BookingViewHolder> adapter;
    ImageView imgfilter;
    MenuItem menuItem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manager_check_hearse_bookings);
        bookingRef = FirebaseDatabase.getInstance().getReference().child("Bookings");
        hearsebookingRecyclerView = findViewById(R.id.recycler_hearse_menu);
        hearsebookingRecyclerView.setHasFixedSize(false);
        layoutManager = new LinearLayoutManager(this);
        hearsebookingRecyclerView.setLayoutManager(layoutManager);
        hearsebookingserching =(EditText) findViewById(R.id.inputserchinghearsebooking);
        hearsebookingserching.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if(editable.toString()!=null){
                    onStart();

                }
                else{
                    Toast.makeText(ManagerCheckHearseBookings.this,"enter some text first", Toast.LENGTH_SHORT).show();
                }


            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        Query query=bookingRef.orderByChild("requestnumber").startAt(hearsebookingserching.getText().toString()).endAt(hearsebookingserching.getText().toString()+"\uf8ff");
        FirebaseRecyclerOptions<Bookings> options =
                new FirebaseRecyclerOptions.Builder<Bookings>()
                        .setQuery(query, Bookings.class)
                        .build();

        adapter =new FirebaseRecyclerAdapter<Bookings, BookingViewHolder>(options) {


            @Override
            protected void onBindViewHolder(@NonNull BookingViewHolder holder, int position, @NonNull final Bookings model) {
                holder.usersName.setText("B.No. " + model.getRequestnumber() + "Name: " + model.getName() );
                holder.usersPhoneNumber.setText("Phone: " + model.getPhone() );
                holder.usersBookingDate.setText("Booking Date:" + model.getBookingdate());
                holder.usersDateTime.setText("Booked at: " + model.getDate() + " " + model.getTime() );
                holder.usersBookedHearseDescription.setText("Hearse Name " + model.getHearsename() + " , " + model.getHearsedescription() );
//                holder.itemView.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View view) {
//                        CharSequence options [] = new CharSequence[]{
//                                "Yes",
//                                "No"
//
//                        };
//                        AlertDialog.Builder builder=new AlertDialog.Builder(ManagerCheckHearseBookings.this);
//                        builder.setTitle("Do You Want To Delete This Hearse Booking?");
//                        builder.setItems(options, new DialogInterface.OnClickListener() {
//                            @Override
//                            public void onClick(DialogInterface dialogInterface, int i) {
//                                if (i == 0) {
//                                    // String uID = getRef(position).getKey();
//                                    // RemoveOrder(uID);
//                                    //  ordersRef.removeValue();
//
//                                    // */
//                                    bookingRef.child(model.getPid()).removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
//                                        @Override
//                                        public void onComplete(@NonNull Task<Void> task) {
//                                            if (task.isSuccessful()) {
//                                                Toast.makeText(ManagerCheckHearseBookings.this, "Herase Booking Deleted successfully", Toast.LENGTH_SHORT).show();
//                                                //Intent intent = new Intent(AdminNewOrdersActivity.this, MainActivity.class);
//                                                // startActivity(intent);
//                                            }
//
//                                        }
//                                    });
//                                }
//                                else{
//                                    finish();
//
//                                }
//
//                            }
//                        });
//                        builder.show();
//
//                    }
//                });
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
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.hearsefiltermenu, menu);

        menuItem = menu.findItem(R.id.hearsefilter_occupancy);



        // if notification than set the badge icon layout
        menuItem.setActionView(R.layout.hearsefilterlayout);
        // get the view from the nav item
        View view = menuItem.getActionView();

        imgfilter=view.findViewById(R.id.filterhearsebtn);
        imgfilter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ManagerCheckHearseBookings.this, ManagerFilterHearseBookings.class);
                // intent.putExtra("Admin", "Admin");
                startActivity(intent);

            }
        });

        // set the pending notifications value




        return super.onCreateOptionsMenu(menu);
    }
}
