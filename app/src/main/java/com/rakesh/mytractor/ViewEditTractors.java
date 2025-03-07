package com.rakesh.mytractor;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.rakesh.mytractor.database.DatabaseHandler;
import com.rakesh.mytractor.model.Tractor;
import com.rakesh.mytractor.session.SessionManager;

import java.util.List;

public class ViewEditTractors extends AppCompatActivity {

    private RecyclerView recyclerView;
    private TractorAdapter adapter;
    private List<Tractor> tractorList;

    SessionManager mSessionManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_edit_tractors);

        recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        mSessionManager = new SessionManager(this);
        // Add divider
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(recyclerView.getContext(), LinearLayoutManager.VERTICAL);
        recyclerView.addItemDecoration(dividerItemDecoration);

        DatabaseHandler dbHandler = new DatabaseHandler(this);
        tractorList = dbHandler.getAllTractors(mSessionManager.getUserName());

        adapter = new TractorAdapter(this, tractorList);
        recyclerView.setAdapter(adapter);
    }
}