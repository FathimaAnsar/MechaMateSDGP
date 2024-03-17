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

    // Getter method for serviceRecordId
    public String getServiceRecordId() {
        return serviceRecordId;
    }

    // Setter method for serviceRecordId
    public void setServiceRecordId(String serviceRecordId) {
        this.serviceRecordId = serviceRecordId;
    }

    // Getter method for description
    public String getDescription() {
        return description;
    }

    // Setter method for description
    public void setDescription(String description) {
        this.description = description;
    }

    // Getter method for date
    public Date getDate() {
        return date;
    }

    // Setter method for date
    public void setDate(Date date) {
        this.date = date;
    }

    // Getter method for mileage
    public long getMileage() {
        return mileage;
    }

    // Setter method for mileage
    public void setMileage(long mileage) {
        this.mileage = mileage;
    }

    // Getter method for services
    public List<ServiceDTO> getServices() {
        return services;
    }

    // Setter method for services
    public void setServices(List<ServiceDTO> services) {
        this.services = services;
    }

}
