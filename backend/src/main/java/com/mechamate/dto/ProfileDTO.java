package com.mechamate.dto;

import org.bson.types.ObjectId;

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

    // Constructor for ProfileDTO class
    public ProfileDTO(String userId, String username, String email, String telephone, String firstName, String lastName, String language, long vehicleCount) {
        this.userId = userId;
        this.username = username;
        this.email = email;
        this.telephone = telephone;
        this.firstName = firstName;
        this.lastName = lastName;
        this.language = language;
        this.vehicleCount = vehicleCount;
    }

    // Getter method for userId
    public String getUserId() {
        return userId;
    }

    // Setter method for userId
    public void setUserId(String userId) {
        this.userId = userId;
    }

    // Getter method for username
    public String getUsername() {
        return username;
    }

    // Setter method for username
    public void setUsername(String username) {
        this.username = username;
    }

    // Getter method for email
    public String getEmail() {
        return email;
    }

    // Setter method for email
    public void setEmail(String email) {
        this.email = email;
    }

    // Getter method for telephone
    public String getTelephone() {
        return telephone;
    }

    // Setter method for telephone
    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    // Getter method for firstName
    public String getFirstName() {
        return firstName;
    }

    // Setter method for firstName
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    // Getter method for lastName
    public String getLastName() {
        return lastName;
    }

    // Setter method for lastName
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    // Getter method for language
    public String getLanguage() {
        return language;
    }

    // Setter method for language
    public void setLanguage(String language) {
        this.language = language;
    }

    // Getter method for vehicleCount
    public long getVehicleCount() {
        return vehicleCount;
    }

    // Setter method for vehicleCount
    public void setVehicleCount(long vehicleCount) {
        this.vehicleCount = vehicleCount;
    }
}
