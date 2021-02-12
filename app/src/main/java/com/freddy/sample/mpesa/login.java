package com.freddy.sample.mpesa;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class login extends AppCompatActivity {
    //remove ine case
    private FirebaseUser currentUser;
    private FirebaseAuth mAUTH;
    private ProgressDialog loadingbar;

    TextView register,forgotpass,admin;
    Button login;
    EditText Email,Password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        try {
            mAUTH = FirebaseAuth.getInstance();
            //remove ine case
            currentUser = mAUTH.getCurrentUser();
//        register=(TextView) findViewById(R.id.gotoRegister);
       forgotpass=(TextView) findViewById(R.id.changePassword);
            Email = (EditText) findViewById(R.id.inputEmail);
            Password = (EditText) findViewById(R.id.inputPassword);

            loadingbar = new ProgressDialog(this);
//        register.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intent=new Intent(login.this,Registration.class);
//                startActivity(intent);
//            }
//        });
            login = (Button) findViewById(R.id.btnLogin);
            login.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    AllowUserToLogin();
                }
            });
        forgotpass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(login.this, Resetpassword.class);
                startActivity(intent);
            }
        });
        }catch(Exception e){
            e.printStackTrace();
        }

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.loginoptions,menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        super.onOptionsItemSelected(item);
        if (item.getItemId()== R.id.foradmin){
            Intent intent=new Intent(login.this,AdminLoginActivity.class);
            startActivity(intent);

        }


            return super.onOptionsItemSelected(item);

        }


    private void AllowUserToLogin() {
        String email=Email.getText().toString();
        String pass=Password.getText().toString();
        if (TextUtils.isEmpty(email)){
            Email.setError("please enter your email address");
            Toast.makeText(this, "please enter your email address", Toast.LENGTH_SHORT).show();

        }
        if (TextUtils.isEmpty(pass)){
            Password.setError("please enter your password");
            Toast.makeText(this, "please enter your password", Toast.LENGTH_SHORT).show();

        }

        else{
            loadingbar.setTitle("Sign in");
            loadingbar.setMessage("Please Wait...");
            loadingbar.setCanceledOnTouchOutside(true);
            loadingbar.show();
            mAUTH.signInWithEmailAndPassword(email,pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {

                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if(task.isSuccessful()){
                        SendUserToMainActivity();
                        Toast.makeText(login.this, "Login Successfull", Toast.LENGTH_SHORT).show();
                        loadingbar.dismiss();
                    }
                    else{
                        String message=task.getException().toString();
                        Toast.makeText(login.this, "Error : " + message, Toast.LENGTH_SHORT).show();
                        loadingbar.dismiss();
                    }

                }
            });

        }
//        String email=Email.getText().toString();
//        String pass=Password.getText().toString();
//        if (TextUtils.isEmpty(email)){
//            Email.setError("please enter your email address");
//            Toast.makeText(this, "please enter your email address", Toast.LENGTH_SHORT).show();
//
//        }
//        if (TextUtils.isEmpty(pass)){
//            Password.setError("please enter your password");
//            Toast.makeText(this, "please enter your password", Toast.LENGTH_SHORT).show();
//
//        }
//        else{
//            if (email.equals("mortician@gmail.com") && (pass.equals("mortician54321"))){
//                loadingbar.setTitle("Sign in");
//                loadingbar.setMessage("Please Wait...");
//                loadingbar.setCanceledOnTouchOutside(true);
//                loadingbar.show();
//                SendUserToMainActivity();
//                Toast.makeText(login.this, "Login Successfull", Toast.LENGTH_SHORT).show();
//                loadingbar.dismiss();
//
//            }
//            else {
//                Toast.makeText(this, "please enter correct details", Toast.LENGTH_SHORT).show();
//
//            }
//
//
//
//        }

//        else{
//            loadingbar.setTitle("Sign in");
//            loadingbar.setMessage("Please Wait...");
//            loadingbar.setCanceledOnTouchOutside(true);
//            loadingbar.show();
//            mAUTH.signInWithEmailAndPassword(email,pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
//                @Override
//                public void onComplete(@NonNull Task<AuthResult> task) {
//                    if(task.isSuccessful()){
//                        SendUserToMainActivity();
//                        Toast.makeText(login.this, "Login Successfull", Toast.LENGTH_SHORT).show();
//                        loadingbar.dismiss();
//                    }
//                    else{
//                        String message=task.getException().toString();
//                        Toast.makeText(login.this, "Error : " + message, Toast.LENGTH_SHORT).show();
//                        loadingbar.dismiss();
//                    }
//
//                }
//            });
//
//        }
    }

   /* @Override
    protected void onStart() {
        super.onStart();
        if (currentUser !=null){
            SendUserToMainActivity();
        }
    }*/

    private void SendUserToMainActivity() {
        Intent intent=new Intent(login.this, MainActivity2.class);
        startActivity(intent);
    }
}
