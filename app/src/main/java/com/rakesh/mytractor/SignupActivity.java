package com.rakesh.mytractor;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.rakesh.mytractor.database.DatabaseHandler;

import java.util.Calendar;

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

        dobEditText.setOnClickListener(v -> showDatePickerDialog());

        findViewById(R.id.signup_button).setOnClickListener(v -> {
            if (validateInput()) {
                DatabaseHandler mDbHandler = new DatabaseHandler(SignupActivity.this);
                long id = mDbHandler.addUser(
                        surnameEditText.getText().toString().trim(),
                        nameEditText.getText().toString().trim(),
                        emailEditText.getText().toString().trim(),
                        passwordEditText.getText().toString().trim(),
                        dobEditText.getText().toString().trim()
                );

                if (id > 0) {
                    Toast.makeText(SignupActivity.this, "Registration successful!", Toast.LENGTH_SHORT).show();
                    new AlertDialog.Builder(SignupActivity.this)
                            .setTitle("Success")
                            .setMessage("You have successfully registered.")
                            .setPositiveButton(android.R.string.ok, (dialog, which) -> finish())
                            .show();
                }
            }
        });
    }

    private void showDatePickerDialog() {
        final Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(
                SignupActivity.this,
                (view, year1, month1, dayOfMonth) -> dobEditText.setText(dayOfMonth + "/" + (month1 + 1) + "/" + year1),
                year, month, day
        );
        datePickerDialog.show();
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