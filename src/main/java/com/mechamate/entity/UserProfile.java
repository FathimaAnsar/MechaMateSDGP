package com.mechamate.entity;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.*;
import org.apache.commons.codec.digest.DigestUtils;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.mongodb.core.mapping.FieldType;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.time.Instant;
import java.util.ArrayList;


@NoArgsConstructor
@AllArgsConstructor
@Data
@Document(collection = "UserProfile")
public class UserProfile {

    public enum Status {
        StatusActive,
        StatusInactive,
        StatusBlocked,
        StatusPending,
        StatusDeleted
    }


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private ObjectId id;

    public ObjectId getId() {
        return id;
    }

    @NotBlank(message = "username is mandatory")
    private String username;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @NotBlank(message = "password is mandatory")
    @Size(min = 6, max = 50, message = "Min size = 6, Max size = 50")
    private String password;

    @NotBlank(message = "email is mandatory")
    @Email
    private String email;

    private Status status;
    @NotBlank(message = "firstName is mandatory")
    private String firstName;

    @NotBlank(message = "lastName is mandatory")
    private String lastName;

    private String activationKey;

    @DBRef
    private ArrayList<Vehicle> vehicles;

    @DBRef
    private Session currentSession;

    @DBRef
    private ArrayList<Session> otherSessions;

    public void addVehicle(Vehicle vehicle) {
        this.vehicles.add(vehicle);
    }

    public void removeVehicle(int index) {
        this.vehicles.remove(index);
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
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

    public Session getCurrentSession() {
        return currentSession;
    }

    public void setCurrentSession(Session currentSession) {
        this.currentSession = currentSession;
    }

    public void setActivationKey() {
        activationKey =  DigestUtils.sha256Hex("ACTIV#>>" + this.getUsername() + this.getEmail() + String.valueOf(Instant.now().getEpochSecond()) + "<<#");
        status = Status.StatusPending;
    }

    public void clearActivationKey(Status status) {
        activationKey =  "";
        this.status = status;
    }

    public String getActivationKey() {
        return activationKey;
    }
}
