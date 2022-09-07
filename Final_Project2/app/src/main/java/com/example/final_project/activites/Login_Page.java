package com.example.final_project.activites;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.final_project.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class Login_Page extends AppCompatActivity {

    EditText email, password;
    FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_page);

        //getSupportActionBar().hide();
        auth = FirebaseAuth.getInstance();

        email = findViewById(R.id.emailSignin);
        password = findViewById(R.id.passwordSignin);

    }

    public void signIn(View view){
        String userEmail = email.getText().toString();
        String userPassword = password.getText().toString();

        if (TextUtils.isEmpty(userEmail)){
            Toast.makeText(this, "Enter Email Address!", Toast.LENGTH_LONG).show();
            email.setError("Enter Email Address!");
            return;
        }

        if (TextUtils.isEmpty(userPassword)){
            Toast.makeText(this, "Enter Password!", Toast.LENGTH_LONG).show();
            password.setError("Enter Password!");
            return;
        }

        if (userPassword.length() < 6){
            Toast.makeText(this, "Password to short, enter minimum 6 characters!", Toast.LENGTH_SHORT).show();
            password.setError("Password to short, enter minimum 6 characters!");
            return;
        }

        auth.signInWithEmailAndPassword(userEmail, userPassword)
            .addOnCompleteListener(Login_Page.this, new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {

                    if (task.isSuccessful()){
                        Toast.makeText(Login_Page.this, "Login Successfully", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(Login_Page.this, Home_Page.class));
                    } else {
                        Toast.makeText(Login_Page.this, "Error" + task.getException(), Toast.LENGTH_SHORT).show();
                    }

                }
            });


    }

    public void signUp(View view){
        startActivity(new Intent(Login_Page.this, Sign_up_Page.class));
    }
}