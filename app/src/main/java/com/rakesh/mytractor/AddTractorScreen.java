package com.rakesh.mytractor;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.rakesh.mytractor.database.DatabaseHandler;
import com.rakesh.mytractor.session.SessionManager;

public class AddTractorScreen extends AppCompatActivity {

    private EditText tractorNameEditText, companyEditText, modelEditText, yearEditText;
    private RadioGroup typeGroup;
    private Button submitButton;
    private SessionManager sessionManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_add_tractor_screen);
        sessionManager = new SessionManager(this);

        tractorNameEditText = findViewById(R.id.tractor_name);
        companyEditText = findViewById(R.id.company);
        modelEditText = findViewById(R.id.model);
        yearEditText = findViewById(R.id.manufactured_year);
        typeGroup = findViewById(R.id.type_group);
        submitButton = findViewById(R.id.submit_button);

        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = tractorNameEditText.getText().toString();
                String company = companyEditText.getText().toString();
                String model = modelEditText.getText().toString();
                String yearStr = yearEditText.getText().toString();

                if (name.isEmpty() || name.length() > 20) {
                    tractorNameEditText.setError("Name must be a non-empty string with a maximum of 20 characters");
                    return;
                }

                if (company.isEmpty() || company.length() > 20) {
                    companyEditText.setError("Company must be a non-empty string with a maximum of 20 characters");
                    return;
                }

                if (model.isEmpty() || model.length() > 20) {
                    modelEditText.setError("Model must be a non-empty string with a maximum of 20 characters");
                    return;
                }

                if (yearStr.isEmpty() || yearStr.length() != 4 || !yearStr.matches("\\d{4}")) {
                    yearEditText.setError("Year must be a 4-digit number");
                    return;
                }


                if(typeGroup.getCheckedRadioButtonId() == -1){
                    Toast.makeText(AddTractorScreen.this, "Please select a type", Toast.LENGTH_SHORT).show();
                    return;
                }

                int year = Integer.parseInt(yearStr);
                int selectedTypeId = typeGroup.getCheckedRadioButtonId();
                RadioButton selectedTypeButton = findViewById(selectedTypeId);
                String type = selectedTypeButton.getText().toString();

                DatabaseHandler mDbHandler = new DatabaseHandler(AddTractorScreen.this);
                long id = mDbHandler.addTractor(name, company, model, year, type, sessionManager.getUserName());
                if (id > 0) {
                    Toast.makeText(AddTractorScreen.this, "Tractor added successfully", Toast.LENGTH_SHORT).show();
                    finish();
                } else {
                    Toast.makeText(AddTractorScreen.this, "Failed to add tractor", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}