package com.freddy.sample.mpesa;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.cepheuen.elegantnumberbutton.view.ElegantNumberButton;
//import com.example.e_funeral.Model.Products;
import com.freddy.sample.mpesa.Model.Products;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;

public class ProductDetailsActivity extends AppCompatActivity
{
    private Button addToCartButton;
    private ImageView productImage;
    private ElegantNumberButton numberButton;
    private TextView productPrice,productDescription,productName;
    private String productID="";
    private FirebaseAuth mAuth;
    private String currentUserId;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_details);
        mAuth=FirebaseAuth.getInstance();
        addToCartButton=(Button)findViewById(R.id.pd_add_to_cart_button);
        numberButton=(ElegantNumberButton) findViewById(R.id.number_btn);
        productImage=(ImageView) findViewById(R.id.product_image_details);
        productDescription=(TextView)findViewById(R.id.product_description_details);
        productName=(TextView)findViewById(R.id.product_name_description);
        productPrice=(TextView)findViewById(R.id.product_price_details);

        productID=getIntent().getStringExtra("pid");
        //getProductDetails(productID);
        getProductDetails(productID);
        
        addToCartButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               addToCartList(); 
            }
        });


    }

    /*private void getProductDetail(String productID) {
        DatabaseReference productsRef=FirebaseDatabase.getInstance().getReference().child("Products");
        productsRef.child(productID).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()){
                    Products products=dataSnapshot.getValue(Products.class);
                    productName.setText(products.getPname());
                    productPrice.setText(products.getPrice());
                    productDescription.setText(products.getDescription());
                    Picasso.get().load(products.getImage()).into(productImage);


                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }*/

    private void addToCartList()
    {
        String saveCurrentTime,saveCurrentDate;
        Calendar calForDate= Calendar.getInstance();
        SimpleDateFormat currentDate=new SimpleDateFormat("MMM dd, yyyy");
        saveCurrentDate=currentDate.format(calForDate.getTime());

        SimpleDateFormat currentTime=new SimpleDateFormat("HH:mm:ss a");
        saveCurrentTime=currentTime.format(calForDate.getTime());

       final DatabaseReference cartListRef=FirebaseDatabase.getInstance().getReference().child("Cart List");
        currentUserId=mAuth.getCurrentUser().getUid();
        final HashMap<String, Object> cartMap=new HashMap<>();
        cartMap.put("pid", productID);
        cartMap.put("uid", currentUserId);
        cartMap.put("pname", productName.getText().toString());
        cartMap.put("price", productPrice.getText().toString());
        cartMap.put("date", saveCurrentDate);
        cartMap.put("time", saveCurrentTime);
        cartMap.put("quantity", numberButton.getNumber());
        cartMap.put("discount", "");
        //left out prevalent in the line below(supposed to put currentuserid)

        cartListRef.child("User View").child(currentUserId)/*.child(Prevalent.currentOnlineUser.getUid())*/.child("Products").child(productID).updateChildren(cartMap).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()){
                    cartListRef.child("Admin View").child(currentUserId)/*.child(Prevalent.currentOnlineUser.getUid())*/ .child("Products").child(productID).updateChildren(cartMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if(task.isSuccessful()){
                                Toast.makeText(ProductDetailsActivity.this,"Added to Cart List", Toast.LENGTH_SHORT).show();
                                Intent intent=new Intent(ProductDetailsActivity.this,MainActivity2.class);
                               // intent.putExtra("pid",model.getPid());
                                startActivity(intent);

                            }
                            else{
                                String message = task.getException().toString();
                                Toast.makeText(ProductDetailsActivity.this, "Error: " + message, Toast.LENGTH_LONG).show();
                            }

                        }
                    });

                }
            }
        });








    }

    private void getProductDetails(final String productID) {
        DatabaseReference productsRef= FirebaseDatabase.getInstance().getReference().child("Products");
        productsRef.child(productID).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot)
            {
                if(dataSnapshot.exists()){
                    Products products=dataSnapshot.getValue(Products.class);
                    productName.setText(products.getPname());
                    productPrice.setText(products.getPrice());
                    productDescription.setText(products.getDescription());
                    Picasso.get().load(products.getImage()).into(productImage);



                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}

