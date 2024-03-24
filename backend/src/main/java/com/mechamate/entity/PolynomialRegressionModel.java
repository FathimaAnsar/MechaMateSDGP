package com.mechamate.entity;

import java.util.List;

public class PolynomialRegressionModel extends PredictionModel {

    private Double aValue;
    private Double bValue;
    private Double cValue;

    // Constructor
    public PolynomialRegressionModel(String name, String description, PredictionModel.modelType modelType,
                                     Double aValue, Double bValue, Double cValue,
                                     List<Maintenance.MaintenanceType> appliedMaintenanceList, PredictionModel.ParameterType paramType) {
        super(name, description, modelType, appliedMaintenanceList, paramType);
        this.aValue = aValue;
        this.bValue = bValue;
        this.cValue = cValue;
    }

    public Double getAValue() {
        return aValue;
    }

    public void setAValue(Double aValue) {
        this.aValue = aValue;
    }

    public Double getBValue() {
        return bValue;
    }

    public void setBValue(Double bValue) {
        this.bValue = bValue;
    }

    public Double getCValue() {
        return cValue;
    }

    public void setCValue(Double cValue) {
        this.cValue = cValue;
    }
}


