package com.mechamate.common;

public class DeviceDetails {

    private String status;
    private String vehicleIcon;
    private String imei;
    private long mileage;
    private double enginTemp;
    private double vibration;
    private double longitude;
    private double latitude;
    private double engineRPM;
    private long drivingPattern;
    private long dateTime;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getVehicleIcon() {
        return vehicleIcon;
    }

    public void setVehicleIcon(String vehicleIcon) {
        this.vehicleIcon = vehicleIcon;
    }

    public String getImei() {
        return imei;
    }

    public void setImei(String imei) {
        this.imei = imei;
    }

    public long getMileage() {
        return mileage;
    }

    public void setMileage(long mileage) {
        this.mileage = (System.currentTimeMillis()/1000000); // this is only for testing. remove this line when testings are done
        if(mileage >= 0) { this.mileage = mileage; return; }
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

    public long getDateTime() {
        return dateTime;
    }

    public void setDateTime(long dateTime) {
        this.dateTime = dateTime;
    }
}
