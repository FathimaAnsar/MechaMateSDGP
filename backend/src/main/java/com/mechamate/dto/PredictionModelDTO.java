package com.mechamate.dto;

import com.mechamate.entity.Maintenance;
import org.bson.types.ObjectId;

import java.util.List;

public class PredictionModelDTO {

    private String modelId;
    private String name;
    private String description;
    private double mValue;
    private double cValue;
    private List<Maintenance.MaintenanceType> appliedMaintenanceList;

    public PredictionModelDTO(String modelId, String name, String description, double mValue, double cValue, List<Maintenance.MaintenanceType> appliedMaintenanceList) {
        this.modelId = modelId;
        this.name = name;
        this.description = description;
        this.mValue = mValue;
        this.cValue = cValue;
        this.appliedMaintenanceList = appliedMaintenanceList;
    }

    public String getModelId() {
        return modelId;
    }

    public void setModelId(String modelId) {
        this.modelId = modelId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public double getmValue() {
        return mValue;
    }

    public void setmValue(double mValue) {
        this.mValue = mValue;
    }

    public double getcValue() {
        return cValue;
    }

    public void setcValue(double cValue) {
        this.cValue = cValue;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<Maintenance.MaintenanceType> getAppliedMaintenanceList() {
        return appliedMaintenanceList;
    }

    public void setAppliedMaintenanceList(List<Maintenance.MaintenanceType> appliedMaintenanceList) {
        this.appliedMaintenanceList = appliedMaintenanceList;
    }

    
}
