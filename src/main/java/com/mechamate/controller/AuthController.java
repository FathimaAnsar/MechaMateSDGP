package com.mechamate.controller;

import com.mechamate.entity.Request;
import com.mechamate.entity.Response;
import com.mechamate.entity.Session;
import com.mechamate.service.DatabaseHandler;
import com.mechamate.service.ProfileManager;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.regex.Pattern;

@RestController
@RequestMapping(value = "api/v1/auth")
public class AuthController {

    @Autowired
    public DatabaseHandler databaseHandler;

    @Autowired
    public ProfileManager profileManager;


    @GetMapping("/test")
    public String test() {
        return """
                <!DOCTYPE html>
                <html lang="en">
                  <head>
                    <meta charset="UTF-8" />
                  </head>

                  <body>
                    <h2></h2>

                    <form id="form">
                     

                      <input type="text" id="username" name="username"  autocomplete="on"/><br>

                      <input type="text" id="password" name="password" autocomplete="on"/><br>
                      <input type="text" id="email" name="email" autocomplete="on"/><br>
                      <input type="text" id="fname" name="fname" autocomplete="on"/><br>
                      <input type="text" id="lname" name="lname" autocomplete="on"/><br>

                       
                    

                      <button type="submit" id="submit-btn">Log in</button>
                    </form>

                    <script >
                const form = document.getElementById('form');

                form.addEventListener('submit', async event => {
                  event.preventDefault();

                  const data = new FormData(form);

                  console.log(Array.from(data));

                  try {
                    const res = await fetch(
                      'http://mechamate.com:8080/api/v1/auth/signup',
                      {
                        method: 'POST',
                        body: data,
                      },
                    );

                    const resData = await res.json();

                    console.log(resData);
                  } catch (err) {
                    console.log(err.message);
                  }
                });

                    </script>
                 </body>
                </html>
                """;
    }


    @PostMapping("/signup")
    public Response signup(String username, String password, String email, String fname, String lname,
                            @CookieValue(name = "session", required = false) String sessionKey,
                           HttpServletResponse resp
                            ) {

        Response response = new Response();
        Session session = null;

        if(sessionKey != null && !sessionKey.isEmpty()) {
            session = getUserSession(sessionKey);
            if(session != null) {
                if(session.getUserProfile() != null) {
                    response.setResponseStatus(Response.ResponseStatus.InvalidRequest);
                    response.setMessage("This request is not valid! You are already logged in!");
                    return response;
                }
            }
        }

        if(username.isEmpty()) {
            response.setResponseStatus(Response.ResponseStatus.InvalidParameters);
            response.setMessage("username is missing!");
            return response;
        }
        if(password.isEmpty()) {
            response.setResponseStatus(Response.ResponseStatus.InvalidParameters);
            response.setMessage("password is missing!");
            return response;
        }
        if(email.isEmpty()) {
            response.setResponseStatus(Response.ResponseStatus.InvalidParameters);
            response.setMessage("email is missing!");
            return response;
        }
        if(fname.isEmpty()) {
            response.setResponseStatus(Response.ResponseStatus.InvalidParameters);
            response.setMessage("First name is missing!");
            return response;
        }
        if(lname.isEmpty()) {
            response.setResponseStatus(Response.ResponseStatus.InvalidParameters);
            response.setMessage("Last name is missing!");
            return response;
        }

        if(!Pattern.compile("^(?=.{5,20}$)(?![_.])(?!.*[_.]{2})[a-zA-Z0-9._]+(?<![_.])$")
                .matcher(username).matches()) {
            response.setResponseStatus(Response.ResponseStatus.InvalidParameters);
            response.setMessage("username is invalid! username should contain only alphanumeric characters" +
                    ", '_' and '.'. and it should be in range of 5 and 20 characters");
            return response;
        }
        if(!Pattern.compile("^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#!)(|}{?><,/.:;\"'$%^&+=])(?=\\S+$).{6,}$")
                .matcher(password).matches()) {
            response.setResponseStatus(Response.ResponseStatus.InvalidParameters);
            response.setMessage("password is invalid! password should contain minimum of 6 characters" +
                    " including at least 1 uppercase letter, 1 lowercase letter, 1 number and 1 special character");
            return response;
        }
        if(!Pattern.compile("^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}(?:\\.[a-zA-Z]{2,})?$")
                .matcher(email).matches()) {
            response.setResponseStatus(Response.ResponseStatus.InvalidParameters);
            response.setMessage("email address is invalid!");
            return response;
        }
        if(!Pattern.compile("^[a-zA-Z]*$")
                .matcher(fname).matches()) {
            response.setResponseStatus(Response.ResponseStatus.InvalidParameters);
            response.setMessage("First name is invalid!");
            return response;
        }
        if(!Pattern.compile("^[a-zA-Z]*$")
                .matcher(lname).matches()) {
            response.setResponseStatus(Response.ResponseStatus.InvalidParameters);
            response.setMessage("Last name is invalid!");
            return response;
        }

        Request request = new Request();
        request.setParameter("username", username);
        request.setParameter("password", password);
        request.setParameter("email", email);
        request.setParameter("fname", fname);
        request.setParameter("lname", lname);
        request.setRequestType(Request.RequestType.RequestSignup);
        return profileManager.signup(request, resp);

    }


