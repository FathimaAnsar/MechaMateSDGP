package com.mechamate.entity;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

// Annotation to specify this class as a document in MongoDB
@Document(collection = "PredictionModels")
public class PredictionModel {

    // MongoDB ID field
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private ObjectId _id;

    // Name of the prediction model
    private String name;

    // Description of the prediction model
    private String description;

    // Value of the slope parameter for the prediction model
    private double mValue;

    // Value of the intercept parameter for the prediction model
    private double cValue;

    // List of maintenance types applied by this prediction model
    private List<Maintenance.MaintenanceType> appliedMaintenanceList;

    // Constructor
    public PredictionModel(String name, String description, double mValue, double cValue, List<Maintenance.MaintenanceType> appliedMaintenanceList) {
        this.name = name;
        this.description = description;
        this.mValue = mValue;
        this.cValue = cValue;
        this.appliedMaintenanceList = appliedMaintenanceList;
    }

    // Getters and setters
    public ObjectId get_id() {
        return _id;
    }

    public void set_id(ObjectId _id) {
        this._id = _id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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

    public List<Maintenance.MaintenanceType> getAppliedMaintenanceList() {
        return appliedMaintenanceList;
    }

    public void setAppliedMaintenanceList(List<Maintenance.MaintenanceType> appliedMaintenanceList) {
        this.appliedMaintenanceList = appliedMaintenanceList;
    }

}
