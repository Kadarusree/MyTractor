package com.rakesh.mytractor;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.rakesh.mytractor.database.DatabaseHandler;
import com.rakesh.mytractor.model.Tractor;

import java.util.List;

public class ViewEditTractors extends AppCompatActivity {

    private RecyclerView recyclerView;
    private TractorAdapter adapter;
    private List<Tractor> tractorList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_edit_tractors);

        recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        DatabaseHandler dbHandler = new DatabaseHandler(this);
        tractorList = dbHandler.getAllTractors();

        adapter = new TractorAdapter(this, tractorList);
        recyclerView.setAdapter(adapter);
    }
}