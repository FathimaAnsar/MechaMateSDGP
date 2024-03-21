package com.mechamate.entity;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

// Annotation to specify this class as a document in MongoDB
@Document(collection = "Maintenance")
public class Maintenance {

    // Enum to represent different types of maintenance
    public enum MaintenanceType {
        Default,
        WheelAlignment,
        EngineOilChange,
        BrakeFluidChange,
        BrakeCaliperChange,
        CoolantChange,
        TireChange,
        PistonChange
    }

    // MongoDB ID field
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private ObjectId _id;

    // Type of maintenance
    private MaintenanceType maintenanceType;

    // Name of the maintenance
    private String name;

    // Description of the maintenance
    private String description;

    // List of prediction models associated with this maintenance
    private List<ObjectId> predictionModels;

    // Constructor
    public Maintenance(MaintenanceType maintenanceType, String name, String description, List<ObjectId> predictionModels) {
        this.maintenanceType = maintenanceType;
        this.name = name;
        this.description = description;
        this.predictionModels = predictionModels;
    }

    // Getters and setters
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
