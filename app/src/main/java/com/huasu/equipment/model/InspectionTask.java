package com.huasu.equipment.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

/**
 * 点检任务数据模型
 */
public class InspectionTask implements Serializable {

    @SerializedName("id")
    private int id;

    @SerializedName("name")
    private String name;

    @SerializedName("task_name")
    private String taskName;

    @SerializedName("state")
    private String state;

    @SerializedName("planned_start_date")
    private String plannedStartDate;

    @SerializedName("planned_end_date")
    private String plannedEndDate;

    @SerializedName("actual_start_date")
    private String actualStartDate;

    @SerializedName("actual_end_date")
    private String actualEndDate;

    @SerializedName("total_equipment")
    private int totalEquipment;

    @SerializedName("inspected_equipment")
    private int inspectedEquipment;

    @SerializedName("abnormal_equipment")
    private int abnormalEquipment;

    @SerializedName("normal_equipment")
    private int normalEquipment;

    @SerializedName("completion_rate")
    private double completionRate;

    @SerializedName("responsible_id")
    private UserInfo responsibleId;

    @SerializedName("route_execution_ids")
    private List<RouteExecution> routeExecutionIds;

    @SerializedName("notes")
    private String notes;

    // 本地数据库字段
    private long lastUpdateTime;

    public InspectionTask() {
        this.lastUpdateTime = System.currentTimeMillis();
    }

    public static class UserInfo implements Serializable {
        @SerializedName("id")
        private int id;
        @SerializedName("name")
        private String name;

        public int getId() { return id; }
        public void setId(int id) { this.id = id; }
        public String getName() { return name; }
        public void setName(String name) { this.name = name; }
    }

    public static class RouteExecution implements Serializable {
        @SerializedName("id")
        private int id;
        @SerializedName("equipment_id")
        private EquipmentBasicInfo equipmentId;
        @SerializedName("execution_order")
        private int executionOrder;
        @SerializedName("status")
        private String status;
        @SerializedName("is_abnormal")
        private boolean isAbnormal;
        @SerializedName("inspection_time")
        private String inspectionTime;
        @SerializedName("abnormal_description")
        private String abnormalDescription;
        @SerializedName("notes")
        private String notes;

        public int getId() { return id; }
        public void setId(int id) { this.id = id; }
        public EquipmentBasicInfo getEquipmentId() { return equipmentId; }
        public void setEquipmentId(EquipmentBasicInfo equipmentId) { this.equipmentId = equipmentId; }
        public int getExecutionOrder() { return executionOrder; }
        public void setExecutionOrder(int executionOrder) { this.executionOrder = executionOrder; }
        public String getStatus() { return status; }
        public void setStatus(String status) { this.status = status; }
        public boolean isAbnormal() { return isAbnormal; }
        public void setAbnormal(boolean abnormal) { isAbnormal = abnormal; }
        public String getInspectionTime() { return inspectionTime; }
        public void setInspectionTime(String inspectionTime) { this.inspectionTime = inspectionTime; }
        public String getAbnormalDescription() { return abnormalDescription; }
        public void setAbnormalDescription(String abnormalDescription) { this.abnormalDescription = abnormalDescription; }
        public String getNotes() { return notes; }
        public void setNotes(String notes) { this.notes = notes; }
    }

    public static class EquipmentBasicInfo implements Serializable {
        @SerializedName("id")
        private int id;
        @SerializedName("name")
        private String name;
        @SerializedName("equipment_name")
        private String equipmentName;
        @SerializedName("complete_name")
        private String completeName;
        @SerializedName("location")
        private String location;

        public int getId() { return id; }
        public void setId(int id) { this.id = id; }
        public String getName() { return name; }
        public void setName(String name) { this.name = name; }
        public String getEquipmentName() { return equipmentName; }
        public void setEquipmentName(String equipmentName) { this.equipmentName = equipmentName; }
        public String getCompleteName() { return completeName; }
        public void setCompleteName(String completeName) { this.completeName = completeName; }
        public String getLocation() { return location; }
        public void setLocation(String location) { this.location = location; }
    }

    // Getters and Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getTaskName() { return taskName; }
    public void setTaskName(String taskName) { this.taskName = taskName; }

    public String getState() { return state; }
    public void setState(String state) { this.state = state; }

    public String getPlannedStartDate() { return plannedStartDate; }
    public void setPlannedStartDate(String plannedStartDate) { this.plannedStartDate = plannedStartDate; }

    public String getPlannedEndDate() { return plannedEndDate; }
    public void setPlannedEndDate(String plannedEndDate) { this.plannedEndDate = plannedEndDate; }

    public String getActualStartDate() { return actualStartDate; }
    public void setActualStartDate(String actualStartDate) { this.actualStartDate = actualStartDate; }

    public String getActualEndDate() { return actualEndDate; }
    public void setActualEndDate(String actualEndDate) { this.actualEndDate = actualEndDate; }

    public int getTotalEquipment() { return totalEquipment; }
    public void setTotalEquipment(int totalEquipment) { this.totalEquipment = totalEquipment; }

    public int getInspectedEquipment() { return inspectedEquipment; }
    public void setInspectedEquipment(int inspectedEquipment) { this.inspectedEquipment = inspectedEquipment; }

    public int getAbnormalEquipment() { return abnormalEquipment; }
    public void setAbnormalEquipment(int abnormalEquipment) { this.abnormalEquipment = abnormalEquipment; }

    public int getNormalEquipment() { return normalEquipment; }
    public void setNormalEquipment(int normalEquipment) { this.normalEquipment = normalEquipment; }

    public double getCompletionRate() { return completionRate; }
    public void setCompletionRate(double completionRate) { this.completionRate = completionRate; }

    public UserInfo getResponsibleId() { return responsibleId; }
    public void setResponsibleId(UserInfo responsibleId) { this.responsibleId = responsibleId; }

    public List<RouteExecution> getRouteExecutionIds() { return routeExecutionIds; }
    public void setRouteExecutionIds(List<RouteExecution> routeExecutionIds) { this.routeExecutionIds = routeExecutionIds; }

    public String getNotes() { return notes; }
    public void setNotes(String notes) { this.notes = notes; }

    public long getLastUpdateTime() { return lastUpdateTime; }
    public void setLastUpdateTime(long lastUpdateTime) { this.lastUpdateTime = lastUpdateTime; }

    /**
     * 获取任务状态显示文本
     */
    public String getStateText() {
        switch (state) {
            case "draft": return "待开始";
            case "in_progress": return "进行中";
            case "completed": return "已完成";
            default: return "未知";
        }
    }

    /**
     * 获取状态颜色资源ID
     */
    public int getStateColor() {
        switch (state) {
            case "draft": return android.R.color.holo_orange_dark;
            case "in_progress": return android.R.color.holo_blue_dark;
            case "completed": return android.R.color.holo_green_dark;
            default: return android.R.color.darker_gray;
        }
    }
}
