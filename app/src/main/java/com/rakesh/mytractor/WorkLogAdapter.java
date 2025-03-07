package com.rakesh.mytractor;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.rakesh.mytractor.model.WorkLog;

import java.util.List;

public class WorkLogAdapter extends RecyclerView.Adapter<WorkLogAdapter.WorkLogViewHolder> {

    private Context context;
    private List<WorkLog> workLogs;

    public WorkLogAdapter(Context context, List<WorkLog> workLogs) {
        this.context = context;
        this.workLogs = workLogs;
    }

    @NonNull
    @Override
    public WorkLogViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_work_log, parent, false);
        return new WorkLogViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull WorkLogViewHolder holder, int position) {
        WorkLog workLog = workLogs.get(position);
        holder.tractorTextView.setText("Tractor : "+workLog.getTractor());
        holder.workTypeTextView.setText("Work Type : "+workLog.getWorkType());
        holder.workNameTextView.setText("Work Name : "+workLog.getWorkName());
        holder.rentalTypeTextView.setText("Payment Type : "+workLog.getRentalType());
        holder.totalRentTextView.setText("Total Rent : "+String.valueOf(workLog.getTotalRent()));
        holder.tripExpensesTextView.setText("Trip Expenses : "+String.valueOf(workLog.getTripExpenses()));
        holder.remarksTextView.setText("Remarks : "+workLog.getRemarks());
        holder.workDateTextView.setText("Date : "+workLog.getWorkDate());
        holder.customerNameTextView.setText("Customer Name : "+workLog.getCustomerName());
    }

    @Override
    public int getItemCount() {
        return workLogs.size();
    }

    public static class WorkLogViewHolder extends RecyclerView.ViewHolder {

        TextView tractorTextView, workTypeTextView, workNameTextView, rentalTypeTextView, totalRentTextView, tripExpensesTextView, remarksTextView, workDateTextView, customerNameTextView;

        public WorkLogViewHolder(@NonNull View itemView) {
            super(itemView);
            tractorTextView = itemView.findViewById(R.id.tractor);
            workTypeTextView = itemView.findViewById(R.id.work_type);
            workNameTextView = itemView.findViewById(R.id.work_name);
            rentalTypeTextView = itemView.findViewById(R.id.rental_type);
            totalRentTextView = itemView.findViewById(R.id.total_rent);
            tripExpensesTextView = itemView.findViewById(R.id.trip_expenses);
            remarksTextView = itemView.findViewById(R.id.remarks);
            workDateTextView = itemView.findViewById(R.id.work_date);
            customerNameTextView = itemView.findViewById(R.id.customer_name);
        }
    }
}