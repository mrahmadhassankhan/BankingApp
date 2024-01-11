package com.example.ahmad_banking;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class Deposit extends AppCompatActivity {

    EditText deposit_amount;
    String userName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_deposit);

        // Retrieve the deposit_amount EditText from the layout
        deposit_amount = findViewById(R.id.deposit_amount);

        // Retrieve the username from the intent
        Intent intent = getIntent();
        userName = intent.getStringExtra("userName");
    }

    // Method to navigate back to the Savings activity
    public void callDepositBackBtn(View view) {
        Intent intent = new Intent(Deposit.this, Savings.class);
        startActivity(intent);
    }

    // Method to validate the entered amount
    private Boolean validateAmount() {
        String val = deposit_amount.getText().toString();
        if (val.isEmpty()) {
            deposit_amount.setError("Field cannot be empty");
            return false;
        } else {
            deposit_amount.setError(null);
            return true;
        }
    }

    // Method to process the deposit and navigate to the Dashboard activity
    public void callDepositDashboard(View view) {
        if (!validateAmount()) {
            return;
        }

        // Convert the entered amount to an integer
        int deposit_Amount = Integer.parseInt(deposit_amount.getText().toString());

        // Create a new Intent to navigate to the Dashboard activity
        Intent intent = new Intent(Deposit.this, Dashboard.class);
        intent.putExtra("depositAmount", deposit_Amount);
        intent.putExtra("userID", userName);
        startActivity(intent);

        // Show a toast message indicating the successful deposit
        Toast.makeText(Deposit.this, "Deposit Successfully Processed", Toast.LENGTH_SHORT).show();
    }
}
