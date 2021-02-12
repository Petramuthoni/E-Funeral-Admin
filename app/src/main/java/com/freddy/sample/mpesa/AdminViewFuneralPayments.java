package com.freddy.sample.mpesa;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.graphics.pdf.PdfDocument;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.freddy.sample.mpesa.Model.funeralpayments;
import com.freddy.sample.mpesa.ViewHolder.AdminViewfpaymentViewHolder;

import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import static android.graphics.Typeface.DEFAULT;
import static android.graphics.Typeface.SANS_SERIF;

public class AdminViewFuneralPayments extends AppCompatActivity {
    private RecyclerView funeralpaymentRecyclerView ;
    RecyclerView.LayoutManager layoutManager;
    private DatabaseReference funeralpaymentRef,HearseRef,morticiandeceasedref;
    private StorageReference HearseImagesRef;
    EditText serching;
    String saveCurrentDate,saveCurrentTime, hearseRandomKey,downloadImageUrl;
    Bitmap bmp,scaledbmp;
    int pageWidth=1200;
    private Uri ImageUri;

    Date dateObj;
    SimpleDateFormat dateFormart;
    public static final String TAG = AdminViewFuneralPayments.class.getSimpleName();
    private ProgressDialog loadingBar;
    private FirebaseAuth mAuth;
    private String  currentUserID;

    private FirebaseRecyclerAdapter<funeralpayments, AdminViewfpaymentViewHolder> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_view_funeral_payments);
        mAuth = FirebaseAuth.getInstance();
        currentUserID = mAuth.getCurrentUser().getUid();
        HearseImagesRef = FirebaseStorage.getInstance().getReference().child("Invoices Images");
        HearseRef = FirebaseDatabase.getInstance().getReference().child("Invoices");
        funeralpaymentRef = FirebaseDatabase.getInstance().getReference().child("funeral requests payments admin");
        morticiandeceasedref = FirebaseDatabase.getInstance().getReference().child("funeral requests payments mortician").child(currentUserID).child("funeral payments");
        funeralpaymentRecyclerView = findViewById(R.id.recycler_funeralviewpaymentsrequest_menu);
        serching=(EditText)findViewById(R.id.inputserchingpayment);
        loadingBar = new ProgressDialog(this);

        funeralpaymentRecyclerView.setHasFixedSize(false);
        layoutManager = new LinearLayoutManager(this);
        funeralpaymentRecyclerView.setLayoutManager(layoutManager);
        bmp= BitmapFactory.decodeResource(getResources(),R.drawable.b5);
        scaledbmp=Bitmap.createScaledBitmap(bmp,1200,518,false);
        funeralpaymentRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                int sum=0;
