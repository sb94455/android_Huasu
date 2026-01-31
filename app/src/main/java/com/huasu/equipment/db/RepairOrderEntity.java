package com.huasu.equipment.db;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

/**
 * 报修单实体
 */
@Entity(tableName = "repair_orders")
public class RepairOrderEntity {

    @PrimaryKey
    private int id;

    private String name;

    @ColumnInfo(name = "equipment_name")
    private String equipmentName;

    @ColumnInfo(name = "equipment_code")
    private String equipmentCode;

    @ColumnInfo(name = "fault_type")
    private String faultType;

    @ColumnInfo(name = "fault_level")
    private String faultLevel;

    @ColumnInfo(name = "fault_description")
    private String faultDescription;

    private String state;

    @ColumnInfo(name = "report_time")
    private String reportTime;

    private String priority;

    @ColumnInfo(name = "last_update_time")
    private long lastUpdateTime;

    public RepairOrderEntity() {
        this.lastUpdateTime = System.currentTimeMillis();
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

    public String getPriority() { return priority; }
    public void setPriority(String priority) { this.priority = priority; }

    public long getLastUpdateTime() { return lastUpdateTime; }
    public void setLastUpdateTime(long lastUpdateTime) { this.lastUpdateTime = lastUpdateTime; }
}
