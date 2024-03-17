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

    // Getter method for modelId
    public String getModelId() {
        return modelId;
    }

    // Setter method for modelId
    public void setModelId(String modelId) {
        this.modelId = modelId;
    }

    // Getter method for name
    public String getName() {
        return name;
    }

    // Setter method for name
    public void setName(String name) {
        this.name = name;
    }

    // Getter method for description
    public String getDescription() {
        return description;
    }

    // Setter method for description
    public void setDescription(String description) {
        this.description = description;
    }

    // Getter method for mValue
    public double getmValue() {
        return mValue;
    }

    // Setter method for mValue
    public void setmValue(double mValue) {
        this.mValue = mValue;
    }

    // Getter method for cValue
    public double getcValue() {
        return cValue;
    }

    // Setter method for cValue
    public void setcValue(double cValue) {
        this.cValue = cValue;
    }

    // Getter method for appliedMaintenanceList
    public List<Maintenance.MaintenanceType> getAppliedMaintenanceList() {
        return appliedMaintenanceList;
    }

    // Setter method for appliedMaintenanceList
    public void setAppliedMaintenanceList(List<Maintenance.MaintenanceType> appliedMaintenanceList) {
        this.appliedMaintenanceList = appliedMaintenanceList;
    }
}
