package com.example.mohamedhanydev.repeatcancellation;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.SparseBooleanArray;
import android.view.ActionMode;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AbsListView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import adapters.HistoryAdapter;

import elements.Contact;
import elements.ImageElement;
import elements.TinyDB;

/**
 * Created by MohamedDev on 5/8/2017.
 */
@SuppressLint("NewApi")

public class History extends Activity {
    ListView listView ;
    TextView place;

    ArrayList<String> Desclist;
    ArrayList<String> Datelist;
    HistoryAdapter adapter;
    Button delete,selectAll ;
    boolean state ;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.history);
        LinearLayout back = (LinearLayout) findViewById(R.id.back);


        listView = (ListView)findViewById(R.id.listview1);
        place =(TextView) findViewById(R.id.place);
        /*
        Get data Saved in intent

         */

/*
Reterieve History from DB

 */

        TinyDB tinydb = new TinyDB(this);

        Datelist = tinydb.getListString("date");
        Desclist=tinydb.getListString("desc");
        if (!Datelist.isEmpty()&& ! Desclist.isEmpty()) {
           place.setVisibility(View.GONE);
            adapter = new HistoryAdapter(History.this,R.layout.history_item_list, Datelist, Desclist);
            listView.setAdapter(adapter);
            listView.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE_MODAL);
            delete = (Button) findViewById(R.id.delete);
            selectAll = (Button) findViewById(R.id.selectAll);
/*

Select all click
 */
            selectAll.setOnClickListener(new View.OnClickListener() {

                public void onClick(View v) {
                    for(int i=0; i < listView.getChildCount(); i++){
                        RelativeLayout itemLayout = (RelativeLayout) listView.getChildAt(i);
                        CheckBox cb = (CheckBox)itemLayout.findViewById(R.id.box);
                        cb.setChecked(true);

                    }
                    adapter.selectall(true);


                }
            });
/*
delete click

show options yes or no
 */
            delete.setOnClickListener(new View.OnClickListener() {

                public void onClick(View v) {
                    if (adapter.mSelectedItemsIds.size() == 0) {
                        Toast.makeText(History.this, "please select your rows",
                                Toast.LENGTH_LONG).show();

                    } else {
                        AlertDialog.Builder builder = new AlertDialog.Builder(
                                History.this);
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
                                for (int i =  (selected.size() - 1); i >= 0; i--) {
                                    if  (selected.valueAt(i)) {
                                        String selecteditem = adapter
                                                .getItem(selected.keyAt(i));
                                        Desclist.remove(selected.keyAt(i));

                                        adapter.remove(selecteditem);




                                        TinyDB tinydb = new TinyDB(History.this);
                                        tinydb.putListString("date", Datelist);
                                        tinydb.putListString("desc", Desclist);


                                        // Remove  selected items following the ids
                                    }
                                }
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







        }
        /*
        Back Button
         */
        back.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {

finish();

            }
        });


    }
    public void onBackPressed() {
       finish();
    }


}