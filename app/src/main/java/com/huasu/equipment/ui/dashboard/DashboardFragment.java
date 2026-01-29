package com.huasu.equipment.ui.dashboard;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.huasu.equipment.R;
import com.huasu.equipment.api.ApiClient;
import com.huasu.equipment.api.response.BaseResponse;
import com.huasu.equipment.api.response.DashboardResponse;
import com.huasu.equipment.ui.dashboard.adapter.PendingTaskAdapter;
import com.huasu.equipment.ui.dashboard.adapter.RecentRepairAdapter;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * 仪表盘 Fragment
 */
public class DashboardFragment extends Fragment {

    private TextView tvEquipmentTotal;
    private TextView tvEquipmentNormal;
    private TextView tvEquipmentAbnormal;
    private TextView tvEquipmentMaintenance;
    private TextView tvInspectionTotal;
    private TextView tvInspectionPending;
    private TextView tvInspectionInProgress;
    private TextView tvInspectionCompleted;
    private TextView tvRepairTotal;
    private TextView tvRepairPending;
    private TextView tvRepairInProgress;
    private TextView tvRepairDone;
    private RecyclerView rvPendingTasks;
    private RecyclerView rvRecentRepairs;

    private PendingTaskAdapter pendingTaskAdapter;
    private RecentRepairAdapter recentRepairAdapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_dashboard, container, false);

        initViews(view);
        loadDashboardData();

        return view;
    }

    private void initViews(View view) {
        // Equipment Stats
        tvEquipmentTotal = view.findViewById(R.id.tvEquipmentTotal);
        tvEquipmentNormal = view.findViewById(R.id.tvEquipmentNormal);
        tvEquipmentAbnormal = view.findViewById(R.id.tvEquipmentAbnormal);
        tvEquipmentMaintenance = view.findViewById(R.id.tvEquipmentMaintenance);

        // Inspection Stats
        tvInspectionTotal = view.findViewById(R.id.tvInspectionTotal);
        tvInspectionPending = view.findViewById(R.id.tvInspectionPending);
        tvInspectionInProgress = view.findViewById(R.id.tvInspectionInProgress);
        tvInspectionCompleted = view.findViewById(R.id.tvInspectionCompleted);

        // Repair Stats
        tvRepairTotal = view.findViewById(R.id.tvRepairTotal);
        tvRepairPending = view.findViewById(R.id.tvRepairPending);
        tvRepairInProgress = view.findViewById(R.id.tvRepairInProgress);
        tvRepairDone = view.findViewById(R.id.tvRepairDone);

        // Lists
        rvPendingTasks = view.findViewById(R.id.rvPendingTasks);
        rvRecentRepairs = view.findViewById(R.id.rvRecentRepairs);

        // Setup adapters
        pendingTaskAdapter = new PendingTaskAdapter();
        rvPendingTasks.setLayoutManager(new LinearLayoutManager(getContext()));
        rvPendingTasks.setAdapter(pendingTaskAdapter);

        recentRepairAdapter = new RecentRepairAdapter();
        rvRecentRepairs.setLayoutManager(new LinearLayoutManager(getContext()));
        rvRecentRepairs.setAdapter(recentRepairAdapter);
    }

    private void loadDashboardData() {
        Call<BaseResponse<DashboardResponse>> call = ApiClient.getApiService().getDashboardStats();

        call.enqueue(new Callback<BaseResponse<DashboardResponse>>() {
            @Override
            public void onResponse(Call<BaseResponse<DashboardResponse>> call,
                                 Response<BaseResponse<DashboardResponse>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    BaseResponse<DashboardResponse> baseResponse = response.body();

                    if (baseResponse.isSuccess()) {
                        displayDashboardData(baseResponse.getData());
                    } else {
                        showError(baseResponse.getError());
                    }
                } else {
                    showError("加载失败");
                }
            }

            @Override
            public void onFailure(Call<BaseResponse<DashboardResponse>> call, Throwable t) {
                showError("网络错误: " + t.getMessage());
            }
        });
    }

    private void displayDashboardData(DashboardResponse data) {
        // Equipment Stats
        tvEquipmentTotal.setText(String.valueOf(data.getEquipmentStats().getTotal()));
        tvEquipmentNormal.setText(String.valueOf(data.getEquipmentStats().getNormal()));
        tvEquipmentAbnormal.setText(String.valueOf(data.getEquipmentStats().getAbnormal()));
        tvEquipmentMaintenance.setText(String.valueOf(data.getEquipmentStats().getMaintenance()));

        // Inspection Stats
        tvInspectionTotal.setText(String.valueOf(data.getInspectionStats().getTotal()));
        tvInspectionPending.setText(String.valueOf(data.getInspectionStats().getPending()));
        tvInspectionInProgress.setText(String.valueOf(data.getInspectionStats().getInProgress()));
        tvInspectionCompleted.setText(String.valueOf(data.getInspectionStats().getCompleted()));

        // Repair Stats
        tvRepairTotal.setText(String.valueOf(data.getRepairStats().getTotal()));
        tvRepairPending.setText(String.valueOf(data.getRepairStats().getPending()));
        tvRepairInProgress.setText(String.valueOf(data.getRepairStats().getInProgress()));
        tvRepairDone.setText(String.valueOf(data.getRepairStats().getDone()));

        // Lists
        pendingTaskAdapter.setData(data.getMyPendingTasks());
        recentRepairAdapter.setData(data.getRecentRepairs());
    }

    private void showError(String message) {
        Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
    }
}
