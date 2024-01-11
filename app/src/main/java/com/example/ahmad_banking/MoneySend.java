package com.example.ahmad_banking;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

public class MoneySend extends AppCompatActivity {
    ImageView moneysendbackbtn;
    EditText moneysenduserID, moneysendamount, moneysendmessage;
    Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_money_send);

        // Retrieve UI elements from the layout
        moneysendbackbtn = findViewById(R.id.moneySendBackButton);
        moneysenduserID = findViewById(R.id.moneySend_userID);
        moneysendamount = findViewById(R.id.moneySend_amount);
        moneysendmessage = findViewById(R.id.moneySend_message);

        // Set click listener for the back button
        moneysendbackbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Navigate back to the Dashboard activity
                Intent intent = new Intent(MoneySend.this, Dashboard.class);
                startActivity(intent);
                finish();
            }
        });
    }

    // Validate Account Number
    private Boolean validateAccountNum() {
        String val = moneysenduserID.getText().toString();
        if (val.isEmpty()) {
            moneysenduserID.setError("Field cannot be empty");
            return false;
        } else {
            moneysenduserID.setError(null);
            return true;
        }
    }

    // Validate Amount
    private Boolean validateAmount() {
        String val = moneysendamount.getText().toString();
        if (val.isEmpty()) {
            moneysendamount.setError("Field cannot be empty");
            return false;
        } else {
            moneysendamount.setError(null);
            return true;
        }
    }

    public void callMoneySendConfirmScreen(View view) {
        if (!validateAccountNum() || !validateAmount()) {
            return;
        }

        // Get the necessary data from the UI elements and the previous intent
        String userName = getIntent().getStringExtra("userName");
        String userBalance = getIntent().getStringExtra("balance");
        String MoneySenduserID = moneysenduserID.getText().toString();
        String MoneySendamount = moneysendamount.getText().toString();
        String MoneySendmessage = moneysendmessage.getText().toString();

        // Create an intent to navigate to the MoneySendConfirmation activity
        intent = new Intent(getApplicationContext(), MoneySendConfirmation.class);
        intent.putExtra("userName", userName);
        intent.putExtra("balance", userBalance);
        intent.putExtra("keyuserID", MoneySenduserID);
        intent.putExtra("keymoneyamount", MoneySendamount);
        intent.putExtra("keymoneymessage", MoneySendmessage);
        startActivity(intent);
    }
}
