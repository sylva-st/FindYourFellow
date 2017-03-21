package com.example.ankit.findyourfellow;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {


    protected Button createProfileButton = null;
    protected Button loginToProfileButton = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Find Your Friends");

        createProfileButton = (Button) findViewById(R.id.newCreateButton);
        loginToProfileButton = (Button) findViewById(R.id.loginButton);

        createProfileButton.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View v)
            {
                goToCreateActivity();
            }
        });

        loginToProfileButton.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View v)
            {
                goToLoginActivity();
            }
        });

    }

    void goToCreateActivity()
    {
        Intent intent = new Intent(MainActivity.this, CreateProfileActivity.class);
        startActivity(intent);
    }

    void goToLoginActivity()
    {
        Intent intent = new Intent(MainActivity.this, LoginActivity.class);
        startActivity(intent);
    }


    /*
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
    */

}
