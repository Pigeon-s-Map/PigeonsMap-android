package com.example.pigeonsmap_android;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity {

    private EditText username;
    private EditText password;
    Button loginButton;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


        username= findViewById(R.id.editTextusername);
        password= findViewById(R.id.editTextPassword);
        loginButton= findViewById(R.id.buttonSubmit);


        mAuth=FirebaseAuth.getInstance();


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

        Intent setupIntent=new Intent(LoginActivity.this,MapActivity.class);
        /*no back */
        startActivity(setupIntent);
        finish();
    }







}
