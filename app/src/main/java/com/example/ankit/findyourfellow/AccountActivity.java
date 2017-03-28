package com.example.ankit.findyourfellow;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;


public class AccountActivity extends AppCompatActivity {

    private Button manageFriendsButton;
    private Button trackFriendsButton;
    private Switch trackSwitch;

    private FirebaseAuth mAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Welcome");

        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        manageFriendsButton = (Button) findViewById(R.id.manageButton);
        trackFriendsButton = (Button) findViewById(R.id.trackButton);
        trackSwitch = (Switch) findViewById(R.id.trackingSwitch);

        mAuth = FirebaseAuth.getInstance();

        trackSwitch.setChecked(false);

        trackSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked)
            {
                if(isChecked){
                    Intent intent = new Intent(getApplicationContext(), LocationHelper.class);
                    startService(intent);
                    Toast.makeText(getApplicationContext(),"START", Toast.LENGTH_LONG).show();
                }
                else
                {
                    Intent intent = new Intent(getApplicationContext(), LocationHelper.class);
                    stopService(intent);

                    Toast.makeText(getApplicationContext(),"STOP", Toast.LENGTH_LONG).show();
                }

            }
        });

        manageFriendsButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                goToManageActivity();
            }
        });

        trackFriendsButton.setOnClickListener(new View.OnClickListener()
        {

            @Override
            public void onClick(View v)
            {
                goToTrackActivity();
            }
        });


    }

    private void goToManageActivity()
    {
        Intent intent = new Intent(AccountActivity.this, ManageFriendsActivity.class);
        startActivity(intent);
    }

    private void goToTrackActivity()
    {
        Intent intent = new Intent(AccountActivity.this, TrackFriendsActivity.class);
        startActivity(intent);
    }

    private void userSignOut()
    {
        mAuth.signOut();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    //manage what happens when options on the toolbar are clicked
    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        switch(item.getItemId())
        {
            case R.id.signout:
                userSignOut();
                startActivity(new Intent(AccountActivity.this, MainActivity.class));
                Toast.makeText(getApplicationContext(), "Signed out", Toast.LENGTH_LONG).show();
                return true;
            case R.id.action_settings:
                Toast.makeText(getApplicationContext(), "Settings", Toast.LENGTH_LONG).show();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
