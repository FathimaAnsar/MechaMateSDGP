package com.mechamate.dto;

import com.mechamate.entity.Maintenance;
import com.mechamate.entity.PredictionModel;

import java.util.List;

public class LinearRegressionModelDTO extends PredictionModelDTO{
    private double mValue;
    private double cValue;

    public LinearRegressionModelDTO(String modelId, String name, String description, PredictionModel.modelType modelType,
                                    double mValue, double cValue, List<Maintenance.MaintenanceType> appliedMaintenanceList) {
        super(modelId, name, description, modelType, appliedMaintenanceList);
        this.mValue = mValue;
        this.cValue = cValue;
    }

    public double getmValue() {
        return mValue;
    }

    public void setmValue(double mValue) {
        this.mValue = mValue;
    }

    public double getcValue() {
        return cValue;
    }

    public void setcValue(double cValue) {
        this.cValue = cValue;
    }
}
