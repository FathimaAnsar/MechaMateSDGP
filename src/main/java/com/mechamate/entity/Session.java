package com.mechamate.entity;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

import java.text.SimpleDateFormat;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "Session")
public class Session {
    public enum SessionStatus {
        ACTIVE,
        INACTIVE,
        EXPIRED,
        LOGGED_OUT,
        LOCKED_OUT,
        SUSPENDED,
        TERMINATED
    }

    @Id
    private long sessionId;

    private UserProfile userProfile;

    private Device device;

    @GeneratedValue(strategy = GenerationType.AUTO)
    private String sessionKey;

    private SessionStatus sessionStatus;

    private SimpleDateFormat createdOn;

    private SimpleDateFormat expireOn;

    private boolean isValid;

    private Language language;

    private Theme theme;

    private int loginAttempts;

    public Session(UserProfile userProfile) {
        this.sessionId = sessionId;
        this.userProfile = userProfile;
        this.device = device;
        this.sessionKey = sessionKey;
        this.sessionStatus = sessionStatus;
        this.createdOn = createdOn;
        this.expireOn = expireOn;
        this.isValid = isValid;
        this.language = userProfile.getLanguage();
        this.theme = userProfile.getTheme();
        this.loginAttempts = loginAttempts;
    }
}
