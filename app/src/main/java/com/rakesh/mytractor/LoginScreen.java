package com.rakesh.mytractor;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.rakesh.mytractor.database.DatabaseHandler;
import com.rakesh.mytractor.session.SessionManager;

public class LoginScreen extends AppCompatActivity {

    private EditText emailEditText, passwordEditText;
    private Button loginButton;
    private TextView signUp;
    private SessionManager sessionManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_screen);

        emailEditText = findViewById(R.id.email);
        passwordEditText = findViewById(R.id.password);
        loginButton = findViewById(R.id.login_button);
        signUp = findViewById(R.id.signup_cta);
        sessionManager = new SessionManager(this);


        loginButton.setOnClickListener(v -> {
            if (validateInput()) {
                DatabaseHandler mDbHandler = new DatabaseHandler(LoginScreen.this);
                if (mDbHandler.checkUser(emailEditText.getText().toString().trim(),
                        passwordEditText.getText().toString().trim())) {
                    Toast.makeText(LoginScreen.this, "Login successful", Toast.LENGTH_SHORT).show();
                    // Navigate to the main screen or dashboard
                    long userId = mDbHandler.getUserIdByEmail(emailEditText.getText().toString().trim());
                    sessionManager.createLoginSession(userId, emailEditText.getText().toString().trim());
                    Toast.makeText(LoginScreen.this, "Login successful", Toast.LENGTH_SHORT).show();
                    Intent i = new Intent(LoginScreen.this, DashboardScreen.class);
                    startActivity(i);
                    finish();

                } else {
                    Toast.makeText(LoginScreen.this, "Invalid email or password", Toast.LENGTH_SHORT).show();
                }
            }
        });

        signUp.setOnClickListener(v -> {
            // Navigate to the sign up screen
            Intent i = new Intent(LoginScreen.this, SignupActivity.class);
            startActivity(i);
        });
    }

    private boolean validateInput() {
        if (TextUtils.isEmpty(emailEditText.getText().toString().trim()) || !Patterns.EMAIL_ADDRESS.matcher(emailEditText.getText().toString().trim()).matches()) {
            emailEditText.setError("Valid email is required");
            return false;
        }
        if (TextUtils.isEmpty(passwordEditText.getText().toString().trim())) {
            passwordEditText.setError("Password is required");
            return false;
        }
        return true;
    }
}