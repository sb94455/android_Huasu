package com.huasu.equipment.api.response;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

/**
 * 仪表盘统计数据响应
 */
public class DashboardResponse implements Serializable {

    @SerializedName("equipment_stats")
    private EquipmentStats equipmentStats;

    @SerializedName("inspection_stats")
    private InspectionStats inspectionStats;

    @SerializedName("repair_stats")
    private RepairStats repairStats;

    @SerializedName("my_pending_tasks")
    private List<PendingTask> myPendingTasks;

    @SerializedName("recent_repairs")
    private List<RecentRepair> recentRepairs;

    public static class EquipmentStats implements Serializable {
        @SerializedName("total")
        private int total;
        @SerializedName("normal")
        private int normal;
        @SerializedName("abnormal")
        private int abnormal;
        @SerializedName("maintenance")
        private int maintenance;

        public int getTotal() { return total; }
        public int getNormal() { return normal; }
        public int getAbnormal() { return abnormal; }
        public int getMaintenance() { return maintenance; }
    }

    public static class InspectionStats implements Serializable {
        @SerializedName("total")
        private int total;
        @SerializedName("pending")
        private int pending;
        @SerializedName("in_progress")
        private int inProgress;
        @SerializedName("completed")
        private int completed;

        public int getTotal() { return total; }
        public int getPending() { return pending; }
        public int getInProgress() { return inProgress; }
        public int getCompleted() { return completed; }
    }

    public static class RepairStats implements Serializable {
        @SerializedName("total")
        private int total;
        @SerializedName("pending")
        private int pending;
        @SerializedName("in_progress")
        private int inProgress;
        @SerializedName("done")
        private int done;

        public int getTotal() { return total; }
        public int getPending() { return pending; }
        public int getInProgress() { return inProgress; }
        public int getDone() { return done; }
    }

    public static class PendingTask implements Serializable {
        @SerializedName("id")
        private int id;
        @SerializedName("name")
        private String name;
        @SerializedName("task_name")
        private String taskName;
        @SerializedName("planned_start_date")
        private String plannedStartDate;
        @SerializedName("total_equipment")
        private int totalEquipment;
        @SerializedName("inspected_equipment")
        private int inspectedEquipment;

        public int getId() { return id; }
        public String getName() { return name; }
        public String getTaskName() { return taskName; }
        public String getPlannedStartDate() { return plannedStartDate; }
        public int getTotalEquipment() { return totalEquipment; }
        public int getInspectedEquipment() { return inspectedEquipment; }
    }

    public static class RecentRepair implements Serializable {
        @SerializedName("id")
        private int id;
        @SerializedName("name")
        private String name;
        @SerializedName("equipment_name")
        private String equipmentName;
        @SerializedName("fault_type")
        private String faultType;
        @SerializedName("state")
        private String state;
        @SerializedName("create_date")
        private String createDate;

        public int getId() { return id; }
        public String getName() { return name; }
        public String getEquipmentName() { return equipmentName; }
        public String getFaultType() { return faultType; }
        public String getState() { return state; }
        public String getCreateDate() { return createDate; }
    }

    // Getters
    public EquipmentStats getEquipmentStats() { return equipmentStats; }
    public InspectionStats getInspectionStats() { return inspectionStats; }
    public RepairStats getRepairStats() { return repairStats; }
    public List<PendingTask> getMyPendingTasks() { return myPendingTasks; }
    public List<RecentRepair> getRecentRepairs() { return recentRepairs; }
}
