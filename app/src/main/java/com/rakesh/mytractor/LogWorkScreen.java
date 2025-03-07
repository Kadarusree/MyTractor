package com.rakesh.mytractor;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.rakesh.mytractor.database.DatabaseHandler;
import com.rakesh.mytractor.session.SessionManager;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class LogWorkScreen extends AppCompatActivity {

    private EditText workDateEditText, totalRentEditText, tripExpensesEditText, remarksEditText, customerNameEditText;
    private Spinner selectTractorSpinner, workTypeSpinner;
    private AutoCompleteTextView workNameAutoCompleteTextView;
    private RadioGroup rentalTypeGroup;
    private Button saveButton;
    private DatabaseHandler dbHandler;
    private List<String> tractorList;
    private SessionManager mSessionManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_work_screen);

        workDateEditText = findViewById(R.id.work_date);
        selectTractorSpinner = findViewById(R.id.select_tractor);
        workTypeSpinner = findViewById(R.id.work_type);
        workNameAutoCompleteTextView = findViewById(R.id.work_name);
        rentalTypeGroup = findViewById(R.id.rental_type_group);
        totalRentEditText = findViewById(R.id.total_rent);
        tripExpensesEditText = findViewById(R.id.trip_expenses);
        remarksEditText = findViewById(R.id.remarks);
        customerNameEditText = findViewById(R.id.customer_name);
        saveButton = findViewById(R.id.save_button);

        mSessionManager = new SessionManager(this);

        dbHandler = new DatabaseHandler(this);
        tractorList = dbHandler.getAllTractorNames(mSessionManager.getUserName());

        ArrayAdapter<String> tractorAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, tractorList);
        tractorAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        selectTractorSpinner.setAdapter(tractorAdapter);

        ArrayList<String> workTypes = new ArrayList<>();
        workTypes.add("Agriculture");
        workTypes.add("Non-Agriculture");

        ArrayAdapter<String> workTypeAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, workTypes);
        workTypeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        workTypeSpinner.setAdapter(workTypeAdapter);

        ArrayList<String> workNames = new ArrayList<>();
        workNames.add("Ploughing");
        workNames.add("Harvesting");
        // Add other work names...

        ArrayAdapter<String> workNameAdapter = new ArrayAdapter<>(this, android.R.layout.simple_dropdown_item_1line, workNames);
        workNameAutoCompleteTextView.setAdapter(workNameAdapter);

        workDateEditText.setOnClickListener(v -> showDatePickerDialog());

        saveButton.setOnClickListener(v -> {
            if (validateInputs()) {
                String workDate = workDateEditText.getText().toString();
                String selectedTractor = selectTractorSpinner.getSelectedItem().toString();
                String workType = workTypeSpinner.getSelectedItem().toString();
                String workName = workNameAutoCompleteTextView.getText().toString();
                String rentalType = ((RadioButton) findViewById(rentalTypeGroup.getCheckedRadioButtonId())).getText().toString();
                double totalRent = Double.parseDouble(totalRentEditText.getText().toString());
                double tripExpenses = Double.parseDouble(tripExpensesEditText.getText().toString());
                String remarks = remarksEditText.getText().toString();
                String customerName = customerNameEditText.getText().toString();

                long id = dbHandler.addWorkLog(selectedTractor, workType, workName, rentalType, totalRent, tripExpenses, remarks, workDate, customerName);
                if (id == -1) {
                    Toast.makeText(LogWorkScreen.this, "Failed to save work log", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(LogWorkScreen.this, "Work log saved successfully", Toast.LENGTH_SHORT).show();
                    finish();
                }
            }
        });
    }

    private boolean validateInputs() {
        if (workDateEditText.getText().toString().isEmpty()) {
            Toast.makeText(this, "Please select a work date", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (selectTractorSpinner.getSelectedItem() == null) {
            Toast.makeText(this, "Please select a tractor", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (workTypeSpinner.getSelectedItem() == null) {
            Toast.makeText(this, "Please select a work type", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (workNameAutoCompleteTextView.getText().toString().isEmpty()) {
            Toast.makeText(this, "Please enter a work name", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (rentalTypeGroup.getCheckedRadioButtonId() == -1) {
            Toast.makeText(this, "Please select a rental type", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (totalRentEditText.getText().toString().isEmpty()) {
            Toast.makeText(this, "Please enter the total rent", Toast.LENGTH_SHORT).show();
            return false;
        }
        try {
            Double.parseDouble(totalRentEditText.getText().toString());
        } catch (NumberFormatException e) {
            Toast.makeText(this, "Please enter a valid total rent", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (tripExpensesEditText.getText().toString().isEmpty()) {
            Toast.makeText(this, "Please enter the trip expenses", Toast.LENGTH_SHORT).show();
            return false;
        }
        try {
            Double.parseDouble(tripExpensesEditText.getText().toString());
        } catch (NumberFormatException e) {
            Toast.makeText(this, "Please enter a valid trip expenses", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (customerNameEditText.getText().toString().isEmpty()) {
            Toast.makeText(this, "Please enter the customer name", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    private void showDatePickerDialog() {
        final Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(
                LogWorkScreen.this,
                (view, year1, month1, dayOfMonth) -> workDateEditText.setText(dayOfMonth + "/" + (month1 + 1) + "/" + year1),
                year, month, day
        );
        datePickerDialog.show();
    }
}