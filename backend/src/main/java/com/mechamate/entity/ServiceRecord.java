package com.mechamate.entity;

import com.mechamate.dto.ServiceDTO;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;
import java.util.List;

// Annotation to specify this class as a document in MongoDB
@Document(collection = "ServiceRecords")
public class ServiceRecord {

    // MongoDB ID field
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private ObjectId _id;

    // Vehicle registration number
    private String vehicleRegNo;

    // Description of the service record
    private String description;

    // Date of the service record
    private Date date;

    // Mileage of the vehicle at the time of service
    private long mileage;

    // List of service details associated with this service record
    private List<ServiceDTO> services;

    // Constructor
    public ServiceRecord(String vehicleRegNo, String description, Date date, long mileage, List<ServiceDTO> services) {
        this.vehicleRegNo = vehicleRegNo;
        this.description = description;
        this.date = date;
        this.mileage = mileage;
        this.services = services;
    }

    // Getters and setters
    public ObjectId get_id() {
        return _id;
    }

    public void set_id(ObjectId _id) {
        this._id = _id;
    }

    public String getVehicleRegNo() {
        return vehicleRegNo;
    }

    public void setVehicleRegNo(String vehicleRegNo) {
        this.vehicleRegNo = vehicleRegNo;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public long getMileage() {
        return mileage;
    }

    public void setMileage(long mileage) {
        this.mileage = mileage;
    }

    public List<ServiceDTO> getServices() {
        return services;
    }

    public void setServices(List<ServiceDTO> services) {
        this.services = services;
    }
}
