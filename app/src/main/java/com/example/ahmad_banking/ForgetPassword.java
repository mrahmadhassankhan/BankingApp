package com.example.ahmad_banking;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.provider.Settings;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class ForgetPassword extends AppCompatActivity {
    // Variables
    ImageView screenIcon;
    TextView title, description;
    TextInputLayout userNameText;
    Button fpnextBtn;
    ProgressBar progressBar;

    private FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget_password);

        // Hooks
        screenIcon = findViewById(R.id.forget_password_icon);
        title = findViewById(R.id.forget_password_title);
        description = findViewById(R.id.forget_password_description);
        userNameText = findViewById(R.id.forget_password_user);
        progressBar = findViewById(R.id.progress_bar);

        progressBar.setVisibility(View.GONE);

        // Initialize Firestore
        db = FirebaseFirestore.getInstance();
    }

    // Validates the entered username
    private boolean validateUsername() {
        String val = userNameText.getEditText().getText().toString().trim();

        if (val.isEmpty()) {
            userNameText.setError("Enter valid username");
            return false;
        } else {
            userNameText.setError(null);
            userNameText.setErrorEnabled(false);
            return true;
        }
    }

    // Navigates back to the Login screen
    public void callBackScreenFromForgetPassword(View view) {
        Intent intent = new Intent(ForgetPassword.this, Login.class);
        startActivity(intent);
    }

    // Handles the action when the Next button is clicked
    public void forgetNextBtn(View view) {
        // Check the internet connection
        if (!isConnected()) {
            showCustomDialog();
            return;
        }

        // Validate username
        if (!validateUsername()) {
            return;
        }

        progressBar.setVisibility(View.VISIBLE);

        // Get the entered username
        final String username = userNameText.getEditText().getText().toString().trim();

        DocumentReference checkUserQuery = db.collection("Users").document(username);
        checkUserQuery.get().addOnCompleteListener(task -> {
            if (task.isSuccessful() && task.getResult() != null && task.getResult().exists()) {
                // User exists, navigate to SetNewPassword activity
                Intent intent = new Intent(ForgetPassword.this, SetNewPassword.class);
                intent.putExtra("username", username);
                startActivity(intent);
                progressBar.setVisibility(View.GONE);
            } else {
                progressBar.setVisibility(View.GONE);
                userNameText.setError("No such user exists!");
                userNameText.requestFocus();
            }
        });
    }

    // Checks if the device is connected to the internet
    private boolean isConnected() {
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo wifiConn = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        NetworkInfo mobileConn = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);

        return (wifiConn != null && wifiConn.isConnected()) || (mobileConn != null && mobileConn.isConnected());
    }

    // Shows a custom dialog to prompt the user to connect to the internet
    private void showCustomDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(ForgetPassword.this);
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
}
