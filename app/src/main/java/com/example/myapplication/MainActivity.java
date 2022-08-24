package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.ContentResolver;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.View;
import android.widget.ArrayAdapter;

import com.example.myapplication.databinding.ActivityMainBinding;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding mBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = ActivityMainBinding.inflate(getLayoutInflater());
        View view = mBinding.getRoot();
        setContentView(view);


        mBinding.getContact.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("Range")
            @Override
            public void onClick(View view) {

                if (ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.READ_CONTACTS) == PackageManager.PERMISSION_GRANTED) {

                    ContentResolver contentResolver = getContentResolver();
                    String[] projection = {ContactsContract.Contacts.DISPLAY_NAME};

                    Cursor c = contentResolver.query(ContactsContract.Contacts.CONTENT_URI,
                            projection,
                            null,
                            null,
                            ContactsContract.Contacts.DISPLAY_NAME);

                    if (c != null) {
                        List<String> contactList = new ArrayList<>();
                        String columnIndex = ContactsContract.Contacts.DISPLAY_NAME;

                        while (c.moveToNext()) {
                            contactList.add(c.getString(c.getColumnIndex(columnIndex)));
                        }
                        c.close();


                        ArrayAdapter<String> contactAdapter = new ArrayAdapter<String>(MainActivity.this,
                                android.R.layout.simple_list_item_1,
                                contactList);

                        mBinding.contactListView.setAdapter(contactAdapter);


                    }
                } else {

                    Snackbar.make(view, "Permission needed", Snackbar.LENGTH_INDEFINITE).setAction(
                            "Give Permission", new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.READ_CONTACTS}, 1);

                                }
                            }
                    ).show();

                }
            }
        });
    }


}