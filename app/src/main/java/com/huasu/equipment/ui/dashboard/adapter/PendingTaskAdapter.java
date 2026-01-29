package com.huasu.equipment.ui.dashboard.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.huasu.equipment.R;
import com.huasu.equipment.api.response.DashboardResponse;

import java.util.ArrayList;
import java.util.List;

/**
 * 待办任务适配器
 */
public class PendingTaskAdapter extends RecyclerView.Adapter<PendingTaskAdapter.ViewHolder> {

    private List<DashboardResponse.PendingTask> data = new ArrayList<>();

    public void setData(List<DashboardResponse.PendingTask> data) {
        this.data = data != null ? data : new ArrayList<>();
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_pending_task, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        DashboardResponse.PendingTask task = data.get(position);
        holder.bind(task);
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvTaskName;
        TextView tvPlannedDate;
        TextView tvProgress;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvTaskName = itemView.findViewById(R.id.tvTaskName);
            tvPlannedDate = itemView.findViewById(R.id.tvPlannedDate);
            tvProgress = itemView.findViewById(R.id.tvProgress);
        }

        public void bind(DashboardResponse.PendingTask task) {
            tvTaskName.setText(task.getTaskName());
            tvPlannedDate.setText(task.getPlannedStartDate());
            tvProgress.setText(task.getInspectedEquipment() + "/" + task.getTotalEquipment());
        }
    }
}
