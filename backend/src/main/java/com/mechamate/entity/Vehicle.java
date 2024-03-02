package com.mechamate.entity;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

@Document(collection = "Vehicles")
public class Vehicle {

    public enum VehicleType {
        Unknown,
        Car,
        Van,
        Bus,
        Lorry,
        Motorcycle
    }

    public enum FuelType {
        Unknown,
        Petrol,
        Diesel,
        LPGas,
        Electric
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private ObjectId _id;
    private String regNo;
    private VehicleType vehicleType;
    private FuelType fuelType;
    private String vehicleMake;
    private String vehicleModel;
    private Date regExpDate;
    private String insNo;
    private Date insExpDate;
    private ObjectId owner;
    private String obd2DeviceID;
    private long currentMileage;



    public Vehicle(String regNo, VehicleType vehicleType, FuelType fuelType,String vehicleMake,String vehicleModel,String insNo,Date insExpDate,Date regExpDate, ObjectId owner, long currentMileage) {
        this.regNo = regNo;
        this.vehicleType = vehicleType;
        this.fuelType = fuelType;
        this.vehicleMake=vehicleMake;
        this.vehicleModel=vehicleModel;
        this.insNo=insNo;
        this.insExpDate=insExpDate;
        this.regExpDate=regExpDate;
        this.owner = owner;
        this.obd2DeviceID = "";
        this.currentMileage = currentMileage;
    }

    public ObjectId get_id() {
        return _id;
    }

    public void set_id(ObjectId _id) {
        this._id = _id;
    }

    public String getRegNo() {
        return regNo;
    }

    public void setRegNo(String regNo) {
        this.regNo = regNo;
    }

    public VehicleType getVehicleType() {
        return vehicleType;
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

    public Date getRegExpDate() {
        return regExpDate;
    }

    public void setRegExpDate(Date regExpDate) {
        this.regExpDate = regExpDate;
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

    public void setVehicleType(VehicleType vehicleType) {
        this.vehicleType = vehicleType;
    }

    public FuelType getFuelType() {
        return fuelType;
    }

    public void setFuelType(FuelType fuelType) {
        this.fuelType = fuelType;
    }

    public ObjectId getOwner() {
        return owner;
    }

    public void setOwner(ObjectId owner) {
        this.owner = owner;
    }

    public String getObd2DeviceID() {
        return obd2DeviceID;
    }

    public void setObd2DeviceID(String obd2DeviceID) {
        this.obd2DeviceID = obd2DeviceID;
    }

    public long getCurrentMileage() {
        return currentMileage;
    }

    public void setCurrentMileage(long currentMileage) {
        this.currentMileage = currentMileage;
    }

}
