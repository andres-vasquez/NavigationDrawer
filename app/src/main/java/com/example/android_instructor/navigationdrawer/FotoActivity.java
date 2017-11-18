package com.example.android_instructor.navigationdrawer;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.util.Calendar;

import app.horses.camera.CallbackManager;
import app.horses.camera.CameraManager;
import app.horses.camera.view.CallbackView;

public class FotoActivity extends AppCompatActivity implements CallbackView {

    private CallbackManager callbackManager = new CallbackManager();

    private static final String LOG = FotoActivity.class.getSimpleName();
    private Context context;

    private Toolbar toolbar;

    private Button btnFoto;
    private ImageView imgFoto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_foto);
        context = this;

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        btnFoto = (Button) findViewById(R.id.btnFoto);
        imgFoto = (ImageView)findViewById(R.id.imgFoto);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        imgFoto.setImageResource(R.drawable.trueno);
        callbackManager.setCallback(this);

        btnFoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //CameraManager.openCamera(FotoActivity.this);
                //CameraManager.openGallery(FotoActivity.this);

                FirebaseStorage storage = FirebaseStorage.getInstance();
                StorageReference storageRef = storage.getReference();

                // Create a reference to "mountains.jpg"
                StorageReference mountainsRef = storageRef.child("nilton/"+Calendar.getInstance().getTimeInMillis()+"_trueno.jpg");

                imgFoto.setDrawingCacheEnabled(true);
                imgFoto.buildDrawingCache();

                Bitmap bitmap = imgFoto.getDrawingCache();
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
                byte[] data = baos.toByteArray();

                UploadTask uploadTask = mountainsRef.putBytes(data);
                uploadTask.addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception exception) {
                        Log.e(LOG,"Error: "+exception.getMessage());
                    }
                }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        // taskSnapshot.getMetadata() contains file metadata such as size, content-type, and download URL.
                        Uri downloadUrl = taskSnapshot.getDownloadUrl();
                        Log.e(LOG,""+taskSnapshot.getDownloadUrl());
                    }
                });
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(this,requestCode, resultCode, data);
    }

    @Override
    public void successCamera(String path) {
        Log.i(LOG, "successCamera: " + path);
        path = "file:///" + path;
        Picasso.with(context).load(path).into(imgFoto);
    }

    @Override
    public void errorCamera() {
        Log.i(LOG, "errorCamera");
    }

    @Override
    public void cancelCamera() {
        Log.i(LOG, "cancelCamera");
    }
}
