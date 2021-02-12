package com.freddy.sample.mpesa;

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
import android.widget.ImageView;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.freddy.sample.mpesa.Model.Products;
import com.freddy.sample.mpesa.ViewHolder.ProductViewHolder;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
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
    TextView badgeCounter;
    ImageView img;
    // change the number to see badge in action
    int pendingNotifications = 1;
    private DatabaseReference paymentReference;
    private ImageView coffins, flowers, crosses;
    private ImageView bodybags, linens, perfumes,hearses;


    MenuItem menuItem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        mAUTH = FirebaseAuth.getInstance();
        currentUser = mAUTH.getCurrentUser();
        RootRef = FirebaseDatabase.getInstance().getReference();
        ProductsRef = FirebaseDatabase.getInstance().getReference().child("Products");
//        Intent intent=getIntent();
//        Bundle bundle=intent.getExtras();
//        if(bundle != null)
//        {
//            type=getIntent().getExtras().get("Admin").toString();
//        }
        coffins = (ImageView) findViewById(R.id.coffins);
        flowers = (ImageView) findViewById(R.id.flowers);
        crosses = (ImageView) findViewById(R.id.cross);
        bodybags = (ImageView) findViewById(R.id.bodybag);
        hearses = (ImageView) findViewById(R.id.hearse);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer);
        mToggle = new ActionBarDrawerToggle(this, mDrawerLayout, R.string.open, R.string.close);
        mDrawerLayout.addDrawerListener(mToggle);
        mToggle.syncState();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        try {
            paymentReference = FirebaseDatabase.getInstance().getReference().child("funeralnotification");
            paymentReference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    if (dataSnapshot.exists()) {
                        try{
                        pendingNotifications = (int) dataSnapshot.getChildrenCount();
                        badgeCounter.setText(String.valueOf(pendingNotifications));

                    }catch(Exception e){
                           e.printStackTrace();
                        }
                    }

                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
        perfumes = (ImageView) findViewById(R.id.perfume);
        coffins.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity2.this, AdminHomeActivity.class);
                intent.putExtra("category", "coffins");
                startActivity(intent);
            }
        });


        flowers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity2.this, AdminHomeActivity.class);
                intent.putExtra("category", "flowers");
                startActivity(intent);
            }
        });


        crosses.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity2.this, AdminHomeActivity.class);
                intent.putExtra("category", "crosses");
                startActivity(intent);
            }
        });


        bodybags.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity2.this, AdminHomeActivity.class);
                intent.putExtra("category", "bodybags");
                startActivity(intent);
            }
        });


        perfumes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity2.this, AdminHomeActivity.class);
                intent.putExtra("category", "perfumes");
                startActivity(intent);
            }
        });


    }





    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_notification, menu);

        menuItem = menu.findItem(R.id.nav_notification);

        // check if any pending notification
        if (pendingNotifications == 0) {
            // if no pending notification remove badge
            menuItem.setActionView(null);
        } else {

            // if notification than set the badge icon layout
            menuItem.setActionView(R.layout.notification_badge);
            // get the view from the nav item
            View view = menuItem.getActionView();
            // get the text view of the action view for the nav item
            badgeCounter = view.findViewById(R.id.badge_counter);
            img=view.findViewById(R.id.imagebtn);
            img.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(MainActivity2.this, MorticianViewFuneralNotification.class);
                    intent.putExtra("Admin", "Admin");
                    startActivity(intent);

                }
            });

            // set the pending notifications value
            badgeCounter.setText(String.valueOf(pendingNotifications));

        }

        return super.onCreateOptionsMenu(menu);
    }
//    @Override
//    protected void onStart()
//    {
//        super.onStart();
//
//        FirebaseRecyclerOptions<Products> options =
//                new FirebaseRecyclerOptions.Builder<Products>()
//                        .setQuery(ProductsRef, Products.class)
//                        .build();
//
//
//        FirebaseRecyclerAdapter<Products, ProductViewHolder> adapter =
//                new FirebaseRecyclerAdapter<Products, ProductViewHolder>(options) {
//                    @Override
//                    protected void onBindViewHolder(@NonNull ProductViewHolder holder, int position, @NonNull final Products model)
//                    {
//                        holder.txtProductName.setText(model.getPname());
//                        holder.txtProductDescription.setText(model.getDescription());
//                        holder.txtProductPrice.setText("Price = " + model.getPrice() + "$");
//                        Picasso.get().load(model.getImage()).into(holder.imageView);
//
//                        holder.itemView.setOnClickListener(new View.OnClickListener() {
//                            @Override
//                            public void onClick(View view) {
//                                if(type.equals("Admin"))
//                                {
//                                    Intent intent=new Intent(MainActivity2.this,AdminMaintainProductsActivity.class);
//                                    intent.putExtra("pid",model.getPid());
//                                    startActivity(intent);
//
//
//                                }
//                                else
//                                {
//                                    Intent intent=new Intent(MainActivity2.this,ProductDetailsActivity.class);
//                                    intent.putExtra("pid",model.getPid());
//                                    startActivity(intent);
//
//                                }
//
//                            }
//                        });
//                    }
//                    @NonNull
//                    @Override
//                    public ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
//                    {
//                        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.product_items_layout, parent, false);
//                        ProductViewHolder holder = new ProductViewHolder(view);
//                        return holder;
//                    }
//                };
//        recyclerView.setAdapter(adapter);
//        adapter.startListening();
//    }






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

//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        super.onCreateOptionsMenu(menu);
//        getMenuInflater().inflate(R.menu.options,menu);
//
//        return true;
//    }

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
        if (id == R.id.checko) {


            Intent intent = new Intent(MainActivity2.this, AdminNewOrdersActivity.class);
            startActivity(intent);


        }
        if (id == R.id.checkp) {

            Intent intent = new Intent(MainActivity2.this, AdminCheckPayments.class);
            startActivity(intent);


        }
        if (id == R.id.maintainpro) {


            Intent intent = new Intent(MainActivity2.this, MainActivitydashboard.class);
            intent.putExtra("Admin", "Admin");
            startActivity(intent);


        }
        if (id == R.id.checkfr) {


            Intent intent = new Intent(MainActivity2.this, AdminCheckFuneralRequests.class);
            startActivity(intent);


        }
        if (id == R.id.checkA) {


            Intent intent = new Intent(MainActivity2.this, funeralservice.class);
            startActivity(intent);


        }
        else if (id == R.id.checkbrp) {


            Intent intent = new Intent(MainActivity2.this, AdminViewFuneralPayments.class);
            startActivity(intent);

        }
        if (id == R.id.checkhb) {


            Intent intent = new Intent(MainActivity2.this, AdminCheckHearseBookingsActivity.class);
            startActivity(intent);


        }
        if (id == R.id.checkhearsepayments) {


            Intent intent = new Intent(MainActivity2.this, AdminCheckHearsePaymentsActivity.class);
            startActivity(intent);


        }
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
//        else if (id == R.id.logout){
//            if(!type.equals("Admin"))
//            {
//
//                mAUTH.signOut();
//                sendUserToLoginActivity();
//
//            }
//
//
//        }
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
