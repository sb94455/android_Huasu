package com.huasu.equipment.api.request;

import com.google.gson.annotations.SerializedName;

/**
 * 创建报修单请求
 */
public class RepairCreateRequest {

    @SerializedName("equipment_id")
    private int equipmentId;

    @SerializedName("fault_type_id")
    private int faultTypeId;

    @SerializedName("fault_level")
    private String faultLevel;

    @SerializedName("fault_description")
    private String faultDescription;

    @SerializedName("priority")
    private String priority;

    @SerializedName("image_data")
    private String imageData; // base64 encoded image

    public RepairCreateRequest(int equipmentId, int faultTypeId, String faultLevel,
                              String faultDescription, String priority, String imageData) {
        this.equipmentId = equipmentId;
        this.faultTypeId = faultTypeId;
        this.faultLevel = faultLevel;
        this.faultDescription = faultDescription;
        this.priority = priority;
        this.imageData = imageData;
    }

    // Getters
    public int getEquipmentId() { return equipmentId; }
    public int getFaultTypeId() { return faultTypeId; }
    public String getFaultLevel() { return faultLevel; }
    public String getFaultDescription() { return faultDescription; }
    public String getPriority() { return priority; }
    public String getImageData() { return imageData; }
}
