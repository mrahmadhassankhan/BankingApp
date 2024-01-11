package com.example.ahmad_banking;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

public class SetNewPassword extends AppCompatActivity {
    // Variables
    TextInputEditText newPassword, confirmPassword;
    Button setNewPasswordBtn;

    private FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_new_password);

        // Hooks to retrieve the UI elements from the layout
        TextInputLayout newPasswordLayout = findViewById(R.id.new_password);
        TextInputLayout confirmPasswordLayout = findViewById(R.id.confirm_password);
        setNewPasswordBtn = findViewById(R.id.set_new_password_btn);

        // Retrieve the TextInputEditText instances from TextInputLayout
        newPassword = (TextInputEditText) newPasswordLayout.getEditText();
        confirmPassword = (TextInputEditText) confirmPasswordLayout.getEditText();

        // Initialize Firestore
        db = FirebaseFirestore.getInstance();
    }

    // Method to validate the entered password
    private boolean validatePassword() {
        String password = newPassword.getText().toString().trim();
        String confirm = confirmPassword.getText().toString().trim();

        if (password.isEmpty() || confirm.isEmpty()) {
            Toast.makeText(this, "Please enter both fields", Toast.LENGTH_SHORT).show();
            return false;
        } else if (!password.equals(confirm)) {
            Toast.makeText(this, "Passwords do not match", Toast.LENGTH_SHORT).show();
            return false;
        } else {
            return true;
        }
    }

    // Method to handle the set new password button click
    public void setNewPasswordBtn(View view) {
        // Validate password
        if (!validatePassword()) {
            return;
        }

        // Get the entered username from the intent
        final String username = getIntent().getStringExtra("username");
        String newPassword = this.newPassword.getText().toString().trim();

        // Create a reference to the user document in the Firestore collection
        DocumentReference userRef = db.collection("Users").document(username);

        // Update the password field in the user document
        userRef.update("password", newPassword)
                .addOnSuccessListener(aVoid -> {
                    Toast.makeText(SetNewPassword.this, "Password updated successfully", Toast.LENGTH_SHORT).show();
                    finish();
                })
                .addOnFailureListener(e -> {
                    Toast.makeText(SetNewPassword.this, "Failed to update password", Toast.LENGTH_SHORT).show();
                    finish();
                });
    }
}
