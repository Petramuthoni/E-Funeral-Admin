package com.freddy.sample.mpesa;

import android.content.DialogInterface;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.freddy.sample.mpesa.Model.Bookings;
import com.freddy.sample.mpesa.Model.Payments;
import com.freddy.sample.mpesa.Model.hearsepayments;
import com.freddy.sample.mpesa.Model.morticians;
import com.freddy.sample.mpesa.ViewHolder.BookingViewHolder;
import com.freddy.sample.mpesa.ViewHolder.MorticiansViewHolder;
import com.freddy.sample.mpesa.ViewHolder.PaymentsViewHolder;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ManagerViewMorticiansActivity extends AppCompatActivity {
    ListView listv;
    List<morticians> paymentl1;
    DatabaseReference paymentRef,funeralpaymentRef;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manager_view_morticians);
        listv=findViewById(R.id.listviewmorticians);
        // linechart=(LineChart)findViewById(R.id.linegraph);

        paymentl1=new ArrayList<>();
        paymentRef = FirebaseDatabase.getInstance().getReference().child("morticians");
        funeralpaymentRef = FirebaseDatabase.getInstance().getReference().child("funeral requests payments admin");

        paymentRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                paymentl1.clear();

                for (DataSnapshot paymentds : dataSnapshot.getChildren()){
                    morticians fpmts=paymentds.getValue(morticians.class);
                    paymentl1.add(fpmts);
                }
                ListAdapterMorticians adapter = new ListAdapterMorticians(ManagerViewMorticiansActivity.this,paymentl1);
                listv.setAdapter(adapter);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });



    }

}
