package com.example.pigeonsmap_android;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class RegisterActivity extends AppCompatActivity {
    EditText name;
    EditText password;
    EditText emailadd;
    EditText confirmpassword;
    Button registerButton;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        name = findViewById(R.id.editUsername);
        emailadd = findViewById((R.id.emailaddress));
        password = findViewById(R.id.editTextTextPassword1);
        confirmpassword=findViewById(R.id.editTextTextPassword2);
        registerButton = findViewById(R.id.button);
        //FirebaseApp.initializeApp(this);

        mAuth= FirebaseAuth.getInstance();


        registerButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                CreateNewAccount();
            }
        });
    }

    private void CreateNewAccount() {
        String email= emailadd.getText().toString();
        String passwrd= password.getText().toString();
        String confirm_password= confirmpassword.getText().toString();


        if(TextUtils.isEmpty(email)){
            Toast.makeText(this, "Please enter your email", Toast.LENGTH_SHORT).show();
        }else if(TextUtils.isEmpty(passwrd)){
            Toast.makeText(this, "Please enter your password...", Toast.LENGTH_SHORT).show();
        }else if(TextUtils.isEmpty(confirm_password)){
            Toast.makeText(this, "Please enter your password again...", Toast.LENGTH_SHORT).show();
        }else if(!passwrd.equals(confirm_password)){
            Toast.makeText(this, "Passwords are did not match...", Toast.LENGTH_SHORT).show();
        }else {


            /*Firebase part */
            mAuth.createUserWithEmailAndPassword(email,passwrd)
                    .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {

                            if(task.isSuccessful()){

                                Toast.makeText(RegisterActivity.this, "Account is created successfully...", Toast.LENGTH_SHORT).show();
                                SendUserToMapActivity(mAuth.getCurrentUser().getUid());
                            }else {
                                /*when an error */
                                String error_message=task.getException().getMessage();
                                Toast.makeText(RegisterActivity.this, "Something went wrong... : "+error_message, Toast.LENGTH_SHORT).show();

                            }
                        }
                    });
        }

    }

    // From here to MapActivity
    private void SendUserToMapActivity(String user_id){

        Intent setupIntent=new Intent(RegisterActivity.this,MapActivity.class);
        /*no back */
        startActivity(setupIntent);
        finish();
    }



}