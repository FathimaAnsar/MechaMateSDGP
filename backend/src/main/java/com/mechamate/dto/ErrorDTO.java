package com.mechamate.dto;

public class ErrorDTO {

    // Enumeration defining various error statuses
    public enum ErrorStatus {
        InternalError,
        ErrorInvalidRequest,
        ErrorParameterNotFound,
        ErrorUserExists,
        ErrorEmailExists,
        ErrorEmailDoesntExist,
        ErrorUserDoesntExist,
        ErrorActivationFailed,
        ErrorUsernameNotAllowed,
        ErrorLoginFailed,
        ErrorMaxAttemptCountExceeded,
        ErrorUnauthorized,
        ErrorVehicleExists,
        ErrorOperationFailed,
        ErrorPendingActivation,
        ErrorNotSignedIn
    }

    // Error status indicating the type of error
    private ErrorStatus error;

    // Error message providing details about the error
    private String message;

    // Help information to assist in resolving the error
    private String help;

    // Constructor for ErrorDTO class
    public ErrorDTO(ErrorStatus error, String message, String help) {
        this.error = error;
        this.message = message;
        this.help = help;
    }

    // Getter method for error status
    public ErrorStatus getError() {
        return error;
    }

    // Setter method for error status
    public void setError(ErrorStatus error) {
        this.error = error;
    }

    // Getter method for error message
    public String getMessage() {
        return message;
    }

    // Setter method for error message
    public void setMessage(String message) {
        this.message = message;
    }

    // Getter method for help information
    public String getHelp() {
        return help;
    }

    // Setter method for help information
    public void setHelp(String help) {
        this.help = help;
    }

}

