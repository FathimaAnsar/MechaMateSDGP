package com.mechamate.entity;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Document(collection = "PredictionModels")
public class PredictionModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private ObjectId _id;
    private String name;
    private String description;
    private List<Maintenance.MaintenanceType> appliedMaintenanceList;

    public PredictionModel(String name, String description, List<Maintenance.MaintenanceType> appliedMaintenanceList) {
        this.name = name;
        this.description = description;
        this.appliedMaintenanceList = appliedMaintenanceList;
    }

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

    public List<Maintenance.MaintenanceType> getAppliedMaintenanceList() {
        return appliedMaintenanceList;
    }

    public void setAppliedMaintenanceList(List<Maintenance.MaintenanceType> appliedMaintenanceList) {
        this.appliedMaintenanceList = appliedMaintenanceList;
    }

}
