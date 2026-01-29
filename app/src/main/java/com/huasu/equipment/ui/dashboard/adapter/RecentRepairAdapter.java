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
 * 最近报修适配器
 */
public class RecentRepairAdapter extends RecyclerView.Adapter<RecentRepairAdapter.ViewHolder> {

    private List<DashboardResponse.RecentRepair> data = new ArrayList<>();

    public void setData(List<DashboardResponse.RecentRepair> data) {
        this.data = data != null ? data : new ArrayList<>();
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_recent_repair, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        DashboardResponse.RecentRepair repair = data.get(position);
        holder.bind(repair);
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvRepairName;
        TextView tvEquipmentName;
        TextView tvFaultType;
        TextView tvCreateDate;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvRepairName = itemView.findViewById(R.id.tvRepairName);
            tvEquipmentName = itemView.findViewById(R.id.tvEquipmentName);
            tvFaultType = itemView.findViewById(R.id.tvFaultType);
            tvCreateDate = itemView.findViewById(R.id.tvCreateDate);
        }

        public void bind(DashboardResponse.RecentRepair repair) {
            tvRepairName.setText(repair.getName());
            tvEquipmentName.setText(repair.getEquipmentName());
            tvFaultType.setText(repair.getFaultType());
            tvCreateDate.setText(repair.getCreateDate());
        }
    }
}
