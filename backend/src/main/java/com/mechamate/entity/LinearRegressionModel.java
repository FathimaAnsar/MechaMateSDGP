package com.mechamate.entity;

import com.mechamate.features.PdM;

import java.util.List;

public class LinearRegressionModel extends PredictionModel {
    private double mValue;
    private double cValue;

    public LinearRegressionModel(String name, String description, PredictionModel.modelType modelType, double mValue,
                                 double cValue, List<Maintenance.MaintenanceType> appliedMaintenanceList) {
        super(name, description, modelType, appliedMaintenanceList);
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
