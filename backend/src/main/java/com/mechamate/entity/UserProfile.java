package com.mechamate.entity;

import com.mechamate.common.Common;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;
import java.util.Random;

// Annotation to specify this class as a document in MongoDB
@Document(collection = "UserProfiles")
public class UserProfile {

    public UserProfile() {

    }


    // Enum to represent different user status
    public enum Status {
        StatusActive,
        StatusInactive,
        StatusBlocked,
        StatusPendingActivation,
        StatusDeleted
    }

    // MongoDB ID field
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private ObjectId _id;

    // Status of the user profile
    private Status status;

    // Username of the user
    private String username;

    // Password of the user
    private String password;

    // Email of the user
    private String email;

    // Telephone number of the user
    private String telephone;

    // First name of the user
    private String firstname;

    // Last name of the user
    private String lastname;

    // Language preference of the user
    private String language;

    // Activation key for the user's account
    private String activationKey;

    // Number of unsuccessful login attempts
    private int unsuccessfulLoginAttempts;

    // Timestamp of the last login attempt
    private long lastLoginAttemptTime;

    // Recovery key for the user's account
    private String recoveryKey;

    // Flag indicating if the user is a super user
    private boolean superUser;

    // Reference to the subscription associated with the user
    @DBRef
    private Subscription subscription;
    private double longitude;
    private double latitude;
    private boolean isServiceAccount;


    // Constructor
    public UserProfile(Status status, String username, String password, String email, String telephone, String firstname, String lastname, String language, Subscription subscription,
                       double longitude, double latitude, boolean isServiceAccount) {
        this.status = status;
        this.username = username;
        this.password = password;
        this.email = email;
        this.telephone = telephone;
        this.firstname = firstname;
        this.lastname = lastname;
        this.language = language;
        this.activationKey = "";
        this.unsuccessfulLoginAttempts = 0;
        this.lastLoginAttemptTime = 0;
        this.recoveryKey = "";
        this.superUser = false;
        this.subscription = subscription;
        this.longitude = longitude;
        this.latitude = latitude;
        this.isServiceAccount = isServiceAccount;
    }

    // Getters and setters
    public ObjectId get_id() {
        return _id;
    }

    public void set_id(ObjectId _id) {
        this._id = _id;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
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

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getActivationKey() {
        return activationKey;
    }

    public void setActivationKey(String activationKey) {
        this.activationKey = activationKey;
    }

    // Method to create an activation key for the user's account
    public void createActivationKey() {
        Random random = new Random();
        Integer code = random.nextInt(900000) + 100000;
        this.activationKey = code.toString();
        this.status = Status.StatusPendingActivation;
    }

    // Method to clear the activation key and set the user status
    public void clearActivationKey(UserProfile.Status status) {
        this.activationKey = "";
        this.status = status;
    }

    public String getRecoveryKey() {
        return recoveryKey;
    }

    public void setRecoveryKey(String recoveryKey) {
        this.recoveryKey = recoveryKey;
    }

    // Method to create a recovery key for the user's account
    public void createRecoveryKey() {
        Random random = new Random();
        Integer code = random.nextInt(900000) + 100000;
        this.recoveryKey = code.toString();
    }

    // Method to clear the recovery key
    public void clearRecoveryKey() {
        this.recoveryKey = "";
    }

    public int getUnsuccessfulLoginAttempts() {
        return unsuccessfulLoginAttempts;
    }

    public void setUnsuccessfulLoginAttempts(int unsuccessfulLoginAttempts) {
        this.unsuccessfulLoginAttempts = unsuccessfulLoginAttempts;
    }

    public long getLastLoginAttemptTime() {
        return lastLoginAttemptTime;
    }

    public void setLastLoginAttemptTime(long lastLoginAttemptTime) {
        this.lastLoginAttemptTime = lastLoginAttemptTime;
    }

    public boolean isSuperUser() {
        return superUser;
    }

    public void setSuperUser(boolean superUser) {
        this.superUser = superUser;
    }

    public Subscription getSubscription() {
        return subscription;
    }

    public void setSubscription(Subscription subscription) {
        this.subscription = subscription;
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

}







