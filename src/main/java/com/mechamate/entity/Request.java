

package com.mechamate.entity;

import com.mechamate.entity.Response;
import jakarta.persistence.Entity;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Entity
public class Request {

    public enum RequestType {
        RequestDefault,
        RequestSignup,
        RequestActivateAccount,
        RequestLogin,
        RequestLogout,
        RequestShowProfile,
        RequestUpdateProfile,
        RequestDeleteProfile
    }

    private RequestType type;
    private final Map<String, Object> parameters;

    public Request() {
        type = RequestType.RequestDefault;
        parameters = new HashMap<>();
    }

    public RequestType getRequestType() {
        return type;
    }

    public void setRequestType(RequestType type) {
        this.type = type;
    }

    public Object getParameter(String key) {
        return parameters.getOrDefault(key, "");
    }
    public void setParameter(String key, Object value) {
        parameters.put(key, value);
    }
    public void removeParameter(String key) {
        parameters.remove(key);
    }
    public void clearParameters() {
        parameters.clear();
    }



}
