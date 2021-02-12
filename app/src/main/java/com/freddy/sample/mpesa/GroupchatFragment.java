package com.freddy.sample.mpesa;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;


/**
 * A simple {@link Fragment} subclass.
 */
public class GroupchatFragment extends Fragment {
    private View groupFragmentView;
    private ListView listView;
    private ArrayAdapter<String> arrayAdapter;
    private ArrayList<String> listofgroups=new ArrayList<>();
    private DatabaseReference GroupRef;


    public GroupchatFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        GroupRef = FirebaseDatabase.getInstance().getReference().child("Groups");
        groupFragmentView= inflater.inflate(R.layout.fragment_groupchat, container, false);
        listView=(ListView)groupFragmentView.findViewById(R.id.list_view);
        arrayAdapter=new ArrayAdapter<String>(getContext(),android.R.layout.simple_list_item_1,listofgroups);
        listView.setAdapter(arrayAdapter);
        RetrieveAndDisplayGroupNames();
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                String CurrentGroupName= adapterView.getItemAtPosition(position).toString();
                Intent intent=new Intent(getContext(), GroupChatActivity.class);
                intent.putExtra("groupName", CurrentGroupName);
                startActivity(intent);


            }
        });
        return groupFragmentView;
    }

    private void RetrieveAndDisplayGroupNames() {
      GroupRef.addValueEventListener(new ValueEventListener() {
          @Override
          public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
              Set<String> set=new HashSet<>();
              Iterator iterator=dataSnapshot.getChildren().iterator();
              while(iterator.hasNext()){
                  set.add(((DataSnapshot)iterator.next()).getKey());

              }
              listofgroups.clear();
              listofgroups.addAll(set);
              arrayAdapter.notifyDataSetChanged();

          }

          @Override
          public void onCancelled(@NonNull DatabaseError databaseError) {

          }
      }) ;
    }

}
