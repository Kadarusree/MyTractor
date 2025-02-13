package com.rakesh.mytractor;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatActivity;

import com.rakesh.mytractor.database.DatabaseHandler;
import com.rakesh.mytractor.model.Tractor;

import java.util.ArrayList;
import java.util.List;

public class LogWorkScreen extends AppCompatActivity {

    private Spinner selectTractorSpinner, workTypeSpinner;
    private EditText workNameEditText, workExpensesEditText, remarksEditText;
    private RadioGroup paymentTypeGroup;
    private Button saveButton;
    private DatabaseHandler dbHandler;
    private List<String> tractorList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_work_screen);

        selectTractorSpinner = findViewById(R.id.select_tractor);
        workTypeSpinner = findViewById(R.id.work_type);
        workNameEditText = findViewById(R.id.work_name);
        workExpensesEditText = findViewById(R.id.work_expenses);
        remarksEditText = findViewById(R.id.remarks);
        paymentTypeGroup = findViewById(R.id.payment_type_group);
        saveButton = findViewById(R.id.save_button);

        dbHandler = new DatabaseHandler(this);
        tractorList = dbHandler.getAllTractorNames();

        ArrayAdapter<String> tractorAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, tractorList);
        tractorAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        selectTractorSpinner.setAdapter(tractorAdapter);

        ArrayList<String> workTypes = new ArrayList<>();
        workTypes.add("Ploughing");
        workTypes.add("Harvesting");
        workTypes.add("Tilling");
        workTypes.add("Sowing");
        workTypes.add("Spraying");

        ArrayAdapter<String> workTypeAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, workTypes);
        workTypeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        workTypeSpinner.setAdapter(workTypeAdapter);

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int selectedTractorId = ((Tractor) selectTractorSpinner.getSelectedItem()).getId();
                String workType = workTypeSpinner.getSelectedItem().toString();
                String workName = workNameEditText.getText().toString();
                String paymentType = ((RadioButton) findViewById(paymentTypeGroup.getCheckedRadioButtonId())).getText().toString();
                double workExpenses = Double.parseDouble(workExpensesEditText.getText().toString());
                String remarks = remarksEditText.getText().toString();

                dbHandler.addWorkLog(selectedTractorId, workType, workName, paymentType, workExpenses, remarks);
                finish();
            }
        });
    }
}