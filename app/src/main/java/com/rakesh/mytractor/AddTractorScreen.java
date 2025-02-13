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

public class AddTractorScreen extends AppCompatActivity {

    private EditText tractorNameEditText, companyEditText, modelEditText, yearEditText;
    private RadioGroup typeGroup;
    private Button submitButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_tractor_screen);

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
                int year = Integer.parseInt(yearEditText.getText().toString());
                int selectedTypeId = typeGroup.getCheckedRadioButtonId();
                RadioButton selectedTypeButton = findViewById(selectedTypeId);
                String type = selectedTypeButton.getText().toString();

                DatabaseHandler mDbHandler = new DatabaseHandler(AddTractorScreen.this);
                long id = mDbHandler.addTractor(name, company, model, year, type);
                if(id>0){
                    Toast.makeText(AddTractorScreen.this, "Tractor added successfully", Toast.LENGTH_SHORT).show();
                    finish();
                }
                else {
                    Toast.makeText(AddTractorScreen.this, "Failed to add tractor", Toast.LENGTH_SHORT).show();
                }



                finish();
            }
        });
    }
}