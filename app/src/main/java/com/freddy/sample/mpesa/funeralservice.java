package com.freddy.sample.mpesa;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Random;

public class funeralservice extends AppCompatActivity {
    EditText yourname,phone,deceasedname,startdate,enddate;
    RadioGroup radioGroup;
    RadioButton male,female,radioButton;
    Button book;
    TextView txtamount;
    String yname,pfone,dname,sdate,edate,deceasedRandomKey,saveCurrentDate, saveCurrentTime,currentUserID,currentUserName,currentUserPhone,gender,amount,randreq;
    private DatabaseReference admindeceasedRef,userRef,  morticiandeceasedref;
    private FirebaseAuth mAuth;
    Date date1;
    Date date2;
   // viewfuneralrequests viewfuneralrequests;
    String dayDifference="null";




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_funeralservice);
        try {
            mAuth = FirebaseAuth.getInstance();
            currentUserID = mAuth.getCurrentUser().getUid();
            userRef = FirebaseDatabase.getInstance().getReference().child("morticians");
            admindeceasedRef = FirebaseDatabase.getInstance().getReference().child("funeral admin Requests");
            morticiandeceasedref = FirebaseDatabase.getInstance().getReference().child("funeral mortician Requests").child(currentUserID).child("funeral requests");
            yourname = (EditText) findViewById(R.id.bereaved_name);
            phone = (EditText) findViewById(R.id.bereaved_phone);
            deceasedname = (EditText) findViewById(R.id.deceased_names);
            startdate = (EditText) findViewById(R.id.startdate_booking);
            // enddate=(EditText)findViewById(R.id.enddate_booking);

            radioGroup = (RadioGroup) findViewById(R.id.radiogender);
            male = (RadioButton) findViewById(R.id.radiomale);
            female = (RadioButton) findViewById(R.id.radiofemale);
            book = (Button) findViewById(R.id.deceased_booking_btn);
            txtamount = (TextView) findViewById(R.id.txttotalamount);
            //viewfuneralrequests=new viewfuneralrequests();
            amount = getIntent().getStringExtra("Total Amount");
            // amount=viewfuneralrequests.TotalAmount;
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

                    startdate.setText(sdf.format(myCalendar.getTime()));


                }
            };
            final Calendar myCalendar2 = Calendar.getInstance();
            final DatePickerDialog.OnDateSetListener Date = new DatePickerDialog.OnDateSetListener() {

                @Override
                public void onDateSet(DatePicker view, int year, int monthOfYear,
                                      int dayOfMonth) {
                    // TODO Auto-generated method stub
                    myCalendar2.set(Calendar.YEAR, year);
                    myCalendar2.set(Calendar.MONTH, monthOfYear);
                    myCalendar2.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                    String myFormat = "MM/dd/yy"; //In which you need put here
                    SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);


                    enddate.setText(sdf.format(myCalendar2.getTime()));

                }
            };
            // displaySpecificCurrentUserInfo();
            book.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    check();

                }
            });
            startdate.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    new DatePickerDialog(funeralservice.this, date, myCalendar
                            .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                            myCalendar.get(Calendar.DAY_OF_MONTH)).show();

                }
            });
