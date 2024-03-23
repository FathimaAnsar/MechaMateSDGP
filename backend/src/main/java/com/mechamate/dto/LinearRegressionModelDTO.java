package com.mechamate.dto;

import com.mechamate.entity.Maintenance;
import com.mechamate.entity.PredictionModel;

import java.util.List;

public class LinearRegressionModelDTO extends PredictionModelDTO{
    private double slope;
    private double intercept;

    public LinearRegressionModelDTO(String modelId, String name, String description, PredictionModel.modelType modelType, double slope,
                                    double intercept, List<Maintenance.MaintenanceType> appliedMaintenanceList) {
        super(modelId, name, description, modelType, appliedMaintenanceList);
        this.slope = slope;
        this.intercept = intercept;
    }

    public double getSlope() {
        return slope;
    }

    public void setSlope(double slope) {
        this.slope = slope;
    }

    public double getIntercept() {
        return intercept;
    }

    public void setIntercept(double intercept) {
        this.intercept = intercept;
    }
}
