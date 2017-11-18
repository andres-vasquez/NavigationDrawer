package com.example.android_instructor.navigationdrawer;

import android.app.Application;

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


    }
}

