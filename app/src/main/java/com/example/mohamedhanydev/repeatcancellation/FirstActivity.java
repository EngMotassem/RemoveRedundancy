package com.example.mohamedhanydev.repeatcancellation;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Parcelable;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.util.AndroidException;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.io.File;
import java.io.FileInputStream;
import java.io.FilenameFilter;
import java.io.InputStream;
import java.security.MessageDigest;
import java.util.ArrayList;

import elements.FileUtils;
import elements.Functionality;
import elements.ImageElement;


/**
 * Created by MohamedDev on 5/7/2017.
 */

public class FirstActivity extends Activity {



    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.first);

        /*

        Get saved data from intent
         */


        Button Phone = (Button) findViewById(R.id.phone);
        Button history = (Button) findViewById(R.id.history);
        Button image = (Button) findViewById(R.id.imagpage);
        Button video = (Button) findViewById(R.id.videopage);

        // Set your image Array if it is already fill before

/*
phone button
 */
        Phone.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                Intent intent = new Intent(FirstActivity.this,
                        Contactsrepetation.class);


                startActivity(intent);

            }
        });
        /*
        Video Button
         */
        video.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {

                Intent intent = new Intent(FirstActivity.this,
                        VideoAll.class);

//
                startActivity(intent);



            }
        });
        /*
        image Button
         */
        image.setOnClickListener(new View.OnClickListener() {


            public void onClick(View v) {
                    Intent i = new Intent(FirstActivity.this, ImageAll.class);



                    startActivity(i);


            }

        });

        /*
        history button
         */
        history.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {

                Intent intent = new Intent(FirstActivity.this,
                        History.class);



                startActivity(intent);

            }
        });


    }
    /*
    on Back
     */

    public void onBackPressed() {


    }




}
