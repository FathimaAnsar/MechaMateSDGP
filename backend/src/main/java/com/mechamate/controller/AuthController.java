package com.mechamate.controller;

import com.mechamate.common.Common;
import com.mechamate.common.Validation;
import com.mechamate.dto.ErrorDTO;
import com.mechamate.dto.SuccessDTO;
import com.mechamate.entity.Session;
import com.mechamate.entity.UserProfile;
import com.mechamate.service.LanguageManager;
import com.mechamate.service.ProfileManager;
import com.mechamate.service.SessionManager;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {

    @Autowired
    private SessionManager sessionManager;

    @Autowired
    private ProfileManager profileManager;

    @Autowired
    private LanguageManager lang;

    @PostMapping("/signup")
    public ResponseEntity<?> signup(HttpServletRequest request, HttpServletResponse response,
                                    @RequestParam(required = false) String username,
                                    @RequestParam(required = false) String password,
                                    @RequestParam(required = false) String email,
                                    @RequestParam(required = false) String telephone,
                                    @RequestParam(required = false) String firstname,
                                    @RequestParam(required = false) String lastname,
                                    @RequestParam(required = false) boolean isServiceAccount,
                                    @RequestParam(required = false) boolean agreedTOS) {

        Session session = sessionManager.getSession(request, response);
        ResponseEntity<ErrorDTO> resp = Validation.alreadySignedIn(session, lang, request.getSession());
        if(resp != null) return resp;

        if(username == null) username = "";
        if(email == null) email = "";
        if(telephone == null) telephone = "";
        if(firstname == null) firstname = "";
        if(lastname == null) lastname = "";

        resp = Validation.validateUsername(username.trim().toLowerCase(), lang, request.getSession());
        if(resp != null) return resp;

        resp = Validation.validatePassword(password, lang, request.getSession());
        if(resp != null) return resp;

        resp = Validation.validateEmail(email.trim().toLowerCase(), lang, request.getSession());
        if(resp != null) return resp;

        resp = Validation.validateFirstName(firstname.trim().toLowerCase(), lang, request.getSession());
        if(resp != null) return resp;

        resp = Validation.validateLastName(lastname.trim().toLowerCase(), lang, request.getSession());
        if(resp != null) return resp;

        resp = Validation.validateTelephone(telephone.trim(), lang, request.getSession());
        if(resp != null) return resp;

        resp = Validation.validateAgreedTOS(agreedTOS, lang, request.getSession());
        if(resp != null) return resp;

        UserProfile userProfile = new UserProfile(UserProfile.Status.StatusPendingActivation,
                                                            username.trim().toLowerCase(),
                                                            Common.getSha256("AUTH#>>(" + password + ")<<#"),
                                                            email.trim().toLowerCase(),
                                                            telephone.trim(),
                                                            Common.toTitleCase(firstname.trim().toLowerCase()),
                                                            Common.toTitleCase(lastname.trim().toLowerCase()),
                                                            lang.getLanguage(request.getSession()), null, 0.0, 0.0, isServiceAccount);

        resp = profileManager.createUserProfile(request, userProfile);
        if(resp != null) return resp;

        resp = sessionManager.createSession(response, userProfile, false);
        if(resp != null) return resp;

        return new ResponseEntity<>
                (new SuccessDTO(SuccessDTO.SuccessStatus.SignupSucceeded,
                        lang.get("success.signup.succeeded", userProfile.getLanguage()),
                        lang.getFilledWith("success.signup.succeeded.info", userProfile.getLanguage(),
                                new String[]{userProfile.getEmail()})),
                        HttpStatus.OK);
    }




    @GetMapping("/activate")
    public ResponseEntity<?> activate(HttpServletRequest request, HttpServletResponse response,
                                      @RequestParam(required = false) String key) {

        Session session = sessionManager.getSession(request, response);
        ResponseEntity<ErrorDTO> resp = Validation.notSignedIn(session, lang, request.getSession());
        if(resp != null) return resp;

        if(key == null) key = "";

        resp = Validation.validateActivationKey(key.trim().toLowerCase(), lang, request.getSession());
        if(resp != null) return resp;

        UserProfile userProfile = session.getUserProfile();

        resp = profileManager.activateUserProfile(request, key.trim().toLowerCase(), userProfile);
        if(resp != null) return resp;

        return new ResponseEntity<>
                (new SuccessDTO(SuccessDTO.SuccessStatus.ProfileActivationSucceeded,
                        lang.get("success.activate.succeeded", userProfile.getLanguage()),
                        lang.get("success.activate.succeeded.info", userProfile.getLanguage())),
                        HttpStatus.OK);

    }



    @GetMapping("/resend-otp")
    public ResponseEntity<?> activate(HttpServletRequest request, HttpServletResponse response) {
        Session session = sessionManager.getSession(request, response);
        ResponseEntity<ErrorDTO> resp = Validation.notSignedIn(session, lang, request.getSession());
        if(resp != null) return resp;

        UserProfile userProfile = session.getUserProfile();
        resp = profileManager.resendOTP(request, userProfile);
        if(resp != null) return resp;

        return new ResponseEntity<>
                (new SuccessDTO(SuccessDTO.SuccessStatus.OperationSucceeded,
                        lang.get("success.resend.otp.succeeded", userProfile.getLanguage()),
                        lang.get("success.resend.otp.succeeded.info", userProfile.getLanguage())),
                        HttpStatus.OK);

    }



    @PostMapping("/signin")
    public ResponseEntity<?> signin(HttpServletRequest request, HttpServletResponse response,
                                    @RequestParam(required = false) String username,
                                    @RequestParam(required = false) String password,
                                    @RequestParam(required = false) boolean keepMeSignedIn) {

        Session session = sessionManager.getSession(request, response);
        ResponseEntity<ErrorDTO> resp = Validation.alreadySignedIn(session, lang, request.getSession());
        if(resp != null) {
            return new ResponseEntity<>
                    (new SuccessDTO(SuccessDTO.SuccessStatus.AlreadySignedIn,
                            lang.get("success.already.signedin",  lang.getLanguage(request.getSession())),
                            lang.get("success.already.signedin.info",  lang.getLanguage(request.getSession()))),
                            HttpStatus.OK);
        }

        if(username == null) username = "";

        resp = Validation.validateUsername(username.trim().toLowerCase(), lang, request.getSession());
        if(resp != null) return resp;

        resp = Validation.validatePassword(password, lang, request.getSession());
        if(resp != null) return resp;

        UserProfile userProfile = profileManager.getUserProfileByUsername(username);

        resp = profileManager.signin(request, username.trim().toLowerCase(), password, userProfile);
        if(resp != null) return resp;

        resp = sessionManager.createSession(response, userProfile, keepMeSignedIn);
        if(resp != null) return resp;

        return new ResponseEntity<>
                (new SuccessDTO(SuccessDTO.SuccessStatus.SigninSucceeded,
                        lang.get("success.signin.succeeded", userProfile.getLanguage()),
                        lang.get("success.signin.succeeded.info", userProfile.getLanguage())),
                        HttpStatus.OK);

    }




    @RequestMapping("/signout")
    public ResponseEntity<?> singout(HttpServletRequest request, HttpServletResponse response) {

        Session session = sessionManager.getSession(request, response);
        ResponseEntity<ErrorDTO> resp = Validation.notSignedIn(session, lang, request.getSession());
        if(resp != null) return resp;

        UserProfile userProfile = session.getUserProfile();

        resp = profileManager.signout(request, userProfile);
        if(resp != null) return resp;

        resp = sessionManager.deleteSession(request, response, userProfile);
        if(resp != null) return resp;

        return new ResponseEntity<>
                (new SuccessDTO(SuccessDTO.SuccessStatus.SignoutSucceeded,
                        lang.get("success.signout.succeeded", userProfile.getLanguage()),
                        lang.get("success.signout.succeeded.info", userProfile.getLanguage())),
                        HttpStatus.OK);
    }




    @PostMapping("/recover")
    public ResponseEntity<?> recover(HttpServletRequest request,
                                     @RequestParam(required = false) String email) {

        if(email == null) email = "";
        ResponseEntity<ErrorDTO> resp = Validation.validateEmail(email.trim().toLowerCase(),
                lang, request.getSession());
        if(resp != null) return resp;

        UserProfile userProfile = profileManager.getUserProfileByEmail(email.trim().toLowerCase());

        resp = profileManager.recoverPassword(request, userProfile);
        if(resp != null) return resp;

        return new ResponseEntity<>
                (new SuccessDTO(SuccessDTO.SuccessStatus.RecoveryEmailSent,
                        lang.get("success.recover.pass.succeeded", userProfile.getLanguage()),
                        lang.getFilledWith("success.recover.pass.succeeded.info", userProfile.getLanguage(),
                                new String[]{userProfile.getEmail()})),
                        HttpStatus.OK);
    }




    @PostMapping("/reset")
    public ResponseEntity<?> reset(HttpServletRequest request,
                                      @RequestParam(required = false) String token,
                                      @RequestParam(required = false) String password) {

        if(token == null) token = "";
        ResponseEntity<ErrorDTO> resp = Validation.validateRecoveryKey(token.trim().toLowerCase(), lang,
                request.getSession());
        if(resp != null) return resp;

        resp = Validation.validatePassword(password, lang, request.getSession());
        if(resp != null) return resp;

        UserProfile userProfile = profileManager.getUserProfileByRecoveryKey(token.trim().toLowerCase());

        resp = profileManager.resetPassword(request, Common.getSha256("AUTH#>>(" + password + ")<<#"), userProfile);
        if(resp != null) return resp;

        return new ResponseEntity<>
                (new SuccessDTO(SuccessDTO.SuccessStatus.ResetPasswordSucceeded,
                        lang.get("success.reset.pass.succeeded", userProfile.getLanguage()),
                        lang.getFilledWith("reset.recover.pass.succeeded.info", userProfile.getLanguage(),
                                new String[]{userProfile.getEmail()})),
                        HttpStatus.OK);
    }







}
