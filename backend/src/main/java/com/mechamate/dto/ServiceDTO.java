package com.mechamate.dto;

import com.mechamate.entity.Maintenance;

public class ServiceDTO {

    public enum ServiceQualityEnum {
        Low,
        Medium,
        High
    }

    private String description;
    private Maintenance.MaintenanceType appliedMaintenanceId;
    private long nextServiceInKMs;
    private ServiceQualityEnum serviceQuality;
    private boolean archived;


    public ServiceDTO(String description, Maintenance.MaintenanceType appliedMaintenanceId, long nextServiceInKMs, ServiceQualityEnum serviceQuality, boolean archived) {
        this.description = description;
        this.appliedMaintenanceId = appliedMaintenanceId;
        this.nextServiceInKMs = nextServiceInKMs;
        this.serviceQuality = serviceQuality;
        this.archived = archived;
    }

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

    public boolean isArchived() {
        return archived;
    }

    public void setArchived(boolean archived) {
        this.archived = archived;
    }
}
