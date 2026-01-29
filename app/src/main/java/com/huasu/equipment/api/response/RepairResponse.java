package com.huasu.equipment.api.response;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

/**
 * 报修单详情响应
 */
public class RepairResponse implements Serializable {

    @SerializedName("id")
    private int id;

    @SerializedName("name")
    private String name;

    @SerializedName("equipment_id")
    private EquipmentBasicInfo equipmentId;

    @SerializedName("equipment_code")
    private String equipmentCode;

    @SerializedName("equipment_name")
    private String equipmentName;

    @SerializedName("model")
    private String model;

    @SerializedName("location")
    private String location;

    @SerializedName("fault_type")
    private FaultTypeInfo faultType;

    @SerializedName("fault_level")
    private String faultLevel;

    @SerializedName("fault_description")
    private String faultDescription;

    @SerializedName("state")
    private String state;

    @SerializedName("report_time")
    private String reportTime;

    @SerializedName("process_time")
    private String processTime;

    @SerializedName("reporter")
    private String reporter;

    @SerializedName("current_handler")
    private UserInfo currentHandler;

    @SerializedName("result_handler")
    private UserInfo resultHandler;

    @SerializedName("result_id")
    private ResultInfo resultId;

    @SerializedName("result_description")
    private String resultDescription;

    @SerializedName("total_cost")
    private double totalCost;

    @SerializedName("total_labor_cost")
    private double totalLaborCost;

    @SerializedName("total_spare_cost")
    private double totalSpareCost;

    @SerializedName("work_time")
    private double workTime;

    @SerializedName("priority")
    private String priority;

    @SerializedName("process_details")
    private List<ProcessDetail> processDetails;

    public static class EquipmentBasicInfo implements Serializable {
        @SerializedName("id")
        private int id;
        @SerializedName("name")
        private String name;
        @SerializedName("equipment_name")
        private String equipmentName;
        @SerializedName("model")
        private String model;

        public int getId() { return id; }
        public String getName() { return name; }
        public String getEquipmentName() { return equipmentName; }
        public String getModel() { return model; }
    }

    public static class FaultTypeInfo implements Serializable {
        @SerializedName("id")
        private int id;
        @SerializedName("name")
        private String name;

        public int getId() { return id; }
        public String getName() { return name; }
    }

    public static class UserInfo implements Serializable {
        @SerializedName("id")
        private int id;
        @SerializedName("name")
        private String name;

        public int getId() { return id; }
        public String getName() { return name; }
    }

    public static class ResultInfo implements Serializable {
        @SerializedName("id")
        private int id;
        @SerializedName("name")
        private String name;

        public int getId() { return id; }
        public String getName() { return name; }
    }

    public static class ProcessDetail implements Serializable {
        @SerializedName("process_time")
        private String processTime;
        @SerializedName("processor")
        private String processor;
        @SerializedName("state")
        private String state;

        public String getProcessTime() { return processTime; }
        public String getProcessor() { return processor; }
        public String getState() { return state; }
    }

    // Getters
    public int getId() { return id; }
    public String getName() { return name; }
    public EquipmentBasicInfo getEquipmentId() { return equipmentId; }
    public String getEquipmentCode() { return equipmentCode; }
    public String getEquipmentName() { return equipmentName; }
    public String getModel() { return model; }
    public String getLocation() { return location; }
    public FaultTypeInfo getFaultType() { return faultType; }
    public String getFaultLevel() { return faultLevel; }
    public String getFaultDescription() { return faultDescription; }
    public String getState() { return state; }
    public String getReportTime() { return reportTime; }
    public String getProcessTime() { return processTime; }
    public String getReporter() { return reporter; }
    public UserInfo getCurrentHandler() { return currentHandler; }
    public UserInfo getResultHandler() { return resultHandler; }
    public ResultInfo getResultId() { return resultId; }
    public String getResultDescription() { return resultDescription; }
    public double getTotalCost() { return totalCost; }
    public double getTotalLaborCost() { return totalLaborCost; }
    public double getTotalSpareCost() { return totalSpareCost; }
    public double getWorkTime() { return workTime; }
    public String getPriority() { return priority; }
    public List<ProcessDetail> getProcessDetails() { return processDetails; }
}
