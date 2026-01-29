package com.huasu.equipment.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * 报修单数据模型
 */
public class RepairOrder implements Serializable {

    @SerializedName("id")
    private int id;

    @SerializedName("name")
    private String name;

    @SerializedName("equipment_name")
    private String equipmentName;

    @SerializedName("equipment_code")
    private String equipmentCode;

    @SerializedName("fault_type")
    private String faultType;

    @SerializedName("fault_level")
    private String faultLevel;

    @SerializedName("fault_description")
    private String faultDescription;

    @SerializedName("state")
    private String state;

    @SerializedName("report_time")
    private String reportTime;

    @SerializedName("current_handler")
    private UserInfo currentHandler;

    @SerializedName("priority")
    private String priority;

    // 本地数据库字段
    private long lastUpdateTime;

    public RepairOrder() {
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

    // Getters and Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getEquipmentName() { return equipmentName; }
    public void setEquipmentName(String equipmentName) { this.equipmentName = equipmentName; }

    public String getEquipmentCode() { return equipmentCode; }
    public void setEquipmentCode(String equipmentCode) { this.equipmentCode = equipmentCode; }

    public String getFaultType() { return faultType; }
    public void setFaultType(String faultType) { this.faultType = faultType; }

    public String getFaultLevel() { return faultLevel; }
    public void setFaultLevel(String faultLevel) { this.faultLevel = faultLevel; }

    public String getFaultDescription() { return faultDescription; }
    public void setFaultDescription(String faultDescription) { this.faultDescription = faultDescription; }

    public String getState() { return state; }
    public void setState(String state) { this.state = state; }

    public String getReportTime() { return reportTime; }
    public void setReportTime(String reportTime) { this.reportTime = reportTime; }

    public UserInfo getCurrentHandler() { return currentHandler; }
    public void setCurrentHandler(UserInfo currentHandler) { this.currentHandler = currentHandler; }

    public String getPriority() { return priority; }
    public void setPriority(String priority) { this.priority = priority; }

    public long getLastUpdateTime() { return lastUpdateTime; }
    public void setLastUpdateTime(long lastUpdateTime) { this.lastUpdateTime = lastUpdateTime; }

    /**
     * 获取状态显示文本
     */
    public String getStateText() {
        switch (state) {
            case "draft": return "待分派";
            case "assigned": return "待接受";
            case "in_progress01": return "已开始";
            case "in_progress02": return "已接受";
            case "waiting_approval": return "待验收";
            case "done": return "验收通过";
            default: return "未知";
        }
    }

    /**
     * 获取故障等级显示文本
     */
    public String getFaultLevelText() {
        switch (faultLevel) {
            case "low": return "一般";
            case "medium": return "紧急";
            case "high": return "严重";
            default: return "未知";
        }
    }

    /**
     * 获取优先级显示文本
     */
    public String getPriorityText() {
        switch (priority) {
            case "0": return "低";
            case "1": return "中";
            case "2": return "高";
            case "3": return "紧急";
            default: return "未知";
        }
    }

    /**
     * 获取优先级颜色
     */
    public int getPriorityColor() {
        switch (priority) {
            case "0": return android.R.color.holo_green_dark;
            case "1": return android.R.color.holo_blue_dark;
            case "2": return android.R.color.holo_orange_dark;
            case "3": return android.R.color.holo_red_dark;
            default: return android.R.color.darker_gray;
        }
    }
}
