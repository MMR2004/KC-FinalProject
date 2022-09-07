package com.example.final_project.activites;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.final_project.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class Sign_up_Page extends AppCompatActivity {

    EditText name, email, password;
    private FirebaseAuth auth;

    SharedPreferences sharedPreferences;

    Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up_page);

        button = findViewById(R.id.button);

        //getSupportActionBar().hide();

        auth = FirebaseAuth.getInstance();

        if (auth.getCurrentUser() != null){

            startActivity(new Intent(Sign_up_Page.this, Home_Page.class));
            finish();
        }
        name = findViewById(R.id.nameSignup);
        email = findViewById(R.id.emailSignup);
        password = findViewById(R.id.passwordSignup);

        sharedPreferences = getSharedPreferences("onBoardingScreen", MODE_PRIVATE);

        boolean isFirstTime = sharedPreferences.getBoolean("firstTime", true);

        if (isFirstTime){
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putBoolean("firstTime", false);
            editor.commit();

            Intent intent = new Intent(Sign_up_Page.this, OnBoardingActivity.class);
            startActivity(intent);
            finish();
        }

    }

    public void signup(View view){

        String userName = name.getText().toString();
        String userEmail = email.getText().toString();
        String userPassword = password.getText().toString();

        if (TextUtils.isEmpty(userName)){
            //Toast.makeText(this, "Enter Name!", Toast.LENGTH_LONG).show();
            name.setError("Enter Name!");
            return;
        }

        if (TextUtils.isEmpty(userEmail)){
            //Toast.makeText(this, "Enter Email Address!", Toast.LENGTH_LONG).show();
            email.setError("Enter Email Address!");
            return;
        }

        if (TextUtils.isEmpty(userPassword)){
            //Toast.makeText(this, "Enter Password!", Toast.LENGTH_LONG).show();
            password.setError("Enter Password!");
            return;
        }

        if (userPassword.length() < 6){
            //Toast.makeText(this, "Password to short, enter minimum 6 characters!", Toast.LENGTH_SHORT).show();
            password.setError("Password to short, enter minimum 6 characters!");
            return;
        }
        auth.createUserWithEmailAndPassword(userEmail, userPassword)
                .addOnCompleteListener(Sign_up_Page.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        if (task.isSuccessful()){
                            Toast.makeText(Sign_up_Page.this, "Successfully Register", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(Sign_up_Page.this, Home_Page.class));
                        }else {
                            Toast.makeText(Sign_up_Page.this, "Registration Failed" + task.getException(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    public void signin(View view){
        startActivity(new Intent(Sign_up_Page.this, Login_Page.class));
    }

}