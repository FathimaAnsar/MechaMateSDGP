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

    // Getter method for password
    public String getPassword() {
        return password;
    }

    // Setter method for password
    public void setPassword(String password) {
        this.password = password;
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

}
