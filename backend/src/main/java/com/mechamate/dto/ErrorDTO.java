package com.mechamate.dto;


public class ErrorDTO {

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

    private ErrorStatus error;
    private String message;
    private String help;

    public ErrorDTO(ErrorStatus error, String message, String help) {
        this.error = error;
        this.message = message;
        this.help = help;
    }

    public ErrorStatus getError() {
        return error;
    }

    public void setError(ErrorStatus error) {
        this.error = error;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getHelp() {
        return help;
    }

    public void setHelp(String help) {
        this.help = help;
    }


}
