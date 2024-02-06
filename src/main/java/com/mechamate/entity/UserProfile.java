package com.mechamate.entity;

import com.mechamate.service.Session;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

import jdk.jshell.JShell;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;

/**
 * Entity class representing user profiles stored in MongoDB.
 */
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "UserProfile")
public class UserProfile {

    /**
     * Enum representing different status levels for user profiles.
     */
    public enum Status {
        ACTIVE,
        INACTIVE,
        PENDING
    }

    public enum SubscriptionType {
        BASIC,
        STANDARD,
        PREMIUM,
        ENTERPRISE,
        CUSTOM
    }


    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private String userProfileId;

    @NotBlank(message = "Username is mandatory")
    private String username;

    @NotBlank(message = "Password is mandatory")
    @Size(min = 1, max = 8, message = "Password length must be between 1 - 8")
    private String password;

    private Status status; // Use the enum type for status

    @NotBlank(message = "First name cannot be empty")
    private String firstName;

    @NotBlank(message = "Last name cannot be empty")
    private String lastName;

    @DBRef
    private List<Vehicle> vehicles;

    @DBRef
    @NotBlank(message = "Current session cannot be empty")
    private Session currentSession;

    @DBRef
    private ArrayList<Session> otherSessions;

    @DBRef
    private Theme theme;

    @DBRef
    private SubscriptionType subscriptionType;

    @DBRef
    private Language language;

    public UserProfile(String userProfileId) {
        /**
            Test constructor for userprofile class
        */
        this.userProfileId = userProfileId;
        this.username = "username";
        this.password = "password";
        this.status = Status.ACTIVE;
        this.firstName = "firstName";
        this.lastName = "lastName";
        this.vehicles = new ArrayList<>();
        this.currentSession = new Session();
        this.otherSessions = new ArrayList<>();
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
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

    public List<Vehicle> getVehicles() {
        return vehicles;
    }

    public void setVehicles(List<Vehicle> vehicles) {
        this.vehicles = vehicles;
    }

    public Session getCurrentSession() {
        return currentSession;
    }

    public void setCurrentSession(Session currentSession) {
        this.currentSession = currentSession;
    }

    public ArrayList<Session> getOtherSessions() {
        return otherSessions;
    }

    public void setOtherSessions(ArrayList<Session> otherSessions) {
        this.otherSessions = otherSessions;
    }

    public void linkVehicleToUserProfile(Vehicle vehicle){
        vehicles.add(vehicle);
    }

    public Theme getTheme() {
        return theme;
    }

    public void setTheme(Theme theme) {
        this.theme = theme;
    }

    public SubscriptionType getSubscriptionType() {
        return subscriptionType;
    }

    public void setSubscriptionType(SubscriptionType subscriptionType) {
        this.subscriptionType = subscriptionType;
    }

    public Language getLanguage() {
        return language;
    }

    public void setLanguage(Language language) {
        this.language = language;
    }
}
