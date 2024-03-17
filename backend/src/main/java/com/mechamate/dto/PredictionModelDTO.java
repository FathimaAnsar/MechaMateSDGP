package com.mechamate.dto;

import com.mechamate.entity.Maintenance;
import org.bson.types.ObjectId;

import java.util.List;

public class PredictionModelDTO {
    // Unique identifier for the prediction model
    private String modelId;

    // Name of the prediction model
    private String name;

    // Description of the prediction model
    private String description;

    // Value of the slope (m) in the prediction model
    private double mValue;

    // Value of the y-intercept (c) in the prediction model
    private double cValue;

    // List of maintenance types to which this prediction model is applied
    private List<Maintenance.MaintenanceType> appliedMaintenanceList;

    // Constructor for PredictionModelDTO class
    public PredictionModelDTO(String modelId, String name, String description, double mValue, double cValue, List<Maintenance.MaintenanceType> appliedMaintenanceList) {
        this.modelId = modelId;
        this.name = name;
        this.description = description;
        this.mValue = mValue;
        this.cValue = cValue;
        this.appliedMaintenanceList = appliedMaintenanceList;
    }

    // Getters and setters
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

    public void setDescription(String description) {
        this.description = description;
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

    public List<Maintenance.MaintenanceType> getAppliedMaintenanceList() {
        return appliedMaintenanceList;
    }

    public void setAppliedMaintenanceList(List<Maintenance.MaintenanceType> appliedMaintenanceList) {
        this.appliedMaintenanceList = appliedMaintenanceList;
    }
}
