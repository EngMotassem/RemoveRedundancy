package com.example.mohamedhanydev.repeatcancellation;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.ContentResolver;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;

import android.os.Build;
import android.os.Bundle;

import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v4.content.ContextCompat;
import android.util.SparseBooleanArray;

import android.widget.Button;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;


import android.content.pm.PackageManager;
import android.database.Cursor;
import android.provider.ContactsContract;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;


import adapters.ListAdapter;
import elements.Contact;
import elements.ImageElement;
import elements.TinyDB;

import static android.Manifest.permission.READ_CONTACTS;

public class Contactsrepetation extends Activity  {

    ListView listView ;
    Cursor cursor ;

    ArrayList<Contact>contacts;
    ArrayList<Contact>flag;
    ArrayList<String> Datelist;
    ArrayList<String> Desclist;
    ArrayList<Contact>contactsrepeat = new ArrayList<>();
    ListAdapter adapter;
Button delete,selectAll ;



    String name, phonenumber ;
    TextView place;
boolean state ;
    public  static final int RequestPermissionCode  = 1 ;
    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        LinearLayout back = (LinearLayout) findViewById(R.id.back);



        final ContentResolver cr = getContentResolver();
        place = (TextView) findViewById(R.id.place);

        listView = (ListView) findViewById(R.id.listview1);
        contacts = new ArrayList<Contact>();
        flag = new ArrayList<Contact>();
        delete = (Button) findViewById(R.id.delete);
        selectAll = (Button) findViewById(R.id.selectAll);
/*

Functionality for select all
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


        Functionality for delete

         */

        delete.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                if (adapter.mSelectedItemsIds.size() == 0) {
                    Toast.makeText(Contactsrepetation.this, "please select your rows",
                            Toast.LENGTH_LONG).show();

                } else {
                    AlertDialog.Builder builder = new AlertDialog.Builder(
                            Contactsrepetation.this);
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
                            System.out.println(selected.size() + "   55555555477777");
                            for (int i = (selected.size() - 1); i >= 0; i--) {
                                if (selected.valueAt(i)) {
                                    Contact selecteditem = adapter
                                            .getItem(selected.keyAt(i));
                                    Uri myUri = Uri.parse(selecteditem.getUrl());
                                    cr.delete(myUri, null, null);

                                    // Remove  selected items following the ids
                                    adapter.remove(selecteditem);
                                }
                            }
                            DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
                            Date date = new Date();
                            String test = new StringBuffer("elements deleted from contacts :-").append(selected.size()).toString();
                            Desclist.add(test);
                            Datelist.add(dateFormat.format(date));
                            TinyDB tinydb = new TinyDB(Contactsrepetation.this);
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
Get permission to read conatacts
 */
        if (getPermissionToReadUserContacts()) {
/*

if it is true read contact from phone
 */

            GetContactsIntoArrayList();

/*
Check to hide the text  (No Results)
 */
            if (contactsrepeat.size() > 0) {
                place.setVisibility(View.GONE);


            }


            /*
            Get your History To update it
             */
            try {
                TinyDB tinydb = new TinyDB(this);

                Datelist = tinydb.getListString("date");
                Desclist = tinydb.getListString("desc");


            } catch (NullPointerException e) {

            }

            try {


                if (Datelist.equals(null)&&Desclist.equals(null)) {

                    this.Datelist = new ArrayList<>();
                    this.Desclist = new ArrayList<>();
                }
            }
            catch (NullPointerException e)
            {


            }

            /*

            Set your adapter
             */
            adapter =new ListAdapter(Contactsrepetation.this,0, contactsrepeat);

            listView.setAdapter(adapter);
            listView.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE_MODAL);


        }

        /*

        Back Button
         */
        back.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {

                Intent intent = new Intent(Contactsrepetation.this,
                        FirstActivity.class);


                startActivity(intent);


            }
        });

        }

