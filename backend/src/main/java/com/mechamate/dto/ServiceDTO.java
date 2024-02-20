package com.mechamate.dto;

import org.bson.types.ObjectId;

public class ServiceDTO {
    private String description;
    private String appliedMaintenanceId;
    private long nextServiceInKMs;
    private long nextServiceInMonths;
    private boolean archived;

    public ServiceDTO(String description, String appliedMaintenanceId, long nextServiceInKMs, long nextServiceInMonths, boolean archived) {
        this.description = description;
        this.appliedMaintenanceId = appliedMaintenanceId;
        this.nextServiceInKMs = nextServiceInKMs;
        this.nextServiceInMonths = nextServiceInMonths;
        this.archived = archived;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getAppliedMaintenanceId() {
        return appliedMaintenanceId;
    }

    public void setAppliedMaintenanceId(String appliedMaintenanceId) {
        this.appliedMaintenanceId = appliedMaintenanceId;
    }

    public long getNextServiceInKMs() {
        return nextServiceInKMs;
    }

    public void setNextServiceInKMs(long nextServiceInKMs) {
        this.nextServiceInKMs = nextServiceInKMs;
    }

    public long getNextServiceInMonths() {
        return nextServiceInMonths;
    }

    public void setNextServiceInMonths(long nextServiceInMonths) {
        this.nextServiceInMonths = nextServiceInMonths;
    }

    public boolean isArchived() {
        return archived;
    }

    public void setArchived(boolean archived) {
        this.archived = archived;
    }
}
