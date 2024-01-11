package com.example.ahmad_banking;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

public class Splash extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        //getSupportActionBar().hide();

        // Create a new Handler and post a delayed Runnable
        // The Runnable will be executed after 2000 milliseconds (2 seconds)
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                // Create a new Intent to navigate to the Home activity
                Intent intent = new Intent(getApplicationContext(), Home.class);
                startActivity(intent);
                finish(); // Finish the current activity to prevent going back to the splash screen
            }
        }, 2000); // Set the delay in milliseconds (2000ms = 2 seconds)
    }
}
