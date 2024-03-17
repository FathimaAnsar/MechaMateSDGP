package com.mechamate.dto;

public class SuccessDTO {

    // Enumeration defining various success statuses
    public enum SuccessStatus {
        SignupSucceeded,
        ProfileActivationSucceeded,
        AlreadySignedIn,
        SigninSucceeded,
        SignoutSucceeded,
        RecoveryEmailSent,
        ResetPasswordSucceeded,
        OperationSucceeded
    }

    // Status indicating the type of success
    private SuccessStatus status;

    // Message providing details about the success
    private String message;

    // Additional information related to the success
    private String info;

    // Constructor for SuccessDTO class
    public SuccessDTO(SuccessStatus status, String message, String info) {
        this.status = status;
        this.message = message;
        this.info = info;
    }

    // Getter method for status
    public SuccessStatus getStatus() {
        return status;
    }

    // Setter method for status
    public void setStatus(SuccessStatus status) {
        this.status = status;
    }

    // Getter method for info
    public String getInfo() {
        return info;
    }

    // Setter method for info
    public void setInfo(String info) {
        this.info = info;
    }

    // Getter method for message
    public String getMessage() {
        return message;
    }

    // Setter method for message
    public void setMessage(String message) {
        this.message = message;
    }

}