//        enddate.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                new DatePickerDialog(funeralservice.this, Date, myCalendar2
//                        .get(Calendar.YEAR), myCalendar2.get(Calendar.MONTH),
//                        myCalendar2.get(Calendar.DAY_OF_MONTH)).show();
//
//            }
//        });
            // int selectedGender=radioGroup.getCheckedRadioButtonId();
            // radioButton=findViewById(selectedGender);
            // gender=radioButton.getText().toString().trim();
            radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                                                      @Override
                                                      public void onCheckedChanged(RadioGroup group, int checkedId) {
                                                          radioButton = (RadioButton) findViewById(checkedId);
                                                          gender = radioButton.getText().toString();
                                                          Toast.makeText(getBaseContext(), radioButton.getText(), Toast.LENGTH_SHORT).show();
                                                      }
                                                  }
            );
        }catch (Exception e){
            e.printStackTrace();
        }
        displaySpecificCurrentUserInfo();





    }

    private void check() {
        try{
        yname = yourname.getText().toString();
        pfone = phone.getText().toString();
        dname = deceasedname.getText().toString();
        sdate = startdate.getText().toString();
        // edate= enddate.getText().toString();
        if (TextUtils.isEmpty(yname)) {
            Toast.makeText(funeralservice.this, "Please provide your name", Toast.LENGTH_SHORT).show();

        } else if (TextUtils.isEmpty(pfone)) {
            Toast.makeText(funeralservice.this, "Please provide your phone number", Toast.LENGTH_SHORT).show();

        } else if (TextUtils.isEmpty(dname)) {
            Toast.makeText(funeralservice.this, "Please provide the deceased full Name", Toast.LENGTH_SHORT).show();

        } else if (TextUtils.isEmpty(sdate)) {
            Toast.makeText(funeralservice.this, "Please provide the start preservation date", Toast.LENGTH_SHORT).show();

        }
//        else if (TextUtils.isEmpty(edate)) {
//            saverequesttodatabasewithnoedate();
//            saverequesttoadminwithnoedate();
//            //Toast.makeText(funeralservice.this, "Please provide the end preservation date", Toast.LENGTH_SHORT).show();
//
//        }
        else {

            submitdeceasedBooking();
        }
    }catch(Exception e){
        e.printStackTrace();
    }
    }

//    private void saverequesttoadminwithnoedate() {
//        Random rand=new Random();
//        int randNum = rand.nextInt(10000)+15;
//        randreq=String.valueOf(randNum);
//        Calendar calendar = Calendar.getInstance();
//
//        SimpleDateFormat currentDate = new SimpleDateFormat("MMM dd, yyyy");
//        saveCurrentDate = currentDate.format(calendar.getTime());
//
//        SimpleDateFormat currentTime = new SimpleDateFormat("HH:mm:ss a");
//        saveCurrentTime = currentTime.format(calendar.getTime());
//        deceasedRandomKey = saveCurrentDate + saveCurrentTime;
//        HashMap<String, Object> deceasedMap = new HashMap<>();
//        deceasedMap.put("did", deceasedRandomKey);
//        deceasedMap.put("bereavedname",yname) ;
//        deceasedMap.put("phonenumber", pfone);
//        deceasedMap.put("startdate", sdate);
//        deceasedMap.put("deceasedgender", gender);
//        deceasedMap.put("enddate", "no value");
//        deceasedMap.put("durationindays","no value" );
//        deceasedMap.put("deceasedname", dname);
//
//        deceasedMap.put("currentdate", saveCurrentDate);
//        deceasedMap.put("currenttime", saveCurrentTime);
//        deceasedMap.put("rqn", randreq);
//        //ordersMap.put("state", "not shipped");
//
//        admindeceasedref.child(deceasedRandomKey).updateChildren(deceasedMap).addOnCompleteListener(new OnCompleteListener<Void>() {
//            @Override
//            public void onComplete(@NonNull Task<Void> task) {
//                if (task.isSuccessful()) {
//                    //lest out products child
//
//
//                    Toast.makeText(funeralservice.this, "Your Request has been made Successfully", Toast.LENGTH_LONG).show();
//                    Intent intent = new Intent(funeralservice.this, MainActivity2.class);
//                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
//                    startActivity(intent);
//                    finish();
//                }
//
//            }
//        });
//    }

