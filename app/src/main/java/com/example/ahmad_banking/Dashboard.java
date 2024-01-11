package com.example.ahmad_banking;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Objects;


public class Dashboard extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    // Variables
    static final float END_SCALE = 0.7f;

    TextView availablebalanceholder, user_Name;

    // Drawer Menu
    DrawerLayout drawerLayout;
    NavigationView navigationView;
    ImageView menuIcon, banktransferCard, sendmoneyCard, savingsCard, currencyConCard;
    LinearLayout contentView;

    Intent intent;
    String userName;
    int withdrawAmount;
    int depositAmount;
    Integer balance ;
    private  FirebaseFirestore firestore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        // Get the intent and extract data from it
        intent = getIntent();
        userName = intent.getStringExtra("userID");
        withdrawAmount = intent.getIntExtra("withdrawAmount", 0);
        depositAmount = intent.getIntExtra("depositAmount",0);
        firestore = FirebaseFirestore.getInstance();

        // Initialize UI elements
        menuIcon = findViewById(R.id.menu_icon);
        contentView = findViewById(R.id.content);
        availablebalanceholder = findViewById(R.id.available_balance_holder);
        user_Name = findViewById(R.id.user_name);

        // Initialize drawer menu elements
        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.navigation_view);
        banktransferCard = findViewById(R.id.bankTransferCard);
        sendmoneyCard = findViewById(R.id.sendMoneyCard);
        savingsCard = findViewById(R.id.savingsCard);
        currencyConCard = findViewById(R.id.currencyConverterCard);

        // Set up navigation drawer
        navigationDrawer();

        // Set click listeners for card views
        currencyConCard.setOnClickListener(view -> {
            Intent intent = new Intent(getApplicationContext(), CurrencyConvert.class);
            intent.putExtra("userName", userName);
            startActivity(intent);
        });

        banktransferCard.setOnClickListener(view -> {
            Intent intent = new Intent(getApplicationContext(), BankTransferList.class);
            intent.putExtra("userName", userName);
            startActivity(intent);
        });

        sendmoneyCard.setOnClickListener(view -> {
            Intent intent = new Intent(getApplicationContext(), MoneySend.class);
            intent.putExtra("userName", userName);
            intent.putExtra("balance",balance);
            startActivity(intent);
        });

        savingsCard.setOnClickListener(view -> {
            Intent intent = new Intent(getApplicationContext(), Savings.class);
            intent.putExtra("userName", userName);
            startActivity(intent);
        });

        // Show username and balance
        showUsername();
        showBalance();

        // Process withdraw amount if available
        if (withdrawAmount > 0 && withdrawAmount <= 99999999) {
            processWithdraw();
            showBalance();
        }

        // Process deposit amount if available
        if (depositAmount > 0 && depositAmount <=99999999) {
            processDeposit();
            showBalance();
        }
    }

    // Method to process withdraw amount
    private void processWithdraw() {
        // Get a reference to the "Users" collection in Firestore
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        DocumentReference userRef = db.collection("Users").document(userName);

        // Retrieve the balance field from the user document
        userRef.get().addOnCompleteListener(task -> {
            if (task.isSuccessful() && task.getResult() != null) {
                DocumentSnapshot document = task.getResult();
                if (document.exists()) {
                    // Retrieve the balance value from the document
                    int balance = Objects.requireNonNull(document.getLong("balance")).intValue();
                    // Deduct the withdraw amount from the balance
                    balance -= withdrawAmount;

                    // Update the balance in the document
                    userRef.update("balance", balance)
                            .addOnCompleteListener(updateTask -> {
                                if (updateTask.isSuccessful()) {
                                    showBalance();
                                    Toast.makeText(Dashboard.this, "Withdraw Successful", Toast.LENGTH_SHORT).show();
                                } else {
                                    Toast.makeText(Dashboard.this, "Failed to update balance", Toast.LENGTH_SHORT).show();
                                }
                            });
                }
            }
        });
    }

    // Method to process deposit amount
    private void processDeposit() {
        // Get a reference to the "Users" collection in Firestore
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        CollectionReference collectionReference = db.collection("Users");
        DocumentReference userRef = collectionReference.document(userName);

        // Retrieve the balance field from the user document
        userRef.get().addOnCompleteListener(task -> {
            if (task.isSuccessful() && task.getResult() != null) {
                DocumentSnapshot document = task.getResult();
                if (document.exists()) {
                    // Retrieve the balance value from the document
                    balance = Objects.requireNonNull(document.getLong("balance")).intValue();
                    // Add the amount to the balance
                    balance += depositAmount;

                    // Update the balance in the document
                    Integer finalBalance = balance;
                    userRef.update("balance", balance)
                            .addOnCompleteListener(updateTask -> {
                                if (updateTask.isSuccessful()) {
                                    showBalance();
                                    // Format the balance using the string resource
                                    String formattedBalance = getString(R.string.balance_format, finalBalance);

                                    // Set the formatted balance in the TextView
                                    availablebalanceholder.setText(formattedBalance);

                                    Toast.makeText(Dashboard.this, "Deposit Successful", Toast.LENGTH_SHORT).show();
                                } else {
                                    Toast.makeText(Dashboard.this, "Failed to update balance", Toast.LENGTH_SHORT).show();
                                }
                            });
                }
            }
        });
    }

    // Method to show the username
    public void showUsername() {
        // Get a reference to the "Users" collection in Firestore
        FirebaseFirestore db = FirebaseFirestore.getInstance();

        if (userName != null) {
            DocumentReference userRef = db.collection("Users").document(userName);

            // Retrieve the username field from the user document
            userRef.get().addOnCompleteListener(task -> {
                if (task.isSuccessful() && task.getResult() != null) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        // Retrieve the username value from the document
                        String username = document.getString("userID");
                        // Format the username using the string resource
                        String formattedUsername = getString(R.string.username_format, username);

                        // Set the formatted username in the TextView
                        user_Name.setText(formattedUsername);
                    }
                }
            });
        }
    }

    // Method to show the balance
    public void showBalance() {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        if (userName != null) {
            DocumentReference userRef = db.collection("Users").document(userName);
            userRef.get().addOnCompleteListener(task -> {
                if (task.isSuccessful() && task.getResult() != null) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        // Retrieve the current balance from the document
                        int balance = Objects.requireNonNull(document.getLong("balance")).intValue();

                        // Display the current balance
                        String formattedBalance = getString(R.string.balance_format, balance);
                        runOnUiThread(() ->  availablebalanceholder.setText(formattedBalance));
                    }
                }
            });
        }
    }

    // Method to handle navigation drawer functionality
    private void navigationDrawer() {
        // Navigation Drawer
        navigationView.bringToFront();
        navigationView.setNavigationItemSelectedListener(this);

        menuIcon.setOnClickListener(v -> {
            if (drawerLayout.isDrawerVisible(GravityCompat.START))
                drawerLayout.closeDrawer(GravityCompat.START);
            else drawerLayout.openDrawer(GravityCompat.START);
        });

        animateNavigationDrawer();
    }

    // Method to animate navigation drawer
    private void animateNavigationDrawer() {
        drawerLayout.addDrawerListener(new DrawerLayout.SimpleDrawerListener() {
            @Override
            public void onDrawerSlide(@NonNull View drawerView, float slideOffset) {
                // Scale the View based on current slide offset
                final float diffScaledOffset = slideOffset * (1 - END_SCALE);
                final float offsetScale = 1 - diffScaledOffset;
                contentView.setScaleX(offsetScale);
                contentView.setScaleY(offsetScale);

                // Translate the View, accounting for the scaled width
                final float xOffset = drawerView.getWidth() * slideOffset;
                final float xOffsetDiff = contentView.getWidth() * diffScaledOffset / 2;
                final float xTranslation = xOffset - xOffsetDiff;
                contentView.setTranslationX(xTranslation);
            }
        });
    }

    // Method to handle back button press
    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerVisible(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    // Method to handle sign out functionality
    public void signOut() {
        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        firebaseAuth.signOut();

        // Redirect the user to the Home activity
        Intent intent = new Intent(getApplicationContext(), Home.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        finish();
    }

    // Method to delete the user account
    private void deleteUser(String username) {
        DocumentReference userRef = firestore.collection("Users").document(username);
        userRef.delete()
                .addOnSuccessListener(aVoid -> {
                    Toast.makeText(Dashboard.this, "Account deleted successfully", Toast.LENGTH_SHORT).show();
                    finish();
                })
                .addOnFailureListener(e -> {
                    Toast.makeText(Dashboard.this, "Failed to delete account", Toast.LENGTH_SHORT).show();
                    finish();
                });
    }

    // Method to handle navigation drawer item selection
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.deleteAccount) {
            deleteUser(userName);
            Intent intent1 = new Intent(Dashboard.this, Login.class);
            startActivity(intent1);
        } else if (id == R.id.logout) {
            signOut();
        }

        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }
}
