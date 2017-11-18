package com.example.android_instructor.navigationdrawer;

import android.app.Application;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.widget.ImageView;

import com.mikepenz.materialdrawer.util.AbstractDrawerImageLoader;
import com.mikepenz.materialdrawer.util.DrawerImageLoader;
import com.squareup.picasso.Picasso;

import app.horses.camera.CameraManager;

/**
 * Created by Android-Instructor on 17/11/2017.
 */

public class App extends Application {
    @Override
    public void onCreate() {
        super.onCreate();

        /** Simple Usage (default values) */
        CameraManager.init(this);

        /** Complete usage */
        CameraManager.init(
                new CameraManager.Builder(this)
                        .setPrimaryColor(R.color.colorPrimary)
                        .enableCropSquare(false) //if cropper in square mode
                        .build());

        //Inicializar cargar foto de perfil desde URL
        DrawerImageLoader.init(new AbstractDrawerImageLoader() {
            @Override
            public void set(ImageView imageView, Uri uri, Drawable placeholder) {
                Picasso.with(imageView.getContext()).load(uri).placeholder(placeholder).into(imageView);
            }

            @Override
            public void cancel(ImageView imageView) {
                Picasso.with(imageView.getContext()).cancelRequest(imageView);
            }
        });
    }

    @Override
    protected void attachBaseContext(Context context) {
        super.attachBaseContext(context);
        //Multidex.install(this);
    }
}

