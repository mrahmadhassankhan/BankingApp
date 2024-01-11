package com.example.ahmad_banking;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ahmad_banking.Database.BankTransactionHelperClass;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class SendBankConfirmation extends AppCompatActivity {
    ImageView sendmoneyconfirmbackbtn;
    TextView sendaccnumTV, sendamountTV, sendmessageTV;
    Intent intent;
    String message, userName, recipientAccNo;
    int amount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_bank_confirmation);

        // Retrieve UI elements from the layout
        sendmoneyconfirmbackbtn = findViewById(R.id.sendMoneyConfirmBackButton);
        sendamountTV = findViewById(R.id.confirm_amountTV);
        sendaccnumTV = findViewById(R.id.recipientAccNum);
        sendmessageTV = findViewById(R.id.confirm_messageTV);

        // Retrieve data from the intent
        userName = getIntent().getStringExtra("userName");
        String sendacc = getIntent().getStringExtra("keyaccnum");
        String sendamount = getIntent().getStringExtra("keyamount");
        String sendmessage = getIntent().getStringExtra("keymessage");

        // Set the data in the corresponding TextViews
        sendaccnumTV.setText(sendacc);
        sendamountTV.setText("PKR " + sendamount + ".00");
        sendmessageTV.setText(sendmessage);

        // Set onClickListener for the back button
        sendmoneyconfirmbackbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SendBankConfirmation.this, SendBank.class);
                startActivity(intent);
            }
        });
    }

    // Method to save transaction details in the database
    private void saveTransactionDetails() {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        recipientAccNo = sendaccnumTV.getText().toString();
        message = sendmessageTV.getText().toString();
        userName = getIntent().getStringExtra("userName");
        amount = Integer.parseInt(sendamountTV.getText().toString().replace("PKR ", "").replace(".00", ""));
        BankTransactionHelperClass transaction = new BankTransactionHelperClass(userName, recipientAccNo, amount, message);

        db.collection("Bank Transactions")
                .add(transaction)
                .addOnSuccessListener(documentReference -> {
                    // Update the balance in the database
                    updateBalance();
                })
                .addOnFailureListener(e -> {
                    Toast.makeText(SendBankConfirmation.this, "Failed to save transaction", Toast.LENGTH_SHORT).show();
                });
    }

    // Method to update the balance in the database
    private void updateBalance() {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        DocumentReference userRef = db.collection("Users").document(userName);

        userRef.get().addOnCompleteListener(task -> {
            if (task.isSuccessful() && task.getResult() != null) {
                DocumentSnapshot document = task.getResult();
                if (document.exists()) {
                    int balance = document.getLong("balance").intValue();
                    balance -= amount;

                    userRef.update("balance", balance)
                            .addOnCompleteListener(updateTask -> {
                                if (updateTask.isSuccessful()) {
                                    Toast.makeText(SendBankConfirmation.this, "Transfer Successful", Toast.LENGTH_SHORT).show();
                                    finish();
                                } else {
                                    Toast.makeText(SendBankConfirmation.this, "Failed to transfer money", Toast.LENGTH_SHORT).show();
                                }
                                finish(); // Finish the activity
                            });
                }
            }
        });
    }

    // Method to handle the "Next" button click
    public void callConfirmNextScreen(View view) {
        saveTransactionDetails();
    }
}
