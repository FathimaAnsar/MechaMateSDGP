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

    public enum modelType {
        polynomial,
        linear,
    }

    public enum ParameterType {
        EngineTemp,
        Temp,
        Vibration,
        DrivingPattern,
        RPM,
        HourOfTheDay
    }

    // MongoDB ID field
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private ObjectId _id;

    // Name of the prediction model
    private String name;

    // Description of the prediction model
    private String description;

    private modelType modelType;

    private ParameterType paramType;

    // List of maintenance types applied by this prediction model
    private List<Maintenance.MaintenanceType> appliedMaintenanceList;

    // Constructor
    public PredictionModel(String name, String description, PredictionModel.modelType modelType,
                           List<Maintenance.MaintenanceType> appliedMaintenanceList, ParameterType paramType) {
        this.name = name;
        this.description = description;
        this.modelType = modelType;
        this.appliedMaintenanceList = appliedMaintenanceList;
        this.paramType = paramType;
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

    public PredictionModel.modelType getModelType() {
        return modelType;
    }

    public void setModelType(PredictionModel.modelType modelType) {
        this.modelType = modelType;
    }

    public List<Maintenance.MaintenanceType> getAppliedMaintenanceList() {
        return appliedMaintenanceList;
    }

    public void setAppliedMaintenanceList(List<Maintenance.MaintenanceType> appliedMaintenanceList) {
        this.appliedMaintenanceList = appliedMaintenanceList;
    }

    public ParameterType getParamType() {
        return paramType;
    }

    public void setParamType(ParameterType paramType) {
        this.paramType = paramType;
    }

}
