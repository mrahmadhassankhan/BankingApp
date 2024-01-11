package com.example.ahmad_banking;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkCapabilities;
import android.os.Bundle;
import android.provider.Settings;
import android.provider.Telephony;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ahmad_banking.Database.UserHelperClass;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Objects;

public class Register extends AppCompatActivity {
    // Declare UI elements
    ImageView backBtn;
    Button login;
    Button regBtn;
    TextView titleText;

    TextInputLayout regUserID, regfirstname, reglastname, regemail, regphonenum, regpassword;

    // Account Balance on Registration
    int balance = 0;
    private FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        // Hooks for UI elements
        backBtn = findViewById(R.id.SignupBackButton);
        login = findViewById(R.id.Signup_login_btn);
        regBtn = findViewById(R.id.reg_btn);
        titleText = findViewById(R.id.Signup_title_text);

        // Hooks to all XML elements in activity_sign_up.xml
        regUserID = findViewById(R.id.userID);
        regfirstname = findViewById(R.id.firstName);
        reglastname = findViewById(R.id.lastName);
        regemail = findViewById(R.id.email);
        regphonenum = findViewById(R.id.phoneNo);
        regpassword = findViewById(R.id.password);

        // Back to Welcome Screen (Home)
        backBtn.setOnClickListener(view -> {
            Intent intent = new Intent(Register.this, Home.class);
            startActivity(intent);
        });

        // Initialize Firestore
        db = FirebaseFirestore.getInstance();
    }

    /*
     Validation Functions
     */

    // Validate User ID field
    private Boolean validateUserID() {
        String val = Objects.requireNonNull(regUserID.getEditText()).getText().toString();

        if (val.isEmpty()) {
            regUserID.setError("Field cannot be empty");
            return false;
        } else {
            regUserID.setError(null);
            regUserID.setErrorEnabled(false);
            return true;
        }
    }

    // Validate First Name field
    private Boolean validateFirstName() {
        String val = Objects.requireNonNull(regfirstname.getEditText()).getText().toString();

        if (val.isEmpty()) {
            regfirstname.setError("Field cannot be empty");
            return false;
        } else {
            regfirstname.setError(null);
            regfirstname.setErrorEnabled(false);
            return true;
        }
    }

    // Validate Last Name field
    private Boolean validateLastName() {
        String val = Objects.requireNonNull(reglastname.getEditText()).getText().toString();

        if (val.isEmpty()) {
            reglastname.setError("Field cannot be empty");
            return false;
        } else {
            reglastname.setError(null);
            reglastname.setErrorEnabled(false);
            return true;
        }
    }

    // Validate Email field
    private boolean validateEmail() {
        String val = Objects.requireNonNull(regemail.getEditText()).getText().toString().trim();
        String checkEmail = "[a-zA-Z0-9._-]+@[a-z]+.+[a-z]+";

        if (val.isEmpty()) {
            regemail.setError("Field cannot be empty");
            return false;
        } else if (!val.matches(checkEmail)) {
            regemail.setError("Invalid Email!");
            return false;
        } else {
            regemail.setError(null);
            regemail.setErrorEnabled(false);
            return true;
        }
    }

    // Validate Phone Number field
    private boolean validatePhoneNumber() {
        String val = Objects.requireNonNull(regphonenum.getEditText()).getText().toString();

        if (val.isEmpty()) {
            regphonenum.setError("Field cannot be empty");
            return false;
        } else {
            regphonenum.setError(null);
            regphonenum.setErrorEnabled(false);
            return true;
        }
    }

    // Validate Password field
    private boolean validatePassword() {
        String val = Objects.requireNonNull(regpassword.getEditText()).getText().toString().trim();
        String checkPassword = "^(?=.*[a-zA-Z])(?=\\S+$).{8,}$";

        if (val.isEmpty()) {
            regpassword.setError("Field cannot be empty");
            return false;
        } else if (!val.matches(checkPassword)) {
            regpassword.setError("Password should contain at least 8 characters!");
            return false;
        } else {
            regpassword.setError(null);
            regpassword.setErrorEnabled(false);
            return true;
        }
    }

    // Register user button click event
    public void registerUser(View view) {
        // Check the internet connection
        if (!isConnected()) {
            showCustomDialog();
            return;
        }

        // Validate input fields
        if (!validateUserID() || !validateFirstName() || !validateLastName() || !validateEmail() ||
                !validatePhoneNumber() || !validatePassword()) {
            return;
        }

        // Get all the values from input fields
        String userID = Objects.requireNonNull(regUserID.getEditText().getText().toString());
        String firstname = Objects.requireNonNull(regfirstname.getEditText()).getText().toString();
        String lastname = Objects.requireNonNull(reglastname.getEditText()).getText().toString();
        String email = Objects.requireNonNull(regemail.getEditText()).getText().toString();
        String phoneNo = Objects.requireNonNull(regphonenum.getEditText()).getText().toString();
        String password = Objects.requireNonNull(regpassword.getEditText()).getText().toString();

        // Create a new user document in a separate collection within the "Users" collection
        UserHelperClass user = new UserHelperClass(userID, firstname, lastname, email, phoneNo, password, this.balance);
        db.collection("Users").document(userID) // Use the user ID as the document ID
                .set(user)
                .addOnSuccessListener(aVoid -> {
                    // Document added successfully
                    Intent intent = new Intent(getApplicationContext(), Dashboard.class);
                    intent.putExtra("userID", userID);
                    intent.putExtra("firstname", firstname);
                    intent.putExtra("lastname", lastname);
                    intent.putExtra("email", email);
                    intent.putExtra("phoneNo", phoneNo);
                    intent.putExtra("password", password);
                    startActivity(intent);
                    Toast.makeText(getApplicationContext(), "Registered Successfully", Toast.LENGTH_SHORT).show();
                })
                .addOnFailureListener(e -> {
                    // Handle failure
                });
    }


    // Internet connectivity

    // Check if the device is connected to the internet
    private boolean isConnected() {
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkCapabilities networkCapabilities = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
            networkCapabilities = connectivityManager.getNetworkCapabilities(connectivityManager.getActiveNetwork());
        }

        if (networkCapabilities != null) {
            return networkCapabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET);
        }

        return false;
    }

    // Show a custom dialog to prompt user to connect to the internet
    private void showCustomDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(Register.this);
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

    // Login Existing
    public void callLoginFromSignUp(View view) {
        startActivity(new Intent(Register.this, Login.class));
    }
}
