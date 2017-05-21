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

public class ImageAll  extends Activity {


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

        setContentView(R.layout.iamge_all);
        LinearLayout back = (LinearLayout) findViewById(R.id.back);
        progressdialog = new ProgressDialog(ImageAll.this);
        Imagesrepeat = new ArrayList<>();

        Images = new ArrayList<>();
        Images2 = new ArrayList<>();

        flag = new ArrayList<>();
        tinyDB = new TinyDB(ImageAll.this);
        arr =new ArrayList<>();
        flag3 =new ArrayList<>();

        flag2 =new ArrayList<>();
        state =tinyDB.getBoolean("Imagename");
        if(!state) {
            progressdialog.setMessage("loading");
            progressdialog.show();
            new Thread() {
                public void run() {
                    for (String iamge : Functionality.getPathOfAllImages(ImageAll.this, "image")) {
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
                    tinyDB.putListImage("Imagename2",Imagesrepeat);
                    tinyDB.putBoolean("Imagename",true);
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


        Button iamge_name = (Button) findViewById(R.id.iamge_name);
        Button imageit_self = (Button) findViewById(R.id.imageit_self);
        /*
        search in images name button

         */

        iamge_name.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
tinyDB.putString("page","image");

                if(state)
{
    Intent i = new Intent(ImageAll.this,ImageItself.class);


    startActivity(i);
}
else
{
    CreateProgressDialog();


    ShowProgressDialog();
}




            }
        });
        /*
        Search in image itself button
         */
        imageit_self.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                tinyDB.putString("page","imageit");

                if(state)
                {
                    Intent i = new Intent(ImageAll.this,ImageItself.class);


                    startActivity(i);

                }
                else {

                    CreateProgressDialog();


                    ShowProgressDialog();
                }




            }
        });
        /*
        Back button
         */
        back.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {

                Intent i = new Intent(ImageAll.this,FirstActivity.class);


                startActivity(i);

            }
        });


    }
    public void onBackPressed() {
        Intent i = new Intent(ImageAll.this,FirstActivity.class);


        startActivity(i);
    }

    public void CreateProgressDialog()
    {

        progressdialog = new ProgressDialog(ImageAll.this);

        progressdialog.setIndeterminate(false);

        progressdialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        progressdialog.setMessage("Fetching images");

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
                tinyDB.putListImage("Imageit2",Imagefilter);
                tinyDB.putBoolean("Imageit",true);
                Intent intent = new Intent(ImageAll.this,ImageItself.class);


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