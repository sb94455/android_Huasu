package com.huasu.equipment.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * 设备数据模型
 */
public class Equipment implements Serializable {

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

    @SerializedName("model")
    private String model;

    @SerializedName("qc_num")
    private String qcNum;

    @SerializedName("equipment_age")
    private int equipmentAge;

    @SerializedName("warranty_status")
    private String warrantyStatus;

    // 本地数据库字段
    private boolean isFavorite;
    private long lastUpdateTime;

    public Equipment() {
        this.lastUpdateTime = System.currentTimeMillis();
    }

    // Getters and Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getEquipmentName() { return equipmentName; }
    public void setEquipmentName(String equipmentName) { this.equipmentName = equipmentName; }

    public String getCompleteName() { return completeName; }
    public void setCompleteName(String completeName) { this.completeName = completeName; }

    public String getEquipmentType() { return equipmentType; }
    public void setEquipmentType(String equipmentType) { this.equipmentType = equipmentType; }

    public String getState() { return state; }
    public void setState(String state) { this.state = state; }

    public String getLocation() { return location; }
    public void setLocation(String location) { this.location = location; }

    public String getModel() { return model; }
    public void setModel(String model) { this.model = model; }

    public String getQcNum() { return qcNum; }
    public void setQcNum(String qcNum) { this.qcNum = qcNum; }

    public int getEquipmentAge() { return equipmentAge; }
    public void setEquipmentAge(int equipmentAge) { this.equipmentAge = equipmentAge; }

    public String getWarrantyStatus() { return warrantyStatus; }
    public void setWarrantyStatus(String warrantyStatus) { this.warrantyStatus = warrantyStatus; }

    public boolean isFavorite() { return isFavorite; }
    public void setFavorite(boolean favorite) { isFavorite = favorite; }

    public long getLastUpdateTime() { return lastUpdateTime; }
    public void setLastUpdateTime(long lastUpdateTime) { this.lastUpdateTime = lastUpdateTime; }

    /**
     * 获取设备状态显示文本
     */
    public String getStateText() {
        switch (state) {
            case "normal": return "正常";
            case "bug": return "异常";
            case "maintenance": return "维修中";
            case "scrapped": return "已报废";
            default: return "未知";
        }
    }

    /**
     * 获取设备类型显示文本
     */
    public String getEquipmentTypeText() {
        switch (equipmentType) {
            case "production": return "生产设备";
            case "office": return "办公设备";
            case "testing": return "检测设备";
            case "other": return "其他设备";
            default: return "未知";
        }
    }

    /**
     * 获取保修状态显示文本
     */
    public String getWarrantyStatusText() {
        switch (warrantyStatus) {
            case "in_warranty": return "保修期内";
            case "out_of_warranty": return "已过保修期";
            case "no_warranty": return "无保修信息";
            default: return "未知";
        }
    }
}
