package com.example.ahmad_banking;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.ahmad_banking.Database.TransactionHelperClass;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class MoneySendConfirmation extends AppCompatActivity {
    ImageView moneysendconfirmbackbtn;
    public TextView moneysenduserIDTV;
    public TextView moneysendamountTV;
    public TextView moneysendmessageTV;
    Intent intent;
    String userName, recipientID, message;
    int amount,newBalance,balance;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_money_send_confirmation);
        moneysendconfirmbackbtn = findViewById(R.id.MoneyMoneySendConfirmBackButton);
        moneysendamountTV = findViewById(R.id.moneySend_confirm_amountTV);
        moneysenduserIDTV = findViewById(R.id.recipientUserID);
        moneysendmessageTV = findViewById(R.id.moneySend_confirm_messageTV);

        String MoneySenduserID = getIntent().getStringExtra("keyuserID");
        String MoneySendamount = getIntent().getStringExtra("keymoneyamount");
        String MoneySendmessage = getIntent().getStringExtra("keymoneymessage");

        moneysenduserIDTV.setText(MoneySenduserID);
        moneysendamountTV.setText("PKR " + MoneySendamount + ".00");
        moneysendmessageTV.setText(MoneySendmessage);

        intent = getIntent(); // Initialize the intent variable

        moneysendconfirmbackbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MoneySendConfirmation.this, MoneySend.class);
                startActivity(intent);
            }
        });

    }

    public void saveTransactionDetails() {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        userName = intent.getStringExtra("userName");
        recipientID = moneysenduserIDTV.getText().toString();
        message = moneysendmessageTV.getText().toString();
        amount = Integer.parseInt(moneysendamountTV.getText().toString().replace("PKR ", "").replace(".00", ""));

        TransactionHelperClass transaction = new TransactionHelperClass(userName, recipientID, amount, message);
        db.collection("Transactions")
                .add(transaction)
                .addOnSuccessListener(documentReference -> {
                    // Update the balance in the database
                    updateBalance();
                    updateRecipientAccBalance();
                })
                .addOnFailureListener(e -> {
                    Toast.makeText(MoneySendConfirmation.this, "Failed to save transaction", Toast.LENGTH_SHORT).show();
                });
    }

    private void updateBalance() {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        DocumentReference userRef = db.collection("Users").document(userName);
        userRef.get().addOnCompleteListener(task -> {
            if (task.isSuccessful() && task.getResult() != null) {
                DocumentSnapshot document = task.getResult();
                if (document.exists()) {
                    balance = document.getLong("balance").intValue();
                    newBalance = balance - amount;

                    userRef.update("balance", newBalance)
                            .addOnCompleteListener(updateTask -> {
                                if (updateTask.isSuccessful()) {
                                    Toast.makeText(MoneySendConfirmation.this, "Money Sending", Toast.LENGTH_SHORT).show();
                                    finish();
                                } else {
                                    Toast.makeText(MoneySendConfirmation.this, "Failed to sent money", Toast.LENGTH_SHORT).show();
                                }
                                finish(); // Finish the activity
                            });
                }
            }
        });
    }

    private  void updateRecipientAccBalance(){
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        DocumentReference userRef = db.collection("Users").document(recipientID);
        userRef.get().addOnCompleteListener(task -> {
            if (task.isSuccessful() && task.getResult() != null) {
                DocumentSnapshot document = task.getResult();
                if (document.exists()) {
                    balance = document.getLong("balance").intValue();
                    newBalance = balance + amount;

                    userRef.update("balance", newBalance)
                            .addOnCompleteListener(updateTask -> {
                                if (updateTask.isSuccessful()) {
                                    Toast.makeText(MoneySendConfirmation.this, "Money Sent Successfully to Recipient", Toast.LENGTH_SHORT).show();
                                    finish();
                                } else {
                                    Toast.makeText(MoneySendConfirmation.this, "Failed to sent money to Recipient", Toast.LENGTH_SHORT).show();
                                }
                                finish(); // Finish the activity
                            });
                }
            }
        });
    }

    public void callConfirmNextScreen(View view) {
        saveTransactionDetails();
        }
    }


