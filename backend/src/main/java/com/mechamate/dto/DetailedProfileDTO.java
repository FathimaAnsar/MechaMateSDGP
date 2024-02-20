package com.mechamate.dto;


import com.mechamate.entity.Vehicle;

import java.util.List;

public class DetailedProfileDTO {
    private String userId;
    private String username;
    private String email;
    private String telephone;
    private String firstName;
    private String lastName;
    private String language;
    private List<VehicleDTO> vehicles;

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

    public List<VehicleDTO> getVehicles() {
        return vehicles;
    }

    public void setVehicles(List<VehicleDTO> vehicles) {
        this.vehicles = vehicles;
    }

}

