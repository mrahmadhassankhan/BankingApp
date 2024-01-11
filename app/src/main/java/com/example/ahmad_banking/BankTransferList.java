package com.example.ahmad_banking;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import java.util.ArrayList;
import java.util.List;

public class BankTransferList extends AppCompatActivity implements BankAdapter.SelectedBank {
    Toolbar toolbar;
    RecyclerView recyclerView;
    List<BankModel> bankModelList = new ArrayList<>();
    Intent intent;
    String username;

    String[] banknames = {
            "National Bank of Pakistan",
            "United Bank Limited",
            "Habib Bank Limited",
            "Meezan Bank Limited",
            "Allied Bank Limited",
            "Bank Alfalah Limited",
            "Faysal Bank Limited",
            "Askari Bank Limited",
            "Bank of Punjab",
            "Sindh Bank Limited",
            "Bank Al-Habib Limited",
            "Standard Chartered Bank Limited",
            "MCB Bank Limited",
            "BankIslami Pakistan Limited",
            "Dubai Islamic Bank Pakistan Limited",
            "Soneri Bank Limited",
            "The Bank of Khyber",
            "Silkbank Limited",
            "JS Bank Limited",
            "Summit Bank Limited"
    };

    BankAdapter bankAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bank_transfer_list);

        // Initialize UI elements
        intent = getIntent();
        recyclerView = findViewById(R.id.recyclerview);
        toolbar = findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("");

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));

        // Populate the bankModelList with BankModel objects
        for (String s : banknames) {
            BankModel bankModel = new BankModel(s);
            bankModelList.add(bankModel);
        }

        // Set up the BankAdapter and attach it to the RecyclerView
        bankAdapter = new BankAdapter(bankModelList, this);
        recyclerView.setAdapter(bankAdapter);
    }

    @Override
    public void selectedBank(BankModel bankModel) {
        // Get the selected bank and navigate to the SendBank activity
        username = intent.getStringExtra("userName");
        intent = new Intent(BankTransferList.this, SendBank.class);
        intent.putExtra("userName", username);
        intent.putExtra("data", bankModel);
        startActivity(intent);
    }


    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        // Handle menu item clicks
        int id = item.getItemId();

        return super.onOptionsItemSelected(item);
    }
}
