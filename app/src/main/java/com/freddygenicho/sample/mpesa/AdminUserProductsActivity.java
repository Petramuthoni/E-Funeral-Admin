package com.freddygenicho.sample.mpesa;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

//import com.example.e_funeral.Model.Cart;
//import com.example.e_funeral.ViewHolder.CartViewHolder;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.freddy.sample.mpesa.R;
import com.freddygenicho.sample.mpesa.Model.Cart;
import com.freddygenicho.sample.mpesa.ViewHolder.CartViewHolder;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class AdminUserProductsActivity extends AppCompatActivity {
    private RecyclerView productlist;
    RecyclerView.LayoutManager layoutManager;
    private DatabaseReference cartlistRef;
    private String userID = "";
    private FirebaseRecyclerAdapter<Cart, CartViewHolder> adapter;
    private FirebaseAuth mAuth;
    private String currentUserId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_user_products2);
        mAuth= FirebaseAuth.getInstance();
        currentUserId=mAuth.getCurrentUser().getUid();
        userID=getIntent().getStringExtra("uid");
        //in the line below u need to pass userID mahali kwa currentuserId
        cartlistRef= FirebaseDatabase.getInstance().getReference().child("Cart List").child("Admin View").child(userID).child("Products");
        productlist = findViewById(R.id.products_list);
        productlist.setHasFixedSize(false);
        layoutManager = new LinearLayoutManager(this);
        productlist.setLayoutManager(layoutManager);
    }

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseRecyclerOptions<Cart> options =
                new FirebaseRecyclerOptions.Builder<Cart>()
                        .setQuery(cartlistRef, Cart.class)
                        .build();

        adapter =new FirebaseRecyclerAdapter<Cart, CartViewHolder>(options) {



            @Override
            protected void onBindViewHolder(@NonNull CartViewHolder holder, int position, @NonNull final Cart model) {
                holder.txtProductQuantity.setText( "Quantity = "+ model.getQuantity());
                holder.txtProductPrice.setText("Price = "+ model.getPrice() + "$" );
                holder.txtProductName.setText(model.getPname());

            }
            @NonNull
            @Override
            public CartViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cart_items_layout, parent, false);
                CartViewHolder holder = new CartViewHolder(view);
                return holder;
            }

        };
        productlist.setAdapter(adapter);
        adapter.startListening();


    }
}
