package com.huasu.equipment.db;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

/**
 * 设备实体
 */
@Entity(tableName = "equipment")
public class EquipmentEntity {

    @PrimaryKey
    private int id;

    private String name;

    @ColumnInfo(name = "equipment_name")
    private String equipmentName;

    @ColumnInfo(name = "complete_name")
    private String completeName;

    @ColumnInfo(name = "equipment_type")
    private String equipmentType;

    private String state;

    private String location;

    private String model;

    @ColumnInfo(name = "qc_num")
    private String qcNum;

    @ColumnInfo(name = "equipment_age")
    private int equipmentAge;

    @ColumnInfo(name = "warranty_status")
    private String warrantyStatus;

    @ColumnInfo(name = "is_favorite")
    private boolean isFavorite;

    @ColumnInfo(name = "last_update_time")
    private long lastUpdateTime;

    // Constructors
    public EquipmentEntity() {
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
}
