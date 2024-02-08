package com.mechamate.service;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class ProfileManager {

    @Autowired
    public DatabaseHandler databaseHandler;

    @Autowired
    public NotificationManager notificationManager;

  //  private Log log;

   // public ProfileManager() {

  //  }

  //  public ProfileManager(Log log) {
  //      this.log = log;
  //  }


    public Response signup(Request request, HttpServletResponse resp) {

        Response response = new Response();

        if(request == null) {
            response.setResponseStatus(Response.ResponseStatus.InternalError);
            response.setError("request object is null");
            return response;
        }

        String username, password, email, fname, lname;
        try {
            username = (String) request.getParameter("username");
            password = (String) request.getParameter("password");
            email = (String) request.getParameter("email");
            fname = (String) request.getParameter("fname");
            lname = (String) request.getParameter("lname");
        } catch (Exception e) {
            response.setResponseStatus(Response.ResponseStatus.InternalError);
            response.setError("exception thrown (" + e.getMessage() + ")");
            return response;
        }

        if(username.isEmpty() || password.isEmpty() || email.isEmpty() || fname.isEmpty() || lname.isEmpty()) {
            response.setResponseStatus(Response.ResponseStatus.InternalError);
            response.setError("Invalid parameters");
            return response;
        }

        DatabaseHandler databaseHandler = this.databaseHandler;
        if(databaseHandler.isUserProfileExist(username)) {
            response.setResponseStatus(Response.ResponseStatus.InternalError);
            response.setError("Invalid parameters");
            return response;

        }
        UserProfile userProfile = new UserProfile();
        userProfile.setUsername(username);
        userProfile.setPassword(DigestUtils.sha256Hex("PSW#>>" + password + "<<#"));
        userProfile.setEmail(email);
        userProfile.setFirstName(fname);
        userProfile.setLastName(lname);
        userProfile.setStatus(UserProfile.Status.StatusPending);
        userProfile.setActivationKey();
        Session session = new Session();
        session.setUserProfile(userProfile);
        databaseHandler.addUserProfile(userProfile);
        databaseHandler.addSession(session);

        if(notificationManager.sendActivationEmail(userProfile)) {
            response.setResponseStatus(Response.ResponseStatus.Success);
//            response.setParameter("username", username);
//            response.setParameter("status", userProfile.getStatus());
//            response.setParameter("email", email);
//            response.setParameter("fname", fname);
//            response.setParameter("lname", lname);
            Cookie cookie = new Cookie("session", session.getSessionKey());
            cookie.setSecure(false);
            cookie.setHttpOnly(true);
            cookie.setMaxAge(7 * 24 * 60 * 60);
            resp.addCookie(cookie);
            response.setMessage("Please check your email inbox in order to activate your account");
            return response;
        } else {
            response.setResponseStatus(Response.ResponseStatus.InternalError);
            response.setError("Failed to send activation link");
            return response;
        }
    }


    public Response activate(Request request, UserProfile userProfile) {

        Response response = new Response();

        if(request == null) {
            response.setResponseStatus(Response.ResponseStatus.InternalError);
            response.setError("request object is null");
            return response;
        }

        String activationKey;
        try {
            activationKey = (String) request.getParameter("key");
        } catch (Exception e) {
            response.setResponseStatus(Response.ResponseStatus.InternalError);
            response.setError("exception thrown (" + e.getMessage() + ")");
            return response;
        }

        if(activationKey.length() != 64) {
            response.setResponseStatus(Response.ResponseStatus.InternalError);
            response.setError("Invalid parameters");
            return response;
        }

        if(userProfile.getStatus() != UserProfile.Status.StatusPending) {
            response.setResponseStatus(Response.ResponseStatus.InvalidRequest);
            response.setError("Your account is not in a pending status for activation");
            return response;
        }

        if(!userProfile.getActivationKey().equals(activationKey)) {
            response.setResponseStatus(Response.ResponseStatus.ActivationFailed);
            response.setError("Failed to activate the account! Invalid activation key");
            return response;

        }

        DatabaseHandler databaseHandler = this.databaseHandler;
        userProfile.clearActivationKey(UserProfile.Status.StatusActive);
        databaseHandler.updateUserProfile(userProfile);

        if(notificationManager.sendActivationEmail(userProfile)) {
            //
        } else {
            //
        }
        response.setResponseStatus(Response.ResponseStatus.Success);
        response.setMessage("Welcome to MechaMate - Your account is activated successfully!");
        return response;
    }




    public Response signin(Request request, HttpServletResponse resp) {

        Response response = new Response();

        if(request == null) {
            response.setResponseStatus(Response.ResponseStatus.InternalError);
            response.setError("request object is null");
            return response;
        }

        String username, password;
        try {
            username = (String) request.getParameter("username");
            password = (String) request.getParameter("password");
        } catch (Exception e) {
            response.setResponseStatus(Response.ResponseStatus.InternalError);
            response.setError("exception thrown (" + e.getMessage() + ")");
            return response;
        }

        if(username.length() < 5 || password.length() < 6) {
            response.setResponseStatus(Response.ResponseStatus.InternalError);
            response.setError("Invalid parameters");
            return response;
        }

        DatabaseHandler databaseHandler = this.databaseHandler;
        if(!databaseHandler.isUserProfileExist(username)) {
            response.setResponseStatus(Response.ResponseStatus.NotFound);
            response.setError("This user doesn't exist");
            return response;

        }

        UserProfile userProfile = databaseHandler.getUserProfile(username);
        if(userProfile == null) {
            response.setResponseStatus(Response.ResponseStatus.InternalError);
            response.setError("Failed to fetch the user profile");
            return response;
        }

        if(!userProfile.getPassword().equals(DigestUtils.sha256Hex("PSW#>>" + password + "<<#"))) {
            response.setResponseStatus(Response.ResponseStatus.AuthenticationFailed);
            response.setError("Login failed! password is invalid");
            return response;
        }

        Session session = new Session();
        session.setUserProfile(userProfile);
        databaseHandler.addUserProfile(userProfile);
        databaseHandler.addSession(session);

        Cookie cookie = new Cookie("session", session.getSessionKey());
        cookie.setSecure(false);
        cookie.setHttpOnly(true);
        cookie.setMaxAge(7 * 24 * 60 * 60);
        resp.addCookie(cookie);

        response.setResponseStatus(Response.ResponseStatus.Success);
        if(userProfile.getStatus() != UserProfile.Status.StatusPending) {
            response.setError("Login succeeded! (Your account is in a pending status for activation)");
        } else {
            response.setMessage("Login succeeded!");
        }

//        if(notificationManager.sendActivationEmail(userProfile)) {
//            //
//        } else {
//            //
//        }
        return response;
    }



    public Response signout(Request request, String sessionKey, HttpServletResponse resp) {

        Response response = new Response();

        if(request == null) {
            response.setResponseStatus(Response.ResponseStatus.InternalError);
            response.setError("request object is null");
            return response;
        }

        DatabaseHandler databaseHandler = this.databaseHandler;
        databaseHandler.removeSession(sessionKey);

        Cookie cookie = new Cookie("session", null);
        cookie.setSecure(false);
        cookie.setHttpOnly(true);
        cookie.setMaxAge(0);
        resp.addCookie(cookie);

        response.setResponseStatus(Response.ResponseStatus.Success);
        response.setMessage("Signed out successfully!");
        return response;
    }


}













