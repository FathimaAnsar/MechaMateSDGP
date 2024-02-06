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
@Data
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

    public UserProfile(String userName) {
        /**
            Test constructor for userprofile class
        */
        this.username = userName;
        this.password = "password";
        this.status = Status.ACTIVE;
        this.firstName = "firstName";
        this.lastName = "lastName";
        this.vehicles = new ArrayList<>();
        this.currentSession = new Session();
        this.otherSessions = new ArrayList<>();
    }

    public void addVehicle(Vehicle vehicle){
        this.vehicles.add(vehicle);
    }

    public void removeVehicle(int index){
        this.vehicles.remove(index);
    }

}
