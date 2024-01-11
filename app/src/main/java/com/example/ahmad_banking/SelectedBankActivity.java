package com.example.ahmad_banking;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

public class SelectedBankActivity extends AppCompatActivity {

    TextView tvBank;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_selected_bank);

        // Retrieve UI elements from the layout
        tvBank = findViewById(R.id.selectedBank);

        // Get the intent that started this activity
        Intent intent = getIntent();

        // Check if the intent contains extras
        if (intent.getExtras() != null) {
            // Retrieve the BankModel object from the extras
            BankModel bankModel = (BankModel) intent.getSerializableExtra("data");

            // Set the bank name in the TextView
            tvBank.setText(bankModel.getBankName());

            // Add the bank name as an extra in the intent
            intent.putExtra("bankName", tvBank.getText().toString());
        }
    }
}
