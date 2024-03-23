package com.mechamate.dto;

import com.mechamate.entity.Maintenance;
import com.mechamate.entity.PredictionModel;

import java.util.List;

public class PolynomialRegressionModelDTO extends PredictionModelDTO{
    private Double aValue;
    private Double bValue;
    private Double cValue;


    public PolynomialRegressionModelDTO(String modelId, String name, String description, PredictionModel.modelType modelType,
                                         Double aValue, Double bValue, Double cValue,
                                        List<Maintenance.MaintenanceType> appliedMaintenanceList) {
        super(modelId, name, description, modelType, appliedMaintenanceList);
        this.aValue = aValue;
        this.bValue = bValue;
        this.cValue = cValue;
    }


    public Double getaValue() {
        return aValue;
    }

    public void setaValue(Double aValue) {
        this.aValue = aValue;
    }

    public Double getbValue() {
        return bValue;
    }

    public void setbValue(Double bValue) {
        this.bValue = bValue;
    }

    public Double getcValue() {
        return cValue;
    }

    public void setcValue(Double cValue) {
        this.cValue = cValue;
    }
}
