package com.freddygenicho.sample.mpesa;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

//import com.example.e_funeral.Model.Products;
//import com.example.e_funeral.ViewHolder.ProductViewHolder;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.freddy.sample.mpesa.R;
import com.freddygenicho.sample.mpesa.Model.Products;
import com.freddygenicho.sample.mpesa.ViewHolder.ProductViewHolder;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

public class SearchProductsActivity extends AppCompatActivity {

    private Button SearchBtn;
    private EditText inputText;
    private RecyclerView searchList;
    private String SearchInput;
    private DatabaseReference ProductsRef;

    RecyclerView.LayoutManager layoutManager;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_products);
        ProductsRef = FirebaseDatabase.getInstance().getReference().child("Products");
        SearchBtn=(Button)findViewById(R.id.search_btn);
        inputText=(EditText)findViewById(R.id.search_product_name);
        searchList=(RecyclerView)findViewById(R.id.search_list);
        searchList.setHasFixedSize(false);
        layoutManager = new LinearLayoutManager(this);
        searchList.setLayoutManager(layoutManager);


        SearchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SearchInput=inputText.getText().toString();


                onStart();


            }
        });



    }

    @Override
    protected void onStart() {
        super.onStart();


        FirebaseRecyclerOptions<Products> options = new FirebaseRecyclerOptions.Builder<Products>().setQuery(ProductsRef.orderByChild("pname").startAt(SearchInput),Products.class).build();
        FirebaseRecyclerAdapter<Products, ProductViewHolder> adapter=new FirebaseRecyclerAdapter<Products, ProductViewHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull ProductViewHolder holder, int position, @NonNull final Products model)
            {
                holder.txtProductName.setText(model.getPname());
                holder.txtProductDescription.setText(model.getDescription());
                holder.txtProductPrice.setText("Price = " + model.getPrice() + "$");
                Picasso.get().load(model.getImage()).into(holder.imageView);
                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent=new Intent(SearchProductsActivity.this,ProductDetailsActivity.class);
                        intent.putExtra("pid",model.getPid());
                        startActivity(intent);
                    }
                });

            }

            @NonNull
            @Override
            public ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
                View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.product_items_layout,parent,false);
                ProductViewHolder holder = new ProductViewHolder(view);
                return holder;
            }
        };
        searchList.setAdapter(adapter);
        adapter.startListening();
    }


}

   /* FirebaseRecyclerOptions<Products> options =
            new FirebaseRecyclerOptions.Builder<Products>()
                    .setQuery(ProductsRef, Products.class)
                    .build();


    FirebaseRecyclerAdapter<Products, ProductViewHolder> adapter =
            new FirebaseRecyclerAdapter<Products, ProductViewHolder>(options) {
                @Override
                protected void onBindViewHolder(@NonNull ProductViewHolder holder, int position, @NonNull final Products model)
                {
                    holder.txtProductName.setText(model.getPname());
                    holder.txtProductDescription.setText(model.getDescription());
                    holder.txtProductPrice.setText("Price = " + model.getPrice() + "$");
                    Picasso.get().load(model.getImage()).into(holder.imageView);

                    holder.itemView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            if(type.equals("Admin"))
                            {
                                Intent intent=new Intent(MainActivity.this,AdminMaintainProductsActivity.class);
                                intent.putExtra("pid",model.getPid());
                                startActivity(intent);


                            }
                            else
                            {
                                Intent intent=new Intent(MainActivity.this,ProductDetailsActivity.class);
                                intent.putExtra("pid",model.getPid());
                                startActivity(intent);

                            }

                        }
                    });
                }
                @NonNull
                @Override
                public ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
                {
                    View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.product_items_layout, parent, false);
                    ProductViewHolder holder = new ProductViewHolder(view);
                    return holder;
                }
            };
        recyclerView.setAdapter(adapter);
                adapter.startListening();*/
