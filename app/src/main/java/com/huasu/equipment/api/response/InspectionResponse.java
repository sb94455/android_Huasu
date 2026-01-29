package com.huasu.equipment.api.response;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

/**
 * 点检任务详情响应
 */
public class InspectionResponse implements Serializable {

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

    @SerializedName("route_execution_ids")
    private List<RouteExecution> routeExecutionIds;

    @SerializedName("notes")
    private String notes;

    public static class RouteExecution implements Serializable {
        @SerializedName("id")
        private int id;

        @SerializedName("equipment_id")
        private EquipmentBasicInfo equipmentId;

        @SerializedName("inspection_standard_id")
        private StandardInfo inspectionStandardId;

        @SerializedName("execution_order")
        private int executionOrder;

        @SerializedName("status")
        private String status; // pending, in_progress, resolved

        @SerializedName("is_abnormal")
        private boolean isAbnormal;

        @SerializedName("inspection_time")
        private String inspectionTime;

        @SerializedName("completion_time")
        private String completionTime;

        @SerializedName("abnormal_description")
        private String abnormalDescription;

        @SerializedName("action_taken")
        private String actionTaken;

        @SerializedName("notes")
        private String notes;

        @SerializedName("inspection_items")
        private List<InspectionItemResult> inspectionItems;

        // Getters
        public int getId() { return id; }
        public EquipmentBasicInfo getEquipmentId() { return equipmentId; }
        public StandardInfo getInspectionStandardId() { return inspectionStandardId; }
        public int getExecutionOrder() { return executionOrder; }
        public String getStatus() { return status; }
        public boolean isAbnormal() { return isAbnormal; }
        public String getInspectionTime() { return inspectionTime; }
        public String getCompletionTime() { return completionTime; }
        public String getAbnormalDescription() { return abnormalDescription; }
        public String getActionTaken() { return actionTaken; }
        public String getNotes() { return notes; }
        public List<InspectionItemResult> getInspectionItems() { return inspectionItems; }
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
        public String getName() { return name; }
        public String getEquipmentName() { return equipmentName; }
        public String getCompleteName() { return completeName; }
        public String getLocation() { return location; }
    }

    public static class StandardInfo implements Serializable {
        @SerializedName("id")
        private int id;
        @SerializedName("name")
        private String name;
        @SerializedName("standard_name")
        private String standardName;

        public int getId() { return id; }
        public String getName() { return name; }
        public String getStandardName() { return standardName; }
    }

    public static class InspectionItemResult implements Serializable {
        @SerializedName("id")
        private int id;
        @SerializedName("inspection_item_id")
        private InspectionItemInfo inspectionItemId;
        @SerializedName("type_conf")
        private String typeConf;
        @SerializedName("result")
        private String result;
        @SerializedName("abnormal_description")
        private String abnormalDescription;
        @SerializedName("option_values")
        private String optionValues;
        @SerializedName("lower_limit")
        private String lowerLimit;
        @SerializedName("upper_limit")
        private String upperLimit;
        @SerializedName("unit")
        private String unit;

        public int getId() { return id; }
        public InspectionItemInfo getInspectionItemId() { return inspectionItemId; }
        public String getTypeConf() { return typeConf; }
        public String getResult() { return result; }
        public String getAbnormalDescription() { return abnormalDescription; }
        public String getOptionValues() { return optionValues; }
        public String getLowerLimit() { return lowerLimit; }
        public String getUpperLimit() { return upperLimit; }
        public String getUnit() { return unit; }
    }

    public static class InspectionItemInfo implements Serializable {
        @SerializedName("id")
        private int id;
        @SerializedName("name")
        private String name;
        @SerializedName("item_code")
        private String itemCode;

        public int getId() { return id; }
        public String getName() { return name; }
        public String getItemCode() { return itemCode; }
    }

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
    public List<RouteExecution> getRouteExecutionIds() { return routeExecutionIds; }
    public String getNotes() { return notes; }
}
