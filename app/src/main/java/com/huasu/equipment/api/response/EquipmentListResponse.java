package com.huasu.equipment.api.response;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

/**
 * 设备列表响应
 */
public class EquipmentListResponse extends BaseResponse<List<EquipmentListItem>> {
    // 继承自 BaseResponse，data 为 List<EquipmentListItem>
}

/**
 * 设备列表项
 */
public class EquipmentListItem implements Serializable {

    @SerializedName("id")
    private int id;

    @SerializedName("name")
    private String name;

    @SerializedName("equipment_name")
    private String equipmentName;

    @SerializedName("complete_name")
    private String completeName;

    @SerializedName("equipment_type")
    private String equipmentType;

    @SerializedName("state")
    private String state;

    @SerializedName("location")
    private String location;

    @SerializedName("location_id")
    private EquipmentResponse.LocationInfo locationId;

    @SerializedName("responsible_id")
    private EquipmentResponse.ResponsibleInfo responsibleId;

    @SerializedName("model")
    private String model;

    @SerializedName("brand")
    private String brand;

    @SerializedName("qc_num")
    private String qcNum;

    @SerializedName("equipment_age")
    private int equipmentAge;

    @SerializedName("warranty_status")
    private String warrantyStatus;

    // Getters
    public int getId() { return id; }
    public String getName() { return name; }
    public String getEquipmentName() { return equipmentName; }
    public String getCompleteName() { return completeName; }
    public String getEquipmentType() { return equipmentType; }
    public String getState() { return state; }
    public String getLocation() { return location; }
    public EquipmentResponse.LocationInfo getLocationId() { return locationId; }
    public EquipmentResponse.ResponsibleInfo getResponsibleId() { return responsibleId; }
    public String getModel() { return model; }
    public String getBrand() { return brand; }
    public String getQcNum() { return qcNum; }
    public int getEquipmentAge() { return equipmentAge; }
    public String getWarrantyStatus() { return warrantyStatus; }
}
