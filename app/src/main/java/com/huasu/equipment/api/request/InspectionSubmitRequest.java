package com.huasu.equipment.api.request;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * 点检结果提交请求
 */
public class InspectionSubmitRequest {

    @SerializedName("task_id")
    private int taskId;

    @SerializedName("route_execution_id")
    private int routeExecutionId;

    @SerializedName("status")
    private String status; // pending, in_progress, resolved

    @SerializedName("is_abnormal")
    private boolean isAbnormal;

    @SerializedName("abnormal_description")
    private String abnormalDescription;

    @SerializedName("action_taken")
    private String actionTaken;

    @SerializedName("inspection_items")
    private List<InspectionItem> inspectionItems;

    @SerializedName("notes")
    private String notes;

    public InspectionSubmitRequest(int taskId, int routeExecutionId, String status,
                                  boolean isAbnormal, String abnormalDescription,
                                  String actionTaken, List<InspectionItem> inspectionItems,
                                  String notes) {
        this.taskId = taskId;
        this.routeExecutionId = routeExecutionId;
        this.status = status;
        this.isAbnormal = isAbnormal;
        this.abnormalDescription = abnormalDescription;
        this.actionTaken = actionTaken;
        this.inspectionItems = inspectionItems;
        this.notes = notes;
    }

    // Getters and Setters
    public int getTaskId() { return taskId; }
    public int getRouteExecutionId() { return routeExecutionId; }
    public String getStatus() { return status; }
    public boolean isAbnormal() { return isAbnormal; }
    public String getAbnormalDescription() { return abnormalDescription; }
    public String getActionTaken() { return actionTaken; }
    public List<InspectionItem> getInspectionItems() { return inspectionItems; }
    public String getNotes() { return notes; }

    /**
     * 点检项结果
     */
    public static class InspectionItem {
        @SerializedName("id")
        private int id;

        @SerializedName("result")
        private String result; // normal, abnormal, pending

        @SerializedName("abnormal_description")
        private String abnormalDescription;

        public InspectionItem(int id, String result, String abnormalDescription) {
            this.id = id;
            this.result = result;
            this.abnormalDescription = abnormalDescription;
        }

        // Getters
        public int getId() { return id; }
        public String getResult() { return result; }
        public String getAbnormalDescription() { return abnormalDescription; }
    }
}
