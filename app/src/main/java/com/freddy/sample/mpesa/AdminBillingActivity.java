package com.freddy.sample.mpesa;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
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
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.paypal.android.sdk.payments.PayPalConfiguration;
import com.paypal.android.sdk.payments.PayPalFuturePaymentActivity;
import com.paypal.android.sdk.payments.PayPalPayment;
import com.paypal.android.sdk.payments.PayPalService;
import com.paypal.android.sdk.payments.PaymentActivity;
import com.paypal.android.sdk.payments.PaymentConfirmation;

import org.json.JSONException;

import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Random;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class AdminBillingActivity extends AppCompatActivity implements TokenListener {
    EditText cname, cnumber, cstartdate, cendate,camount,totalAmountbalance;
    String bnames, bnumbers, bstarting, sdate, edate,yname,pfone,randpayment,saveCurrentDate,saveCurrentTime;
    String dayDifference = "null";
    TextView txtdays;
    Button mp,payp,payp2;
    //public static final String TAG = MainActivity.class.getSimpleName();
    public static final String TAG = AdminBillingActivity.class.getSimpleName();
     public static int TotalAmount = 10;
     static  int constantvalue=0;
    private String paymentRandomKey;
    private SweetAlertDialog sweetAlertDialog;
    private Mpesa mpesa;
    private String valueTobepaid,valuePaid,valueBalance,phone_number, names;
    private DatabaseReference userRef, requestRef,paymentAdminRef,morticiandeceasedref;
    private FirebaseAuth mAuth;
    private String  currentUserID,currentUserName,currentUserPhone;
    private int PAYPAL_REQ_CODE=999;
    private static final int REQUEST_CODE_FUTURE_PAYMENT = 2;
    private static PayPalConfiguration config=new PayPalConfiguration().environment(PayPalConfiguration.ENVIRONMENT_SANDBOX).clientId(PaypalClientIDConfigClass.PAYPAL_CLIENT_ID);

    private String amount,totalAmounts,totalduration,startdate,enddate,deceasedgenders,deceasednames,reqids, reqnumbers,currentuser,reqrandomnumbers;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_billing);
      mAuth = FirebaseAuth.getInstance();
        currentUserID = mAuth.getCurrentUser().getUid();
//        userRef = FirebaseDatabase.getInstance().getReference().child("User");
        userRef = FirebaseDatabase.getInstance().getReference().child("morticians");
//        requestRef = FirebaseDatabase.getInstance().getReference().child("funeral view");
            paymentAdminRef = FirebaseDatabase.getInstance().getReference().child("funeral requests payments admin");
        morticiandeceasedref = FirebaseDatabase.getInstance().getReference().child("funeral requests payments mortician").child(currentUserID).child("funeral payments");
            //paymentRef = FirebaseDatabase.getInstance().getReference().child("funeralpayments").child(currentUserID).child("funeral requests payments");

            cname = (EditText) findViewById(R.id.billingbereavednameET);
            cnumber = (EditText) findViewById(R.id.billigbereavedphoneET);
            cstartdate = (EditText) findViewById(R.id.billingstartdateET);
            cendate = (EditText) findViewById(R.id.billingevdadateET);
            camount = (EditText) findViewById(R.id.billingtotalamountET);
            txtdays = (TextView) findViewById(R.id.biilingtotalpreservationdays);
            mp = (Button) findViewById(R.id.billingbtnmpesa);
            payp = (Button) findViewById(R.id.billingbtnpaypal);
       // payp2 = (Button) findViewById(R.id.billingbtnpaypal2);
            bnames = getIntent().getStringExtra("bn");
            bnumbers = getIntent().getStringExtra("bno");
            bstarting = getIntent().getStringExtra("st");
            deceasednames = getIntent().getStringExtra("dn");
            deceasedgenders = getIntent().getStringExtra("dg");
            reqnumbers = getIntent().getStringExtra("rn");
            startdate = getIntent().getStringExtra("st");
            edate = cendate.getText().toString();

            cname.setText(bnames);
            cnumber.setText(bnumbers);
            cstartdate.setText(bstarting);
            amount = camount.getText().toString();
        names= cname.getText().toString();
        phone_number = cnumber.getText().toString();
        amount = camount.getText().toString();
            //  valueBalance=totalAmountbalance.getText().toString();
            final Calendar myCalendar = Calendar.getInstance();
            final DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {

                @Override
                public void onDateSet(DatePicker view, int year, int monthOfYear,
                                      int dayOfMonth) {
                    // TODO Auto-generated method stub
                    myCalendar.set(Calendar.YEAR, year);
                    myCalendar.set(Calendar.MONTH, monthOfYear);
                    myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                    String myFormat = "MM/dd/yy"; //In which you need put here
                    SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

                    cendate.setText(sdf.format(myCalendar.getTime()));


                }
            };
            cendate.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    new DatePickerDialog(AdminBillingActivity.this, date, myCalendar
                            .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                            myCalendar.get(Calendar.DAY_OF_MONTH)).show();

                }
            });
            mpesa = new Mpesa(Config.CONSUMER_KEY, Config.CONSUMER_SECRET, Mode.SANDBOX);

            sweetAlertDialog = new SweetAlertDialog(this, SweetAlertDialog.PROGRESS_TYPE);
            sweetAlertDialog.setTitleText("Connecting to Safaricom");
            sweetAlertDialog.setContentText("Please wait...");
            sweetAlertDialog.setCancelable(false);
        Intent intent=new Intent(AdminBillingActivity.this, PayPalService.class);
        intent.putExtra(PayPalService.EXTRA_PAYPAL_CONFIGURATION,config);
        startService(intent);


            mp.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {


                    check();
                }


