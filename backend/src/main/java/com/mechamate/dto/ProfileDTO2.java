package com.mechamate.dto;

public class ProfileDTO2 {
    // Password of the user
    private String password;

    // Telephone number of the user
    private String telephone;

    // First name of the user
    private String firstName;

    // Last name of the user
    private String lastName;

    // Language preference of the user
    private String language;
    private double longitude;
    private double latitude;
    private boolean isServiceAccount;
    private boolean isAvailable;

    // Constructor for ProfileDTO2 class
    public ProfileDTO2(String password, String telephone, String firstName, String lastName, String language,
                       double longitude, double latitude, boolean isServiceAccount, boolean isAvailable) {
        this.password = password;
        this.telephone = telephone;
        this.firstName = firstName;
        this.lastName = lastName;
        this.language = language;
        this.longitude = longitude;
        this.latitude = latitude;
        this.isServiceAccount = isServiceAccount;
        this.isAvailable = isAvailable;
    }

    // Getters and setters
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
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

    public boolean isServiceAccount() {
        return isServiceAccount;
    }

    public void setServiceAccount(boolean serviceAccount) {
        isServiceAccount = serviceAccount;
    }

    public boolean isAvailable() {
        return isAvailable;
    }

    public void setAvailable(boolean available) {
        isAvailable = available;
    }
}
