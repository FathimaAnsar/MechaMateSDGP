package com.mechamate.dto;

import com.mechamate.entity.Maintenance;
import com.mechamate.entity.PredictionModel;

import java.util.List;

public class PolynomialRegressionModelDTO extends PredictionModelDTO{
    private Double[] coefficients;

    public PolynomialRegressionModelDTO(String modelId, String name, String description, PredictionModel.modelType modelType,
                                        Double[] coefficients, List<Maintenance.MaintenanceType> appliedMaintenanceList) {
        super(modelId, name, description, modelType, appliedMaintenanceList);
        this.coefficients = coefficients;
    }

    public Double[] getCoefficients() {
        return coefficients;
    }

    public void setCoefficients(Double[] coefficients) {
        this.coefficients = coefficients;
    }
}
