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

//import com.example.e_funeral.Model.Hearses;
//import com.example.e_funeral.ViewHolder.HearseViewHolder;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.freddy.sample.mpesa.R;
import com.freddygenicho.sample.mpesa.Model.Hearses;
import com.freddygenicho.sample.mpesa.ViewHolder.HearseViewHolder;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

public class MainActivityHearse extends AppCompatActivity {
   private RecyclerView hearseRecyclerView;
    RecyclerView.LayoutManager layoutManager;
    private DatabaseReference hearseRef;
   // private DatabaseReference RootRef;
  // private String type="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_hearse);
        hearseRef = FirebaseDatabase.getInstance().getReference().child("Hearses");
        hearseRecyclerView = findViewById(R.id.recycler_hearse_menu);
        hearseRecyclerView.setHasFixedSize(false);
        layoutManager = new LinearLayoutManager(this);
        hearseRecyclerView.setLayoutManager(layoutManager);
       // Intent intent=getIntent();
      //  Bundle bundle=intent.getExtras();
      //  if(bundle != null)
       // {
       //     type=getIntent().getExtras().get("Admin").toString();
      //  }
    }
    @Override
    protected void onStart()
    {
        super.onStart();

        FirebaseRecyclerOptions<Hearses> options =
                new FirebaseRecyclerOptions.Builder<Hearses>()
                        .setQuery(hearseRef, Hearses.class)
                        .build();


        FirebaseRecyclerAdapter<Hearses, HearseViewHolder> adapter =
                new FirebaseRecyclerAdapter<Hearses, HearseViewHolder>(options) {
                    @Override
                    protected void onBindViewHolder(@NonNull HearseViewHolder holder, int position, @NonNull final Hearses model) {
                        holder.txtHearseName.setText(model.getPname());
                        holder.txtHearseDescription.setText(model.getDescription());
                        Picasso.get().load(model.getImage()).into(holder.HearseimageView);
                       holder.itemView.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                              //  if(type.equals("Admin"))
                              //  {
                                    //Intent intent=new Intent(MainActivityHearse.this,AdminMaintainProductsActivity.class);
                                   // intent.putExtra("pid",model.getPid());
                                  //  startActivity(intent);
                                Intent intent=new Intent(MainActivityHearse.this,HearseBookingActivity.class);
                                 intent.putExtra("pid",model.getPid());
                                startActivity(intent);


                                }
                              //  else
                              //  {
                                   // Intent intent=new Intent(MainActivityHearse.this,HearseBookingActivity.class);
                                   // intent.putExtra("pid",model.getPid());
                                  //  startActivity(intent);

                               // }

                            });
                       // });*/

                    }

                   /* @Override
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
                    }*/
                    @NonNull
                    @Override
                    public HearseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
                    {
                        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.hearse_item_layout, parent, false);
                        HearseViewHolder holder = new HearseViewHolder(view);
                        return holder;
                    }
                };
        hearseRecyclerView.setAdapter(adapter);
        adapter.startListening();
    }
}
