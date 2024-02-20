package com.mechamate.entity;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Document(collection = "Maintenance")
public class Maintenance {

    public enum MaintenanceType {
        Default,
        WheelAlignment,
        EngineOilChange,
        BrakeFluidChange,
        BrakeCaliperChange,
        CoolantChange,
        TireChange,
        PistonRingsChange,
        PistonChange,
        DieselFilterChange,
        VipersChange

    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private ObjectId _id;
    private MaintenanceType maintenanceType;
    private String name;
    private String description;
    private List<ObjectId> predictionModels;

    public Maintenance(MaintenanceType maintenanceType, String name, String description, List<ObjectId> predictionModels) {
        this.maintenanceType = maintenanceType;
        this.name = name;
        this.description = description;
        this.predictionModels = predictionModels;
    }

    public ObjectId get_id() {
        return _id;
    }

    public void set_id(ObjectId _id) {
        this._id = _id;
    }

    public MaintenanceType getMaintenanceType() {
        return maintenanceType;
    }

    public void setMaintenanceType(MaintenanceType maintenanceType) {
        this.maintenanceType = maintenanceType;
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

    public List<ObjectId> getPredictionModels() {
        return predictionModels;
    }

    public void setPredictionModels(List<ObjectId> predictionModels) {
        this.predictionModels = predictionModels;
    }

}

