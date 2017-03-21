package com.example.ankit.findyourfellow;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.firebase.client.ChildEventListener;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

public class ManageFriendsActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private ArrayList<String> allFriend = new ArrayList<>();
    private ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_friends);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Manage Friends");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mAuth = FirebaseAuth.getInstance();

        listView = (ListView) findViewById(R.id.friendsList);

        final ArrayAdapter<String> friendsAdapter= new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, allFriend);

        listView.setAdapter(friendsAdapter);

        String thisUser = mAuth.getCurrentUser().getUid().toString();

        //allFriends.add(thisUser);

        Firebase friendRef = new Firebase("https://findyourfellow.firebaseio.com/Users/" + thisUser + "/Friends");

        friendRef.addChildEventListener(new ChildEventListener() {

            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {

                String friend = dataSnapshot.getValue(String.class);

                allFriend.add(friend);

                friendsAdapter.notifyDataSetChanged();
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });

    }

    void goToAddFriendActivity()
    {
        Intent intent = new Intent(ManageFriendsActivity.this, AddFriendActivity.class);
        startActivity(intent);
    }

    void goToRequestActivity()
    {
        Intent intent = new Intent(ManageFriendsActivity.this, RequestActivity.class);
        startActivity(intent);
    }

    private void userSignOut()
    {
        mAuth.signOut();
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
            case R.id.signout:
                userSignOut();
                startActivity(new Intent(ManageFriendsActivity.this, MainActivity.class));
                Toast.makeText(getApplicationContext(), "Signed out", Toast.LENGTH_LONG).show();
                return true;
            case R.id.action_settings:
                Toast.makeText(getApplicationContext(), "Action", Toast.LENGTH_LONG).show();
                return true;
            case R.id.add_friend:
                Toast.makeText(getApplicationContext(), "Add", Toast.LENGTH_LONG).show();
                goToAddFriendActivity();
                return true;
            case R.id.delete_friend:
                Toast.makeText(getApplicationContext(), "Delete", Toast.LENGTH_LONG).show();
                return true;
            case R.id.requests:
                Toast.makeText(getApplicationContext(), "Requests", Toast.LENGTH_LONG).show();
                goToRequestActivity();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

}
