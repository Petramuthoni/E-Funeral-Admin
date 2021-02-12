package com.freddy.sample.mpesa;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;

public class DonationsRequestsAddActivity extends AppCompatActivity {
    private String DeceasedName, PleaDescription, PleaDonationTo;
    private Button AddNewDonationButton;
    private ImageView InputDeceasedImage;
    private EditText InputDeceasedName, InputPleaDescription, InputPleaDonationTo;
    private static final int GalleryPick = 1;
    private Uri ImageUri;
    private String deceasedRandomKey, downloadImageUrl,saveCurrentDate, saveCurrentTime;
    private StorageReference deceasedImagesRef;
    private DatabaseReference deceasedRef;
    private ProgressDialog loadingBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_donations_requests_add);
        deceasedImagesRef = FirebaseStorage.getInstance().getReference().child("Deceased Images");
        deceasedRef = FirebaseDatabase.getInstance().getReference().child("Donation Pleas");


        AddNewDonationButton = (Button) findViewById(R.id.add_donation_plea);
        InputDeceasedImage = (ImageView) findViewById(R.id.select_deceased_image);
        InputDeceasedName = (EditText) findViewById(R.id.deceased_name);
        InputPleaDescription = (EditText) findViewById(R.id.plea_description);
        InputPleaDonationTo = (EditText) findViewById(R.id.donation_line);
        loadingBar = new ProgressDialog(this);
        InputDeceasedImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                OpenGallery();
            }
        });
        AddNewDonationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                ValidateProductData();
            }
        });
    }

    private void ValidateProductData() {
        PleaDescription = InputPleaDescription.getText().toString();
        PleaDonationTo = InputPleaDonationTo.getText().toString();
        DeceasedName = InputDeceasedName.getText().toString();


        if (ImageUri == null) {
            Toast.makeText(this, "Deceased image is mandatory...", Toast.LENGTH_SHORT).show();
        } else if (TextUtils.isEmpty(PleaDescription)) {
            Toast.makeText(this, "Please write donation plea description...", Toast.LENGTH_SHORT).show();
        } else if (TextUtils.isEmpty(PleaDonationTo)) {
            Toast.makeText(this, "Please write receiver donation means...", Toast.LENGTH_SHORT).show();
        } else if (TextUtils.isEmpty(DeceasedName)) {
            Toast.makeText(this, "Please write deceased name...", Toast.LENGTH_SHORT).show();
        } else {
            StoreProductInformation();
        }
    }

    private void StoreProductInformation() {
        loadingBar.setTitle("Add Donation Plea");
        loadingBar.setMessage("Dear Client, please wait while we are adding the donation plea you made.");
        loadingBar.setCanceledOnTouchOutside(false);
        loadingBar.show();

        Calendar calendar = Calendar.getInstance();

        SimpleDateFormat currentDate = new SimpleDateFormat("MMM dd, yyyy");
        saveCurrentDate = currentDate.format(calendar.getTime());

        SimpleDateFormat currentTime = new SimpleDateFormat("HH:mm:ss a");
        saveCurrentTime = currentTime.format(calendar.getTime());

        deceasedRandomKey = saveCurrentDate + saveCurrentTime;


        final StorageReference filePath = deceasedImagesRef.child(ImageUri.getLastPathSegment() +  deceasedRandomKey + ".jpg");

        final UploadTask uploadTask = filePath.putFile(ImageUri);


        uploadTask.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e)
            {
                String message = e.toString();
                Toast.makeText(DonationsRequestsAddActivity.this, "Error: " + message, Toast.LENGTH_SHORT).show();
                loadingBar.dismiss();
            }
        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot)
            {
                Toast.makeText(DonationsRequestsAddActivity.this, "Deceased Image uploaded Successfully...", Toast.LENGTH_SHORT).show();

                Task<Uri> urlTask = uploadTask.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
                    @Override
                    public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception
                    {
                        if (!task.isSuccessful())
                        {
                            throw task.getException();

                        }

                        downloadImageUrl = filePath.getDownloadUrl().toString();
                        return filePath.getDownloadUrl();
                    }
                }).addOnCompleteListener(new OnCompleteListener<Uri>() {
                    @Override
                    public void onComplete(@NonNull Task<Uri> task)
                    {
                        if (task.isSuccessful())
                        {
                            downloadImageUrl = task.getResult().toString();

                            Toast.makeText(DonationsRequestsAddActivity.this, "got the Deceased image Url Successfully...", Toast.LENGTH_SHORT).show();

                            SaveProductInfoToDatabase();
                        }
                    }
                });
            }
        });

    }

    private void SaveProductInfoToDatabase() {
        HashMap<String, Object> deceasedMap = new HashMap<>();
        deceasedMap.put("pid",  deceasedRandomKey);
        deceasedMap.put("date", saveCurrentDate);
        deceasedMap.put("time", saveCurrentTime);
        deceasedMap.put("pleadescription", PleaDescription);
        deceasedMap.put("image", downloadImageUrl);
        deceasedMap.put("donateto",PleaDonationTo);
        deceasedMap.put("dname", DeceasedName);

        deceasedRef.child(deceasedRandomKey).updateChildren(deceasedMap)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task)
                    {
                        if (task.isSuccessful())
                        {
                            Intent intent = new Intent(DonationsRequestsAddActivity.this, DonationsRequestsHomeActivity.class);
                            startActivity(intent);

                            loadingBar.dismiss();
                            Toast.makeText(DonationsRequestsAddActivity.this, "Donation Plea is added successfully..", Toast.LENGTH_SHORT).show();
                        }
                        else
                        {
                            loadingBar.dismiss();
                            String message = task.getException().toString();
                            Toast.makeText(DonationsRequestsAddActivity.this, "Error: " + message, Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }




    private void OpenGallery() {
        Intent galleryIntent = new Intent();
        galleryIntent.setAction(Intent.ACTION_GET_CONTENT);
        galleryIntent.setType("image/*");
        startActivityForResult(galleryIntent, GalleryPick);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode==GalleryPick  &&  resultCode==RESULT_OK  &&  data!=null)
        {
            ImageUri = data.getData();
            InputDeceasedImage.setImageURI(ImageUri);
        }
    }


}

