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
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

public class UserIDActivity extends AppCompatActivity {

    protected TextView idText;

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_id);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Your UserID");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);


        mAuth = FirebaseAuth.getInstance();

        idText = (TextView) findViewById(R.id.userText);

        idText.setText(mAuth.getCurrentUser().getUid().toString());
    }

    private void userSignOut()
    {
        mAuth.signOut();
    }


    void goToRequestActivity()
    {
        Intent intent = new Intent(UserIDActivity.this, RequestActivity.class);
        startActivity(intent);
    }

    void goToAddFriendActivity()
    {
        Intent intent = new Intent(UserIDActivity.this, AddFriendActivity.class);
        startActivity(intent);
    }

    void goToManageActivity()
    {
        Intent intent = new Intent(UserIDActivity.this, ManageFriendsActivity.class);
        startActivity(intent);
    }

    void goToTrackActivity()
    {
        Intent intent = new Intent(UserIDActivity.this, TrackFriendsActivity.class);
        startActivity(intent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_manage_friends, menu);
        return true;
    }


    //manage what happens when options on the toolbar are clicked
    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        switch(item.getItemId())
        {
            /*
            case R.id.signout:
                userSignOut();
                startActivity(new Intent(UserIDActivity.this, MainActivity.class));
                Toast.makeText(getApplicationContext(), "Signed out", Toast.LENGTH_LONG).show();
                return true;
                */
            case R.id.track_activity:
                goToTrackActivity();
                return true;
            case R.id.add_friend:
                //  Toast.makeText(getApplicationContext(), "Requests", Toast.LENGTH_LONG).show();
                goToAddFriendActivity();
                return true;
            case R.id.requests:
                //Toast.makeText(getApplicationContext(), "Get User ID", Toast.LENGTH_LONG).show();
                goToRequestActivity();
                return true;
            case R.id.manage_friends:
                goToManageActivity();
            default:
                return super.onOptionsItemSelected(item);
        }
    }

}
