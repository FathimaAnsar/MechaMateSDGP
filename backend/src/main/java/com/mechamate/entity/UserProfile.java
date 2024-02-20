package com.mechamate.entity;

import com.mechamate.common.Common;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Document(collection = "UserProfiles")
public class UserProfile {

    public enum Status {
        StatusActive,
        StatusInactive,
        StatusBlocked,
        StatusPendingActivation,
        StatusDeleted
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private ObjectId _id;
    private Status status;
    private String username;
    private String password;
    private String email;
    private String telephone;
    private String firstname;
    private String lastname;
    private String language;
    private String activationKey;
    private int unsuccessfulLoginAttempts;
    private long lastLoginAttemptTime;
    private String recoveryKey;
    private boolean superUser;

    @DBRef
    private Subscription subscription;

    public UserProfile(Status status, String username, String password, String email, String telephone, String firstname, String lastname, String language, Subscription subscription) {
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
    }

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

    public void createActivationKey() {
        this.activationKey = Common.getSha256("ACTV#>>(" + username + email + password + System.currentTimeMillis() + ")<<#");
        this.status = Status.StatusPendingActivation;
    }

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

    public void createRecoveryKey() {
        this.recoveryKey = Common.getSha256("RECV#>>(" + username + System.currentTimeMillis() + email + password + ")<<#");
        this.status = Status.StatusPendingActivation;
    }

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
}







