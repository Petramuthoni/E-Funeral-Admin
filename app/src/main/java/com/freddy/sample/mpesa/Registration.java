package com.freddy.sample.mpesa;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class Registration extends AppCompatActivity {
    Button register;
    public EditText Name,Phone,Email,Password;
    private FirebaseAuth mAuth;
    private ProgressDialog loadingbar;
    private DatabaseReference RootRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        mAuth=FirebaseAuth.getInstance();
        RootRef= FirebaseDatabase.getInstance().getReference();
        register=(Button) findViewById(R.id.btnRegister);
        Name=(EditText)findViewById(R.id.inputName);
        Phone=(EditText)findViewById(R.id.inputPhone);
        Email=(EditText)findViewById(R.id.inputEmail);
        Password=(EditText)findViewById(R.id.inputPassword);
        loadingbar=new ProgressDialog(this);
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                createNewAccount();

            }
        });
    }

    private void createNewAccount() {
        final String email=Email.getText().toString();
        String pass=Password.getText().toString();
        final String names=Name.getText().toString();
        final String phonenumber=Phone.getText().toString();
        if (TextUtils.isEmpty(email)){
            Email.setError("please enter your email address");
            Toast.makeText(this, "please enter your email address", Toast.LENGTH_SHORT).show();

        }
        if (TextUtils.isEmpty(pass)){
            Password.setError("please enter your password");
            Toast.makeText(this, "please enter your password", Toast.LENGTH_SHORT).show();

        }
        if (TextUtils.isEmpty(names)){
            Name.setError("please enter your name");
            Toast.makeText(this, "please enter your name", Toast.LENGTH_SHORT).show();

        }
        if (TextUtils.isEmpty(phonenumber)){
            Phone.setError("please enter your phone number");
            Toast.makeText(this, "please enter your phone number", Toast.LENGTH_SHORT).show();

        }
        else{
            loadingbar.setTitle("Creating New Account");
            loadingbar.setMessage("please wait while we are creating an account for you");
            loadingbar.setCanceledOnTouchOutside(true);
            loadingbar.show();
            mAuth.createUserWithEmailAndPassword(email,pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if(task.isSuccessful()){
                      final String currentUserId=mAuth.getCurrentUser().getUid();

                         /*RootRef.child("Users").child(currentUserId).setValue("");*/
                        HashMap<String, Object> profilemap=new HashMap<>();
                        profilemap.put("uid", currentUserId);
                        profilemap.put("name", names);
                        profilemap.put("Email", email);
                        profilemap.put("Phone", phonenumber);
                        RootRef.child("Users").child(currentUserId).setValue(profilemap);
                        SendUserToLoginActivity();
                        Toast.makeText(Registration.this, "Account Created Successfully", Toast.LENGTH_SHORT).show();
                        loadingbar.dismiss();

                    }
                    else{
                        String message=task.getException().toString();
                        Toast.makeText(Registration.this, "Error : " + message, Toast.LENGTH_SHORT).show();
                        loadingbar.dismiss();

                    }

                }
            });

        }

    }

    private void SendUserToLoginActivity() {
        Intent intent=new Intent(Registration.this,login.class);
        startActivity(intent);
    }
}
