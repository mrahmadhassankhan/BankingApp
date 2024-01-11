package com.example.ahmad_banking;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Pair;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;

public class Home extends AppCompatActivity {
    Button loginButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Set the activity to fullscreen
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        // Set the layout for the activity
        setContentView(R.layout.activity_home);
    }

    // Call Login Screen
    public void callLoginScreen(View view) {
        // Create an intent to navigate to the Login activity
        Intent intent = new Intent(getApplicationContext(), Login.class);

        // Create a pair for the transition animation
        Pair[] pairs = new Pair[1];
        pairs[0] = new Pair<View, String>(findViewById(R.id.loginBtn), "transition_login");

        // Set the transition animation options
        ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(Home.this, pairs);
        startActivity(intent, options.toBundle());
    }

    // Call Signup Screen
    public void callSignUpScreen(View view) {
        // Create an intent to navigate to the Register activity
        Intent intent = new Intent(getApplicationContext(), Register.class);

        // Create a pair for the transition animation
        Pair[] pairs = new Pair[1];
        pairs[0] = new Pair<View, String>(findViewById(R.id.openNewAccBtn), "transition_signup");

        // Set the transition animation options
        ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(Home.this, pairs);
        startActivity(intent, options.toBundle());
    }
}
