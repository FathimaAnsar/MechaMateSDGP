package com.mechamate.dto;

import com.mechamate.entity.Maintenance;

public class ServiceDTO {

    // Enum for defining service quality levels
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
    private boolean isDone;

    private long addedDate;


    // Constructor for ServiceDTO class
    public ServiceDTO(String description, Maintenance.MaintenanceType appliedMaintenanceId, long nextServiceInKMs, ServiceQualityEnum serviceQuality, boolean isDone, long addedDate) {
        this.description = description;
        this.appliedMaintenanceId = appliedMaintenanceId;
        this.nextServiceInKMs = nextServiceInKMs;
        this.serviceQuality = serviceQuality;
        this.isDone = isDone;
        this.addedDate = addedDate;
    }

    // Getters and setters
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Maintenance.MaintenanceType getAppliedMaintenanceId() {
        return appliedMaintenanceId;
    }

    public void setAppliedMaintenanceId(Maintenance.MaintenanceType appliedMaintenanceId) {
        this.appliedMaintenanceId = appliedMaintenanceId;
    }

    public long getNextServiceInKMs() {
        return nextServiceInKMs;
    }

    public void setNextServiceInKMs(long nextServiceInKMs) {
        this.nextServiceInKMs = nextServiceInKMs;
    }

    public ServiceQualityEnum getServiceQuality() {
        return serviceQuality;
    }

    public void setServiceQuality(ServiceQualityEnum serviceQuality) {
        this.serviceQuality = serviceQuality;
    }

    public boolean isDone() {
        return isDone;
    }

    public void setDone(boolean done) {
        isDone = done;
    }

    public long getAddedDate() {
        return addedDate;
    }

    public void setAddedDate(long addedDate) {
        this.addedDate = addedDate;
    }

}
