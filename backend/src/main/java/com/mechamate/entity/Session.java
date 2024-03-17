package com.mechamate.entity;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

// Annotation to specify this class as a document in MongoDB
@Document(collection = "Sessions")
public class Session {

    // MongoDB ID field
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private ObjectId _id;

    // Timestamp indicating until when the session is valid
    private long validUntil;

    // Key associated with the session
    private String sessionKey;

    // Reference to the user profile associated with the session
    @DBRef
    private UserProfile userProfile;

    // Constructor
    public Session(long validUntil, String sessionKey, UserProfile userProfile) {
        this.validUntil = validUntil;
        this.sessionKey = sessionKey;
        this.userProfile = userProfile;
    }

    // Getters and setters
    public ObjectId get_id() {
        return _id;
    }

    public void set_id(ObjectId _id) {
        this._id = _id;
    }

    public long getValidUntil() {
        return validUntil;
    }

    public void setValidUntil(long validUntil) {
        this.validUntil = validUntil;
    }

    public String getSessionKey() {
        return sessionKey;
    }

    public void setSessionKey(String sessionKey) {
        this.sessionKey = sessionKey;
    }

    public UserProfile getUserProfile() {
        return userProfile;
    }

    public void setUserProfile(UserProfile userProfile) {
        this.userProfile = userProfile;
    }

    // Method to check if the session has expired
    public boolean hasExpired() {
        return ((System.currentTimeMillis() - validUntil) >= 0);
    }
}
