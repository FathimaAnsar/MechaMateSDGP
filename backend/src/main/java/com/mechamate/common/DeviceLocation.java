package com.mechamate.common;

public class DeviceLocation {

    private boolean isDeviceOnline;
    private String vehicleRegNo;
    private double longitude;
    private double latitude;
    private String mapUrl;
    private String locationDateTime;

    public DeviceLocation(boolean isDeviceOnline, String vehicleRegNo, double longitude, double latitude, String mapUrl, String locationDateTime) {
        this.isDeviceOnline = isDeviceOnline;
        this.vehicleRegNo = vehicleRegNo;
        this.longitude = longitude;
        this.latitude = latitude;
        this.mapUrl = mapUrl;
        this.locationDateTime = locationDateTime;
    }

    public boolean isDeviceOnline() {
        return isDeviceOnline;
    }

    public void setDeviceOnline(boolean deviceOnline) {
        isDeviceOnline = deviceOnline;
    }

    public String getVehicleRegNo() {
        return vehicleRegNo;
    }

    public void setVehicleRegNo(String vehicleRegNo) {
        this.vehicleRegNo = vehicleRegNo;
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

    public String getMapUrl() {
        return mapUrl;
    }

    public void setMapUrl(String mapUrl) {
        this.mapUrl = mapUrl;
    }

    public String getLocationDateTime() {
        return locationDateTime;
    }

    public void setLocationDateTime(String locationDateTime) {
        this.locationDateTime = locationDateTime;
    }
}

