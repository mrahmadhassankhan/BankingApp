package com.example.ahmad_banking;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class Savings extends AppCompatActivity {
    private String userName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_savings);

        // Retrieve the username from the intent
        Intent intent = getIntent();
        if (intent != null) {
            userName = intent.getStringExtra("userName");
        }
    }

    // Method to navigate to the Deposit activity
    public void callDeposit(View view) {
        Intent intent = new Intent(this, Deposit.class);
        intent.putExtra("userName", userName);
        startActivity(intent);
    }

    // Method to navigate to the Withdraw activity
    public void callWithdraw(View view) {
        Intent intent = new Intent(this, Withdraw.class);
        intent.putExtra("userName", userName);
        startActivity(intent);
    }
}
