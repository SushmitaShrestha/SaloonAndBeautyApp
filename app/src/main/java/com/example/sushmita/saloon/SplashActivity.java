package com.example.sushmita.saloon;

/**
 * Sushmita Shrestha -2019445
 */

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.example.sushmita.saloon.login.LoginActivity;
import com.google.firebase.auth.FirebaseAuth;

public class SplashActivity extends AppCompatActivity {

    public static int SPLASH_TIME_OUT = 2000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent;
                if (FirebaseAuth.getInstance().getCurrentUser() == null) {
                    intent = new Intent(SplashActivity.this, LoginActivity.class);
                } else {
                    intent = new Intent(SplashActivity.this, WelcomeActivity.class);
                }
                startActivity(intent);
                finish();
            }
        }, SPLASH_TIME_OUT);
    }
}
