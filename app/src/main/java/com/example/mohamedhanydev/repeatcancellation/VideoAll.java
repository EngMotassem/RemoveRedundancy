package com.example.mohamedhanydev.repeatcancellation;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;


import java.util.ArrayList;

import elements.Functionality;
import elements.ImageElement;
import elements.TinyDB;

/**
 * Created by MohamedDev on 5/12/2017.
 */

public class VideoAll extends Activity {
    private static final String TAG = "s";
    ArrayList<ImageElement> Imagefilter;

    int s=0;

    ArrayList<String> arr;
    boolean state;
    ArrayList<String>flag2;
    ArrayList<ImageElement> Images;//
    ArrayList<ImageElement> Images2;//

    ArrayList<ImageElement> flag;//
    ArrayList<ImageElement> flag3;//

    ArrayList<ImageElement> Imagesrepeat;
    ProgressDialog progressdialog;
    TinyDB tinyDB ;

    Handler handler = new Handler();

    //selectAll  delete  back
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.videos_all);
        LinearLayout back = (LinearLayout) findViewById(R.id.back);
        progressdialog = new ProgressDialog(VideoAll.this);
        Imagesrepeat = new ArrayList<>();

        Images = new ArrayList<>();
        Images2 = new ArrayList<>();

        flag = new ArrayList<>();
        tinyDB = new TinyDB(VideoAll.this);
        arr =new ArrayList<>();
        flag3 =new ArrayList<>();

        flag2 =new ArrayList<>();
        state =tinyDB.getBoolean("videoname");
        if(!state) {
            progressdialog.setMessage("loading");
            progressdialog.show();
            new Thread() {
                public void run() {
                    for (String iamge : Functionality.getPathOfAllImages(VideoAll.this, "video")) {
                        ImageElement m = new ImageElement();
                        m.setUrl(iamge);
                        String filename = iamge.substring(iamge.lastIndexOf("/") + 1);
                        m.setName(filename);
                        Images.add(m);
                        Images2.add(m);

                        flag.add(m);
                        flag3.add(m);

                    }



                    int i = 0;
                    int repeat = 0;
                    while (Images.size() > 1) {


                        Images.remove(i);
                        ImageElement base = flag.get(i);

                        for (int j = 0; j < Images.size(); j++) {
                            ImageElement imageElement = new ImageElement();


                            if (Images.get(j).getName().equals(flag.get(i).getName())) {
                                repeat++;

                                imageElement.setName(Images.get(j).getName());
                                imageElement.setUrl(Images.get(j).getUrl());

                                Imagesrepeat.add(imageElement);
                                Images.remove(j);
                                flag.remove(j + 1);

                                j = -1;

                            }
                        }


                        if (repeat > 0) {

                            Imagesrepeat.add(base);


                            repeat = 0;
                        }
                        flag.remove(i);
                    }
                    tinyDB.putListImage("videoname2",Imagesrepeat);
                    tinyDB.putBoolean("videoname",true);
                    try{
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                // remove the retrieving of data from this method and let it just build the views
                                progressdialog.dismiss();
                            }
                        });
                    } catch (Exception e) {
                        Log.e("tag", e.getMessage());
                    }
                }
            }.start();

        }
        back.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {

                Intent i = new Intent(VideoAll.this,FirstActivity.class);


                startActivity(i);


            }
        });




        Button video_name = (Button) findViewById(R.id.video_nmea);
        Button video_self = (Button) findViewById(R.id.video_self);

        video_name.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {

                tinyDB.putString("page","video");

                if(state)
                {
                    Intent i = new Intent(VideoAll.this,Videos.class);


                    startActivity(i);
                }
                else
                {
                    CreateProgressDialog();


                    ShowProgressDialog();
                }







            }
        });
        video_self.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {

                tinyDB.putString("page","videosit");

                if(state)
                {
                    Intent i = new Intent(VideoAll.this,Videos.class);


                    startActivity(i);

                }
                else {

                    CreateProgressDialog();


                    ShowProgressDialog();
                }



            }
        });

    }
    public void onBackPressed() {
        Intent i = new Intent(VideoAll.this,FirstActivity.class);



        startActivity(i);

    }
    public void CreateProgressDialog()
    {

        progressdialog = new ProgressDialog(VideoAll.this);

        progressdialog.setIndeterminate(false);

        progressdialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        progressdialog.setMessage("Fetching Videos");

        progressdialog.setCancelable(false);
        progressdialog.setCanceledOnTouchOutside(false);
        progressdialog.setMax(Images2.size());


        progressdialog.show();

    }
    public void ShowProgressDialog()
    {




        new Thread(new Runnable() {
            @Override
            public void run() {

                Imagefilter =new ArrayList<ImageElement>();
                arr =new ArrayList<>();

                for(int i=0 ; i<Images2.size();i++)
                {
                    s=i;
                    arr.add(Functionality.getMd5OfFile(Images2.get(i).getUrl()));
                    flag2.add(Functionality.getMd5OfFile(Images2.get(i).getUrl()));


                    try{
                        Thread.sleep(200);
                    }catch(InterruptedException e){
                        e.printStackTrace();
                    }

                    handler.post(new Runnable() {
                        @Override
                        public void run() {

                            progressdialog.setProgress(s);


                            if(s == Images2.size()-1){

                                progressdialog.dismiss();




                            }



                        }
                    });
                }
                int i=0;
                int repeat=0;
                while (arr.size()>1)
                {


                    Images2.remove(i);
                    arr.remove(i);

                    ImageElement base = flag3.get(i);


                    for(int j=0 ; j<arr.size();j++)
                    {
                        ImageElement imageElement = new ImageElement();



                        if(arr.get(j).equals(flag2.get(i)))
                        {
                            repeat++;

                            imageElement.setName(Images2.get(j).getName());
                            imageElement.setUrl(Images2
                                    .get(j).getUrl());

                            Imagefilter.add(imageElement);
                            Images2.remove(j);
                            arr.remove(j);
                            flag2.remove(j+1);
                            flag3.remove(j+1);

                            j=-1;

                        }
                    }


                    if(repeat>0)
                    {

                        Imagefilter.add(base);


                        repeat=0;
                    }
                    flag2.remove(i);
                    flag3.remove(i);

                }
                tinyDB.putListImage("videoit2",Imagefilter);
                tinyDB.putBoolean("videoit",true);
                Intent intent = new Intent(VideoAll.this,Videos.class);


                startActivity(intent);
            }
        }).start();





    }
    @Override
    protected void onDestroy()
    {
        super.onDestroy();

        progressdialog.dismiss();

    }








}