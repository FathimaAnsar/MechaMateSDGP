package com.mechamate.entity;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "Vehicles")
public class Vehicle {

    public enum VehicleModel {
        Unknown,
        Car,
        Van,
        SUV,
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
    private VehicleModel model;
    private FuelType fuelType;
    private ObjectId owner;

    public Vehicle(String regNo, VehicleModel model, FuelType fuelType, ObjectId owner) {
        this.regNo = regNo;
        this.model = model;
        this.fuelType = fuelType;
        this.owner = owner;
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

    public VehicleModel getModel() {
        return model;
    }

    public void setModel(VehicleModel model) {
        this.model = model;
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
}
