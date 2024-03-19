package com.mechamate.dto;

import java.util.List;

public class DetailedProfileDTO {
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

    // List of vehicles associated with the user
    private List<VehicleDTO> vehicles;

    // Constructor for DetailedProfileDTO class
    public DetailedProfileDTO(String userId, String username, String email, String telephone, String firstName, String lastName, String language, List<VehicleDTO> vehicles) {
        this.userId = userId;
        this.username = username;
        this.email = email;
        this.telephone = telephone;
        this.firstName = firstName;
        this.lastName = lastName;
        this.language = language;
        this.vehicles = vehicles;
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

    // Getter method for vehicles
    public List<VehicleDTO> getVehicles() {
        return vehicles;
    }

    // Setter method for vehicles
    public void setVehicles(List<VehicleDTO> vehicles) {
        this.vehicles = vehicles;
    }

}
