package com.example.mohamedhanydev.repeatcancellation;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.SparseBooleanArray;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import adapters.VideoAdapter;
import elements.ImageElement;
import elements.TinyDB;


/**
 * Created by MohamedDev on 5/12/2017.
 */

public class Videos  extends Activity {

    private static final String TAG ="ss" ;
    ListView listView ;

    ArrayList<String> Datelist;
    ArrayList<String> Desclist;
    ArrayList<ImageElement>VideosRepeat ;
    ArrayList<ImageElement>Videositself ;


    VideoAdapter adapter;
    Button delete,selectAll ;
    TextView place;
    private ProgressDialog progress;
    boolean state ;
    TinyDB tinyDB;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.image_it);
//get image element list
        LinearLayout back = (LinearLayout) findViewById(R.id.back);
        tinyDB =new TinyDB(Videos.this);



        VideosRepeat =  tinyDB.getListImage("videoname2",ImageElement.class);
        Videositself =    tinyDB.getListImage("videoit2",ImageElement.class);



        final String page = tinyDB.getString("page");
        back.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {

                Intent intent = new Intent(Videos.this,
                        VideoAll.class);


                startActivity(intent);


            }
        });


        progress = new ProgressDialog(Videos.this);

        place = (TextView) findViewById(R.id.place);
        delete = (Button) findViewById(R.id.delete);
        selectAll = (Button) findViewById(R.id.selectAll);
        listView = (ListView) findViewById(R.id.listview1);


        selectAll.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {

                for(int i=0; i < listView.getChildCount(); i++){
                    LinearLayout itemLayout = (LinearLayout) listView.getChildAt(i);
                    CheckBox cb = (CheckBox)itemLayout.findViewById(R.id.box);
                    cb.setChecked(true);

                }
                adapter.selectall(true);


            }
        });

        delete.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                if (adapter.mSelectedItemsIds.size() == 0) {
                    Toast.makeText(Videos.this, "please select your rows",
                            Toast.LENGTH_LONG).show();

                } else {
                    AlertDialog.Builder builder = new AlertDialog.Builder(
                            Videos.this);
                    builder.setMessage("Do you  want to delete selected record(s)?");

                    builder.setNegativeButton("No", new DialogInterface.OnClickListener() {

                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            // TODO  Auto-generated method stub

                        }
                    });
                    builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {

                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            // TODO  Auto-generated method stub

                            progress.setMessage("Deleting pleas wait...");
                            progress.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                            progress.setIndeterminate(true);
                            progress.show();
                            Thread thread = new Thread(){

                                @Override
                                public void run() {

                                    try {
                                        synchronized (this) {
                                            wait(500);

                                            runOnUiThread(new Runnable() {
                                                @Override
                                                public void run() {
                            SparseBooleanArray selected = adapter
                                    .getSelectedIds();
                            for (int i = (selected.size() - 1); i >= 0; i--) {
                                if (selected.valueAt(i)) {
                                    ImageElement selecteditem = adapter
                                            .getItem(selected.keyAt(i));
                                    File file = new File(selecteditem.getUrl());
                                    boolean deleted = file.delete();
                                    if(page.equals("video")) {

                                        VideosRepeat.remove(selecteditem);
                                        for (int s = 0; s < Videositself.size(); s++)
                                            if (Videositself.get(s).getUrl().equals(selecteditem.getUrl()))
                                                Videositself.remove(s);

                                    }
                                    else
                                    {
                                        Videositself.remove(selecteditem);
                                        for (int s = 0; s < VideosRepeat.size(); s++)
                                            if (VideosRepeat.get(s).getUrl().equals(selecteditem.getUrl()))
                                                VideosRepeat.remove(s);

                                    }




                                    // Remove  selected items following the ids
                                    adapter.remove(selecteditem);
                                }
                            }
                                                    tinyDB.putListImage("videoit2",Videositself);
                                                    tinyDB.putListImage("videoname2",VideosRepeat);
                            DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
                            Date date = new Date();
                            String test = new StringBuffer("elements deleted From VIDEOS :-").append(selected.size()).toString();
                            Desclist.add(test);
                            Datelist.add(dateFormat.format(date));
                            TinyDB tinydb = new TinyDB(Videos.this);
                            tinydb.putListString("date", Datelist);
                            tinydb.putListString("desc", Desclist);

                            adapter.selectall(false);
                            adapter. mSelectedItemsIds = new  SparseBooleanArray();


                            // Close CAB
                            selected.clear();
                                                    progress.dismiss();


                                                }
                                            });

                                        }
                                    } catch (InterruptedException e) {
                                        e.printStackTrace();
                                    }

                                };

                            };
                            thread.start();

                        }
                    });

                    AlertDialog alert = builder.create();
                    alert.setIcon(R.drawable.questionicon);// dialog  Icon
                    alert.setTitle("Confirmation"); // dialog  Title
                    alert.show();


                }



            }
        });

        progress.setMessage("Please Wait Loading...");
        progress.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progress.setIndeterminate(true);
        progress.show();


        Thread thread = new Thread(){

            @Override
            public void run() {

                try {
                    synchronized (this) {
                        wait(500);

                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {


                                // Start

                                try {
                                    TinyDB tinydb = new TinyDB(Videos.this);

                                    Datelist = tinydb.getListString("date");
                                    Desclist = tinydb.getListString("desc");


                                } catch (NullPointerException e) {

                                }

                                try {


                                    if (Datelist.equals(null) && Desclist.equals(null)) {

                                        Videos.this.Datelist = new ArrayList<>();
                                        Videos.this.Desclist = new ArrayList<>();
                                    }
                                } catch (NullPointerException e) {


                                }
                                if(page.equals("video")) {

                                    adapter = new VideoAdapter(Videos.this, 0, VideosRepeat);
                                }
                                else {
                                    adapter = new VideoAdapter(Videos.this, 0, Videositself);

                                }
                                adapter.notifyDataSetChanged();
                                listView.setAdapter(adapter);
                                listView.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE_MODAL);
                                if (page.equals("video")) {

                                    if (VideosRepeat.size() == 1) {
                                        VideosRepeat.remove(0);
                                        tinyDB.putListImage("videoname2",VideosRepeat);
                                    }
                                    // Start
                                    if (VideosRepeat.size() > 0) {
                                        place.setVisibility(View.GONE);


                                    }
                                } else {
                                    if (Videositself.size() == 1) {
                                        Videositself.remove(0);
                                        tinyDB.putListImage("videoit2",Videositself);

                                    }
                                    // Start
                                    if (Videositself.size() > 0) {
                                        place.setVisibility(View.GONE);


                                    }
                                }
                                progress.dismiss();


                            }
                        });

                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            };

        };
        thread.start();

    }



    public void onBackPressed() {
        Intent intent = new Intent(Videos.this,
                VideoAll.class);


        startActivity(intent);

    }


}