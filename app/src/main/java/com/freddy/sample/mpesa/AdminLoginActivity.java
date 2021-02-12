package com.freddy.sample.mpesa;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class AdminLoginActivity extends AppCompatActivity {
    private Button adminLogin;
    private EditText email, password;
    private String AdminEmail, AdminPassword;
    private FirebaseAuth mAUTH;
    private FirebaseUser currentUser;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_login);
        try {
            mAUTH = FirebaseAuth.getInstance();
            //remove ine case
            currentUser = mAUTH.getCurrentUser();
            adminLogin = (Button) findViewById(R.id.btnAdminLogin);
            email = (EditText) findViewById(R.id.inputAdminEmail);
            password = (EditText) findViewById(R.id.inputAdminPassword);

            adminLogin.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    // checkDetails();

                    //Intent intent=new Intent(AdminLoginActivity.this,AdminCategoryActivity.class);
                    // startActivity(intent);
               /* if (email.equals("efuneraladmin@gmail.com") && password.equals("admin54321")) {
                    Intent intent = new Intent(AdminLoginActivity.this, AdminCategoryActivity.class);
                    startActivity(intent);

                }*/
                    allowAdminToLogin();
                }

            });
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    private void allowAdminToLogin() {
        String Email=email.getText().toString();
        String pass=password.getText().toString();
        if (TextUtils.isEmpty(Email)){
            Toast.makeText(this, "please enter your email address", Toast.LENGTH_SHORT).show();

        }
        if (TextUtils.isEmpty(pass)){
            Toast.makeText(this, "please enter your password", Toast.LENGTH_SHORT).show();

        }
        else{
            if (Email.equals("efuneraladmin@gmail.com") && (pass.equals("admin54321"))){
                SendUserToMainActivity();

            }
            else {
                Toast.makeText(this, "please enter correct details", Toast.LENGTH_SHORT).show();

            }



        }
    }

    private void SendUserToMainActivity() {
        try {
            Intent intent = new Intent(AdminLoginActivity.this, ManagerDashboard.class);
            startActivity(intent);
        }catch(Exception e){
            Toast.makeText(AdminLoginActivity.this,e.getMessage(),Toast.LENGTH_LONG).show();
        }
    }
}