    @GetMapping("/activate")
    public Response activate(String key, @CookieValue(name = "session", required = false) String sessionKey) {

        Response response = new Response();
        Session session = null;

        if(sessionKey != null && !sessionKey.isEmpty()) {
            session = getUserSession(sessionKey);
            if(session == null) {
                response.setResponseStatus(Response.ResponseStatus.InvalidRequest);
                response.setMessage("Please login in order to activate your account!");
                return response;
            } else {
                if(session.getUserProfile() == null) {
                    response.setResponseStatus(Response.ResponseStatus.InvalidRequest);
                    response.setMessage("Please login in order to activate your account!");
                    return response;
                }
            }
        } else {
            response.setResponseStatus(Response.ResponseStatus.InvalidRequest);
            response.setMessage("Please login in order to activate your account!");
            return response;
        }

        if(key.isEmpty()) {
            response.setResponseStatus(Response.ResponseStatus.InvalidParameters);
            response.setMessage("activation key is missing!");
            return response;
        }

        if(key.length() != 64) {
            response.setResponseStatus(Response.ResponseStatus.InvalidParameters);
            response.setMessage("key is invalid!");
            return response;
        }

        Request request = new Request();
        request.setRequestType(Request.RequestType.RequestActivateAccount);
        request.setParameter("key", key);
        return profileManager.activate(request, session.getUserProfile());
    }



    @PostMapping("/signin")
    public Response signin(String username, String password,
                           @CookieValue(name = "session", required = false) String sessionKey,
                           HttpServletResponse resp) {

        Response response = new Response();
        Session session = null;

        if(sessionKey != null && !sessionKey.isEmpty()) {
            session = getUserSession(sessionKey);
            if(session != null) {
                if(session.getUserProfile() != null) {
                    response.setResponseStatus(Response.ResponseStatus.AlreadyLoggedIn);
                    response.setMessage("Already signed in!");
                    return response;
                }
            }
        }

        if(username.isEmpty()) {
            response.setResponseStatus(Response.ResponseStatus.InvalidParameters);
            response.setMessage("username is missing!");
            return response;
        }
        if(password.isEmpty()) {
            response.setResponseStatus(Response.ResponseStatus.InvalidParameters);
            response.setMessage("password is missing!");
            return response;
        }

        if(!Pattern.compile("^(?=.{5,20}$)(?![_.])(?!.*[_.]{2})[a-zA-Z0-9._]+(?<![_.])$")
                .matcher(username).matches()) {
            response.setResponseStatus(Response.ResponseStatus.InvalidParameters);
            response.setMessage("username is invalid! username should contain only alphanumeric characters" +
                    ", '_' and '.'. and it should be in range of 5 and 20 characters");
            return response;
        }
        if(!Pattern.compile("^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#!)(|}{?><,/.:;\"'$%^&+=])(?=\\S+$).{6,}$")
                .matcher(password).matches()) {
            response.setResponseStatus(Response.ResponseStatus.InvalidParameters);
            response.setMessage("password is invalid! password should contain minimum of 6 characters" +
                    " including at least 1 uppercase letter, 1 lowercase letter, 1 number and 1 special character");
            return response;
        }

        Request request = new Request();
        request.setParameter("username", username);
        request.setParameter("password", password);
        request.setRequestType(Request.RequestType.RequestLogin);
        return profileManager.signin(request, resp);

    }


    @PostMapping("/signout")
    public Response signout(@CookieValue(name = "session", required = false) String sessionKey,
                           HttpServletResponse resp) {

        Response response = new Response();
        Session session = null;

        if(sessionKey != null && !sessionKey.isEmpty()) {
            session = getUserSession(sessionKey);
            if(session != null) {
                if(session.getUserProfile() != null) {
                    Request request = new Request();
                    request.setRequestType(Request.RequestType.RequestLogout);
                    return profileManager.signout(request, sessionKey, resp);
                }
            }
        }
        response.setResponseStatus(Response.ResponseStatus.InvalidRequest);
        response.setMessage("Session is not found to signout!");
        return response;
    }















    public Session getUserSession(String sessionKey) {
        if(sessionKey.isEmpty()) return null;
        if(!databaseHandler.isSessionExist(sessionKey)) return null;
        Session session = databaseHandler.getSession(sessionKey);
        if(session.isExpired()) {
            databaseHandler.removeSession(sessionKey);
            return null;
        }
        return session;
    }


}
