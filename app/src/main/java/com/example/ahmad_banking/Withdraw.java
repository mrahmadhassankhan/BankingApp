package com.example.ahmad_banking;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class Withdraw extends AppCompatActivity {

    public EditText withdraw_amount;
    public Intent intent;
    String userName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_withdraw);

        // Retrieve the withdraw_amount EditText from the layout
        withdraw_amount = findViewById(R.id.withdraw_amount);

        // Retrieve the username from the intent
        Intent intent = getIntent();
        userName = intent.getStringExtra("userName");
    }

    // Method to navigate back to the Savings activity
    public void callWithdrawBackBtn(View view) {
        Intent intent = new Intent(Withdraw.this, Savings.class);
        startActivity(intent);
    }

    // Method to validate the entered amount
    public Boolean validateAmount() {
        String val = withdraw_amount.getText().toString();
        if (val.isEmpty()) {
            withdraw_amount.setError("Field cannot be empty");
            return false;
        } else {
            withdraw_amount.setError(null);
            return true;
        }
    }

    // Method to process the withdrawal and navigate to the Dashboard activity
    public void callWithdrawDashboard(View view) {
        if (!validateAmount()) {
            return;
        }

        // Convert the entered amount to an integer
        int withdrawAmount = Integer.parseInt(withdraw_amount.getText().toString());

        // Create a new Intent to navigate to the Dashboard activity
        Intent intent = new Intent(Withdraw.this, Dashboard.class);
        intent.putExtra("withdrawAmount", withdrawAmount);
        intent.putExtra("userID", userName);
        startActivity(intent);

        // Show a toast message indicating the successful withdrawal
        Toast.makeText(Withdraw.this, "Withdraw Successfully Processed", Toast.LENGTH_SHORT).show();

        // Finish the current activity
        finish();
    }
}
