package com.freddy.sample.mpesa;

import android.app.DatePickerDialog;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.freddy.sample.mpesa.Model.Bookings;
import com.freddy.sample.mpesa.Model.funeralrequests;
import com.freddy.sample.mpesa.ViewHolder.hearsefilterViewHolder;
import com.freddy.sample.mpesa.ViewHolder.morguefilterviewholder;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class ManagerFilterHearseBookings extends AppCompatActivity {
    private RecyclerView requestsRecyclerView ;
    RecyclerView.LayoutManager layoutManager;
    private DatabaseReference bookingRef;
    private FirebaseRecyclerAdapter<Bookings, hearsefilterViewHolder> adapter;
    private FirebaseAuth mAuth;
    private String currentUserId,  userID;
    EditText fromd,tod;
    Date date_minimal;
    Date date_maximal;
    Button show;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manager_filter_hearse_bookings);
        mAuth= FirebaseAuth.getInstance();
        //   currentUserId=mAuth.getCurrentUser().getUid();
        bookingRef = FirebaseDatabase.getInstance().getReference().child("Bookings");
        requestsRecyclerView = findViewById(R.id.recycler_filterherse_menu);
        requestsRecyclerView.setHasFixedSize(false);
        layoutManager = new LinearLayoutManager(this);
        requestsRecyclerView.setLayoutManager(layoutManager);
        fromd=(EditText)findViewById(R.id.managerinputstart);
        tod=(EditText)findViewById(R.id.managerinputend);
        show=(Button)findViewById(R.id.managershow_data);

        fromd.addTextChangedListener(new TextWatcher() {
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
                    Toast.makeText(ManagerFilterHearseBookings.this,"enter some text first", Toast.LENGTH_SHORT).show();
                }


            }
        });
        tod.addTextChangedListener(new TextWatcher() {
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
                    Toast.makeText(ManagerFilterHearseBookings.this,"enter some text first", Toast.LENGTH_SHORT).show();
                }


            }
        });

//        fdate=fromdate.getText().toString();
//        tdate=todate.getText().toString();
        final Calendar myCalendar1 = Calendar.getInstance();
        final DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                // TODO Auto-generated method stub
                myCalendar1.set(Calendar.YEAR, year);
                myCalendar1.set(Calendar.MONTH, monthOfYear);
                myCalendar1.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                String myFormat = "MM/dd/yy"; //In which you need put here
                SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

                fromd.setText(sdf.format(myCalendar1.getTime()));



            }
        };
        final Calendar myCalendar12 = Calendar.getInstance();
        final DatePickerDialog.OnDateSetListener Date = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                // TODO Auto-generated method stub
                myCalendar12.set(Calendar.YEAR, year);
                myCalendar12.set(Calendar.MONTH, monthOfYear);
                myCalendar12.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                String myFormat = "MM/dd/yy"; //In which you need put here
                SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);


                tod.setText(sdf.format(myCalendar12.getTime()));


            }
        };
        fromd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new DatePickerDialog(ManagerFilterHearseBookings.this, date, myCalendar1
                        .get(Calendar.YEAR), myCalendar1.get(Calendar.MONTH),
                        myCalendar1.get(Calendar.DAY_OF_MONTH)).show();

            }
        });
        tod.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new DatePickerDialog(ManagerFilterHearseBookings.this, Date, myCalendar12
                        .get(Calendar.YEAR), myCalendar12.get(Calendar.MONTH),
                        myCalendar12.get(Calendar.DAY_OF_MONTH)).show();

            }
        });
        show.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

//                fdate=fromdate.getText().toString();
//                tdate=todate.getText().toString();

                onStart();
//                Query query = requestsRef.orderByChild("startdate").startAt(date_minimal.getTime()).endAt(date_maximal.getTime());
//                query.addListenerForSingleValueEvent(new ValueEventListener() {
//                    @Override
//                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                        for (DataSnapshot item : dataSnapshot.getChildren()) {
//                            funeralrequests user = item.getValue(funeralrequests.class);
//
//                        }
//                    }
//
//                    @Override
//                    public void onCancelled(@NonNull DatabaseError databaseError) {
//
//                    }
//                });

            }
        });

    }
    @Override
    protected void onStart() {
        super.onStart();
        final Calendar myCalendar1 = Calendar.getInstance();
        final DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                // TODO Auto-generated method stub
                myCalendar1.set(Calendar.YEAR, year);
                myCalendar1.set(Calendar.MONTH, monthOfYear);
                myCalendar1.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                String myFormat = "MM/dd/yy"; //In which you need put here
                SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

                fromd.setText(sdf.format(myCalendar1.getTime()));
                date_minimal = myCalendar1.getTime();


            }
        };
        final Calendar myCalendar12 = Calendar.getInstance();
        final DatePickerDialog.OnDateSetListener Date = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                // TODO Auto-generated method stub
                myCalendar12.set(Calendar.YEAR, year);
                myCalendar12.set(Calendar.MONTH, monthOfYear);
                myCalendar12.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                String myFormat = "MM/dd/yy"; //In which you need put here
                SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);


                tod.setText(sdf.format(myCalendar12.getTime()));
                date_maximal = myCalendar12.getTime();

            }
        };
        fromd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new DatePickerDialog(ManagerFilterHearseBookings.this, date, myCalendar1
                        .get(Calendar.YEAR), myCalendar1.get(Calendar.MONTH),
                        myCalendar1.get(Calendar.DAY_OF_MONTH)).show();

            }
        });
        tod.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new DatePickerDialog(ManagerFilterHearseBookings.this, Date, myCalendar12
                        .get(Calendar.YEAR), myCalendar12.get(Calendar.MONTH),
                        myCalendar12.get(Calendar.DAY_OF_MONTH)).show();

            }
        });
        FirebaseRecyclerOptions<Bookings> options = new FirebaseRecyclerOptions.Builder<Bookings>().setQuery(bookingRef.orderByChild("bookingdate").startAt(fromd.getText().toString()).endAt(tod.getText().toString()),Bookings.class).build();




//        Query query = requestsRef.orderByChild("startdate").startAt(fromdate.getText().toString()).endAt(todate.getText().toString());
//            FirebaseRecyclerOptions<funeralrequests> options =
//                    new FirebaseRecyclerOptions.Builder<funeralrequests>()
//                            .setQuery(requestsRef, funeralrequests.class)
//                            .build();

        adapter = new FirebaseRecyclerAdapter<Bookings, hearsefilterViewHolder>(options) {
            @NonNull
            @Override
            public hearsefilterViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
                View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.filterhearseoccupancylayout, viewGroup, false);
                hearsefilterViewHolder holder = new hearsefilterViewHolder(view);
                return holder;
            }

            @Override
            protected void onBindViewHolder(@NonNull final hearsefilterViewHolder holder, int position, @NonNull final Bookings model) {

                    holder.txtbno.setText(model.getRequestnumber());
                    holder.txtbname.setText(model.getName());
                    holder.txtbhn.setText(model.getHearsename());
                    holder.txtbd.setText(model.getBookingdate());


//


            }
        };

        requestsRecyclerView.setAdapter(adapter);
        adapter.startListening();
    }
}
