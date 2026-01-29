package com.huasu.equipment.api.response;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

/**
 * 点检任务列表响应
 */
public class InspectionListResponse extends BaseResponse<List<InspectionListItem>> {
}

/**
 * 点检任务列表项
 */
public class InspectionListItem implements Serializable {

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
    private EquipmentResponse.ResponsibleInfo responsibleId;

    // Getters
    public int getId() { return id; }
    public String getName() { return name; }
    public String getTaskName() { return taskName; }
    public String getState() { return state; }
    public String getPlannedStartDate() { return plannedStartDate; }
    public String getPlannedEndDate() { return plannedEndDate; }
    public String getActualStartDate() { return actualStartDate; }
    public String getActualEndDate() { return actualEndDate; }
    public int getTotalEquipment() { return totalEquipment; }
    public int getInspectedEquipment() { return inspectedEquipment; }
    public int getAbnormalEquipment() { return abnormalEquipment; }
    public int getNormalEquipment() { return normalEquipment; }
    public double getCompletionRate() { return completionRate; }
    public EquipmentResponse.ResponsibleInfo getResponsibleId() { return responsibleId; }
}
