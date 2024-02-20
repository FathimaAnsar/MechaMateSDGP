package com.mechamate.dto;

import com.mechamate.entity.Maintenance;
import org.bson.types.ObjectId;

import java.util.ArrayList;
import java.util.List;

public class MaintenanceDTO {
    private String maintenanceId;
    private Maintenance.MaintenanceType maintenanceType;
    private String name;
    private String description;
    private List<String> predictionModels;

    public MaintenanceDTO(String maintenanceId, Maintenance.MaintenanceType maintenanceType, String name, String description, List<String> predictionModels) {
        this.maintenanceId = maintenanceId;
        this.maintenanceType = maintenanceType;
        this.name = name;
        this.description = description;
        this.predictionModels = predictionModels;
    }

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
