package com.mechamate.dto;

public class SuccessDTO {

    // Enum for defining various success statuses
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

    // Getters and setters
    public SuccessStatus getStatus() {
        return status;
    }

    public void setStatus(SuccessStatus status) {
        this.status = status;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

}
