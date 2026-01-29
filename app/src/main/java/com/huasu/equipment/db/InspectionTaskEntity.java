package com.huasu.equipment.db;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

/**
 * 点检任务实体
 */
@Entity(tableName = "inspection_tasks")
public class InspectionTaskEntity {

    @PrimaryKey
    private int id;

    private String name;
    private String taskName;
    private String state;
    private String plannedStartDate;
    private String plannedEndDate;
    private int totalEquipment;
    private int inspectedEquipment;
    private int abnormalEquipment;
    private int normalEquipment;
    private double completionRate;
    private long lastUpdateTime;

    public InspectionTaskEntity() {
        this.lastUpdateTime = System.currentTimeMillis();
    }

    // Getters and Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getTaskName() { return taskName; }
    public void setTaskName(String taskName) { this.taskName = taskName; }

    public String getState() { return state; }
    public void setState(String state) { this.state = state; }

    public String getPlannedStartDate() { return plannedStartDate; }
    public void setPlannedStartDate(String plannedStartDate) { this.plannedStartDate = plannedStartDate; }

    public String getPlannedEndDate() { return plannedEndDate; }
    public void setPlannedEndDate(String plannedEndDate) { this.plannedEndDate = plannedEndDate; }

    public int getTotalEquipment() { return totalEquipment; }
    public void setTotalEquipment(int totalEquipment) { this.totalEquipment = totalEquipment; }

    public int getInspectedEquipment() { return inspectedEquipment; }
    public void setInspectedEquipment(int inspectedEquipment) { this.inspectedEquipment = inspectedEquipment; }

    public int getAbnormalEquipment() { return abnormalEquipment; }
    public void setAbnormalEquipment(int abnormalEquipment) { this.abnormalEquipment = abnormalEquipment; }

    public int getNormalEquipment() { return normalEquipment; }
    public void setNormalEquipment(int normalEquipment) { this.normalEquipment = normalEquipment; }

    public double getCompletionRate() { return completionRate; }
    public void setCompletionRate(double completionRate) { this.completionRate = completionRate; }

    public long getLastUpdateTime() { return lastUpdateTime; }
    public void setLastUpdateTime(long lastUpdateTime) { this.lastUpdateTime = lastUpdateTime; }
}
