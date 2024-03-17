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

    // Constructor for ProfileDTO2 class
    public ProfileDTO2(String password, String telephone, String firstName, String lastName, String language) {
        this.password = password;
        this.telephone = telephone;
        this.firstName = firstName;
        this.lastName = lastName;
        this.language = language;
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

}
