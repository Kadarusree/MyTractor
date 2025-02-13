package com.rakesh.mytractor;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.rakesh.mytractor.database.DatabaseHandler;
import com.rakesh.mytractor.model.Tractor;

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
        holder.tractorName.setText(tractor.getName());
        holder.tractorDetails.setText(tractor.getCompany() + ", " + tractor.getModel() + ", " + tractor.getYear() + ", " + tractor.getType());

        holder.editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    @Override
    public int getItemCount() {
        return tractorList.size();
    }

    public static class TractorViewHolder extends RecyclerView.ViewHolder {

        TextView tractorName, tractorDetails;
        Button editButton;

        public TractorViewHolder(@NonNull View itemView) {
            super(itemView);
            tractorName = itemView.findViewById(R.id.tractor_name);
            tractorDetails = itemView.findViewById(R.id.tractor_details);
            editButton = itemView.findViewById(R.id.edit_button);
        }
    }
}