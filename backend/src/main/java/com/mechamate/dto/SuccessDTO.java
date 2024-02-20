package com.mechamate.dto;

public class SuccessDTO {

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

    private SuccessStatus status;
    private String message;
    private String info;

    public SuccessDTO(SuccessStatus status, String message, String info) {
        this.status = status;
        this.message = message;
        this.info = info;
    }

    public SuccessStatus getStatus() {
        return status;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public void setStatus(SuccessStatus status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

}
