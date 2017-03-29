package com.example.ankit.findyourfellow;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Toast;

public class FriendInfoActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_friend_info);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Friend Information");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        String fLat = getIntent().getStringExtra("FRIENDLATITUDE");
        String fLong = getIntent().getStringExtra("FRIENDLONGITUDE");
        String uLat = getIntent().getStringExtra("USERLATITUDE");
        String uLong = getIntent().getStringExtra("USERLONGITUDE");

        Toast.makeText(this, "Friend Latitude: " + fLat, Toast.LENGTH_SHORT).show();
        Toast.makeText(this, "Friend Longitude: " + fLong, Toast.LENGTH_SHORT).show();
        //Toast.makeText(this, "User Latitude: " + uLat, Toast.LENGTH_LONG).show();
        //Toast.makeText(this, "User Longitude: " + uLong, Toast.LENGTH_LONG).show();
    }

}
