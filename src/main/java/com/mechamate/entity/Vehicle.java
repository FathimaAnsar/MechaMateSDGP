package com.mechamate.entity;

import jakarta.persistence.Entity;

import com.mechamate.entity.UserProfile;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Document(collection = "Vehicle")
public class Vehicle {
    public enum VehicleType {
        SEDAN,
        SUV,
        TRUCK,
        VAN,
        HATCHBACK,
        CONVERTIBLE,
        COUPE,
        OTHER
    }

    @Id
    private String vehicleId;
    private String vehicleRegNo;
    private VehicleType vehicleType;
    private String description;
    @DBRef
    private UserProfile owner;

    private String manufacturer;

    private String model;

    private int mileageKMs;

    private List<Maintenance> maintenanceList;

    private List<ServiceRecord> serviceRecords;

    public void addServiceRecord(ServiceRecord serviceRecord){
        serviceRecords.add(serviceRecord);
    }

    public void addMaintenance(Maintenance maintenance){
        maintenanceList.add(maintenance);
    }

}
