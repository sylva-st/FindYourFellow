package com.example.ankit.findyourfellow;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.firebase.client.Firebase;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginActivity extends AppCompatActivity {

    private EditText mEmailField;
    private EditText mPassworField;

    private Button mLoginButton;

    private FirebaseAuth mAuth;

    private FirebaseAuth.AuthStateListener mAuthListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Sign In");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mAuth = FirebaseAuth.getInstance();

        mEmailField = (EditText) findViewById(R.id.emailField);
        mPassworField = (EditText) findViewById(R.id.passwordField);

        mLoginButton = (Button) findViewById(R.id.loginButton);

        mAuthListener = new FirebaseAuth.AuthStateListener()
        {

            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth)
            {
                if(firebaseAuth.getCurrentUser() != null)
                {

                    goToAccountActivity();
                    // startActivity(new Intent(LoginActivity.this, AccountActivity.class));
                }
            }
        };

        mLoginButton.setOnClickListener(new View.OnClickListener()
        {

            @Override
            public void onClick(View v)
            {
                startLogin();
            }
        });

    }


    void goToAccountActivity()
    {
        Intent intent = new Intent(LoginActivity.this, AccountActivity.class);
        startActivity(intent);
    }


    protected void onStart()
    {
        super.onStart();

        mAuth.addAuthStateListener(mAuthListener);
    }


    private void startLogin()
    {
        String email = mEmailField.getText().toString();
        String password = mPassworField.getText().toString();

        if(!(TextUtils.isEmpty(email) || TextUtils.isEmpty(password)))
        {
            mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>()
            {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task)
                {
                    if (!(task.isSuccessful()))
                        Toast.makeText(LoginActivity.this, "Sign In Problem", Toast.LENGTH_LONG).show();
                    else
                    {
                        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

                        if (user.isEmailVerified())
                        {
                            // user is verified, so you can finish this activity or send user to activity which you want.
                            finish();
                            Toast.makeText(LoginActivity.this, "Successfully logged in", Toast.LENGTH_SHORT).show();
                        }
                        else
                        {
                            // email is not verified, so just prompt the message to the user and restart this activity.
                            // NOTE: don't forget to log out the user.
                            FirebaseAuth.getInstance().signOut();

                            Toast.makeText(LoginActivity.this, "Email not verified", Toast.LENGTH_SHORT).show();

                            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                            startActivity(intent);
                            //restart this activity

                        }

                    }
                }
            });
        }
        else
            Toast.makeText(LoginActivity.this, "Field(s) is/are empty", Toast.LENGTH_LONG).show();

    }

    /*
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_login, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        return super.onOptionsItemSelected(item);
    }
    */
}