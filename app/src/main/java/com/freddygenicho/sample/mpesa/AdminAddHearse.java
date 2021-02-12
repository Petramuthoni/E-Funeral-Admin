package com.freddygenicho.sample.mpesa;

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

import com.freddy.sample.mpesa.R;
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

public class AdminAddHearse extends AppCompatActivity {
    private Button AddNewHearseButton;
    private ImageView InputHearseImage;
    private EditText InputHearseName, InputHearseDescription;
    private static final int GalleryPick = 1;
    private Uri ImageUri;
    private ProgressDialog loadingBar;
    private String CategoryName, Description, Price, Pname, saveCurrentDate, saveCurrentTime;
    private String hearseRandomKey,downloadImageUrl;
    private StorageReference HearseImagesRef;
    private DatabaseReference HearseRef;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_add_hearse);
        HearseImagesRef = FirebaseStorage.getInstance().getReference().child("Hearse Images");
        HearseRef = FirebaseDatabase.getInstance().getReference().child("Hearses");
        AddNewHearseButton = (Button) findViewById(R.id.add_new_hearse);
        InputHearseImage = (ImageView) findViewById(R.id.select_hearse_image);
        InputHearseName = (EditText) findViewById(R.id.Hearse_add_name);
        InputHearseDescription = (EditText) findViewById(R.id.Hearse_add_description);
        loadingBar = new ProgressDialog(this);
        InputHearseImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                OpenGallery();
            }
        });
        AddNewHearseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                ValidateHearseData();
            }
        });
    }

    private void ValidateHearseData() {
        Description = InputHearseDescription.getText().toString();
        Pname = InputHearseName.getText().toString();
        if (ImageUri == null) {
            Toast.makeText(this, "Product image is mandatory...", Toast.LENGTH_SHORT).show();
        } else if (TextUtils.isEmpty(Description)) {
            Toast.makeText(this, "Please write product description...", Toast.LENGTH_SHORT).show();
        } else if (TextUtils.isEmpty(Pname)) {
            Toast.makeText(this, "Please write product name...", Toast.LENGTH_SHORT).show();
        } else {
            StoreHearseInformation();
        }

    }

    private void StoreHearseInformation() {
        loadingBar.setTitle("Add New Product");
        loadingBar.setMessage("Dear Admin, please wait while we are adding the new Hearse.");
        loadingBar.setCanceledOnTouchOutside(false);
        loadingBar.show();

        Calendar calendar = Calendar.getInstance();

        SimpleDateFormat currentDate = new SimpleDateFormat("MMM dd, yyyy");
        saveCurrentDate = currentDate.format(calendar.getTime());

        SimpleDateFormat currentTime = new SimpleDateFormat("HH:mm:ss a");
        saveCurrentTime = currentTime.format(calendar.getTime());

        hearseRandomKey = saveCurrentDate + saveCurrentTime;


        final StorageReference filePath = HearseImagesRef.child(ImageUri.getLastPathSegment()+ hearseRandomKey   + ".jpg");

        final UploadTask uploadTask = filePath.putFile(ImageUri);


        uploadTask.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e)
            {
                String message = e.toString();
                Toast.makeText(AdminAddHearse.this, "Error: " + message, Toast.LENGTH_SHORT).show();
                loadingBar.dismiss();
            }
        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot)
            {
                Toast.makeText(AdminAddHearse.this, "Product Image uploaded Successfully...", Toast.LENGTH_SHORT).show();

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

                            Toast.makeText(AdminAddHearse.this, "got the Product image Url Successfully...", Toast.LENGTH_SHORT).show();

                            SaveHearseInfoToDatabase();
                        }
                    }
                });
            }
        });


    }

    private void SaveHearseInfoToDatabase() {
        HashMap<String, Object> hearseMap = new HashMap<>();
        hearseMap.put("pid", hearseRandomKey);
        hearseMap.put("date", saveCurrentDate);
        hearseMap.put("time", saveCurrentTime);
        hearseMap.put("description", Description);
        hearseMap.put("image", downloadImageUrl);
       // productMap.put("category", CategoryName);
       // productMap.put("price", Price);
        hearseMap.put("pname", Pname);
        HearseRef.child(hearseRandomKey).updateChildren(hearseMap)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task)
                    {
                        if (task.isSuccessful())
                        {
                            Intent intent = new Intent(AdminAddHearse.this, AdminCategoryActivity.class);
                            startActivity(intent);

                            loadingBar.dismiss();
                            Toast.makeText(AdminAddHearse.this, "Product is added successfully..", Toast.LENGTH_SHORT).show();
                        }
                        else
                        {
                            loadingBar.dismiss();
                            String message = task.getException().toString();
                            Toast.makeText(AdminAddHearse.this, "Error: " + message, Toast.LENGTH_SHORT).show();
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
            InputHearseImage.setImageURI(ImageUri);
        }
    }
}
