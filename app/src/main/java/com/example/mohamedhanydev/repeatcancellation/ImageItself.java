package com.example.mohamedhanydev.repeatcancellation;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;

import android.os.Bundle;
import android.util.Log;
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

import adapters.ImageAdapter;
import elements.ImageElement;
import elements.TinyDB;

import static android.content.ContentValues.TAG;

/**
 * Created by MohamedDev on 5/12/2017.
 */

public class ImageItself  extends Activity {

    ListView listView ;

    ArrayList<String> Datelist;
    ArrayList<String> Desclist;
    ArrayList<ImageElement>Imagesrepeat;
    ArrayList<ImageElement>Imagefilter;

    ImageAdapter adapter;
    Button delete,selectAll ;
    TextView place;
    private ProgressDialog progressdialog;
TinyDB tinyDB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.image_it);
        progressdialog = new ProgressDialog(ImageItself.this);

        final LinearLayout back = (LinearLayout) findViewById(R.id.back);
tinyDB =new TinyDB(ImageItself.this);


        place = (TextView) findViewById(R.id.place);
        delete = (Button) findViewById(R.id.delete);
        selectAll = (Button) findViewById(R.id.selectAll);
        listView = (ListView) findViewById(R.id.listview1);

        /*

                Get data Saved in intent

         */

        Imagesrepeat = tinyDB.getListImage("Imageit2",ImageElement.class);
        Imagefilter = tinyDB.getListImage("Imagename2",ImageElement.class);

        final String page = tinyDB.getString("page");

/*
select all button

 */
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
/*
delete Button

 */
        delete.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                if (adapter.mSelectedItemsIds.size() == 0) {
                    Toast.makeText(ImageItself.this, "please select your rows",
                            Toast.LENGTH_LONG).show();

                } else {
                    AlertDialog.Builder builder = new AlertDialog.Builder(
                            ImageItself.this);
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


                            SparseBooleanArray selected = adapter
                                    .getSelectedIds();
                            for (int i = (selected.size() - 1); i >= 0; i--) {
                                if (selected.valueAt(i)) {
                                    ImageElement selecteditem = adapter
                                            .getItem(selected.keyAt(i));

                                    File file = new File(selecteditem.getUrl());
                                    boolean deleted = file.delete();
                                    if(page.equals("imageit")) {

                                        Imagesrepeat.remove(selecteditem);
                                        for (int s = 0; s < Imagefilter.size(); s++)
                                            if (Imagefilter.get(s).getUrl().equals(selecteditem.getUrl()))
                                                Imagefilter.remove(s);
                                    }
                                    else
                                    {
                                        Imagefilter.remove(selecteditem);
                                        for (int s = 0; s < Imagesrepeat.size(); s++)
                                            if (Imagesrepeat.get(s).getUrl().equals(selecteditem.getUrl()))
                                                Imagesrepeat.remove(s);
                                    }



                                    // Remove  selected items following the ids

                                    adapter.remove(selecteditem);
                                }
                            }
                                                    tinyDB.putListImage("Imageit2",Imagesrepeat);
                                                    tinyDB.putListImage("Imagename2",Imagefilter);


                            /*
                            Set date today in history
                             */
                            DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
                            Date date = new Date();
                                                    String test = new StringBuffer("elements deleted From Images :-").append(selected.size()).toString();
                                                    Desclist.add(test);
                                                    Datelist.add(dateFormat.format(date));
                                                    TinyDB tinydb = new TinyDB(ImageItself.this);
                                                    tinydb.putListString("date", Datelist);
                                                    tinydb.putListString("desc", Desclist);

                            adapter.selectall(false);
                            adapter. mSelectedItemsIds = new  SparseBooleanArray();


                            // Close CAB
                            selected.clear();



                        }
                    });
                    AlertDialog alert = builder.create();
                    alert.setIcon(R.drawable.questionicon);// dialog  Icon
                    alert.setTitle("Confirmation"); // dialog  Title
                    alert.show();

                }

            }
        });

        /*


        Checker to delete elements of list if one element just existed and show No results text
         */

try {
    if(page.equals("imageit")) {

        if (Imagesrepeat.size() == 1) {
            Imagesrepeat.remove(0);

            tinyDB.putListImage("Imageit2",Imagesrepeat);
        }// Start
        if (Imagesrepeat.size() > 0) {
            place.setVisibility(View.GONE);


        }
    }
    else
    {
        if (Imagefilter.size() == 1) {
            Imagefilter.remove(0);
            tinyDB.putListImage("Imagename2",Imagefilter);

        }
        // Start
        if (Imagefilter.size() > 0) {
            place.setVisibility(View.GONE);


        }
    }
     /*


        Checker to delete elements of list if one element just existed and show No results text
         */
}
catch (NullPointerException e)
{

}
/*

reterieve history data
 */
                                try {
            TinyDB tinydb = new TinyDB(ImageItself.this);

            Datelist = tinydb.getListString("date");
            Desclist = tinydb.getListString("desc");


        } catch (NullPointerException e) {

        }

        try {


            if (Datelist.equals(null)&&Desclist.equals(null)) {

                Datelist = new ArrayList<>();
                Desclist = new ArrayList<>();
            }
        }
        catch (NullPointerException e)
        {


        }
        /*
        check if key == search in image names  or image content to set the adapter


         */
                                if(page.equals("imageit")) {

                                    adapter = new ImageAdapter(ImageItself.this, 0, Imagesrepeat);
                               }
                                else {
                                    adapter = new ImageAdapter(ImageItself.this, 0, Imagefilter);

                                }



        listView.setAdapter(adapter);
        listView.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE_MODAL);

                                progressdialog.dismiss();





        back.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {

                Intent i = new Intent(ImageItself.this,ImageAll.class);


                startActivity(i);            }
        });


    }










    public void onBackPressed() {
        Intent i = new Intent(ImageItself.this,ImageAll.class);


        startActivity(i);


    }

}