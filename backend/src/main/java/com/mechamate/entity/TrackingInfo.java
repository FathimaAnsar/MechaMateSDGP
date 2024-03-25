package com.mechamate.entity;


import com.mechamate.common.ApiToken;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "TrackingInfo")
public class TrackingInfo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private ObjectId _id;
    private String vehicleRegNo;
    private long mileage;
    private double enginTemp;
    private double vibration;
    private double longitude;
    private double latitude;
    private double engineRPM;
    private long drivingPattern;
    private long hourOfDay;
    private long dateTime;

    public TrackingInfo(String vehicleRegNo, long mileage, double enginTemp, double vibration, double longitude, double latitude, double engineRPM, long dateTime, long hourOfDay, long drivingPattern) {
        this.vehicleRegNo = vehicleRegNo;
        this.mileage = mileage;
        this.enginTemp = enginTemp;
        this.vibration = vibration;
        this.longitude = longitude;
        this.latitude = latitude;
        this.engineRPM = engineRPM;
        this.dateTime = dateTime;
        this.hourOfDay = hourOfDay;
        this.drivingPattern = drivingPattern;
    }

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

    public long getMileage() {
        return mileage;
    }

    public void setMileage(long mileage) {
        this.mileage = mileage;
    }

    public double getEnginTemp() {
        return enginTemp;
    }

    public void setEnginTemp(double enginTemp) {
        this.enginTemp = enginTemp;
    }

    public double getVibration() {
        return vibration;
    }

    public void setVibration(double vibration) {
        this.vibration = vibration;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getEngineRPM() {
        return engineRPM;
    }

    public void setEngineRPM(double engineRPM) {
        this.engineRPM = engineRPM;
    }

    public long getDrivingPattern() {
        return drivingPattern;
    }

    public void setDrivingPattern(long drivingPattern) {
        this.drivingPattern = drivingPattern;
    }

    public long getHourOfDay() {
        return hourOfDay;
    }

    public void setHourOfDay(long hourOfDay) {
        this.hourOfDay = hourOfDay;
    }

    public long getDateTime() {
        return dateTime;
    }

    public void setDateTime(long dateTime) {
        this.dateTime = dateTime;
    }

}
