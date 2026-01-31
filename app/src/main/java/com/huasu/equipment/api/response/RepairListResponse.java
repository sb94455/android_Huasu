package com.huasu.equipment.api.response;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

/**
 * 报修单列表响应
 */
public class RepairListResponse extends BaseResponse<List<RepairListResponse.RepairListItem>> {

    /**
     * 报修单列表项
     */
    public static class RepairListItem implements Serializable {

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
        private RepairResponse.UserInfo currentHandler;

        @SerializedName("priority")
        private String priority;

        // Getters
        public int getId() { return id; }
        public String getName() { return name; }
        public String getEquipmentName() { return equipmentName; }
        public String getEquipmentCode() { return equipmentCode; }
        public String getFaultType() { return faultType; }
        public String getFaultLevel() { return faultLevel; }
        public String getFaultDescription() { return faultDescription; }
        public String getState() { return state; }
        public String getReportTime() { return reportTime; }
        public RepairResponse.UserInfo getCurrentHandler() { return currentHandler; }
        public String getPriority() { return priority; }
    }
}
