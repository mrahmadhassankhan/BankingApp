package com.example.ahmad_banking;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.text.DecimalFormat;

public class CurrencyConvert extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    private EditText valueET;
    private TextView convertedAmount;
    private Spinner spinner;

    private Button convert_btn;
    private String[] currencies = {"USD", "EUR", "GBP", "JPY"};
    private double[] exchangeRates = {0.01, 0.008, 0.007, 1.10};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_currency_convert);

        // Initialize UI elements
        valueET = findViewById(R.id.value_ET);
        convertedAmount = findViewById(R.id.converted_amount);
        spinner = findViewById(R.id.spinner);
        convert_btn = findViewById(R.id.convert_Btn);

        // Set up the spinner with currency options
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, currencies);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);

        // Set up the button click listener to convert currency
        convert_btn.setOnClickListener(v -> {
            convertCurrency();
        });
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//        convertCurrency();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    public void convertCurrency(View view) {
        convertCurrency();
    }

    // Converts the currency based on the selected exchange rate
    private void convertCurrency() {
        String input = valueET.getText().toString();
        if (input.isEmpty()) {
            convertedAmount.setText("");
            return;
        }

        double inputValue = Double.parseDouble(input);
        double exchangeRate = exchangeRates[spinner.getSelectedItemPosition()];
        double convertedValue = inputValue / exchangeRate;

        // Format the converted value with a decimal format
        DecimalFormat decimalFormat = new DecimalFormat("#,##0.00");
        String formattedValue = decimalFormat.format(convertedValue);

        convertedAmount.setText(formattedValue);
    }

    // Navigates back to the currency dashboard
    public void callCurrencyDashboard(View view) {
        finish();
    }
}
