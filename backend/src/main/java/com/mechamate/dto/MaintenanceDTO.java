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

    // Getters and setters
    public String getMaintenanceId() {
        return maintenanceId;
    }

    public void setMaintenanceId(String maintenanceId) {
        this.maintenanceId = maintenanceId;
    }

    public Maintenance.MaintenanceType getMaintenanceType() {
        return maintenanceType;
    }

    public void setMaintenanceType(Maintenance.MaintenanceType maintenanceType) {
        this.maintenanceType = maintenanceType;
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

    public List<String> getPredictionModels() {
        return predictionModels;
    }

    public void setPredictionModels(List<String> predictionModels) {
        this.predictionModels = predictionModels;
    }

}
