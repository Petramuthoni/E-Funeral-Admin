package com.freddy.sample.mpesa;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.freddy.sample.mpesa.Model.hearsepayments;
import com.freddy.sample.mpesa.ViewHolder.hearsepaymentsViewHolder;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

public class AdminCheckHearsePaymentsActivity extends AppCompatActivity {
    private RecyclerView funeralpaymentRecyclerView ;
    RecyclerView.LayoutManager layoutManager;
    private DatabaseReference funeralpaymentRef;
    EditText hearsepaymentserching;
    private FirebaseRecyclerAdapter<hearsepayments, hearsepaymentsViewHolder> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_check_hearse_payments);
        funeralpaymentRef = FirebaseDatabase.getInstance().getReference().child("hearsepayments");
        funeralpaymentRecyclerView = findViewById(R.id.recycler_hearsepayments_menu);
        hearsepaymentserching = findViewById(R.id.inputserchinghearsepayment);
        funeralpaymentRecyclerView.setHasFixedSize(false);
        layoutManager = new LinearLayoutManager(this);
        funeralpaymentRecyclerView.setLayoutManager(layoutManager);
        hearsepaymentserching.addTextChangedListener(new TextWatcher() {
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
                    Toast.makeText(AdminCheckHearsePaymentsActivity.this,"enter some text first", Toast.LENGTH_SHORT).show();
                }


            }
        });

    }
    @Override
    protected void onStart() {
        super.onStart();
        Query query=funeralpaymentRef.orderByChild("bid").startAt( hearsepaymentserching.getText().toString()).endAt( hearsepaymentserching.getText().toString()+"\uf8ff");
        FirebaseRecyclerOptions<hearsepayments> options =
                new FirebaseRecyclerOptions.Builder<hearsepayments>()
                        .setQuery(query, hearsepayments.class)
                        .build();

        adapter =new FirebaseRecyclerAdapter<hearsepayments, hearsepaymentsViewHolder>(options) {


            @Override
            protected void onBindViewHolder(@NonNull hearsepaymentsViewHolder holder, int position, @NonNull final hearsepayments model) {
                // public TextView payName, payPhoneNumber,payTotalAmount,payDateTime,paidTotalAmount,payTotalBalance;
               // holder.hearsepayName.setText("P.No: " + model.getPaymentnumber()+" "+"Name"+model.getName() );
                holder.hearsepayName.setText("P.No: "+model.getHpid()+" "+"Name: "+model.getName() );

                holder. hearsepayPhoneNumber.setText("Phone Number: " + model.getPhonenumber() );

                holder.hearsepayTotalAmount.setText("Amount Paid: " + model.getAmountpaid()+" "+" for Booking Number "+model.getBid() );

                holder.hearsepayDateTime.setText("Paid at: " + model.getDate() + " " + model.getTime() );
                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        CharSequence options [] = new CharSequence[]{
                                "Yes",
                                "No"

                        };
                        AlertDialog.Builder builder=new AlertDialog.Builder(AdminCheckHearsePaymentsActivity.this);
                        builder.setTitle("Do You Want To Delete This Hearse Payment?");
                        builder.setItems(options, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                if (i == 0) {
                                    // String uID = getRef(position).getKey();
                                    // RemoveOrder(uID);
                                    //  ordersRef.removeValue();

                                    // */
                                    funeralpaymentRef.child(model.getPid()).removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            if (task.isSuccessful()) {
                                                Toast.makeText(AdminCheckHearsePaymentsActivity.this, "Hearse Payment Deleted successfully", Toast.LENGTH_SHORT).show();
                                                //Intent intent = new Intent(AdminNewOrdersActivity.this, MainActivity.class);
                                                // startActivity(intent);
                                            }

                                        }
                                    });
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
            public hearsepaymentsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.hearse_payments_layout, parent, false);
                hearsepaymentsViewHolder holder = new hearsepaymentsViewHolder(view);
                return holder;
            }

        };
        funeralpaymentRecyclerView.setAdapter(adapter);
        adapter.startListening();
    }
}
