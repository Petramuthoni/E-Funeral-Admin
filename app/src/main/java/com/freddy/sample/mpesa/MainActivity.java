package com.freddy.sample.mpesa;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.freddy.mpesa.stkpush.Mode;
import com.freddy.mpesa.stkpush.api.response.STKPushResponse;
import com.freddy.mpesa.stkpush.interfaces.STKListener;
import com.freddy.mpesa.stkpush.interfaces.TokenListener;
import com.freddy.mpesa.stkpush.model.Mpesa;
import com.freddy.mpesa.stkpush.model.STKPush;
import com.freddy.mpesa.stkpush.model.Token;
import com.freddy.mpesa.stkpush.model.Transaction;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class MainActivity extends AppCompatActivity implements TokenListener {
    private String  saveCurrentDate, saveCurrentTime;
    private String paymentRandomKey;

    public static final String TAG = MainActivity.class.getSimpleName();

    private EditText phoneET, amountET,nameEditText;
    private SweetAlertDialog sweetAlertDialog;
    private Mpesa mpesa;

    private String phone_number;
    private String amount;
    private String names;
    private DatabaseReference paymentRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        paymentRef = FirebaseDatabase.getInstance().getReference().child("Payments");

        phoneET = findViewById(R.id.phoneET);
        amountET = findViewById(R.id.amountET);
        nameEditText = findViewById(R.id.nameET);

        mpesa = new Mpesa(Config.CONSUMER_KEY, Config.CONSUMER_SECRET, Mode.SANDBOX);

        sweetAlertDialog = new SweetAlertDialog(this, SweetAlertDialog.PROGRESS_TYPE);
        sweetAlertDialog.setTitleText("Connecting to Safaricom");
        sweetAlertDialog.setContentText("Please wait...");
        sweetAlertDialog.setCancelable(false);
    }

    public void startMpesa(View view) {

        phone_number = phoneET.getText().toString();
        amount = amountET.getText().toString();
        names= nameEditText.getText().toString();

        if (phone_number.isEmpty()) {
            Toast.makeText(MainActivity.this, "Phone Number is required", Toast.LENGTH_SHORT).show();
            return;
        }

        if (amount.isEmpty()) {
            Toast.makeText(MainActivity.this, "Amount is required", Toast.LENGTH_SHORT).show();
            return;
        }
        if (names.isEmpty()) {
            Toast.makeText(MainActivity.this, "Your Name is required", Toast.LENGTH_SHORT).show();
            return;
        }

        if (!phone_number.isEmpty() && !amount.isEmpty()) {
            try {
                sweetAlertDialog.show();
                mpesa.getToken(this);
            } catch (UnsupportedEncodingException e) {
                Log.e(TAG, "UnsupportedEncodingException: " + e.getLocalizedMessage());
            }
        } else {
            Toast.makeText(MainActivity.this, "Please make sure that phone number and amount is not empty ", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onTokenSuccess(Token token) {
        STKPush stkPush = new STKPush();
        stkPush.setBusinessShortCode(Config.BUSINESS_SHORT_CODE);
        stkPush.setPassword(STKPush.getPassword(Config.BUSINESS_SHORT_CODE, Config.PASSKEY, STKPush.getTimestamp()));
        stkPush.setTimestamp(STKPush.getTimestamp());
        stkPush.setTransactionType(Transaction.CUSTOMER_PAY_BILL_ONLINE);
        stkPush.setAmount(amount);
        stkPush.setPartyA(STKPush.sanitizePhoneNumber(phone_number));
        stkPush.setPartyB(Config.PARTYB);
        stkPush.setPhoneNumber(STKPush.sanitizePhoneNumber(phone_number));
        stkPush.setCallBackURL(Config.CALLBACKURL);
        stkPush.setAccountReference("E-Funeral");
        stkPush.setTransactionDesc("Making payment to E-Funeral");

        mpesa.startStkPush(token, stkPush, new STKListener() {
            @Override
            public void onResponse(STKPushResponse stkPushResponse) {
                Log.e(TAG, "onResponse: " + stkPushResponse.toJson(stkPushResponse));
                String message = "Please enter your pin to complete transaction";
                sweetAlertDialog.changeAlertType(SweetAlertDialog.SUCCESS_TYPE);
                sweetAlertDialog.setTitleText("Transaction started");
                sweetAlertDialog.setContentText(message);
                SavetoDatabase();

            }






            @Override
            public void onError(Throwable throwable) {
                Log.e(TAG, "stk onError: " + throwable.getMessage());
                sweetAlertDialog.changeAlertType(SweetAlertDialog.ERROR_TYPE);
                sweetAlertDialog.setTitleText("Error");
                sweetAlertDialog.setContentText(throwable.getMessage());
            }
        });
        //add firebase here

    }

    private void SavetoDatabase() {
        Calendar calendar = Calendar.getInstance();

        SimpleDateFormat currentDate = new SimpleDateFormat("MMM dd, yyyy");
        saveCurrentDate = currentDate.format(calendar.getTime());

        SimpleDateFormat currentTime = new SimpleDateFormat("HH:mm:ss a");
        saveCurrentTime = currentTime.format(calendar.getTime());

        paymentRandomKey = saveCurrentDate + saveCurrentTime;
        HashMap<String, Object> paymentMap = new HashMap<>();
        paymentMap.put("pid", paymentRandomKey);
        paymentMap.put("date", saveCurrentDate);
        paymentMap.put("time", saveCurrentTime);
        paymentMap.put("name", names);
        paymentMap.put("phonenumber",  phone_number);
        paymentMap.put("amountpaid", amount);


        paymentRef.child(paymentRandomKey).updateChildren(paymentMap)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task)
                    {
                        if (task.isSuccessful())
                        {
                           // Intent intent = new Intent(MainActivity.this, MainActivity2.class);
                           // startActivity(intent);


                            Toast.makeText(MainActivity.this, "Enter your MPESA pin to complete payment", Toast.LENGTH_SHORT).show();
                        }
                        else
                        {

                            String message = task.getException().toString();
                            Toast.makeText(MainActivity.this, "Error: " + message, Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    @Override
    public void OnTokenError(Throwable throwable) {
        Log.e(TAG, "mpesa Error: " + throwable.getMessage());
        sweetAlertDialog.changeAlertType(SweetAlertDialog.ERROR_TYPE);
        sweetAlertDialog.setTitleText("Error");
        sweetAlertDialog.setContentText(throwable.getMessage());
    }

}
