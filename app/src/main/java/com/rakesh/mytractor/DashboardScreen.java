package com.rakesh.mytractor;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.rakesh.mytractor.database.DatabaseHandler;
import com.rakesh.mytractor.session.SessionManager;

public class DashboardScreen extends AppCompatActivity {

    private TextView userNameTextView;
    private CardView cardAddTractor, cardEditTractors, cardLogDailyWork, cardAddExpenses, cardViewReports, cardLogout;
    private SessionManager sessionManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dasboard_screen);

        sessionManager = new SessionManager(this);

        userNameTextView = findViewById(R.id.user_name);
        cardAddTractor = findViewById(R.id.card_add_tractor);
        cardEditTractors = findViewById(R.id.card_edit_tractors);
        cardLogDailyWork = findViewById(R.id.card_log_daily_work);
        cardAddExpenses = findViewById(R.id.card_add_expenses);
        cardViewReports = findViewById(R.id.card_view_reports);
        cardLogout = findViewById(R.id.card_add_logout);

        String userEmail = sessionManager.getUserName();
        userNameTextView.setText("Welcome, " + userEmail);

        cardAddTractor.setOnClickListener(v -> {
            // Navigate to Add Tractor screen
            startActivity(new Intent(DashboardScreen.this, AddTractorScreen.class));
        });

        cardEditTractors.setOnClickListener(v -> {
            // Navigate to Edit Tractors screen
            DatabaseHandler mDbHandler = new DatabaseHandler(DashboardScreen.this);
            if (mDbHandler.getTractorsCountByUser(sessionManager.getUserName()) > 0){
                startActivity(new Intent(DashboardScreen.this, ViewEditTractors.class));
            }
            else{
                Toast.makeText(DashboardScreen.this, "No tractors found", Toast.LENGTH_SHORT).show();
            }
        });

        cardLogDailyWork.setOnClickListener(v -> {
            // Navigate to Log Daily Work screen
            DatabaseHandler mDbHandler = new DatabaseHandler(DashboardScreen.this);
            if (mDbHandler.getTractorsCountByUser(sessionManager.getUserName()) > 0){
                startActivity(new Intent(DashboardScreen.this, LogWorkScreen.class));
            }
            else{
                Toast.makeText(DashboardScreen.this, "No tractors Added Yet", Toast.LENGTH_SHORT).show();
            }
        });

        cardAddExpenses.setOnClickListener(v -> {
            startActivity(new Intent(DashboardScreen.this, AboutUS.class));
        });

        cardViewReports.setOnClickListener(v -> {
            startActivity(new Intent(DashboardScreen.this, ViewWorkScreen.class));
        });

        cardLogout.setOnClickListener(v -> {
            sessionManager.logoutUser();
            Intent i = new Intent(DashboardScreen.this, LoginScreen.class);
            startActivity(i);
            finish();
        });
    }

    @Override
    public void onBackPressed() {
    }
}