//    private void saverequesttodatabasewithnoedate() {
//        Random rand=new Random();
//        int randNum = rand.nextInt(10000)+15;
//        randreq=String.valueOf(randNum);
//        Calendar calendar = Calendar.getInstance();
//
//        SimpleDateFormat currentDate = new SimpleDateFormat("MMM dd, yyyy");
//        saveCurrentDate = currentDate.format(calendar.getTime());
//
//        SimpleDateFormat currentTime = new SimpleDateFormat("HH:mm:ss a");
//        saveCurrentTime = currentTime.format(calendar.getTime());
//        deceasedRandomKey = saveCurrentDate + saveCurrentTime;
//        HashMap<String, Object> deceasedMap = new HashMap<>();
//        deceasedMap.put("did", deceasedRandomKey);
//        deceasedMap.put("bereavedname",yname) ;
//        deceasedMap.put("phonenumber", pfone);
//        deceasedMap.put("startdate", sdate);
//        deceasedMap.put("deceasedgender", gender);
//        deceasedMap.put("enddate", "no value");
//        deceasedMap.put("durationindays","no value" );
//        deceasedMap.put("deceasedname", dname);
//        deceasedMap.put("rqn", randreq);
//        deceasedMap.put("currentdate", saveCurrentDate);
//        deceasedMap.put("currenttime", saveCurrentTime);
//        //ordersMap.put("state", "not shipped");
//
//        deceasedRef.child(currentUserID).child("funeral requests").child(deceasedRandomKey).updateChildren(deceasedMap).addOnCompleteListener(new OnCompleteListener<Void>() {
//            @Override
//            public void onComplete(@NonNull Task<Void> task) {
//                if (task.isSuccessful()) {
//                    //lest out products child
//
//
//                    Toast.makeText(funeralservice.this, "Your Request has been made Successfully", Toast.LENGTH_LONG).show();
//                    Intent intent = new Intent(funeralservice.this, MainActivity2.class);
//                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
//                    startActivity(intent);
//                    finish();
//                }
//
//            }
//        });
//    }

    private void submitdeceasedBooking()  {
        Calendar calendar = Calendar.getInstance();

        SimpleDateFormat currentDate = new SimpleDateFormat("MMM dd, yyyy");
        saveCurrentDate = currentDate.format(calendar.getTime());

        SimpleDateFormat currentTime = new SimpleDateFormat("HH:mm:ss a");
        saveCurrentTime = currentTime.format(calendar.getTime());
        deceasedRandomKey = saveCurrentDate + saveCurrentTime;
        saverequesttomortician();
        saverequesttoadmin();
//        if(TextUtils.isEmpty(edate)){
//            saverequesttodatabase();
//            saverequesttoadmin();
//
//
//        }
//        else{
//            callthismethodifenddateexists();
//        }


//        public static boolean isDateAfter(String startDate,String endDate)
//        {
//            try
//            {
//                String myFormatString = "yyyy-M-dd"; // for example
//                SimpleDateFormat df = new SimpleDateFormat(myFormatString);
//                Date date1 = df.parse(endDate));
//                Date startingDate = df.parse(startDate);
//
//                if (date1.after(startingDate))
//                    return true;
//                else
//                    return false;
//            }
//            catch (Exception e)
//            {
//
//                return false;
//            }
//        }




    }

    private void saverequesttoadmin() {
        Random rand=new Random();
        int randNum = rand.nextInt(10000)+15;
        randreq=String.valueOf(randNum);
        HashMap<String, Object> deceasedMap = new HashMap<>();
        deceasedMap.put("did", deceasedRandomKey);
        deceasedMap.put("bereavedname",yname) ;
        deceasedMap.put("phonenumber", pfone);
        deceasedMap.put("startdate", sdate);
        deceasedMap.put("deceasedgender", gender);
        // deceasedMap.put("enddate", edate);
        //deceasedMap.put("durationindays",dayDifference );
        deceasedMap.put("deceasedname", dname);
       deceasedMap.put("admittedby",currentUserName );

        deceasedMap.put("currentdate", saveCurrentDate);
        deceasedMap.put("currenttime", saveCurrentTime);
        deceasedMap.put("rqn", randreq);
        //ordersMap.put("state", "not shipped");

        admindeceasedRef.child(deceasedRandomKey).updateChildren(deceasedMap).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    //lest out products child


                    Toast.makeText(funeralservice.this, "Your Request has been made Successfully", Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(funeralservice.this, MainActivity2.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);
                    finish();
                }

            }
        });
    }

