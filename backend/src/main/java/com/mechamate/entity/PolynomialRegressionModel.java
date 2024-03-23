package com.mechamate.entity;

import java.util.List;

public class PolynomialRegressionModel extends PredictionModel {

    private Double[] coefficients;

    // Constructor
    public PolynomialRegressionModel(String name, String description, PredictionModel.modelType modelType, Double[] coefficients,
                                     List<Maintenance.MaintenanceType> appliedMaintenanceList) {
        super(name, description, modelType, appliedMaintenanceList);
        this.coefficients = coefficients;
    }

    public Double[] getCoefficients() {
        return coefficients;
    }

    public void setCoefficients(Double[] coefficients) {
        this.coefficients = coefficients;
    }
}
