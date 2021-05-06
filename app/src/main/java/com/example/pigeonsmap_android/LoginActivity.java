package com.example.pigeonsmap_android;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInApi;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

public class LoginActivity extends AppCompatActivity {

    private EditText username;
    private EditText password;
    Button loginButton;
    private FirebaseAuth mAuth;
    private GoogleSignInClient mGoogleSignInClient;
    private SignInButton google_sign_in_button;
    private static final int RC_SIGN_IN=1111;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        username= findViewById(R.id.editTextusername);
        password= findViewById(R.id.editTextPassword);
        loginButton= findViewById(R.id.buttonSubmit);
        google_sign_in_button=findViewById(R.id.sign_in_button);


        mAuth=FirebaseAuth.getInstance();


        // Configure sign-in to request the user's ID, email address, and basic
        // profile. ID and basic profile are included in DEFAULT_SIGN_IN.
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();

        // Build a GoogleSignInClient with the options specified by gso.
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);


        google_sign_in_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //if (v.getId() == R.id.sign_in_button) {
                    signIn();
                    // ...
                //}
            }

        });


        //after clicking login button
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                AllowingUserToLogin();
            }
        });


    }


    private void AllowingUserToLogin() {

        //getting username and password which user wrote
        String emaillogin=username.getText().toString();
        String passwrdlogin=password.getText().toString();
        //Empty message.
        if(TextUtils.isEmpty(emaillogin)){
            Toast.makeText(this, "Please enter your e-mail address...", Toast.LENGTH_SHORT).show();
        }else if(TextUtils.isEmpty(passwrdlogin)){
            Toast.makeText(this, "Please enter your password...", Toast.LENGTH_SHORT).show();
        }else {

            //Checking user at firebase,
            //sending from here to map activity
            mAuth.signInWithEmailAndPassword(emaillogin,passwrdlogin).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if(task.isSuccessful()){
                        Toast.makeText(LoginActivity.this, "Login is successful...", Toast.LENGTH_SHORT).show();
                        SendUserToMapActivity();
                    }else {
                        //error.
                        String message=task.getException().getMessage();
                        Toast.makeText(LoginActivity.this, "There is an error...: "+message, Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }

    }

    // send to map activity
    private void SendUserToMapActivity(){

        Intent setupIntent = new Intent(LoginActivity.this,AdressesActivity.class);
        /*no back */
        startActivity(setupIntent);
        finish();
    }



    private void signIn() {
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from GoogleSignInClient.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            // The Task returned from this call is always completed, no need to attach
            // a listener.
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            handleSignInResult(task);
        }
    }

    private void handleSignInResult(Task<GoogleSignInAccount> completedTask) {
        try {
            GoogleSignInAccount account = completedTask.getResult(ApiException.class);
            SendUserToMapActivity();

        } catch (ApiException e) {

            SendUserToMapActivity();
        }
    }


}