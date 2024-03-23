package com.mechamate.entity;

import com.mechamate.features.PdM;

import java.util.List;

public class LinearRegressionModel extends PredictionModel {
    private double slope;
    private double intercept;

    // Constructor
    public LinearRegressionModel(String name, String description, PredictionModel.modelType modelType, double slope, double intercept,
                                 List<Maintenance.MaintenanceType> appliedMaintenanceList) {

        super(name, description, modelType, appliedMaintenanceList);
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
