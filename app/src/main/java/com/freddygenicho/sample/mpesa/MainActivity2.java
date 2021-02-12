package com.freddygenicho.sample.mpesa;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.freddy.sample.mpesa.R;
import com.freddygenicho.sample.mpesa.Model.Products;
import com.freddygenicho.sample.mpesa.ViewHolder.ProductViewHolder;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

public class MainActivity2 extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{
    private FirebaseUser currentUser;
    private FirebaseAuth mAUTH;
    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mToggle;
    private DatabaseReference RootRef;
    private FloatingActionButton fab;
    private TextView username;
    private DatabaseReference ProductsRef;
    RecyclerView.LayoutManager layoutManager;

    private RecyclerView recyclerView;
    private String type="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        mAUTH=FirebaseAuth.getInstance();
        currentUser=mAUTH.getCurrentUser();
        RootRef= FirebaseDatabase.getInstance().getReference();
        ProductsRef = FirebaseDatabase.getInstance().getReference().child("Products");
        Intent intent=getIntent();
        Bundle bundle=intent.getExtras();
        if(bundle != null)
        {
            type=getIntent().getExtras().get("Admin").toString();
        }
        mDrawerLayout=(DrawerLayout)findViewById(R.id.drawer);
        mToggle=new ActionBarDrawerToggle(this,mDrawerLayout,R.string.open,R.string.close);
        mDrawerLayout.addDrawerListener(mToggle);
        mToggle.syncState();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        recyclerView = findViewById(R.id.recycler_menu);
        recyclerView.setHasFixedSize(false);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);




        View headerView = navigationView.getHeaderView(0);
//        fab = (FloatingActionButton) findViewById(R.id.fab);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                if(!type.equals("Admin"))
//                {
//                    Intent intent=new Intent(MainActivity2.this,CartActivity.class);
//                    startActivity(intent);
//
//
//                }
//
//            }
//        });

    }
    @Override
    protected void onStart()
    {
        super.onStart();

        FirebaseRecyclerOptions<Products> options =
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
                                    Intent intent=new Intent(MainActivity2.this,AdminMaintainProductsActivity.class);
                                    intent.putExtra("pid",model.getPid());
                                    startActivity(intent);


                                }
                                else
                                {
                                    Intent intent=new Intent(MainActivity2.this,ProductDetailsActivity.class);
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
        adapter.startListening();
    }






   /* @Override
    protected void onStart() {

        super.onStart();
        if (currentUser == null) {
            sendUserToLoginActivity();

        }
    }*/

    private void sendUserToLoginActivity() {
        Intent intent=new Intent(MainActivity2.this,login.class);
        startActivity(intent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.options,menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        super.onOptionsItemSelected(item);
        if (item.getItemId()== R.id.logout){
            mAUTH.signOut();
            sendUserToLoginActivity();

        }


        if (mToggle.onOptionsItemSelected(item)){

            return true;

        }

        return super.onOptionsItemSelected(item);
    }





    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        int id = menuItem.getItemId();

//       if (id == R.id.chat) {
//            if(!type.equals("Admin"))
//            {
//
//                Intent intent=new Intent(MainActivity2.this, Chats.class);
//                startActivity(intent);
//
//            }
//
//        }
//        if (id == R.id.cart){
//            if(!type.equals("Admin"))
//            {
//
//                Intent intent=new Intent(MainActivity2.this,CartActivity.class);
//                startActivity(intent);
//
//
//            }
//
//
//        }
//        else if (id == R.id.search){
//            if(!type.equals("Admin"))
//            {
//
//                Intent intent=new Intent(MainActivity2.this,SearchProductsActivity.class);
//                startActivity(intent);
//
//            }
//
//
//        }
        if (id == R.id.logout){
            if(!type.equals("Admin"))
            {

                mAUTH.signOut();
                sendUserToLoginActivity();

            }


        }
//        else if (id == R.id.hire){
//            if(!type.equals("Admin"))
//            {
//
//                Intent intent=new Intent(MainActivity2.this,MainActivityHearse.class);
//                startActivity(intent);
//
//            }
//
//
//        }
//        else if (id == R.id.donations){
//            if(!type.equals("Admin"))
//            {
//
//                Intent intent=new Intent(MainActivity2.this,DonationsRequestsHomeActivity.class);
//                startActivity(intent);
//
//            }
//
//
//        }



        return true;
    }
}
