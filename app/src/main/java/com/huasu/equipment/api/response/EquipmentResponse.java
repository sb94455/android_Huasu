package com.huasu.equipment.api.response;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

/**
 * 设备详情响应
 */
public class EquipmentResponse implements Serializable {

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

    @SerializedName("level")
    private String level;

    @SerializedName("model")
    private String model;

    @SerializedName("state")
    private String state;

    @SerializedName("location")
    private String location;

    @SerializedName("location_id")
    private LocationInfo locationId;

    @SerializedName("department_id")
    private DepartmentInfo departmentId;

    @SerializedName("responsible_id")
    private ResponsibleInfo responsibleId;

    @SerializedName("brand_id")
    private BrandInfo brandId;

    @SerializedName("supplier_id")
    private SupplierInfo supplierId;

    @SerializedName("purchase_date")
    private String purchaseDate;

    @SerializedName("purchase_amount")
    private double purchaseAmount;

    @SerializedName("warranty_period")
    private int warrantyPeriod;

    @SerializedName("warranty_end_date")
    private String warrantyEndDate;

    @SerializedName("warranty_status")
    private String warrantyStatus;

    @SerializedName("asset_code")
    private String assetCode;

    @SerializedName("expected_scrap_date")
    private String expectedScrapDate;

    @SerializedName("factory_code")
    private String factoryCode;

    @SerializedName("storage_location")
    private String storageLocation;

    @SerializedName("is_monitored")
    private boolean isMonitored;

    @SerializedName("is_controlled")
    private boolean isControlled;

    @SerializedName("qc_num")
    private String qcNum;

    @SerializedName("equipment_age")
    private int equipmentAge;

    @SerializedName("days_to_scrap")
    private int daysToScrap;

    @SerializedName("notes")
    private String notes;

    @SerializedName("related_personnel")
    private List<PersonnelInfo> relatedPersonnel;

    @SerializedName("maintenance_history")
    private List<MaintenanceRecord> maintenanceHistory;

    @SerializedName("inspection_history")
    private List<InspectionRecord> inspectionHistory;

    // Inner classes
    public static class LocationInfo implements Serializable {
        @SerializedName("id")
        private int id;
        @SerializedName("name")
        private String name;
        @SerializedName("complete_name")
        private String completeName;

        public int getId() { return id; }
        public String getName() { return name; }
        public String getCompleteName() { return completeName; }
    }

    public static class DepartmentInfo implements Serializable {
        @SerializedName("id")
        private int id;
        @SerializedName("name")
        private String name;

        public int getId() { return id; }
        public String getName() { return name; }
    }

    public static class ResponsibleInfo implements Serializable {
        @SerializedName("id")
        private int id;
        @SerializedName("name")
        private String name;

        public int getId() { return id; }
        public String getName() { return name; }
    }

    public static class BrandInfo implements Serializable {
        @SerializedName("id")
        private int id;
        @SerializedName("name")
        private String name;

        public int getId() { return id; }
        public String getName() { return name; }
    }

    public static class SupplierInfo implements Serializable {
        @SerializedName("id")
        private int id;
        @SerializedName("name")
        private String name;

        public int getId() { return id; }
        public String getName() { return name; }
    }

    public static class PersonnelInfo implements Serializable {
        @SerializedName("id")
        private int id;
        @SerializedName("name")
        private String name;

        public int getId() { return id; }
        public String getName() { return name; }
    }

    public static class MaintenanceRecord implements Serializable {
        @SerializedName("id")
        private int id;
        @SerializedName("name")
        private String name;
        @SerializedName("fault_type")
        private String faultType;
        @SerializedName("state")
        private String state;
        @SerializedName("create_date")
        private String createDate;

        public int getId() { return id; }
        public String getName() { return name; }
        public String getFaultType() { return faultType; }
        public String getState() { return state; }
        public String getCreateDate() { return createDate; }
    }

    public static class InspectionRecord implements Serializable {
        @SerializedName("id")
        private int id;
        @SerializedName("name")
        private String name;
        @SerializedName("task_name")
        private String taskName;
        @SerializedName("state")
        private String state;
        @SerializedName("actual_start_date")
        private String actualStartDate;

        public int getId() { return id; }
        public String getName() { return name; }
        public String getTaskName() { return taskName; }
        public String getState() { return state; }
        public String getActualStartDate() { return actualStartDate; }
    }

    // Getters
    public int getId() { return id; }
    public String getName() { return name; }
    public String getEquipmentName() { return equipmentName; }
    public String getCompleteName() { return completeName; }
    public String getEquipmentType() { return equipmentType; }
    public String getLevel() { return level; }
    public String getModel() { return model; }
    public String getState() { return state; }
    public String getLocation() { return location; }
    public LocationInfo getLocationId() { return locationId; }
    public DepartmentInfo getDepartmentId() { return departmentId; }
    public ResponsibleInfo getResponsibleId() { return responsibleId; }
    public BrandInfo getBrandId() { return brandId; }
    public SupplierInfo getSupplierId() { return supplierId; }
    public String getPurchaseDate() { return purchaseDate; }
    public double getPurchaseAmount() { return purchaseAmount; }
    public int getWarrantyPeriod() { return warrantyPeriod; }
    public String getWarrantyEndDate() { return warrantyEndDate; }
    public String getWarrantyStatus() { return warrantyStatus; }
    public String getAssetCode() { return assetCode; }
    public String getExpectedScrapDate() { return expectedScrapDate; }
    public String getFactoryCode() { return factoryCode; }
    public String getStorageLocation() { return storageLocation; }
    public boolean isMonitored() { return isMonitored; }
    public boolean isControlled() { return isControlled; }
    public String getQcNum() { return qcNum; }
    public int getEquipmentAge() { return equipmentAge; }
    public int getDaysToScrap() { return daysToScrap; }
    public String getNotes() { return notes; }
    public List<PersonnelInfo> getRelatedPersonnel() { return relatedPersonnel; }
    public List<MaintenanceRecord> getMaintenanceHistory() { return maintenanceHistory; }
    public List<InspectionRecord> getInspectionHistory() { return inspectionHistory; }
}
