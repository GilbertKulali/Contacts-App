package com.example.contactsapp;


import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.ContentResolver;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.os.Build;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;


public class MainActivity extends AppCompatActivity {

    Button loadContacts;

    Cursor cursor ;
    private final String []  mColumnProjections = new String[]{
            ContactsContract.Contacts.DISPLAY_NAME,
            ContactsContract.Contacts.HAS_PHONE_NUMBER


    };






    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        loadContacts = findViewById(R.id.button);

        loadContacts.setOnClickListener(this::loadContacts);


    }


    public void loadContacts(View view) {


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if(checkSelfPermission(Manifest.permission.READ_CONTACTS)!= PackageManager.PERMISSION_GRANTED){

                ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.READ_CONTACTS},0);

            }
            ContentResolver contentResolver = getContentResolver();
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                cursor = contentResolver.query(ContactsContract.Contacts.CONTENT_URI, mColumnProjections, null, null);


                int numberOfContacts = cursor.getCount();


                if(numberOfContacts>0){

                    while(cursor.moveToNext()){

                        int cname = cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME);
                        int cNumber = cursor.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER);

                        @SuppressLint("Range") String contactName = cursor.getString(cname);
                        @SuppressLint("Range") String contactNumber = cursor.getString(cNumber);


                        Log.i("Content Provider", "Contact name: "+contactName+" Contact Number: "+contactNumber);



                    }
                    cursor.close();

                    Toast.makeText(this, "num"+numberOfContacts, Toast.LENGTH_LONG).show();


                }



        }



        }
    }
}
