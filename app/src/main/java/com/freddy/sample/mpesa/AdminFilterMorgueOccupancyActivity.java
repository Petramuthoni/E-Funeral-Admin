package com.freddy.sample.mpesa;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
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
import com.freddy.sample.mpesa.Model.funeralrequests;
import com.freddy.sample.mpesa.ViewHolder.morguefilterviewholder;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class AdminFilterMorgueOccupancyActivity extends AppCompatActivity {
    private RecyclerView requestsRecyclerView ;
    RecyclerView.LayoutManager layoutManager;
    private DatabaseReference requestsRef,admindeceasedRef;
    private FirebaseRecyclerAdapter<funeralrequests, morguefilterviewholder> adapter;
    private FirebaseAuth mAuth;
    private String currentUserId,  userID;
    EditText fromdate,todate;
    Date date_minimal;
    Date date_maximal;
    Button show;
    String fdate,tdate;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_filter_morgue_occupancy);
        mAuth= FirebaseAuth.getInstance();
        //   currentUserId=mAuth.getCurrentUser().getUid();
        requestsRef = FirebaseDatabase.getInstance().getReference().child("funeral Requests");
        admindeceasedRef = FirebaseDatabase.getInstance().getReference().child("funeral admin Requests");
        requestsRecyclerView = findViewById(R.id.recycler_filtermorgue_menu);
        requestsRecyclerView.setHasFixedSize(false);
        layoutManager = new LinearLayoutManager(this);
        requestsRecyclerView.setLayoutManager(layoutManager);
        userID=getIntent().getStringExtra("uid");
        fromdate=(EditText)findViewById(R.id.inputstart);
        todate=(EditText)findViewById(R.id.inputend);
        show=(Button)findViewById(R.id.show_data);

        fromdate.addTextChangedListener(new TextWatcher() {
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
                    Toast.makeText(AdminFilterMorgueOccupancyActivity.this,"enter some text first", Toast.LENGTH_SHORT).show();
                }


            }
        });
        todate.addTextChangedListener(new TextWatcher() {
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
                    Toast.makeText(AdminFilterMorgueOccupancyActivity.this,"enter some text first", Toast.LENGTH_SHORT).show();
                }


            }
        });

//        fdate=fromdate.getText().toString();
//        tdate=todate.getText().toString();

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

                fromdate.setText(sdf.format(myCalendar.getTime()));
                date_minimal = myCalendar.getTime();


            }
        };
        final Calendar myCalendar2 = Calendar.getInstance();
        final DatePickerDialog.OnDateSetListener Date = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                // TODO Auto-generated method stub
                myCalendar2.set(Calendar.YEAR, year);
                myCalendar2.set(Calendar.MONTH, monthOfYear);
                myCalendar2.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                String myFormat = "MM/dd/yy"; //In which you need put here
                SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);


                todate.setText(sdf.format(myCalendar2.getTime()));
                date_maximal = myCalendar2.getTime();

            }
        };
        fromdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new DatePickerDialog(AdminFilterMorgueOccupancyActivity.this, date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();

            }
        });
        todate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new DatePickerDialog(AdminFilterMorgueOccupancyActivity.this, Date, myCalendar2
                        .get(Calendar.YEAR), myCalendar2.get(Calendar.MONTH),
                        myCalendar2.get(Calendar.DAY_OF_MONTH)).show();

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

                    fromdate.setText(sdf.format(myCalendar.getTime()));
                    date_minimal = myCalendar.getTime();


                }
            };
            final Calendar myCalendar2 = Calendar.getInstance();
            final DatePickerDialog.OnDateSetListener Date = new DatePickerDialog.OnDateSetListener() {

                @Override
                public void onDateSet(DatePicker view, int year, int monthOfYear,
                                      int dayOfMonth) {
                    // TODO Auto-generated method stub
                    myCalendar2.set(Calendar.YEAR, year);
                    myCalendar2.set(Calendar.MONTH, monthOfYear);
                    myCalendar2.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                    String myFormat = "MM/dd/yy"; //In which you need put here
                    SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);


                    todate.setText(sdf.format(myCalendar2.getTime()));
                    date_maximal = myCalendar2.getTime();

                }
            };
            fromdate.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    new DatePickerDialog(AdminFilterMorgueOccupancyActivity.this, date, myCalendar
                            .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                            myCalendar.get(Calendar.DAY_OF_MONTH)).show();

                }
            });
            todate.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    new DatePickerDialog(AdminFilterMorgueOccupancyActivity.this, Date, myCalendar2
                            .get(Calendar.YEAR), myCalendar2.get(Calendar.MONTH),
                            myCalendar2.get(Calendar.DAY_OF_MONTH)).show();

                }
            });
            FirebaseRecyclerOptions<funeralrequests> options = new FirebaseRecyclerOptions.Builder<funeralrequests>().setQuery(admindeceasedRef.orderByChild("startdate").startAt(fromdate.getText().toString()).endAt(todate.getText().toString()), funeralrequests.class).build();


//        Query query = requestsRef.orderByChild("startdate").startAt(fromdate.getText().toString()).endAt(todate.getText().toString());
//            FirebaseRecyclerOptions<funeralrequests> options =
//                    new FirebaseRecyclerOptions.Builder<funeralrequests>()
//                            .setQuery(requestsRef, funeralrequests.class)
//                            .build();

            adapter = new FirebaseRecyclerAdapter<funeralrequests, morguefilterviewholder>(options) {
                @NonNull
                @Override
                public morguefilterviewholder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
                    View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.filtermorgueoccupancylayout, viewGroup, false);
                    morguefilterviewholder holder = new morguefilterviewholder(view);
                    return holder;
                }

                @Override
                protected void onBindViewHolder(@NonNull final morguefilterviewholder holder, int position, @NonNull final funeralrequests model) {
                    try {
                        holder.txtdno.setText(model.getRqn());
                        holder.txtdname.setText(model.getDeceasedname());
                        holder.txtdge.setText(model.getDeceasedgender());
                        holder.txtst.setText(model.getStartdate());


//

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            };

            requestsRecyclerView.setAdapter(adapter);
            adapter.startListening();

    }
}
