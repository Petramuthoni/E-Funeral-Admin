package com.freddy.sample.mpesa;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

//import com.example.e_funeral.Model.Donations;
//import com.example.e_funeral.ViewHolder.deceasedViewHolder;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.freddy.sample.mpesa.Model.Donations;
import com.freddy.sample.mpesa.ViewHolder.deceasedViewHolder;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

public class DonationsRequestsHomeActivity extends AppCompatActivity {
    private RecyclerView deceasedRecyclerView;
    RecyclerView.LayoutManager layoutManager;
    private DatabaseReference deceasedRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_donations_requests_home);
        deceasedRef = FirebaseDatabase.getInstance().getReference().child("Donation Pleas");
        deceasedRecyclerView = findViewById(R.id.recycler_deceaseddonations_menu);
        deceasedRecyclerView.setHasFixedSize(false);
        layoutManager = new LinearLayoutManager(this);
        deceasedRecyclerView.setLayoutManager(layoutManager);
    }
    @Override
    protected void onStart()
    {
        super.onStart();

        FirebaseRecyclerOptions<Donations> options =
                new FirebaseRecyclerOptions.Builder<Donations>()
                        .setQuery(deceasedRef, Donations.class)
                        .build();


        FirebaseRecyclerAdapter<Donations, deceasedViewHolder> adapter =
                new FirebaseRecyclerAdapter<Donations, deceasedViewHolder>(options) {
                    @Override
                    protected void onBindViewHolder(@NonNull deceasedViewHolder holder, int position, @NonNull Donations model) {
                        holder.txtDeceasedName.setText(model.getDname());
                        holder. txtDeacesedDonationDescription.setText(model.getPleadescription());
                        holder.txtDonateToPrice.setText("Send Donations To = " + model.getDonateto());
                        Picasso.get().load(model.getImage()).into(holder.imageViews);
                    }




                    @NonNull
                    @Override
                    public deceasedViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
                    {
                        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.deceased_item_layout, parent, false);
                        deceasedViewHolder holder = new deceasedViewHolder(view);
                        return holder;
                    }
                };
        deceasedRecyclerView.setAdapter(adapter);
        adapter.startListening();
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.donorpleaaddoptions,menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        super.onOptionsItemSelected(item);
        if (item.getItemId()== R.id.donationplea){
            Intent intent=new Intent(DonationsRequestsHomeActivity.this,DonationsRequestsAddActivity.class);
            startActivity(intent);

        }


        return super.onOptionsItemSelected(item);

    }
}
