package com.freddygenicho.sample.mpesa;


import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

//import com.example.e_funeral.Model.Orders;
//import com.example.e_funeral.ViewHolder.OrdersViewHolder;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.freddy.sample.mpesa.R;
import com.freddygenicho.sample.mpesa.Model.Orders;
import com.freddygenicho.sample.mpesa.ViewHolder.OrdersViewHolder;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class AdminNewOrdersActivity extends AppCompatActivity {


    private RecyclerView ordersRecyclerView ;
    RecyclerView.LayoutManager layoutManager;
    private DatabaseReference ordersRef;
    private FirebaseRecyclerAdapter<Orders, OrdersViewHolder> adapter;
    private FirebaseAuth mAuth;
    private String currentUserId;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_new_orders);
        mAuth=FirebaseAuth.getInstance();
        currentUserId=mAuth.getCurrentUser().getUid();
        ordersRef = FirebaseDatabase.getInstance().getReference().child("Orders");
        ordersRecyclerView = findViewById(R.id.recycler_orders_menu);
       ordersRecyclerView.setHasFixedSize(false);
        layoutManager = new LinearLayoutManager(this);
        ordersRecyclerView.setLayoutManager(layoutManager);






    }


    @Override
    protected void onStart() {
        super.onStart();
        FirebaseRecyclerOptions<Orders> options =
                new FirebaseRecyclerOptions.Builder<Orders>()
                        .setQuery(ordersRef, Orders.class)
                        .build();

        adapter =new FirebaseRecyclerAdapter<Orders, OrdersViewHolder>(options) {



            @Override
            protected void onBindViewHolder(@NonNull OrdersViewHolder holder, final int position, @NonNull final Orders model) {
                holder.userName.setText("Name: " + model.getName() );
                holder.userPhoneNumber.setText("Phone: " + model.getPhone() );
                holder.userTotalPrice.setText("Total Amount= $" + model.getTotalAmount());
                holder.userDateTime.setText("Order at: " + model.getDate() + " " + model.getTime() );
                holder.userShippingAddress.setText("Shipping Address: " + model.getAddress() + " , " + model.getCity() );
               holder.ShowOrdersBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        String uID=getRef(position).getKey();
                        Intent intent=new Intent(AdminNewOrdersActivity.this,AdminUserProductsActivity.class);
                        intent.putExtra("uid",model.getUid());
                       // intent.putExtra("uid",uID);
                       // inafaa uadd uid kwa db ya products alafu orders(getter n setter) intent.putExtra("uid",model.getPhone());
                        startActivity(intent);

                    }
                });
                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        CharSequence options [] = new CharSequence[]{
                                "Yes",
                                "No"

                        };
                        AlertDialog.Builder builder=new AlertDialog.Builder(AdminNewOrdersActivity.this);
                        builder.setTitle("Do You Want To Delete This Order?");
                        builder.setItems(options, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                if (i == 0) {
                                   // String uID = getRef(position).getKey();
                                   // RemoveOrder(uID);
                                    //  ordersRef.removeValue();

                               // */
                                ordersRef.child(model.getPid()).removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        if (task.isSuccessful()) {
                                            Toast.makeText(AdminNewOrdersActivity.this, "order removed successfully", Toast.LENGTH_SHORT).show();
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
            public OrdersViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.orders_layout, parent, false);
                OrdersViewHolder holder = new OrdersViewHolder(view);
                return holder;
            }

        };
        ordersRecyclerView.setAdapter(adapter);
        adapter.startListening();


    }

    //private void RemoveOrder(String uID) {
     //   ordersRef.removeValue();
    //}
    //parameter string uID whemn u manage to add current online user id

   // private void RemoveOrder() {

   // }


}




        /*FirebaseRecyclerAdapter<Adminorders,AdminOrdersViewHolder> adapter = new FirebaseRecyclerAdapter<Adminorders, AdminOrdersViewHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull AdminOrdersViewHolder holder, final int position, @NonNull final Adminorders model)
            {
                holder.userName.setText("Name: " + model.getName() );
                holder.userPhoneNumber.setText("Phone: " + model.getPhone() );
                holder.userTotalPrice.setText("Total Amount= $" + model.getTotalAmount());
               holder.userDateTime.setText("Order at: " + model.getDate() + " " + model.getTime() );
                holder.userShippingAddress.setText("Shipping Address: " + model.getAddress() + " , " + model.getCity() );
                holder.ShowOrdersBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        String UID=getRef(position).getKey();
                       Intent intent=new Intent(AdminNewOrdersActivity.this,AdminUserProductsActivity.class);
                       intent.putExtra("username",UID);
                       startActivity(intent);

                    }
                });


            }

            @NonNull
            @Override
            public AdminOrdersViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {

                View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.orders_layout,parent,false);
                return new AdminOrdersViewHolder(view);

            }
        };*/