//    private void callthismethodifenddateexists() {
//        try {
//
//            Date date1;
//            Date date2;
//            boolean b = false;
//            SimpleDateFormat dates = new SimpleDateFormat("MM/dd/yyyy");
//            date1 = dates.parse(sdate);
//            date2 = dates.parse(edate);
//
//            if(dates.parse(sdate).before(dates.parse(edate)))
//            {
//                long difference = Math.abs(date1.getTime() - date2.getTime());
//                long differenceDates = difference / (24 * 60 * 60 * 1000);
//                dayDifference = Long.toString(differenceDates);
//                txtamount.setText("The preservation duration will be " + dayDifference + " days,");
//                txtamount.setVisibility(View.VISIBLE);
//                Random rand=new Random();
//                int randNum = rand.nextInt(10000)+15;
//                randreq=String.valueOf(randNum);
//
//
//                saverequesttodatabase();
//                saverequesttoadmin();
//                // b = true;//If start date is before end date
//
//            }
//            else if(dates.parse(sdate).equals(dates.parse(edate)))
//            {
//                Toast.makeText(funeralservice.this,"start date cannot be equal to end date",Toast.LENGTH_LONG).show();
//                // b = true;//If two dates are equal
//            }
//            else
//            {
//                // b = false; //If start date is after the end date
//                Toast.makeText(funeralservice.this,"Start Date cannot be after end date ",Toast.LENGTH_LONG).show();
//            }
//
//        } catch (Exception exception) {
//            Toast.makeText(this, "Unable to find difference", Toast.LENGTH_SHORT).show();
//        }
//    }

    private void saverequesttomortician() {
        Random rand=new Random();
        int randNum = rand.nextInt(10000)+15;
        randreq=String.valueOf(randNum);
        HashMap<String, Object> deceasedMap = new HashMap<>();
        deceasedMap.put("did", deceasedRandomKey);
        deceasedMap.put("bereavedname",yname) ;
        deceasedMap.put("phonenumber", pfone);
        deceasedMap.put("startdate", sdate);
        deceasedMap.put("deceasedgender", gender);
       // deceasedMap.put("enddate", edate);
        //deceasedMap.put("durationindays",dayDifference );
        deceasedMap.put("deceasedname", dname);
       // deceasedMap.put("admittedby",currentUserName );

        deceasedMap.put("currentdate", saveCurrentDate);
        deceasedMap.put("currenttime", saveCurrentTime);
        deceasedMap.put("rqn", randreq);
        //ordersMap.put("state", "not shipped");

        morticiandeceasedref.child(deceasedRandomKey).updateChildren(deceasedMap).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    //lest out products child


                    Toast.makeText(funeralservice.this, "Your Request has been made Successfully", Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(funeralservice.this, MainActivity2.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);
                    finish();
                }

            }
        });
    }

//    private void saverequesttodatabase() {
//        HashMap<String, Object> deceasedMap = new HashMap<>();
//        deceasedMap.put("did", deceasedRandomKey);
//        deceasedMap.put("bereavedname",yname) ;
//        deceasedMap.put("phonenumber", pfone);
//        deceasedMap.put("startdate", sdate);
//        deceasedMap.put("deceasedgender", gender);
//        deceasedMap.put("enddate", edate);
//        deceasedMap.put("durationindays",dayDifference );
//        deceasedMap.put("deceasedname", dname);
//        deceasedMap.put("rqn", randreq);
//        deceasedMap.put("currentdate", saveCurrentDate);
//        deceasedMap.put("currenttime", saveCurrentTime);
//        //ordersMap.put("state", "not shipped");
//
//        deceasedRef.child(currentUserID).child("funeral requests").child(deceasedRandomKey).updateChildren(deceasedMap).addOnCompleteListener(new OnCompleteListener<Void>() {
//            @Override
//            public void onComplete(@NonNull Task<Void> task) {
//                if (task.isSuccessful()) {
//                    //lest out products child
//
//
//                    Toast.makeText(funeralservice.this, "Your Request has been made Successfully", Toast.LENGTH_LONG).show();
//                    Intent intent = new Intent(funeralservice.this, MainActivity2.class);
//                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
//                    startActivity(intent);
//                    finish();
//                }
//
//            }
//        });
//
//    }

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
}