//

            });
//            payp2.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//                    PaypalPaymentMethod();
//                }
//            });
            payp.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    phone_number = cnumber.getText().toString();
                    amount = camount.getText().toString();
                    names= cname.getText().toString();

                    if (phone_number.isEmpty()) {
                        Toast.makeText(AdminBillingActivity.this, "Phone Number is required", Toast.LENGTH_SHORT).show();
                        return;
                    }

                    if (amount.isEmpty()) {
                        Toast.makeText(AdminBillingActivity.this, "Amount is required", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    if (names.isEmpty()) {
                        Toast.makeText(AdminBillingActivity.this, "Your Name is required", Toast.LENGTH_SHORT).show();
                        return;
                    }



                    if (!phone_number.isEmpty() && !amount.isEmpty()) {
                        try {
//                            int valuenumber1=Integer.parseInt(amount);
//                            int valuenumber2=Integer.parseInt(totalAmounttoBepaid);
//                            if(valuenumber1!=valuenumber2){
//                                Toast.makeText(MainActivity.this, "you need to pay the exact total order amount ", Toast.LENGTH_SHORT).show();
                            sweetAlertDialog.show();
                            mpesa.getToken(AdminBillingActivity.this);}



                        catch (UnsupportedEncodingException e) {
                            Log.e(TAG, "UnsupportedEncodingException: " + e.getLocalizedMessage());
                        }
                    } else {
                        Toast.makeText(AdminBillingActivity.this, "Please make sure that phone number and amount is not empty ", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        displaySpecificCurrentUserInfo();


        }

    private void displaySpecificCurrentUserInfo() {
        userRef.child(currentUserID).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()){
                    currentUserName=dataSnapshot.child("morticianname").getValue().toString();
                    //currentUserPhone=dataSnapshot.child("Phone").getValue().toString();
//                    yourname.setText(currentUserName);
//                    phone.setText(currentUserPhone);


                }


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

//    private void PaypalPaymentMethod() {
//        amount = camount.getText().toString();
//        phone_number = cnumber.getText().toString();
//        names= cname.getText().toString();
//        if (phone_number.isEmpty()) {
//            Toast.makeText(AdminBillingActivity.this, "Phone Number is required", Toast.LENGTH_SHORT).show();
//            return;
//        }
//
//        if (amount.isEmpty()) {
//            Toast.makeText(AdminBillingActivity.this, "Amount is required", Toast.LENGTH_SHORT).show();
//            return;
//        }
//        if (names.isEmpty()) {
//            Toast.makeText(AdminBillingActivity.this, "Your Name is required", Toast.LENGTH_SHORT).show();
//            return;
//        }
//
//
//        else {
//            PayPalPayment payment = new PayPalPayment(new BigDecimal(amount), "USD", "Payment For Deceased Body Preservation", PayPalPayment.PAYMENT_INTENT_SALE);
//            Intent intent = new Intent(this, PaymentActivity.class);
//            intent.putExtra(PayPalService.EXTRA_PAYPAL_CONFIGURATION, config);
//            intent.putExtra(PaymentActivity.EXTRA_PAYMENT, payment);
//            startActivityForResult(intent, PAYPAL_REQ_CODE);
//        }
//    }

    private void check() {
        yname =  cname.getText().toString();
        pfone = cnumber.getText().toString();
        sdate = cstartdate.getText().toString();
        edate= cendate.getText().toString();
        if (TextUtils.isEmpty(yname)) {
            Toast.makeText(AdminBillingActivity.this, "Please provide your name", Toast.LENGTH_SHORT).show();

        } else if (TextUtils.isEmpty(pfone)) {
            Toast.makeText(AdminBillingActivity.this, "Please provide your phone number", Toast.LENGTH_SHORT).show();

        }  else if (TextUtils.isEmpty(sdate)) {
            Toast.makeText(AdminBillingActivity.this, "Please provide the start preservation date", Toast.LENGTH_SHORT).show();

        }
        else if (TextUtils.isEmpty(edate)) {

            Toast.makeText(AdminBillingActivity.this, "Please provide end of preservation day", Toast.LENGTH_SHORT).show();

        }
        else {


            gettingdatedifference();

        }
    }

    private void gettingdatedifference() {
        try {
            sdate = cstartdate.getText().toString();
            edate = cendate.getText().toString();
            Date date1;
            Date date2;
            // boolean b = false;
            SimpleDateFormat dates = new SimpleDateFormat("MM/dd/yyyy");
            date1 = dates.parse(sdate);
            date2 = dates.parse(edate);

            if (dates.parse(sdate).before(dates.parse(edate))) {
                 long difference = Math.abs(date1.getTime() - date2.getTime());
                 long differenceDates = difference / (24 * 60 * 60 * 1000);
                dayDifference = Long.toString(differenceDates);
                txtdays.setText("Deceased was preserved for " + dayDifference + " days,");
                txtdays.setVisibility(View.VISIBLE);
               final int  oneTotalAmount = TotalAmount * Integer.valueOf(dayDifference);
                // TotalAmount=oneTotalAmount;

                constantvalue =  oneTotalAmount;
                camount.setText(String.valueOf(constantvalue));
            } else if (dates.parse(sdate).equals(dates.parse(edate))) {
                Toast.makeText(AdminBillingActivity.this, "start date cannot be equal to end date", Toast.LENGTH_LONG).show();
                // b = true;//If two dates are equal
            }


        } catch (Exception e) {
            e.printStackTrace();
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
                SavetoAdminDatabase();

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

    private void SavetoAdminDatabase() {
        try {
            sdate = cstartdate.getText().toString();
            edate = cendate.getText().toString();
            Date date1;
            Date date2;
            // boolean b = false;
            SimpleDateFormat dates = new SimpleDateFormat("MM/dd/yyyy");
            date1 = dates.parse(sdate);
            date2 = dates.parse(edate);

            if (dates.parse(sdate).before(dates.parse(edate))) {
                long difference = Math.abs(date1.getTime() - date2.getTime());
                long differenceDates = difference / (24 * 60 * 60 * 1000);
                dayDifference = Long.toString(differenceDates);
//                txtdays.setText("Deceased was preserved for " + dayDifference + " days,");
//                txtdays.setVisibility(View.VISIBLE);
//                int oneTotalAmount = TotalAmount * Integer.valueOf(dayDifference);
//                // TotalAmount=oneTotalAmount;
//
//                constantvalue = constantvalue + oneTotalAmount;
//                camount.setText(String.valueOf(constantvalue));
            } else if (dates.parse(sdate).equals(dates.parse(edate))) {
                Toast.makeText(AdminBillingActivity.this, "start date cannot be equal to end date", Toast.LENGTH_LONG).show();
                // b = true;//If two dates are equal
            }


        } catch (Exception e) {
            e.printStackTrace();
        }
        Random rand=new Random();
        int randNum = rand.nextInt(10000)+15;
        randpayment=String.valueOf(randNum);
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
        paymentMap.put("deceasedfullnames", deceasednames);
        paymentMap.put("deceasedgenders", deceasedgenders);
        paymentMap.put("preservationend", edate);
        paymentMap.put("preservationstart",  startdate);
        paymentMap.put("preservationduration", dayDifference);
        paymentMap.put("amounttobepaid", amount);
        paymentMap.put("paymentnumber", randpayment);
        paymentMap.put("billedby", currentUserName);
        paymentMap.put("rnumber", reqnumbers);
        paymentMap.put("balance", "0");



        paymentAdminRef.child(paymentRandomKey).updateChildren(paymentMap)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task)
                    {
                        if (task.isSuccessful())
                        {
//                            FirebaseDatabase.getInstance().getReference().child("funeral view").child(currentUserID).child("funeral requests").removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
//                                @Override
//                                public void onComplete(@NonNull Task<Void> task) {
//                                    Intent intent = new Intent(funeralhomepayment.this, requestsummary.class);
//                                    intent.putExtra("Total Paid", String.valueOf(amount));
//                                    intent.putExtra("Total Balance", String.valueOf(valueBalance));
//                                    startActivity(intent);
//
//                                }
//                            });



                            Toast.makeText(AdminBillingActivity.this, "Enter your MPESA pin to complete payment", Toast.LENGTH_SHORT).show();
                        }
                        else
                        {

                            String message = task.getException().toString();
                            Toast.makeText(AdminBillingActivity.this, "Error: " + message, Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    private void SavetoDatabase() {
        try {
            sdate = cstartdate.getText().toString();
            edate = cendate.getText().toString();
            Date date1;
            Date date2;
            // boolean b = false;
            SimpleDateFormat dates = new SimpleDateFormat("MM/dd/yyyy");
            date1 = dates.parse(sdate);
            date2 = dates.parse(edate);

            if (dates.parse(sdate).before(dates.parse(edate))) {
                long difference = Math.abs(date1.getTime() - date2.getTime());
                long differenceDates = difference / (24 * 60 * 60 * 1000);
                dayDifference = Long.toString(differenceDates);
//                txtdays.setText("Deceased was preserved for " + dayDifference + " days,");
//                txtdays.setVisibility(View.VISIBLE);
//                int oneTotalAmount = TotalAmount * Integer.valueOf(dayDifference);
//                // TotalAmount=oneTotalAmount;
//
//                constantvalue = constantvalue + oneTotalAmount;
//                camount.setText(String.valueOf(constantvalue));
            } else if (dates.parse(sdate).equals(dates.parse(edate))) {
                Toast.makeText(AdminBillingActivity.this, "start date cannot be equal to end date", Toast.LENGTH_LONG).show();
                // b = true;//If two dates are equal
            }


        } catch (Exception e) {
            e.printStackTrace();
        }
        Random rand=new Random();
        int randNum = rand.nextInt(10000)+15;
        randpayment=String.valueOf(randNum);
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
        paymentMap.put("deceasedfullnames", deceasednames);
        paymentMap.put("deceasedgenders", deceasedgenders);
        paymentMap.put("preservationend", edate);
        paymentMap.put("preservationstart",  startdate);
        paymentMap.put("preservationduration", dayDifference);
        paymentMap.put("amounttobepaid", amount);
        paymentMap.put("paymentnumber", randpayment);
        paymentMap.put("rnumber", reqnumbers);
        paymentMap.put("balance", "0");



        morticiandeceasedref.child(paymentRandomKey).updateChildren(paymentMap)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task)
                    {
                        if (task.isSuccessful())
                        {
//                            FirebaseDatabase.getInstance().getReference().child("funeral view").child(currentUserID).child("funeral requests").removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
//                                @Override
//                                public void onComplete(@NonNull Task<Void> task) {
//                                    Intent intent = new Intent(funeralhomepayment.this, requestsummary.class);
//                                    intent.putExtra("Total Paid", String.valueOf(amount));
//                                    intent.putExtra("Total Balance", String.valueOf(valueBalance));
//                                    startActivity(intent);
//
//                                }
//                            });



                              Toast.makeText(AdminBillingActivity.this, "Enter your MPESA pin to complete payment", Toast.LENGTH_SHORT).show();
                        }
                        else
                        {

                            String message = task.getException().toString();
                            Toast.makeText(AdminBillingActivity.this, "Error: " + message, Toast.LENGTH_SHORT).show();
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
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode == PAYPAL_REQ_CODE) {
            if (resultCode == Activity.RESULT_OK) {
                PaymentConfirmation confirm = data
                        .getParcelableExtra(PaymentActivity.EXTRA_RESULT_CONFIRMATION);
                if (confirm != null) {
                    try {
                        System.out.println(confirm.toJSONObject().toString(4));
                        System.out.println(confirm.getPayment().toJSONObject()
                                .toString(4));


                        Toast.makeText(getApplicationContext(), "Payment Made Successfully",
                                Toast.LENGTH_LONG).show();
                        SavetoDatabase();

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            } else if (resultCode == Activity.RESULT_CANCELED) {
                System.out.println("The user canceled.");
            } else if (resultCode == PaymentActivity.RESULT_EXTRAS_INVALID) {
                System.out
                        .println("An invalid Payment or PayPalConfiguration was submitted. Please see the docs.");
            }

        } else if (resultCode == Activity.RESULT_CANCELED) {
            Log.i("FuturePaymentExample", "The user canceled.");
        } else if (resultCode == PayPalFuturePaymentActivity.RESULT_EXTRAS_INVALID) {
            Log.i("FuturePaymentExample",
                    "Probably the attempt to previously start the PayPalService had an invalid PayPalConfiguration. Please see the docs.");
        }
    }
}

