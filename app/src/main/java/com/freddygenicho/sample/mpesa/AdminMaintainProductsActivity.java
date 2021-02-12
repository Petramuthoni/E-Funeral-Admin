package com.freddygenicho.sample.mpesa;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

//import com.example.e_funeral.Model.Products;
import com.freddy.sample.mpesa.R;
import com.freddygenicho.sample.mpesa.Model.Products;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.HashMap;

public class AdminMaintainProductsActivity extends AppCompatActivity
{
    private Button applyChangesBtn,deleteBtn;
    private EditText name,price,description;
    private ImageView imageView;
    private String productID="";
    private DatabaseReference productsRef;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_maintain_products);
        productsRef= FirebaseDatabase.getInstance().getReference().child("Products");
        productID=getIntent().getStringExtra("pid");
        applyChangesBtn=(Button)findViewById(R.id.apply_changes_btn);
        deleteBtn=(Button)findViewById(R.id.delete_products_btn);
        name=(EditText)findViewById(R.id.product_name_maintain);
        price=(EditText)findViewById(R.id.product_price_maintain);
        description=(EditText)findViewById(R.id.product_description_maintain);
        imageView=(ImageView)findViewById(R.id.product_image_maintain);
        displaySpecificProductInfo();
        applyChangesBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                applyChanges();
            }
        });
        deleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                deleteThisProduct();
            }
        });

    }

    private void deleteThisProduct()
    {
        productsRef.child(productID).removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task)
            {
                Toast.makeText(AdminMaintainProductsActivity.this,"The Product Has Been Removed Successfully", Toast.LENGTH_LONG).show();
                Intent intent=new Intent(AdminMaintainProductsActivity.this,AdminCategoryActivity.class);
                startActivity(intent);
                finish();

            }
        });

    }

    private void applyChanges() {
        //String pName=name.getText().toString();
        //String pPrice=price.getText().toString();
        //String pDescription=description.getText().toString();

        if(TextUtils.isEmpty(name.getText().toString())){
            Toast.makeText(AdminMaintainProductsActivity.this,"Please write the product's name", Toast.LENGTH_LONG).show();
        }
        else if(TextUtils.isEmpty(price.getText().toString())){
            Toast.makeText(AdminMaintainProductsActivity.this,"Please write the product's price", Toast.LENGTH_LONG).show();
        }

       else if(TextUtils.isEmpty(description.getText().toString())){
            Toast.makeText(AdminMaintainProductsActivity.this,"Please write the product's description", Toast.LENGTH_LONG).show();
        }

        else{

            HashMap<String, Object> productsMap = new HashMap<>();
            productsMap.put("pid", productID);
            productsMap.put("description", description.getText().toString());
            productsMap.put("price",price.getText().toString());
            productsMap.put("pname", name.getText().toString());
            productsRef.child(productID).updateChildren(productsMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task)
                {
                    if (task.isSuccessful())
                    {

                        Toast.makeText(AdminMaintainProductsActivity.this,"Changes Applied Successfully", Toast.LENGTH_LONG).show();
                        Intent intent=new Intent(AdminMaintainProductsActivity.this,AdminCategoryActivity.class);
                        startActivity(intent);
                        finish();

                    }

                }
            });
        }


    }

    private void displaySpecificProductInfo() {
        productsRef.child(productID).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()){

                    //String pName=dataSnapshot.child("pname").getValue().toString();
                  //  String pPrice=dataSnapshot.child("price").getValue().toString();
                  //  String pDescription=dataSnapshot.child("description").getValue().toString();
                  //String pImage=dataSnapshot.child("image").getValue().toString();

                   // name.setText(pName);
                   /// price.setText(pPrice);
                   // description.setText(pDescription);
                 // Picasso.get().load(pImage).into(imageView);
                    Products products=dataSnapshot.getValue(Products.class);
                    name.setText(products.getPname());
                    price.setText(products.getPrice());
                    description.setText(products.getDescription());
                    Picasso.get().load(products.getImage()).into(imageView);


                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
