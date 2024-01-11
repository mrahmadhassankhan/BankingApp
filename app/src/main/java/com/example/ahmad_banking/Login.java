package com.example.ahmad_banking;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.provider.Settings;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.ahmad_banking.Database.UserHelperClass;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

/**
 * This activity handles the user login process.
 */
public class Login extends AppCompatActivity {
    ImageView loginBackBtn;
    ProgressBar progressBar;
    TextInputLayout userID, password;

    FirebaseAuth auth;
    FirebaseUser firebaseUser;

    private FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // Initialize UI elements
        progressBar = findViewById(R.id.progress_bar);
        loginBackBtn = findViewById(R.id.loginBackButton);
        userID = findViewById(R.id.login_userID);
        password = findViewById(R.id.login_password);
        progressBar.setVisibility(View.GONE);

        // Set click listener for back button
        loginBackBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Login.this, Home.class);
                startActivity(intent);
            }
        });

        // Initialize Firestore
        db = FirebaseFirestore.getInstance();
    }

    /**
     * Validates the user ID input field.
     *
     * @return true if the input is valid, false otherwise
     */
    private boolean validateUserID() {
        String val = userID.getEditText().getText().toString();

        if (val.isEmpty()) {
            userID.setError("Enter valid User ID");
            return false;
        } else {
            userID.setError(null);
            userID.setErrorEnabled(false);
            return true;
        }
    }

    /**
     * Validates the password input field.
     *
     * @return true if the input is valid, false otherwise
     */
    private Boolean validatePassword() {
        String val = password.getEditText().getText().toString();
        if (val.isEmpty()) {
            password.setError("Field cannot be empty");
            return false;
        } else {
            password.setError(null);
            password.setErrorEnabled(false);
            return true;
        }
    }

    /**
     * Called when the user clicks the "Login" button.
     * Validates the input fields, checks internet connectivity, and performs user authentication.
     *
     * @param view The clicked view
     */
    public void loginUser(View view) {
        if (!isConnected(this)) {
            showCustomDialog();
        }

        if (!validateUserID() || !validatePassword()) {
            return;
        } else {
            isUser();
        }
    }

    /**
     * Checks if the device is connected to the internet.
     *
     * @param login The Login activity context
     * @return true if the device is connected to the internet, false otherwise
     */
    private boolean isConnected(Login login) {
        ConnectivityManager connectivityManager = (ConnectivityManager) login.getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo wifiConn = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        NetworkInfo mobileConn = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);

        return (wifiConn != null && wifiConn.isConnected()) || (mobileConn != null && mobileConn.isConnected());
    }

    /**
     * Shows a custom dialog to prompt the user to connect to the internet.
     */
    private void showCustomDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(Login.this);
        builder.setMessage("Please connect to the internet to proceed further")
                .setCancelable(false)
                .setPositiveButton("Connect", (dialogInterface, i) -> startActivity(new Intent(Settings.ACTION_WIFI_SETTINGS)))
                .setNegativeButton("Cancel", (dialogInterface, i) -> {
                    startActivity(new Intent(getApplicationContext(), Home.class));
                    finish();
                });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    /**
     * Performs user authentication by checking if the user exists in the Firestore database.
     */
    private void isUser() {
        final String userEnteredUsername = userID.getEditText().getText().toString().trim();
        final String userEnteredPassword = password.getEditText().getText().toString().trim();

        Query checkUser = db.collection("Users").whereEqualTo("userID", userEnteredUsername);
        checkUser.get().addOnCompleteListener(task -> {
            if (task.isSuccessful() && task.getResult() != null && !task.getResult().isEmpty()) {
                DocumentSnapshot document = task.getResult().getDocuments().get(0);
                String passwordFromDB = document.getString("password");
                if (passwordFromDB != null && passwordFromDB.equals(userEnteredPassword)) {
                    String userIDFromDB = document.getString("userID");
                    String firstnameFromDB = document.getString("firstname");
                    String lastnameFromDB = document.getString("lastname");
                    String emailFromDB = document.getString("email");
                    String phoneNoFromDB = document.getString("phoneNo");

                    // Start Dashboard activity with user data
                    Intent intent = new Intent(getApplicationContext(), Dashboard.class);
                    intent.putExtra("userID", userIDFromDB);
                    intent.putExtra("firstname", firstnameFromDB);
                    intent.putExtra("lastname", lastnameFromDB);
                    intent.putExtra("email", emailFromDB);
                    intent.putExtra("phoneNo", phoneNoFromDB);
                    intent.putExtra("password", passwordFromDB);
                    startActivity(intent);

                    progressBar.setVisibility(View.VISIBLE);
                } else {
                    password.setError("Wrong Password");
                    password.requestFocus();
                }
            } else {
                userID.setError("No such user exists");
                userID.requestFocus();
            }
        });
    }

    /**
     * Called when the user clicks the "Sign Up" button.
     * Starts the Register activity.
     *
     * @param view The clicked view
     */
    public void callSignUpFromLogin(View view) {
        startActivity(new Intent(getApplicationContext(), Register.class));
    }

    /**
     * Called when the user clicks the "Forgot Password" button.
     * Starts the ForgetPassword activity.
     *
     * @param view The clicked view
     */
    public void callForgotPassword(View view) {
        startActivity(new Intent(getApplicationContext(), ForgetPassword.class));
    }
}
