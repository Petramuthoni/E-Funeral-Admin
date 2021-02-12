package com.freddygenicho.sample.mpesa;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

import com.freddy.sample.mpesa.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Chats extends AppCompatActivity {
    private Toolbar mToolbar;
    private ViewPager myViewPager;
    private TabLayout myTabLayout;
    private TabsAccessorAdapter myTabsAcccessorAdapter;
    private DatabaseReference RootRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chats);
        RootRef= FirebaseDatabase.getInstance().getReference();
        mToolbar=(Toolbar) findViewById(R.id.main_page_toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setTitle("E Funeral");
        myViewPager=(ViewPager) findViewById(R.id.main_tabs_pager);
        myTabsAcccessorAdapter=new TabsAccessorAdapter(getSupportFragmentManager());
        myViewPager.setAdapter(myTabsAcccessorAdapter);
        myTabLayout=(TabLayout)findViewById(R.id.main_tabs);
        myTabLayout.setupWithViewPager(myViewPager);

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.chats_options,menu);

        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        super.onOptionsItemSelected(item);

        if (item.getItemId()== R.id.create_group_option){
            RequestNewGroup();

        }



        return super.onOptionsItemSelected(item);
    }
    private void RequestNewGroup() {
        AlertDialog.Builder builder=new AlertDialog.Builder(Chats.this,R.style.AlertDialog );
        builder.setTitle("Enter Funeral Group Name:");
        final EditText groupNameField=new EditText(Chats.this);
        builder.setView(groupNameField);
        builder.setPositiveButton("create", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                String groupName=groupNameField.getText().toString();
                if(TextUtils.isEmpty(groupName)){
                    Toast.makeText(Chats.this,"Please enter the name of the funeral group", Toast.LENGTH_SHORT).show();
                }
                else{
                    CreateNewGroup(groupName);


                }

            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.cancel();


            }
        });
        builder.show();
    }
    private void CreateNewGroup(final String groupName) {
        RootRef.child("Groups").child(groupName).setValue("").addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()){
                    Toast.makeText(Chats.this,groupName + "funeral group is created successfully", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
