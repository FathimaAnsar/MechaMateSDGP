package com.mechamate.dto;

import com.mechamate.entity.ServiceRecord;
import com.mechamate.entity.Vehicle;

import java.util.List;

public class VehicleDTO {

    private String registrationNumber;
    private Vehicle.VehicleModel model;
    private Vehicle.FuelType fuelType;
    private List<ServiceRecordDTO> serviceRecords;

    public VehicleDTO(String registrationNumber, Vehicle.VehicleModel model, Vehicle.FuelType fuelType, List<ServiceRecordDTO> serviceRecords) {
        this.registrationNumber = registrationNumber;
        this.model = model;
        this.fuelType = fuelType;
        this.serviceRecords = serviceRecords;
    }

    public String getRegistrationNumber() {
        return registrationNumber;
    }

    public void setRegistrationNumber(String registrationNumber) {
        this.registrationNumber = registrationNumber;
    }

    public Vehicle.VehicleModel getModel() {
        return model;
    }

    public void setModel(Vehicle.VehicleModel model) {
        this.model = model;
    }

    public Vehicle.FuelType getFuelType() {
        return fuelType;
    }

    public void setFuelType(Vehicle.FuelType fuelType) {
        this.fuelType = fuelType;
    }

    public List<ServiceRecordDTO> getServiceRecords() {
        return serviceRecords;
    }

    public void setServiceRecords(List<ServiceRecordDTO> serviceRecords) {
        this.serviceRecords = serviceRecords;
    }
}
