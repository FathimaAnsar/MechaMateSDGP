package com.mechamate.dto;

import com.mechamate.entity.Maintenance;

public class ServiceDTO {

    // Enumeration defining service quality levels
    public enum ServiceQualityEnum {
        Low,
        Medium,
        High
    }

    // Description of the service
    private String description;

    // ID of the applied maintenance associated with the service
    private Maintenance.MaintenanceType appliedMaintenanceId;

    // Distance until the next service is due, in kilometers
    private long nextServiceInKMs;

    // Quality level of the service
    private ServiceQualityEnum serviceQuality;

    // Flag indicating whether the service is archived or not
    private boolean archived;

    // Constructor for ServiceDTO class
    public ServiceDTO(String description, Maintenance.MaintenanceType appliedMaintenanceId, long nextServiceInKMs, ServiceQualityEnum serviceQuality, boolean archived) {
        this.description = description;
        this.appliedMaintenanceId = appliedMaintenanceId;
        this.nextServiceInKMs = nextServiceInKMs;
        this.serviceQuality = serviceQuality;
        this.archived = archived;
    }

    // Getter method for description
    public String getDescription() {
        return description;
    }

    // Setter method for description
    public void setDescription(String description) {
        this.description = description;
    }

    // Getter method for appliedMaintenanceId
    public Maintenance.MaintenanceType getAppliedMaintenanceId() {
        return appliedMaintenanceId;
    }

    // Setter method for appliedMaintenanceId
    public void setAppliedMaintenanceId(Maintenance.MaintenanceType appliedMaintenanceId) {
        this.appliedMaintenanceId = appliedMaintenanceId;
    }

    // Getter method for nextServiceInKMs
    public long getNextServiceInKMs() {
        return nextServiceInKMs;
    }

    // Setter method for nextServiceInKMs
    public void setNextServiceInKMs(long nextServiceInKMs) {
        this.nextServiceInKMs = nextServiceInKMs;
    }

    // Getter method for serviceQuality
    public ServiceQualityEnum getServiceQuality() {
        return serviceQuality;
    }

    // Setter method for serviceQuality
    public void setServiceQuality(ServiceQualityEnum serviceQuality) {
        this.serviceQuality = serviceQuality;
    }

    // Getter method for archived
    public boolean isArchived() {
        return archived;
    }

    // Setter method for archived
    public void setArchived(boolean archived) {
        this.archived = archived;
    }
}
