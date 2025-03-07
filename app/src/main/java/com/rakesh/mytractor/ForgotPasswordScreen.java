package com.rakesh.mytractor;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.rakesh.mytractor.database.DatabaseHandler;

public class ForgotPasswordScreen extends AppCompatActivity {

    private EditText emailEditText, newPasswordEditText, confirmPasswordEditText;
    private Button resetPasswordButton, updatePasswordButton;
    private DatabaseHandler dbHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);

        emailEditText = findViewById(R.id.email);
        newPasswordEditText = findViewById(R.id.new_password);
        confirmPasswordEditText = findViewById(R.id.confirm_password);
        resetPasswordButton = findViewById(R.id.reset_password_button);
        updatePasswordButton = findViewById(R.id.update_password_button);
        dbHandler = new DatabaseHandler(this);

        resetPasswordButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = emailEditText.getText().toString().trim();
                if (TextUtils.isEmpty(email) || !Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                    emailEditText.setError("Valid email is required");
                    return;
                }

                if (dbHandler.checkUserByEmail(email)) {
                    emailEditText.setVisibility(View.GONE);
                    resetPasswordButton.setVisibility(View.GONE);
                    newPasswordEditText.setVisibility(View.VISIBLE);
                    confirmPasswordEditText.setVisibility(View.VISIBLE);
                    updatePasswordButton.setVisibility(View.VISIBLE);
                } else {
                    Toast.makeText(ForgotPasswordScreen.this, "Email not found", Toast.LENGTH_SHORT).show();
                }
            }
        });

        updatePasswordButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String newPassword = newPasswordEditText.getText().toString().trim();
                String confirmPassword = confirmPasswordEditText.getText().toString().trim();

                if (TextUtils.isEmpty(newPassword) || TextUtils.isEmpty(confirmPassword)) {
                    Toast.makeText(ForgotPasswordScreen.this, "Both fields are required", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (!newPassword.equals(confirmPassword)) {
                    Toast.makeText(ForgotPasswordScreen.this, "Passwords do not match", Toast.LENGTH_SHORT).show();
                    return;
                }

                String email = emailEditText.getText().toString().trim();
                dbHandler.updateUserPassword(email, newPassword);
                Toast.makeText(ForgotPasswordScreen.this, "Password updated successfully", Toast.LENGTH_SHORT).show();
                finish();
            }
        });
    }
}