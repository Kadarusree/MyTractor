package com.rakesh.mytractor;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.rakesh.mytractor.model.Tractor;
import com.rakesh.mytractor.database.DatabaseHandler;

import java.util.List;

public class TractorAdapter extends RecyclerView.Adapter<TractorAdapter.TractorViewHolder> {

    private Context context;
    private List<Tractor> tractorList;

    public TractorAdapter(Context context, List<Tractor> tractorList) {
        this.context = context;
        this.tractorList = tractorList;
    }

    @NonNull
    @Override
    public TractorViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_tractor, parent, false);
        return new TractorViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TractorViewHolder holder, int position) {
        Tractor tractor = tractorList.get(position);
        holder.tractorName.setText("Name: " + tractor.getName());
        holder.tractorCompany.setText("Company: " + tractor.getCompany());
        holder.tractorModel.setText("Model: " + tractor.getModel());
        holder.tractorYear.setText("Year: " + tractor.getYear());
        holder.tractorType.setText("Type: " + tractor.getType());

        holder.itemView.setOnClickListener(v -> {
            Toast.makeText(context, "Clicked on: " + tractor.getName(), Toast.LENGTH_SHORT).show();
         //   Intent intent = new Intent(context, TractorDetailActivity.class);
         //   intent.putExtra("TRACTOR_ID", tractor.getId());
         //   context.startActivity(intent);
        });

        holder.editButton.setOnClickListener(v -> {
            // Handle edit button click
        });

        holder.deleteButton.setOnClickListener(v -> {
            DatabaseHandler dbHandler = new DatabaseHandler(context);
            dbHandler.deleteTractor(tractor.getId());
            tractorList.remove(position);
            notifyItemRemoved(position);
            notifyItemRangeChanged(position, tractorList.size());
            Toast.makeText(context, "Deleted: " + tractor.getName(), Toast.LENGTH_SHORT).show();
        });
    }

    @Override
    public int getItemCount() {
        return tractorList.size();
    }

    public static class TractorViewHolder extends RecyclerView.ViewHolder {

        TextView tractorName, tractorCompany, tractorModel, tractorYear, tractorType;
        Button editButton, deleteButton;

        public TractorViewHolder(@NonNull View itemView) {
            super(itemView);
            tractorName = itemView.findViewById(R.id.tractor_name);
            tractorCompany = itemView.findViewById(R.id.tractor_company);
            tractorModel = itemView.findViewById(R.id.tractor_model);
            tractorYear = itemView.findViewById(R.id.tractor_year);
            tractorType = itemView.findViewById(R.id.tractor_type);
            editButton = itemView.findViewById(R.id.edit_button);
            deleteButton = itemView.findViewById(R.id.delete_button);
        }
    }
}