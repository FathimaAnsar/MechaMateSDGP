package com.mechamate.entity;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.apache.commons.codec.digest.DigestUtils;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.NotBlank;
import java.time.Instant;


@Data
@AllArgsConstructor
@Document(collection = "Session")
public class Session {

    public enum SessionStatus {
        SessionActive,
        SessionInactive,
        SessionExpired,
        SessionSuspended
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private ObjectId id;


    @NotBlank(message = "sessionKey is mandatory")
    private String sessionKey;

    @NotBlank(message = "sessionKey is mandatory")
    private SessionStatus status;

    @NotBlank(message = "expireDate is mandatory")
    private long expireDate;

    @DBRef
    private UserProfile userProfile;



    public Session() {
         sessionKey =  DigestUtils.sha256Hex(String.valueOf(Instant.now().getEpochSecond()));
   }


    public String getSessionKey() {
        return  sessionKey;
    }

    public ObjectId getId() {
        return id;
    }

    public boolean isExpired() {
        long curDate = System.currentTimeMillis();
        return (expireDate - curDate) > 0;
    }

    public long getExpireDate() {
        return expireDate;
    }

    public void setExpireDate() {
        this.expireDate = System.currentTimeMillis();
    }

    public UserProfile getUserProfile() {
        return userProfile;
    }

    public void setUserProfile(UserProfile userProfile) {
        this.userProfile = userProfile;
       sessionKey =  DigestUtils.sha256Hex(userProfile.getUsername() + userProfile.getEmail() + String.valueOf(Instant.now().getEpochSecond()));
    }
}
