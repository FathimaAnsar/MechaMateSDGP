
package com.mechamate.entity;

import jakarta.persistence.Entity;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Entity
public class Response {

    public enum ResponseStatus {
        UnknownRequest,
        Success,
        InternalError,
        InvalidRequest,
        InvalidParameters,
        AlreadyLoggedIn,
        AuthenticationFailed,
        ActivationFailed,
        OperationFailed,
        NotFound,
        ServerBusy,
        Rejected,
        TimedOut,
        AlreadyExists
    }

    private ResponseStatus status;
    private String message;
    private String error;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public Response() {
        status = ResponseStatus.UnknownRequest;
    }

    public ResponseStatus getResponseStatus() {
        return status;
    }

    public void setResponseStatus(ResponseStatus status) {
        this.status = status;
    }
}