/*
Get Contact Filter repeat Algorithm
 */

    public void  GetContactsIntoArrayList(){


        cursor = getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null,null, null, null);

        while (cursor.moveToNext()) {
            Contact contact = new Contact();
            name = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));

            phonenumber = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
            contact.setPhone(phonenumber.replaceAll("\\s+",""));
            if (!isnumber(name))
            {

                contact.setName(name);
            }
            else
            {
               contact.setName("Without Name");
            }

            try{
                String lookupKey = cursor.getString(cursor.getColumnIndex(
                        ContactsContract.Contacts.LOOKUP_KEY));
                Uri uri = Uri.withAppendedPath(ContactsContract.
                        Contacts.CONTENT_LOOKUP_URI, lookupKey);
                contact.setUrl(uri.toString());

            }
            catch(Exception e)
            {
                System.out.println(e.getStackTrace()+"error");
            }

            contacts.add(contact);
            flag.add(contact);


        }

        cursor.close();

        int i=0;
        int repeat=0;
        while (contacts.size()>1)
        {


            contacts.remove(i);
            Contact base = flag.get(i);

            for(int j=0 ; j<contacts.size();j++)
            {
                Contact contact = new Contact();


                if(contacts.get(j).getPhone().equals(flag.get(i).getPhone()))
                {
                    repeat++;
                    contact.setUrl(contacts.get(j).getUrl());

                    contact.setName(contacts.get(j).getName());
                    contact.setPhone(contacts.get(j).getPhone());

                    contactsrepeat.add(contact);
                    contacts.remove(j);
                    flag.remove(j+1);

                    j=-1;

                }
            }


            if(repeat>0)
            {

                contactsrepeat.add(base);


                repeat=0;
            }
            flag.remove(i);
        }



    }



    private static final int READ_CONTACTS_PERMISSIONS_REQUEST = 1;

    // Called when the user is performing an action which requires the app to read the
    // user's contacts
    @RequiresApi(api = Build.VERSION_CODES.M)
    public Boolean getPermissionToReadUserContacts() {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            return true;
        }
        if (checkSelfPermission(READ_CONTACTS) == PackageManager.PERMISSION_GRANTED) {

            return true;
        }

        if (ContextCompat.checkSelfPermission(this, READ_CONTACTS)
                != PackageManager.PERMISSION_GRANTED) {


            if (shouldShowRequestPermissionRationale(
                    READ_CONTACTS)) {
                Intent intent = new Intent(Contactsrepetation.this,
                        FirstActivity.class);
                startActivity(intent);
                // Show our own UI to explain to the user why we need to read the contacts
                // before actually requesting the permission and showing the default UI
            }


            requestPermissions(new String[]{READ_CONTACTS},
                    READ_CONTACTS_PERMISSIONS_REQUEST);
        }
        return  false;
    }

    // Callback with the request from calling requestPermissions(...)
    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String permissions[],
                                           @NonNull int[] grantResults) {
        // Make sure it's our original READ_CONTACTS request
        if (requestCode == READ_CONTACTS_PERMISSIONS_REQUEST) {
            if (grantResults.length == 1 &&
                    grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this, "Read Contacts permission granted", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(Contactsrepetation.this,

                FirstActivity.class);
                startActivity(intent);

            } else {
                Toast.makeText(this, "Read Contacts permission denied", Toast.LENGTH_SHORT).show();
            }
        } else {
            super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }
// check if it is number
    public static Boolean isnumber(String number)
    {
      int is= 0;
        number = number.replaceAll("\\s+","");
      for(int i=0 ;i< number.length();i++)
      {
          if( Arrays.asList("1","2","3","4","5","6","7","8","0","9").contains(number.charAt(i)+"")||Arrays.asList("١","٠","٢","٣","٤","٥","٦","٧","٨","٩").contains(number.charAt(i)+"") )

          {
              System.out.println("goooool " + Arrays.asList("1","2","3","4","5","6","7","8","0","9").contains(number.charAt(i)+""));

              is++;

          }


      }
      if(is == number.length())
      return true;
        else
            return false;

    }

    public void onBackPressed() {
        Intent intent = new Intent(Contactsrepetation.this,
                FirstActivity.class);


        startActivity(intent);

    }



}