package com.rakesh.mytractor;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

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
    private Spinner filterTractorSpinner, filterWorkTypeSpinner;
    private Button applyFiltersButton, resetFiltersButton;
    private RecyclerView workLogsRecyclerView;
    private DatabaseHandler dbHandler;
    private List<String> tractorList;
    private List<WorkLog> workLogs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_work_screen);

        filterDateEditText = findViewById(R.id.filter_date);
        filterCustomerNameEditText = findViewById(R.id.filter_customer_name);
        filterTractorSpinner = findViewById(R.id.filter_tractor);
        filterWorkTypeSpinner = findViewById(R.id.filter_work_type);
        applyFiltersButton = findViewById(R.id.apply_filters_button);
        workLogsRecyclerView = findViewById(R.id.work_logs_recycler_view);
        resetFiltersButton = findViewById(R.id.reset_filters_button);


        resetFiltersButton.setOnClickListener(v -> resetFilters());

        dbHandler = new DatabaseHandler(this);
        tractorList = dbHandler.getAllTractorNames(new SessionManager(this).getUserName());

        ArrayAdapter<String> tractorAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, tractorList);
        tractorAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        filterTractorSpinner.setAdapter(tractorAdapter);

        ArrayList<String> workTypes = new ArrayList<>();
        workTypes.add("Agriculture");
        workTypes.add("Non-Agriculture");

        ArrayAdapter<String> workTypeAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, workTypes);
        workTypeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        filterWorkTypeSpinner.setAdapter(workTypeAdapter);



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
        String workType = filterWorkTypeSpinner.getSelectedItem().toString();

        workLogs = dbHandler.getFilteredWorkLogs(date, tractor, customerName, workType);
        WorkLogAdapter adapter = new WorkLogAdapter(this, workLogs);
        workLogsRecyclerView.setAdapter(adapter);
    }

    private void loadWorkLogs() {
        workLogs = dbHandler.getAllWorkLogs();
        WorkLogAdapter adapter = new WorkLogAdapter(this, workLogs);
        workLogsRecyclerView.setAdapter(adapter);
    }

    private void resetFilters() {
        filterDateEditText.setText("");
        filterCustomerNameEditText.setText("");
        filterTractorSpinner.setSelection(0);
        filterWorkTypeSpinner.setSelection(0);
        loadWorkLogs();
    }
}