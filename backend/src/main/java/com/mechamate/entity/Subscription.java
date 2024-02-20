package com.mechamate.entity;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;


@Document(collection = "Subscriptions")
public class Subscription {

    public enum SubscriptionType {
        Free,
        Premium
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private ObjectId _id;
    private SubscriptionType subscriptionType;
    private long validUntil;
    private String username;

    public Subscription(SubscriptionType subscriptionType, long validUntil, String username) {
        this.subscriptionType = subscriptionType;
        this.validUntil = validUntil;
        this.username = username;
    }

    public ObjectId get_id() {
        return _id;
    }

    public void set_id(ObjectId _id) {
        this._id = _id;
    }

    public SubscriptionType getSubscriptionType() {
        return subscriptionType;
    }

    public void setSubscriptionType(SubscriptionType subscriptionType) {
        this.subscriptionType = subscriptionType;
    }

    public long getValidUntil() {
        return validUntil;
    }

    public void setValidUntil(long validUntil) {
        this.validUntil = validUntil;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

}
