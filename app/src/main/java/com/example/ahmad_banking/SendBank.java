package com.example.ahmad_banking;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

public class SendBank extends AppCompatActivity {
    ImageView sendmoneybackbtn;
    EditText sendaccnum, sendmoneyamount, sendmoneymessage;
    Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_bank);

        // Retrieve UI elements from the layout
        sendmoneybackbtn = findViewById(R.id.sendMoneyBackButton);
        sendaccnum = findViewById(R.id.sendMoney_accNo);
        sendmoneyamount = findViewById(R.id.sendMoney_amount);
        sendmoneymessage = findViewById(R.id.sendMoney_message);

        // Set onClickListener for the back button
        sendmoneybackbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SendBank.this, BankTransferList.class);
                startActivity(intent);
            }
        });
    }

    // Method to validate the entered account number
    private Boolean validateAccountNum() {
        String val = sendaccnum.getText().toString();
        if (val.isEmpty()) {
            sendaccnum.setError("Field cannot be empty");
            return false;
        } else {
            sendaccnum.setError(null);
            return true;
        }
    }

    // Method to validate the entered amount
    private Boolean validateAmount() {
        String val = sendmoneyamount.getText().toString();
        if (val.isEmpty()) {
            sendmoneyamount.setError("Field cannot be empty");
            return false;
        } else {
            sendmoneyamount.setError(null);
            return true;
        }
    }

    // Method to handle the "Send Money" button click
    public void callSendMoneyConfirmScreen(View view) {
        if (!validateAccountNum() | !validateAmount()) {
            return;
        }

        // Retrieve the entered account number, amount, message, and username
        String sendacc = sendaccnum.getText().toString();
        String sendamount = sendmoneyamount.getText().toString();
        String sendmessage = sendmoneymessage.getText().toString();
        String userName = getIntent().getStringExtra("userName");

        // Create an intent to navigate to the confirmation screen and pass the data
        Intent intent = new Intent(getApplicationContext(), SendBankConfirmation.class);
        intent.putExtra("userName", userName);
        intent.putExtra("keyaccnum", sendacc);
        intent.putExtra("keyamount", sendamount);
        intent.putExtra("keymessage", sendmessage);
        startActivity(intent);
    }
}
