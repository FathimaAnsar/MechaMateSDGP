package com.mechamate.dto;

public class ProfileDTO {
    // Unique identifier for the user
    private String userId;

    // Username of the user
    private String username;

    // Email address of the user
    private String email;

    // Telephone number of the user
    private String telephone;

    // First name of the user
    private String firstName;

    // Last name of the user
    private String lastName;

    // Language preference of the user
    private String language;

    // Number of vehicles associated with the user
    private long vehicleCount;
    private boolean isServiceAccount;

    // Constructor for ProfileDTO class
    public ProfileDTO(String userId, String username, String email, String telephone, String firstName, String lastName, String language, long vehicleCount, boolean isServiceAccount) {
        this.userId = userId;
        this.username = username;
        this.email = email;
        this.telephone = telephone;
        this.firstName = firstName;
        this.lastName = lastName;
        this.language = language;
        this.vehicleCount = vehicleCount;
        this.isServiceAccount = isServiceAccount;
    }

    // Getters and setters
    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
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

    public long getVehicleCount() {
        return vehicleCount;
    }

    public void setVehicleCount(long vehicleCount) {
        this.vehicleCount = vehicleCount;
    }

    public boolean isServiceAccount() {
        return isServiceAccount;
    }

    public void setServiceAccount(boolean serviceAccount) {
        isServiceAccount = serviceAccount;
    }
}
