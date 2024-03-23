package com.mechamate.dto;

import com.mechamate.entity.Maintenance;
import com.mechamate.entity.PredictionModel;
import org.bson.types.ObjectId;

import java.util.List;

public class PredictionModelDTO {
    // Unique identifier for the prediction model
    private String modelId;

    // Name of the prediction model
    private String name;

    // Description of the prediction model
    private String description;

    // Type of the prediction model
    private PredictionModel.modelType modelType;

    // List of maintenance types to which this prediction model is applied
    private List<Maintenance.MaintenanceType> appliedMaintenanceList;

    // Constructor for PredictionModelDTO class
    public PredictionModelDTO(String modelId, String name, String description,PredictionModel.modelType modelType, List<Maintenance.MaintenanceType> appliedMaintenanceList) {
        this.modelId = modelId;
        this.name = name;
        this.description = description;
        this.modelType = modelType;
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

    public PredictionModel.modelType getModelType() {
        return modelType;
    }

    public void setModelType(PredictionModel.modelType modelType) {
        this.modelType = modelType;
    }

    public List<Maintenance.MaintenanceType> getAppliedMaintenanceList() {
        return appliedMaintenanceList;
    }

    public void setAppliedMaintenanceList(List<Maintenance.MaintenanceType> appliedMaintenanceList) {
        this.appliedMaintenanceList = appliedMaintenanceList;
    }
}
