package com.mechamate.MechaMate;

import java.util.ArrayList;

public class Vehicle {
    private int type;
    private int id;
    private String brand;
    private String model;
    private UserProfile owner;
    private String regNo;
    private int mileageKMs;
    private ArrayList<Maintenance> maintenanceList;
    private ArrayList<ServiceRecord> serviceRecords;
    private Log log;

    // Constructors

    public Vehicle() {

    }
    public String getVehicleTypeDescription() {
        int type = 0;
        switch (type) {
            case 1:
                return "Car";
            case 2:
                return "Bus";
            case 3:
                return "Truck";
            case 4:
                return "Motorcycle";
            // Add more cases for other enum values if needed
            default:
                return "Unknown";
        }
    }

    public Vehicle(int id, int type, String brand, String model, UserProfile owner, String regNo,
                   int mileageKMs, ArrayList<Maintenance> maintenanceList, ArrayList<ServiceRecord> serviceRecords, Log log) {
        this.id = id;
        this.type = type;
        this.brand = brand;
        this.model = model;
        this.owner = owner;
        this.regNo = regNo;
        this.mileageKMs = mileageKMs;
        this.maintenanceList = maintenanceList;
        this.serviceRecords = serviceRecords;
        this.log = log;
    }

    // Getters and Setters

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public UserProfile getOwner() {
        return owner;
    }

    public void setOwner(UserProfile owner) {
        this.owner = owner;
    }

    public String getRegNo() {
        return regNo;
    }

    public void setRegNo(String regNo) {
        this.regNo = regNo;
    }

    public int getMileageKMs() {
        return mileageKMs;
    }

    public void setMileageKMs(int mileageKMs) {
        this.mileageKMs = mileageKMs;
    }

    public ArrayList<Maintenance> getMaintenanceList() {
        return maintenanceList;
    }

    public void setMaintenanceList(ArrayList<Maintenance> maintenanceList) {
        this.maintenanceList = maintenanceList;
    }

    public ArrayList<ServiceRecord> getServiceRecords() {
        return serviceRecords;
    }

    public void setServiceRecords(ArrayList<ServiceRecord> serviceRecords) {
        this.serviceRecords = serviceRecords;
    }

    public Log getLog() {
        return log;
    }

    public void setLog(Log log) {
        this.log = log;
    }

    // Methods

    public void addServiceRecord(ServiceRecord record) {
        this.serviceRecords.add(record);
    }

    public int getMileage() {
        return this.mileageKMs;
    }

    public void updateMileage(int mileage) {
        this.mileageKMs += mileage;
    }
}