//                for(DataSnapshot ds : dataSnapshot.getChildrenCount()){
//
//                }
                for( DataSnapshot ds :dataSnapshot.getChildren()) {
                    Map<String,Object> map=(Map<String,Object>) ds.getValue();
                    Object totalpayment=map.get("amountpaid");
                    int pValue=Integer.parseInt(String.valueOf(totalpayment));
                    sum += pValue;




                }


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        serching.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if(editable.toString()!=null){
                    onStart();

                }
                else{
                    Toast.makeText(AdminViewFuneralPayments.this,"enter some text first", Toast.LENGTH_SHORT).show();
                }


            }
        });

    }
    @Override
    protected void onStart() {
        super.onStart();
        Query query=morticiandeceasedref.orderByChild("rnumber").startAt(serching.getText().toString()).endAt(serching.getText().toString()+"\uf8ff");
        FirebaseRecyclerOptions<funeralpayments> options =
                new FirebaseRecyclerOptions.Builder<funeralpayments>()
                        .setQuery(query, funeralpayments.class)
                        .build();

        adapter =new FirebaseRecyclerAdapter<funeralpayments, AdminViewfpaymentViewHolder>(options) {


            @Override
            protected void onBindViewHolder(@NonNull  AdminViewfpaymentViewHolder holder, int position, @NonNull final funeralpayments model) {
               // public TextView payName, payPhoneNumber,payTotalAmount,payDateTime,paidTotalAmount,payTotalBalance;
                holder.payName.setText("P.No: " + model.getPaymentnumber()+" "+"Name"+model.getName() );
                holder. payPhoneNumber.setText("Phone Number: " + model.getPhonenumber() );
                holder.payTotalAmount.setText("Amount To Be Paid: " + model.getAmounttobepaid() );
                holder.paidTotalAmount.setText("Amount Paid: " + model.getAmountpaid()+" "+" for Admission Number"+model.getRnumber() );
                holder.payTotalBalance.setText("Balance: " + model.getBalance() );
               holder.payDateTime.setText("Paid at: " + model.getDate() + " " + model.getTime() );
//                holder.showi.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View view) {
//                        Calendar calendar = Calendar.getInstance();
//                        SimpleDateFormat currentDate = new SimpleDateFormat("MMM dd, yyyy");
//                        saveCurrentDate = currentDate.format(calendar.getTime());
//
//                        SimpleDateFormat currentTime = new SimpleDateFormat("HH:mm:ss a");
//                        saveCurrentTime = currentTime.format(calendar.getTime());
//                        dateObj=new Date();
//                        dateFormart=new SimpleDateFormat("MMM dd, yyyy");
//                        PdfDocument myPdfDocument=new PdfDocument();
//                        Paint myPaint=new Paint();
//                        Paint titlepaint=new Paint();
//                        PdfDocument.PageInfo myPageInfo1=new PdfDocument.PageInfo.Builder(1200,2010,1).create();
//                        PdfDocument.Page myPage1=myPdfDocument.startPage(myPageInfo1);
//                        Canvas canvas=myPage1.getCanvas();
//                        canvas.drawBitmap(scaledbmp,0,0,myPaint);
//
//                        titlepaint.setTextAlign(Paint.Align.CENTER);
//                        titlepaint.setTypeface(Typeface.create(DEFAULT,Typeface.BOLD));
//                        titlepaint.setTextSize(100);
//                        canvas.drawText("E-Funeral Home",pageWidth/2,270,titlepaint);
//
//                        myPaint.setTextSize(30f);
//                        myPaint.setTextAlign(Paint.Align.RIGHT);
//                        canvas.drawText("Call: 0741 287 733",1160,40,myPaint);
//                        canvas.drawText("0753 881 695",1160 ,80,myPaint);
//
//                        titlepaint.setTextAlign(Paint.Align.CENTER);
//                        titlepaint.setTypeface(Typeface.create(DEFAULT,Typeface.BOLD_ITALIC));
//                        titlepaint.setTextSize(80);
//                        canvas.drawText("Invoice",pageWidth/2,400,titlepaint);
//
//                        myPaint.setTextAlign(Paint.Align.LEFT);
//                        titlepaint.setTextSize(80);
//                        myPaint.setColor(Color.BLACK);
//                        canvas.drawText("Bereaved Name:"+model.getName(),20,590,myPaint);
//                        canvas.drawText("Bereaved Phone Number:"+model.getPhonenumber(),20,640,myPaint);
//                        canvas.drawText("Deceased Name:"+model.getDeceasedfullnames(),20,690,myPaint);
//                        canvas.drawText("Deceased Name:"+model.getDeceasedgenders(),20,740,myPaint);
//
//                        myPaint.setTextAlign(Paint.Align.RIGHT);
//                        titlepaint.setTextSize(100);
//                        canvas.drawText("Invoice No.: "+model.getPaymentnumber(),pageWidth-20,590,myPaint);
//
//
//                        // canvas.drawText("Date "+dateFormart.format(dateObj),-20,640,myPaint);
//
//                        canvas.drawText("Date "+model.getDate(),-20,640,myPaint);
//
//
//                        canvas.drawText("Time "+model.getTime(),-20,690,myPaint);
//
//                        myPaint.setStyle(Paint.Style.STROKE);
//                        myPaint.setStrokeWidth(2);
//                        canvas.drawRect(20,790,pageWidth-20,860,myPaint);
//
//                        myPaint.setTextAlign(Paint.Align.LEFT);
//                        myPaint.setStyle(Paint.Style.FILL);
//                        canvas.drawText("S,No.",40,830,myPaint);
//                        canvas.drawText("Start Date",200,830,myPaint);
//                        canvas.drawText("End Date",400,830,myPaint);
//                        canvas.drawText("Duration day(s)",600,830,myPaint);
//                        canvas.drawText("Rate",870,830,myPaint);
//                        canvas.drawText("Total Amount.",1000,830,myPaint);
//
//                        canvas.drawLine(180,800,180,840,myPaint);
//                        canvas.drawLine(380,800,380,840,myPaint);
//                        canvas.drawLine(580,800,580,840,myPaint);
//                        canvas.drawLine(850,800,850,840,myPaint);
//                        canvas.drawLine(980,800,980,840,myPaint);
//
//                        canvas.drawText("1.",40,950,myPaint);
//                        canvas.drawText(model.getPreservationstart(),200,950,myPaint);
//                        canvas.drawText(model.getPreservationend(),400,950,myPaint);
//                        canvas.drawText(model.getPreservationduration(),600,950,myPaint);
//                        canvas.drawText("10",870,950,myPaint);
//                        canvas.drawText(model.getAmounttobepaid(),1000,950,myPaint);
//
//                        canvas.drawLine(580,1100,pageWidth-20,1100,myPaint);
//                        canvas.drawText("Amount Paid",700,1150,myPaint);
//                        canvas.drawText(":",870,1150,myPaint);
//                        myPaint.setTextAlign(Paint.Align.RIGHT);
//                        canvas.drawText(model.getAmountpaid(),1000,1150,myPaint);
//
//                        myPaint.setTextAlign(Paint.Align.LEFT);
//                        canvas.drawText("Balance",700,1250,myPaint);
//                        canvas.drawText(":",870,1250,myPaint);
//                        myPaint.setTextAlign(Paint.Align.RIGHT);
//                        canvas.drawText(model.getBalance(),1000,1250,myPaint);
//                        myPaint.setTextAlign(Paint.Align.LEFT);
//
//                        myPaint.setColor(Color.BLUE);
//                        canvas.drawRect(580,1300,pageWidth-20,1400,myPaint);
//
//                        myPaint.setColor(Color.WHITE);
//                        myPaint.setTextSize(50f);
//                        myPaint.setTextAlign(Paint.Align.LEFT);
//                        canvas.drawText("STATUS :  ",700,1385,myPaint);
//                        myPaint.setTextAlign(Paint.Align.RIGHT);
//                        canvas.drawText("APPROVED",pageWidth-40,1385,myPaint);
//
//
//                        myPaint.setTextAlign(Paint.Align.LEFT);
//                        myPaint.setTextSize(40f);
//                        myPaint.setTypeface(SANS_SERIF);
//                        myPaint.setColor(Color.BLACK);
//                        canvas.drawText("Thank you for trusting our services.Payment once made is not refundable.Do not misplace this invoice.",40,1500,myPaint);
//
//
//
//
//
//
//
//
//
//                        myPdfDocument.finishPage(myPage1);
//                        File file=new File(Environment.getExternalStorageDirectory(),"/Invoice.pdf");
//                        try{
//                            myPdfDocument.writeTo(new FileOutputStream(file));
//                            Toast.makeText(AdminViewFuneralPayments.this,"file saved successfully",Toast.LENGTH_LONG).show();
//
//                        }
//                        catch(IOException e){
//                            e.printStackTrace();
//                            Toast.makeText(AdminViewFuneralPayments.this, "fail", Toast.LENGTH_LONG).show();
//                        }
//                        myPdfDocument.close();
//
//
//                    }
//                });
                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        CharSequence options [] = new CharSequence[]{
                                "Delete",
                                "Generate Invoice",
                                "Cancel"

                        };
                        AlertDialog.Builder builder=new AlertDialog.Builder(AdminViewFuneralPayments.this);
                        builder.setTitle("Do You Want To Delete This Deceased Payment?");
                        builder.setItems(options, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                if (i == 0) {
                                    // String uID = getRef(position).getKey();
                                    // RemoveOrder(uID);
                                    //  ordersRef.removeValue();

                                    // */
//                                    funeralpaymentRef.child(model.getPid()).removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
//                                        @Override
//                                        public void onComplete(@NonNull Task<Void> task) {
//                                            if (task.isSuccessful()) {
//                                                Toast.makeText(AdminViewFuneralPayments.this, "Deceased Payment Deleted successfully", Toast.LENGTH_SHORT).show();
//                                                //Intent intent = new Intent(AdminNewOrdersActivity.this, MainActivity.class);
//                                                // startActivity(intent);
//                                            }
//
//                                        }
//                                    });
                                    morticiandeceasedref.child(model.getPid()).removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            if(task.isSuccessful()){
                                                funeralpaymentRef.child(model.getPid()).removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
                                                    @Override
                                                    public void onComplete(@NonNull Task<Void> task) {
                                                        if(task.isSuccessful()){
                                                            Toast.makeText(AdminViewFuneralPayments.this,"Deceased Payment Deleted successfully", Toast.LENGTH_SHORT).show();
                                                            //Intent intent=new Intent(AdminCheckFuneralRequests.this,MainActivity.class);
                                                            // intent.putExtra("pid",model.getPid());
                                                            //startActivity(intent);

                                                        }
                                                        else{
                                                            String message = task.getException().toString();
                                                            Toast.makeText(AdminViewFuneralPayments.this, "Error: " + message, Toast.LENGTH_LONG).show();
                                                        }

                                                    }
                                                });

                                            }
                                        }
                                    });
                                } else if (i == 1) {

                                    Calendar calendar = Calendar.getInstance();
                                    SimpleDateFormat currentDate = new SimpleDateFormat("MMM dd, yyyy");
                                    saveCurrentDate = currentDate.format(calendar.getTime());

                                    SimpleDateFormat currentTime = new SimpleDateFormat("HH:mm:ss a");
                                    saveCurrentTime = currentTime.format(calendar.getTime());
                                    hearseRandomKey = saveCurrentDate + saveCurrentTime;
                                    dateObj = new Date();
                                    dateFormart = new SimpleDateFormat("MMM dd, yyyy");
                                    PdfDocument myPdfDocument = new PdfDocument();
                                    Paint myPaint = new Paint();
                                    Paint titlepaint = new Paint();
                                    PdfDocument.PageInfo myPageInfo1 = new PdfDocument.PageInfo.Builder(1200, 2010, 1).create();
                                    PdfDocument.Page myPage1 = myPdfDocument.startPage(myPageInfo1);
                                    Canvas canvas = myPage1.getCanvas();
                                    canvas.drawBitmap(scaledbmp, 0, 0, myPaint);

                                    titlepaint.setTextAlign(Paint.Align.CENTER);
                                    titlepaint.setTypeface(Typeface.create(DEFAULT, Typeface.BOLD));
                                    titlepaint.setTextSize(100);
                                    canvas.drawText("E-Funeral Home", pageWidth / 2, 270, titlepaint);

                                    myPaint.setTextSize(30f);
                                    myPaint.setTextAlign(Paint.Align.RIGHT);
                                    canvas.drawText("Call: 0741 287 733", 1160, 40, myPaint);
                                    canvas.drawText("0753 881 695", 1160, 80, myPaint);

                                    titlepaint.setTextAlign(Paint.Align.CENTER);
                                    titlepaint.setTypeface(Typeface.create(DEFAULT, Typeface.BOLD_ITALIC));
                                    titlepaint.setTextSize(80);
                                    canvas.drawText("Receipt", pageWidth / 2, 400, titlepaint);

                                    myPaint.setTextAlign(Paint.Align.LEFT);
                                    titlepaint.setTextSize(80);
                                    myPaint.setColor(Color.BLACK);
                                    canvas.drawText("Bereaved Name:" + model.getName(), 20, 590, myPaint);
                                    canvas.drawText("Bereaved Phone Number:" + model.getPhonenumber(), 20, 640, myPaint);
                                    canvas.drawText("Deceased Name:" + model.getDeceasedfullnames(), 20, 690, myPaint);
                                    canvas.drawText("Deceased Gender:" + model.getDeceasedgenders(), 20, 740, myPaint);

                                    myPaint.setTextAlign(Paint.Align.RIGHT);
                                    titlepaint.setTextSize(100);
                                    canvas.drawText("Invoice No.: " + model.getPaymentnumber(), pageWidth - 20, 590, myPaint);


                                    canvas.drawText("Date "+dateFormart.format(dateObj),-20,640,myPaint);

                                    canvas.drawText("Date " + model.getDate(), -20, 640, myPaint);


                                    canvas.drawText("Time " + model.getTime(), -20, 690, myPaint);

                                    myPaint.setStyle(Paint.Style.STROKE);
                                    myPaint.setStrokeWidth(2);
                                    canvas.drawRect(20, 790, pageWidth - 20, 860, myPaint);

                                    myPaint.setTextAlign(Paint.Align.LEFT);
                                    myPaint.setStyle(Paint.Style.FILL);
                                    canvas.drawText("S,No.", 40, 830, myPaint);
                                    canvas.drawText("Start Date", 200, 830, myPaint);
                                    canvas.drawText("End Date", 400, 830, myPaint);
                                    canvas.drawText("Duration day(s)", 600, 830, myPaint);
                                    canvas.drawText("Rate", 870, 830, myPaint);
                                    canvas.drawText("Total Amount.", 1000, 830, myPaint);

                                    canvas.drawLine(180, 800, 180, 840, myPaint);
                                    canvas.drawLine(380, 800, 380, 840, myPaint);
                                    canvas.drawLine(580, 800, 580, 840, myPaint);
                                    canvas.drawLine(850, 800, 850, 840, myPaint);
                                    canvas.drawLine(980, 800, 980, 840, myPaint);

                                    canvas.drawText("1.", 40, 950, myPaint);
                                    canvas.drawText(model.getPreservationstart(), 200, 950, myPaint);
                                    canvas.drawText(model.getPreservationend(), 400, 950, myPaint);
                                    canvas.drawText(model.getPreservationduration(), 600, 950, myPaint);
                                    canvas.drawText("10", 870, 950, myPaint);
                                 canvas.drawText(model.getAmounttobepaid(), 1000, 950, myPaint);

                                    canvas.drawLine(580, 1100, pageWidth - 20, 1100, myPaint);
                                    canvas.drawText("Amount Paid", 700, 1150, myPaint);
                                    canvas.drawText(":", 870, 1150, myPaint);
                                    myPaint.setTextAlign(Paint.Align.RIGHT);
                                    canvas.drawText(model.getAmountpaid(), 1000, 1150, myPaint);

                                    myPaint.setTextAlign(Paint.Align.LEFT);
                                    canvas.drawText("Balance", 700, 1250, myPaint);
                                    canvas.drawText(":", 870, 1250, myPaint);
                                    myPaint.setTextAlign(Paint.Align.RIGHT);
                                    canvas.drawText(model.getBalance(), 1000, 1250, myPaint);
                                    myPaint.setTextAlign(Paint.Align.LEFT);

                                    myPaint.setColor(Color.BLUE);
                                    canvas.drawRect(580, 1300, pageWidth - 20, 1400, myPaint);

                                    myPaint.setColor(Color.WHITE);
                                    myPaint.setTextSize(50f);
                                    myPaint.setTextAlign(Paint.Align.LEFT);
                                    canvas.drawText("STATUS :  ", 700, 1385, myPaint);
                                    myPaint.setTextAlign(Paint.Align.RIGHT);
                                    canvas.drawText("APPROVED", pageWidth - 40, 1385, myPaint);


                                    myPaint.setTextAlign(Paint.Align.LEFT);
                                    myPaint.setTextSize(40f);
                                    myPaint.setTypeface(SANS_SERIF);
                                    myPaint.setColor(Color.BLACK);
                                    canvas.drawText("Thank you for trusting our services.Payment once made is not refundable.", 40, 1500, myPaint);


                                    myPdfDocument.finishPage(myPage1);
                                    String filename = "/"+"Receipt"+hearseRandomKey+".pdf";
                                    File file = new File(Environment.getExternalStorageDirectory(),filename);
                                    try {
                                        myPdfDocument.writeTo(new FileOutputStream(file));
                                        Toast.makeText(AdminViewFuneralPayments.this, "file saved successfully", Toast.LENGTH_LONG).show();

                                    } catch (IOException e) {
                                        Log.e(TAG, "stk onError: " + e.getMessage());
                                        e.printStackTrace();
                                        Toast.makeText(AdminViewFuneralPayments.this, e.getMessage(), Toast.LENGTH_LONG).show();
                                    }
                                    //StoreHearseInformation();
                                    myPdfDocument.close();


                            }



                                else{
                                    finish();

                                }

                            }
                        });
                        builder.show();

                    }
                });

            }


            @NonNull
            @Override
            public AdminViewfpaymentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.viewfuneralhomepayment, parent, false);
                AdminViewfpaymentViewHolder holder = new AdminViewfpaymentViewHolder(view);
                return holder;
            }

        };
        funeralpaymentRecyclerView.setAdapter(adapter);
        adapter.startListening();
    }
    private void StoreHearseInformation() {
        loadingBar.setTitle("Add New Product");
        loadingBar.setMessage("Dear Admin, please wait while we are adding the new Hearse.");
        loadingBar.setCanceledOnTouchOutside(true);
        loadingBar.show();

        Calendar calendar = Calendar.getInstance();

        SimpleDateFormat currentDate = new SimpleDateFormat("MMM dd, yyyy");
        saveCurrentDate = currentDate.format(calendar.getTime());

        SimpleDateFormat currentTime = new SimpleDateFormat("HH:mm:ss a");
        saveCurrentTime = currentTime.format(calendar.getTime());

        hearseRandomKey = saveCurrentDate + saveCurrentTime;
        try {


            final StorageReference filePath = HearseImagesRef.child(ImageUri.getPath() + hearseRandomKey + ".pdf");

            final UploadTask uploadTask = filePath.putFile(ImageUri);


            uploadTask.addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    String message = e.toString();
                    Toast.makeText(AdminViewFuneralPayments.this, "Error: " + message, Toast.LENGTH_SHORT).show();
                    loadingBar.dismiss();
                }
            }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    Toast.makeText(AdminViewFuneralPayments.this, "Product Image uploaded Successfully...", Toast.LENGTH_SHORT).show();

                    Task<Uri> urlTask = uploadTask.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
                        @Override
                        public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                            if (!task.isSuccessful()) {
                                throw task.getException();

                            }

                            downloadImageUrl = filePath.getDownloadUrl().toString();
                            return filePath.getDownloadUrl();
                        }
                    }).addOnCompleteListener(new OnCompleteListener<Uri>() {
                        @Override
                        public void onComplete(@NonNull Task<Uri> task) {
                            if (task.isSuccessful()) {
                                downloadImageUrl = task.getResult().toString();

                                Toast.makeText(AdminViewFuneralPayments.this, "got the Product image Url Successfully...", Toast.LENGTH_SHORT).show();

                                SaveHearseInfoToDatabase();
                            }
                        }
                    });
                }
            });
        }catch (Exception e){
            Log.e(TAG, "stk onError: " + e.getMessage());
            e.printStackTrace();
            Toast.makeText(AdminViewFuneralPayments.this, e.getMessage(), Toast.LENGTH_LONG).show();

        }


    }
    private void SaveHearseInfoToDatabase() {
        HashMap<String, Object> hearseMap = new HashMap<>();
        hearseMap.put("pid", hearseRandomKey);
        hearseMap.put("date", saveCurrentDate);
        hearseMap.put("time", saveCurrentTime);
//        hearseMap.put("description", Description);
        hearseMap.put("invoice", downloadImageUrl);
        // productMap.put("category", CategoryName);
        // productMap.put("price", Price);
//        hearseMap.put("pname", Pname);
        HearseRef.child(hearseRandomKey).updateChildren(hearseMap)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task)
                    {
                        if (task.isSuccessful())
                        {
                            Intent intent = new Intent(AdminViewFuneralPayments.this, MainActivity2.class);
                            startActivity(intent);

                            loadingBar.dismiss();
                            Toast.makeText(AdminViewFuneralPayments.this, "Product is added successfully..", Toast.LENGTH_SHORT).show();
                        }
                        else
                        {
                            loadingBar.dismiss();
                            String message = task.getException().toString();
                            Toast.makeText(AdminViewFuneralPayments.this, "Error: " + message, Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);


            ImageUri = data.getData();


    }
}
