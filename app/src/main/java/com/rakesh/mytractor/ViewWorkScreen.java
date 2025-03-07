package com.rakesh.mytractor;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.rakesh.mytractor.database.DatabaseHandler;
import com.rakesh.mytractor.model.WorkLog;
import com.rakesh.mytractor.session.SessionManager;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class ViewWorkScreen extends AppCompatActivity {

    private EditText filterDateEditText, filterCustomerNameEditText;
    private Spinner filterTractorSpinner;
    private Button applyFiltersButton, resetFiltersButton;
    private RecyclerView workLogsRecyclerView;
    private DatabaseHandler dbHandler;
    private List<String> tractorList;
    private List<WorkLog> workLogs;
    private TextView totalExpensesText, totalRentText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_work_screen);

        filterDateEditText = findViewById(R.id.filter_date);
        filterCustomerNameEditText = findViewById(R.id.filter_customer_name);
        filterTractorSpinner = findViewById(R.id.filter_tractor);
        applyFiltersButton = findViewById(R.id.apply_filters_button);
        workLogsRecyclerView = findViewById(R.id.work_logs_recycler_view);
        resetFiltersButton = findViewById(R.id.reset_filters_button);
        totalExpensesText = findViewById(R.id.total_expenses_text);
        totalRentText = findViewById(R.id.total_rent_text);



        resetFiltersButton.setOnClickListener(v -> resetFilters());

        dbHandler = new DatabaseHandler(this);
        tractorList = dbHandler.getAllTractorNames(new SessionManager(this).getUserName());

        ArrayAdapter<String> tractorAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, tractorList);
        tractorAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        filterTractorSpinner.setAdapter(tractorAdapter);



        filterDateEditText.setOnClickListener(v -> showDatePickerDialog());

        applyFiltersButton.setOnClickListener(v -> applyFilters());

        workLogsRecyclerView.setLayoutManager(new GridLayoutManager(this, 1));
        loadWorkLogs();
    }

    private void showDatePickerDialog() {
        final Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(
                ViewWorkScreen.this,
                (view, year1, month1, dayOfMonth) -> filterDateEditText.setText(dayOfMonth + "/" + (month1 + 1) + "/" + year1),
                year, month, day
        );
        datePickerDialog.show();
    }

    private void applyFilters() {
        String date = filterDateEditText.getText().toString();
        String tractor = filterTractorSpinner.getSelectedItem().toString();
        String customerName = filterCustomerNameEditText.getText().toString();

        workLogs = dbHandler.getFilteredWorkLogs(date, tractor, customerName);
        WorkLogAdapter adapter = new WorkLogAdapter(this, workLogs);
        workLogsRecyclerView.setAdapter(adapter);
        updateSums();

    }

    private void loadWorkLogs() {
        workLogs = dbHandler.getAllWorkLogs();
        WorkLogAdapter adapter = new WorkLogAdapter(this, workLogs);
        workLogsRecyclerView.setAdapter(adapter);
        updateSums();

    }

    private void resetFilters() {
        filterDateEditText.setText("");
        filterCustomerNameEditText.setText("");
        filterTractorSpinner.setSelection(0);
        loadWorkLogs();
    }

    private void updateSums() {
        double totalExpenses = 0;
        double totalRent = 0;

        for (WorkLog workLog : workLogs) {
            totalExpenses += workLog.getTripExpenses();
            totalRent += workLog.getTotalRent();
        }

        totalExpensesText.setText("Total Expenses: " + totalExpenses);
        totalRentText.setText("Total Earnings: " + totalRent);
    }
}