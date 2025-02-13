package com.rakesh.mytractor;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.rakesh.mytractor.database.DatabaseHandler;

public class SignupActivity extends AppCompatActivity {

    private EditText surnameEditText, nameEditText, emailEditText, passwordEditText, confirmPasswordEditText, dobEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        surnameEditText = findViewById(R.id.surname);
        nameEditText = findViewById(R.id.name);
        emailEditText = findViewById(R.id.email);
        passwordEditText = findViewById(R.id.password);
        confirmPasswordEditText = findViewById(R.id.confirm_password);
        dobEditText = findViewById(R.id.dob);

        findViewById(R.id.signup_button).setOnClickListener(v -> {
            if (validateInput()) {

                DatabaseHandler mDbHandler = new DatabaseHandler(SignupActivity.this);
                mDbHandler.addUser(surnameEditText.getText().toString().trim(),
                        nameEditText.getText().toString().trim(),
                        emailEditText.getText().toString().trim(),
                        passwordEditText.getText().toString().trim(),
                        dobEditText.getText().toString().trim());
            }
        });
    }

    private boolean validateInput() {
        if (TextUtils.isEmpty(surnameEditText.getText().toString().trim())) {
            surnameEditText.setError("Surname is required");
            return false;
        }
        if (TextUtils.isEmpty(nameEditText.getText().toString().trim())) {
            nameEditText.setError("Name is required");
            return false;
        }
        if (TextUtils.isEmpty(emailEditText.getText().toString().trim()) || !Patterns.EMAIL_ADDRESS.matcher(emailEditText.getText().toString().trim()).matches()) {
            emailEditText.setError("Valid email is required");
            return false;
        }
        if (TextUtils.isEmpty(passwordEditText.getText().toString().trim())) {
            passwordEditText.setError("Password is required");
            return false;
        }
        if (TextUtils.isEmpty(confirmPasswordEditText.getText().toString().trim()) || !confirmPasswordEditText.getText().toString().trim().equals(passwordEditText.getText().toString().trim())) {
            confirmPasswordEditText.setError("Passwords do not match");
            return false;
        }
        if (TextUtils.isEmpty(dobEditText.getText().toString().trim())) {
            dobEditText.setError("Date of Birth is required");
            return false;
        }
        return true;
    }
}