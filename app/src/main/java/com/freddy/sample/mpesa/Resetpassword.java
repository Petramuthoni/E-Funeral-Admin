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
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Resetpassword extends AppCompatActivity {
    Button resetbtn;
    EditText useremail;
    private FirebaseAuth mAuth;
    private ProgressDialog loadingbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resetpassword);
        mAuth = FirebaseAuth.getInstance();


        useremail = findViewById(R.id.inputyourEmail);
        resetbtn=(Button) findViewById(R.id.btnResetPaaword);
        loadingbar=new ProgressDialog(this);
        resetbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseUser user = mAuth.getCurrentUser();

                final String email = useremail.getText().toString();

                if(TextUtils.isEmpty(email))
                {
                    useremail.setError("Email address is required!");
                }
                else
                {

                    loadingbar.setTitle("Resetting your password");
                    loadingbar.setMessage("please wait while we verify your email address");
                    loadingbar.setCanceledOnTouchOutside(true);
                    loadingbar.show();

                    mAuth.sendPasswordResetEmail(email)
                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if(task.isSuccessful())
                                    {
                                        Toast.makeText(getApplicationContext(), "We have sent an email to "+" '"+ email +"'. Please check your email.", Toast.LENGTH_LONG)
                                                .show();
                                        startActivity(new Intent(getApplicationContext(), login.class));
                                        //useremail.setText(null);
                                    }
                                    else
                                    {
                                        String message=task.getException().toString();
                                        Toast.makeText(Resetpassword.this, "Error : " + message, Toast.LENGTH_SHORT).show();
                                        loadingbar.dismiss();

                                        useremail.setText(null);
                                    }
                                    loadingbar.dismiss();
                                }
                            });
                }
            }
        });
    }
}



