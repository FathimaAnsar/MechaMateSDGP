package com.mechamate.dto;

import com.mechamate.entity.ServiceRecord;
import com.mechamate.entity.Vehicle;

import java.util.Date;
import java.util.List;

public class VehicleDTO {

    private String registrationNumber;
    private Vehicle.VehicleType vehicleType;
    private Vehicle.FuelType fuelType;
    private String vehicleMake;
    private String vehicleModel;
    private String insNo;
    private Date insExpDate;
    private Date regExpDate;
    private long currentMileage;
    private String obd2DeviceID;
    private List<ServiceRecordDTO> serviceRecords;

    public VehicleDTO(String registrationNumber, Vehicle.VehicleType vehicleType, Vehicle.FuelType fuelType, String vehicleMake, String vehicleModel, String insNo, Date insExpDate, Date regExpDate, List<ServiceRecordDTO> serviceRecords, long currentMileage, String obd2DeviceID) {
        this.registrationNumber = registrationNumber;
        this.vehicleType = vehicleType;
        this.fuelType = fuelType;
        this.vehicleMake = vehicleMake;
        this.vehicleModel = vehicleModel;
        this.insNo = insNo;
        this.insExpDate = insExpDate;
        this.regExpDate = regExpDate;
        this.serviceRecords = serviceRecords;
        this.currentMileage = currentMileage;
        this.obd2DeviceID= obd2DeviceID;
    }

    public String getRegistrationNumber() {
        return registrationNumber;
    }

    public Vehicle.VehicleType getVehicleType() {
        return vehicleType;
    }

    public void setVehicleType(Vehicle.VehicleType vehicleType) {
        this.vehicleType = vehicleType;
    }

    public String getVehicleMake() {
        return vehicleMake;
    }

    public void setVehicleMake(String vehicleMake) {
        this.vehicleMake = vehicleMake;
    }

    public String getVehicleModel() {
        return vehicleModel;
    }

    public void setVehicleModel(String vehicleModel) {
        this.vehicleModel = vehicleModel;
    }

    public String getInsNo() {
        return insNo;
    }

    public void setInsNo(String insNo) {
        this.insNo = insNo;
    }

    public Date getInsExpDate() {
        return insExpDate;
    }

    public void setInsExpDate(Date insExpDate) {
        this.insExpDate = insExpDate;
    }

    public Date getRegExpDate() {
        return regExpDate;
    }

    public void setRegExpDate(Date regExpDate) {
        this.regExpDate = regExpDate;
    }

    public void setRegistrationNumber(String registrationNumber) {
        this.registrationNumber = registrationNumber;
    }

//    public Vehicle.VehicleType getModel() {
//        return vehicleType;
//    }
//
//    public void setModel(Vehicle.VehicleType vehicleType) {
//        this.vehicleType = vehicleType;
//    }

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

    public long getCurrentMileage() {
        return currentMileage;
    }

    public void setCurrentMileage(long currentMileage) {
        this.currentMileage = currentMileage;
    }

    public String getObd2DeviceID() {
        return obd2DeviceID;
    }

    public void setObd2DeviceID(String obd2DeviceID) {
        this.obd2DeviceID = obd2DeviceID;
    }
}
