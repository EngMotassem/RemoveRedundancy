package com.example.mohamedhanydev.repeatcancellation;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.Window;

import java.util.ArrayList;

import elements.Functionality;
import elements.ImageElement;
import elements.TinyDB;

/**
 * Created by MohamedDev on 5/12/2017.
 */

public class SpalshScreen extends Activity {
    private static final int REQUEST_PERMISSION = 0;

    public void onAttachedToWindow() {
        super.onAttachedToWindow();

    }

    /**
     * Called when the activity is first created.
     */
    Thread splashTread;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);

        setContentView(R.layout.splash);

        StartAnimations();


    }

    private void StartAnimations() {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M
                && ContextCompat.checkSelfPermission(this, android.Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.READ_EXTERNAL_STORAGE},
                    REQUEST_PERMISSION);

            return;

        }
        Startthresad();



    }

    @Override
    public void onRequestPermissionsResult(final int requestCode, @NonNull final String[] permissions, @NonNull final int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_PERMISSION) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Startthresad();

                // Permission granted.
            } else {
                // User refused to grant permission.

            }
        }
    }

    private void Startthresad() {
        splashTread = new Thread() {
            @Override
            public void run() {
                try {
                    int waited = 0;
                    // Splash screen pause time
                    while (waited < 3500) {
                        sleep(100);
                        waited += 100;
                    }

                    Intent intent = new Intent(SpalshScreen.this,
                            FirstActivity.class);
                    TinyDB tinyDB =new TinyDB(SpalshScreen.this);
                    tinyDB.putBoolean("Imageit",false);
                    tinyDB.putBoolean("Imagename",false);
                    tinyDB.putBoolean("videoit",false);
                    tinyDB.putBoolean("videoname",false);

                    startActivity(intent);


                    SpalshScreen.this.finish();
                } catch (InterruptedException e) {
                    // do nothing
                } finally {
                    SpalshScreen.this.finish();
                }

            }
        };
        splashTread.start();

    }
}