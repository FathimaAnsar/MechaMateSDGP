package com.mechamate.dto;

import com.mechamate.entity.Maintenance;
import java.util.List;


public class MaintenanceDTO {
    // Unique identifier for the maintenance
    private String maintenanceId;

    // Type of maintenance (e.g., scheduled, unscheduled)
    private Maintenance.MaintenanceType maintenanceType;

    // Name of the maintenance
    private String name;

    // Description of the maintenance
    private String description;

    // List of prediction models associated with the maintenance
    private List<String> predictionModels;

    // Constructor for MaintenanceDTO class
    public MaintenanceDTO(String maintenanceId, Maintenance.MaintenanceType maintenanceType, String name, String description, List<String> predictionModels) {
        this.maintenanceId = maintenanceId;
        this.maintenanceType = maintenanceType;
        this.name = name;
        this.description = description;
        this.predictionModels = predictionModels;
    }

    // Getter method for maintenanceId
    public String getMaintenanceId() {
        return maintenanceId;
    }

    // Setter method for maintenanceId
    public void setMaintenanceId(String maintenanceId) {
        this.maintenanceId = maintenanceId;
    }

    // Getter method for maintenanceType
    public Maintenance.MaintenanceType getMaintenanceType() {
        return maintenanceType;
    }

    // Setter method for maintenanceType
    public void setMaintenanceType(Maintenance.MaintenanceType maintenanceType) {
        this.maintenanceType = maintenanceType;
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

    // Getter method for predictionModels
    public List<String> getPredictionModels() {
        return predictionModels;
    }

    // Setter method for predictionModels
    public void setPredictionModels(List<String> predictionModels) {
        this.predictionModels = predictionModels;
    }

}
