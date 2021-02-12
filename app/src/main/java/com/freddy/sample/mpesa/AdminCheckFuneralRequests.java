package com.freddy.sample.mpesa;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
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
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.freddy.sample.mpesa.Model.funeralrequests;
import com.freddy.sample.mpesa.ViewHolder.funeralrequestViewHolder;

import com.freddygenicho.sample.mpesa.ProductDetailsActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class AdminCheckFuneralRequests extends AppCompatActivity  {
    private RecyclerView requestsRecyclerView ;
    EditText serch;
    RecyclerView.LayoutManager layoutManager;
    private DatabaseReference requestsRef,morticiandeceasedref,admindeceasedRef;
    private FirebaseRecyclerAdapter<funeralrequests, funeralrequestViewHolder> adapter;
    private FirebaseAuth mAuth;
    private String currentUserId,  userID;
   // viewfuneralrequests viewfuneralrequests;
    int pendingNotifications = 1;
    TextView tc;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_check_funeral_requests);
        mAuth=FirebaseAuth.getInstance();
      currentUserId=mAuth.getCurrentUser().getUid();
        morticiandeceasedref = FirebaseDatabase.getInstance().getReference().child("funeral mortician Requests").child(currentUserId).child("funeral requests");
        requestsRef = FirebaseDatabase.getInstance().getReference().child("funeral Requests");
        admindeceasedRef = FirebaseDatabase.getInstance().getReference().child("funeral admin Requests");
        requestsRecyclerView = findViewById(R.id.recycler_funeralrequest_menu);
        serch=(EditText)findViewById(R.id.inputserching);
        requestsRecyclerView.setHasFixedSize(false);
       // tc=(TextView)findViewById(R.id.showvalue);
        layoutManager = new LinearLayoutManager(this);
        requestsRecyclerView.setLayoutManager(layoutManager);
        userID=getIntent().getStringExtra("uid");
        //viewfuneralrequests=new viewfuneralrequests();


        try {
           // requestsRef = FirebaseDatabase.getInstance().getReference().child("funeralnotification");
            requestsRef.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    if (dataSnapshot.exists()) {
                        pendingNotifications = (int) dataSnapshot.getChildrenCount();
                       // tc.setText(String.valueOf(" "+"Show Morgue Occupancy"+"("+ pendingNotifications+
                               // ")"+" "));
                       // tc.setText(" Show Morgue Occupancy Analysis ");
//                        tc.setOnClickListener(new View.OnClickListener() {
//                            @Override
//                            public void onClick(View view) {
//                                Intent intent=new Intent(AdminCheckFuneralRequests.this,PieChartActivity.class);
//                                intent.putExtra("count",String.valueOf(pendingNotifications));
//                               // Toast.makeText(AdminCheckFuneralRequests.this,"value"+pendingNotifications,Toast.LENGTH_LONG).show();
//                                startActivity(intent);
//                             //   Toast.makeText(AdminCheckFuneralRequests.this,"value"+pendingNotifications,Toast.LENGTH_SHORT);
//
//                            }
//                        });

                    }

                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
        }catch (Exception e)
        {
            e.printStackTrace();
        }

        serch.addTextChangedListener(new TextWatcher() {
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
                    Toast.makeText(AdminCheckFuneralRequests.this,"enter some text first", Toast.LENGTH_SHORT).show();
                }


            }
        });

    }

    @Override
    protected void onStart() {
        super.onStart();
        Query query= morticiandeceasedref.orderByChild("rqn").startAt(serch.getText().toString()).endAt(serch.getText().toString()+"\uf8ff");

        FirebaseRecyclerOptions<funeralrequests> options =
                new FirebaseRecyclerOptions.Builder<funeralrequests>()
                        .setQuery(query, funeralrequests.class)
                        .build();
        adapter = new FirebaseRecyclerAdapter<funeralrequests, funeralrequestViewHolder>(options) {
            @NonNull
            @Override
            public funeralrequestViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
                View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.request_layout, viewGroup, false);
                funeralrequestViewHolder holder = new funeralrequestViewHolder(view);
                return holder;
            }

            @Override
            protected void onBindViewHolder(@NonNull final funeralrequestViewHolder holder, int position, @NonNull final funeralrequests model) {
                try {
                    holder.name.setText("A.No: " + model.getRqn()+" "+" Name"+ model.getBereavedname());
                    holder.phonenumber.setText("Phone: " + model.getPhonenumber());
                    holder.deceasedname.setText("Deceased Name: " + model.getDeceasedname());
                    holder.deceasedgender.setText("Deceased Gender: " + model.getDeceasedgender());
                    holder.fromto.setText("Start Date: " + model.getStartdate() );
                  //  holder.duration.setText("Total Body Preservation Days: " + model.getDurationindays() + " days");
                    holder.userdatetime.setText("Registered at: " + model.getCurrentdate() + " " + model.getCurrenttime());
                    holder.itemView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            CharSequence options [] = new CharSequence[]{
                                    "Delete",
                                    "Bill",
                                    "Cancel"

                            };
                            AlertDialog.Builder builder=new AlertDialog.Builder(AdminCheckFuneralRequests.this);
                            builder.setTitle("Do You Want To Release/Bill This Deceased Body?");
                            builder.setItems(options, new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    if (i == 0) {
                                        // String uID = getRef(position).getKey();
                                        // RemoveOrder(uID);
                                        //  ordersRef.removeValue();

                                        // */
//                                        requestsRef.child(model.getDid()).removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
//                                            @Override
//                                            public void onComplete(@NonNull Task<Void> task) {
//                                                if (task.isSuccessful()) {
//                                                    Toast.makeText(AdminCheckFuneralRequests.this, "Deceased body Released successfully", Toast.LENGTH_SHORT).show();
//                                                    //Intent intent = new Intent(AdminNewOrdersActivity.this, MainActivity.class);
//                                                    // startActivity(intent);
//                                                }
//
//                                            }
//                                        });
                                        morticiandeceasedref.child(model.getDid()).removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
                                            @Override
                                            public void onComplete(@NonNull Task<Void> task) {
                                                if(task.isSuccessful()){
                                                    admindeceasedRef.child(model.getDid()).removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
                                                        @Override
                                                        public void onComplete(@NonNull Task<Void> task) {
                                                            if(task.isSuccessful()){
                                                                Toast.makeText(AdminCheckFuneralRequests.this,"Deceased body Released successfully", Toast.LENGTH_SHORT).show();
                                                                //Intent intent=new Intent(AdminCheckFuneralRequests.this,MainActivity.class);
                                                                // intent.putExtra("pid",model.getPid());
                                                                //startActivity(intent);

                                                            }
                                                            else{
                                                                String message = task.getException().toString();
                                                                Toast.makeText(AdminCheckFuneralRequests.this, "Error: " + message, Toast.LENGTH_LONG).show();
                                                            }

                                                        }
                                                    });

                                                }
                                            }
                                        });

                                    }
                                    else if(i == 1){
                                        Intent intent=new Intent(AdminCheckFuneralRequests.this,AdminBillingActivity.class);
                                        intent.putExtra("bn",model.getBereavedname());
                                        intent.putExtra("bno",model.getPhonenumber());
                                        intent.putExtra("dn",model.getDeceasedname());
                                        intent.putExtra("dg",model.getDeceasedgender());
                                        intent.putExtra("rn",model.getRqn());
                                        intent.putExtra("st",model.getStartdate());

                                        startActivity(intent);

                                    }
                                    else{
                                        finish();

                                    }

                                }
                            });
                            builder.show();

                        }
                    });
//                    holder.accept.setOnClickListener(new View.OnClickListener() {
//                        @Override
//                        public void onClick(View view) {
//                            viewfuneralrequests=new viewfuneralrequests();
//
//                        }
//                    });

                }catch (Exception e)
                {
                    e.printStackTrace();
                }
            }
        };
        requestsRecyclerView.setAdapter(adapter);
        adapter.startListening();
    }

}
