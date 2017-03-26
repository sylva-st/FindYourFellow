package com.example.ankit.findyourfellow;

import android.Manifest;
import android.app.Dialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.location.Location;

import android.location.LocationManager;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.common.api.GoogleApiClient.ConnectionCallbacks;
import com.google.android.gms.common.api.GoogleApiClient.OnConnectionFailedListener;
import com.google.android.gms.location.FusedLocationProviderApi;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.ConnectionResult;

import java.text.DateFormat;
import java.util.Date;

public class testLocationActivity extends AppCompatActivity
{

    protected LocationHelper locationHelper;

    protected TextView textView;
    protected Button btn_start;
    protected Button btn_stop;
    protected BroadcastReceiver broadcastReceiver;

    @Override
    protected void onResume() {
        super.onResume();

        if(broadcastReceiver == null)
        {
            broadcastReceiver = new BroadcastReceiver() {
                @Override
                public void onReceive(Context context, Intent intent) {

                    textView.append("\n" + intent.getExtras().get("coordinates"));

                }
            };
        }
        registerReceiver(broadcastReceiver, new IntentFilter("location_update"));
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        if(broadcastReceiver != null)
        {
            unregisterReceiver(broadcastReceiver);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_location);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Test Location");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        textView = (TextView) findViewById(R.id.textView);
        btn_start = (Button) findViewById(R.id.button);
        btn_stop = (Button) findViewById(R.id.button2);


        if(!runtime_permissions())
        {
            enable_buttons();
        }

    }

    private void enable_buttons()
    {
        btn_start.setOnClickListener(new View.OnClickListener()
        {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), LocationHelper.class);
                startService(intent);
                Toast.makeText(getApplicationContext(),"START", Toast.LENGTH_LONG).show();
            }
        });

        btn_stop.setOnClickListener(new View.OnClickListener()
        {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), LocationHelper.class);
                stopService(intent);

                Toast.makeText(getApplicationContext(),"STOP", Toast.LENGTH_LONG).show();
            }
        });
    }

    private boolean runtime_permissions()
    {
        if(Build.VERSION.SDK_INT >= 23 && ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED)
        {
            requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION}, 100);
            return true;
        }

        return false;


    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults)
    {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if(requestCode == 100)
        {
            if(grantResults[0] == PackageManager.PERMISSION_GRANTED && grantResults[1] == PackageManager.PERMISSION_GRANTED)
            {
                enable_buttons();
            }
            else
            {
                runtime_permissions();
            }
        }
    }
}


/*
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class testLocationActivity extends AppCompatActivity {

    protected TextView latitude;
    protected TextView longitude;
    protected TextView timeLocation;
    protected Button showLocation;
    protected LocationHelper locationHelper;

    String mLastUpdateTime;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_location);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Test Location");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        locationHelper = new LocationHelper(this);

        latitude = (TextView) findViewById(R.id.Latitude);
        longitude = (TextView) findViewById(R.id.Longitude);
        showLocation = (Button) findViewById(R.id.locationButton);
        timeLocation = (TextView) findViewById(R.id.timeTextView);

        /*
        showLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(!locationHelper.isGPSAvailable())
                {
                    buildAlertMessageNoGps();
                }

                updateUI();
            }
        });


    }
            */
/*
    private void updateUI() { //print Location details
        Location currentLocation = locationHelper.getmCurrentLocation();
        mLastUpdateTime = locationHelper.getmLastUpdateTime();
        if (null != currentLocation) {
            latitude.setText(String.valueOf(currentLocation.getLatitude()));
            longitude.setText(String.valueOf(currentLocation.getLongitude()));
            timeLocation.setText(mLastUpdateTime);
            Toast.makeText(this, "Location Updated", Toast.LENGTH_SHORT).show();

        }
        else
        {
            Toast.makeText(this, "Location is NULL", Toast.LENGTH_SHORT).show();
        }
    }

    private void buildAlertMessageNoGps() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Your GPS seems to be disabled, do you want to enable it?")
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(@SuppressWarnings("unused") final DialogInterface dialog, @SuppressWarnings("unused") final int id) {
                        startActivity(new Intent(android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS));
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(final DialogInterface dialog, @SuppressWarnings("unused") final int id) {
                        dialog.cancel();
                    }
                });
        final AlertDialog alert = builder.create();
        alert.show();
    }

}
            */
