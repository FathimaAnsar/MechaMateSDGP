package com.mechamate.dto;

import java.util.Date;
import java.util.List;

public class ServiceRecordDTO {

    // Unique identifier for the service record
    private String serviceRecordId;

    // Description of the service record
    private String description;

    // Date when the service record was created
    private Date date;

    // Mileage at the time of the service record
    private long mileage;

    // List of services associated with the service record
    private List<ServiceDTO> services;

    // Constructor for ServiceRecordDTO class
    public ServiceRecordDTO(String serviceRecordId, String description, Date date, long mileage, List<ServiceDTO> services) {
        this.serviceRecordId = serviceRecordId;
        this.description = description;
        this.date = date;
        this.mileage = mileage;
        this.services = services;
    }

    // Getters and setters
    public String getServiceRecordId() {
        return serviceRecordId;
    }

    public void setServiceRecordId(String serviceRecordId) {
        this.serviceRecordId = serviceRecordId;
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
