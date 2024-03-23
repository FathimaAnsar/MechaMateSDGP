package com.mechamate.dto;

import com.mechamate.entity.Maintenance;
import com.mechamate.entity.PredictionModel;

public class PredictionDTO {
    public enum PredictionStatus {
        Predicted,
        Actual,
        InfoNotFound
    }

    private Maintenance.MaintenanceType maintenanceType;
    private PredictionStatus status;
    private long actualKMs;
    private long predictedKMs;
    private boolean overdue;

    public PredictionDTO(Maintenance.MaintenanceType maintenanceType, PredictionStatus status, long actualKMs, long predictedKMs, boolean overdue) {
        this.maintenanceType = maintenanceType;
        this.status = status;
        this.actualKMs = actualKMs;
        this.predictedKMs = predictedKMs;
        this.overdue = overdue;
    }

    public Maintenance.MaintenanceType getMaintenanceType() {
        return maintenanceType;
    }

    public void setMaintenanceType(Maintenance.MaintenanceType maintenanceType) {
        this.maintenanceType = maintenanceType;
    }

    public PredictionStatus getStatus() {
        return status;
    }

    public void setStatus(PredictionStatus status) {
        this.status = status;
    }

    public long getActualKMs() {
        return actualKMs;
    }

    public void setActualKMs(long actualKMs) {
        this.actualKMs = actualKMs;
    }

    public long getPredictedKMs() {
        return predictedKMs;
    }

    public void setPredictedKMs(long predictedKMs) {
        this.predictedKMs = predictedKMs;
    }

    public boolean isOverdue() {
        return overdue;
    }

    public void setOverdue(boolean overdue) {
        this.overdue = overdue;
    }
}
