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
import com.freddy.sample.mpesa.Model.Payments;
import com.freddy.sample.mpesa.ViewHolder.PaymentsViewHolder;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

public class AdminCheckPayments extends AppCompatActivity {
    private RecyclerView paymentRecyclerView ;
    RecyclerView.LayoutManager layoutManager;
    private DatabaseReference paymentRef;
    EditText paymentserching;
    private FirebaseRecyclerAdapter<Payments, PaymentsViewHolder> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_check_payments);
        paymentRef = FirebaseDatabase.getInstance().getReference().child("Payments");
        paymentRecyclerView = findViewById(R.id.recycler_payments_menu);
        paymentserching=(EditText)findViewById(R.id.inputserchingorderpayment);
        paymentRecyclerView.setHasFixedSize(false);
        layoutManager = new LinearLayoutManager(this);
        paymentRecyclerView.setLayoutManager(layoutManager);
        paymentserching.addTextChangedListener(new TextWatcher() {
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
                    Toast.makeText(AdminCheckPayments.this,"enter some text first", Toast.LENGTH_SHORT).show();
                }


            }
        });


    }

    @Override
    protected void onStart() {
        super.onStart();
        Query query=paymentRef.orderByChild("ordernum").startAt(paymentserching.getText().toString()).endAt(paymentserching.getText().toString()+"\uf8ff");
        FirebaseRecyclerOptions<Payments> options =
                new FirebaseRecyclerOptions.Builder<Payments>()
                        .setQuery(query, Payments.class)
                        .build();

        adapter =new FirebaseRecyclerAdapter<Payments, PaymentsViewHolder>(options) {


            @Override
            protected void onBindViewHolder(@NonNull PaymentsViewHolder holder, int position, @NonNull final Payments model) {
                holder.paymentName.setText("P.No: "+model.getPaymentnum()+"Name: " + model.getName() );
                holder.paymentPhoneNumber.setText("Phone Number: " + model.getPhonenumber() );
                holder.paymentTotalAmount.setText("Paid Amount: " + model.getAmountpaid()+",for order number "+model.getOrdernum() );
                holder.paymentDateTime.setText("Paid at: " + model.getDate() + " " + model.getTime() );
                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        CharSequence options [] = new CharSequence[]{
                                "Yes",
                                "No"

                        };
                        AlertDialog.Builder builder=new AlertDialog.Builder(AdminCheckPayments.this);
                        builder.setTitle("Do You Want To Delete This Order Payment?");
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
                                                Toast.makeText(AdminCheckPayments.this, "Order Payment Deleted successfully", Toast.LENGTH_SHORT).show();
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
