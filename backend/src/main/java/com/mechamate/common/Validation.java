package com.mechamate.common;

import com.mechamate.dto.ErrorDTO;
import com.mechamate.entity.Session;
import com.mechamate.entity.UserProfile;
import com.mechamate.service.LanguageManager;
import com.mechamate.service.SessionManager;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Objects;
import java.util.regex.Pattern;

public class Validation {

    private static ResponseEntity<ErrorDTO> validateInput(String input, String regex, LanguageManager lang,
                                                          HttpSession session, String inputIdentifier) {
        try {
            if (input == null) {
                return new ResponseEntity<>
                        (new ErrorDTO(ErrorDTO.ErrorStatus.ErrorInvalidRequest,
                                lang.getFilledWith("error.input.missing", session, new String[]{inputIdentifier}),
                                lang.getFilledWith("error.input.missing.help", session, new String[]{inputIdentifier})),
                                HttpStatus.OK);
            }
            if (input.isEmpty()) {
                return new ResponseEntity<>
                        (new ErrorDTO(ErrorDTO.ErrorStatus.ErrorInvalidRequest,
                                lang.getFilledWith("error.input.empty", session, new String[]{inputIdentifier}),
                                lang.getFilledWith("error.input.empty.help", session, new String[]{inputIdentifier})),
                                HttpStatus.OK);
            }
            if (Pattern.compile(regex).matcher(input).matches()) return null;
        } catch (Exception ignore) {
            //
        }

        String helpStr = "";
        if(Objects.equals(inputIdentifier, "username"))
            helpStr = lang.get("error.input.invalid.username.help", session);
        else if(Objects.equals(inputIdentifier, "password"))
            helpStr = lang.get("error.input.invalid.password.help", session);
        else if(Objects.equals(inputIdentifier, "email"))
            helpStr = lang.get("error.input.invalid.email.help", session);
        else if(Objects.equals(inputIdentifier, "name"))
            helpStr = lang.get("error.input.invalid.name.help", session);
        else if(Objects.equals(inputIdentifier, "telephone"))
            helpStr = lang.get("error.input.invalid.telephone.help", session);
        else if(Objects.equals(inputIdentifier, "key"))
            helpStr = lang.get("error.input.invalid.key.help", session);
        else if(Objects.equals(inputIdentifier, "token"))
            helpStr = lang.get("error.input.invalid.recovery.key.help", session);
        else
            helpStr = lang.get("error.input.invalid.help", session);

        return new ResponseEntity<>
                (new ErrorDTO(ErrorDTO.ErrorStatus.ErrorInvalidRequest,
                        lang.getFilledWith("error.input.invalid", session, new String[]{inputIdentifier}),
                        helpStr),
                        HttpStatus.OK);
    }




    private static ResponseEntity<ErrorDTO> validateInputJson(String input, String regex, LanguageManager lang,
                                                          HttpSession session, String inputIdentifier) {
        try {
            if (input == null) {
                return new ResponseEntity<>
                        (new ErrorDTO(ErrorDTO.ErrorStatus.ErrorInvalidRequest,
                                lang.getFilledWith("error.input.json.missing", session, new String[]{inputIdentifier}),
                                lang.getFilledWith("error.input.json.missing.help", session, new String[]{inputIdentifier})),
                                HttpStatus.OK);
            }
            if (input.isEmpty()) {
                return new ResponseEntity<>
                        (new ErrorDTO(ErrorDTO.ErrorStatus.ErrorInvalidRequest,
                                lang.getFilledWith("error.input.json.empty", session, new String[]{inputIdentifier}),
                                lang.getFilledWith("error.input.json.empty.help", session, new String[]{inputIdentifier})),
                                HttpStatus.OK);
            }
            if (Pattern.compile(regex).matcher(input).matches()) return null;
        } catch (Exception ignore) {
            //
        }

        String helpStr = "";
        if(Objects.equals(inputIdentifier, "Vehicle registration number"))
            helpStr = lang.get("error.input.json.invalid.regno.help", session);
//        else if(Objects.equals(inputIdentifier, "password"))
//            helpStr = lang.get("error.input.invalid.password.help", session);
        else
            helpStr = lang.get("error.input.json.invalid.help", session);

        return new ResponseEntity<>
                (new ErrorDTO(ErrorDTO.ErrorStatus.ErrorInvalidRequest,
                        lang.getFilledWith("error.input.json.invalid", session, new String[]{inputIdentifier}),
                        helpStr),
                        HttpStatus.OK);
    }





    public static ResponseEntity<ErrorDTO> validateUsername(String username, LanguageManager lang,
                                                            HttpSession session) {
        return validateInput(username, "^(?=.{5,20}$)(?![_.])(?!.*[_.]{2})[a-zA-Z0-9._]+(?<![_.])$",
                lang , session, "username");
    }

    public static ResponseEntity<ErrorDTO> validateFirstName(String name, LanguageManager lang,
                                                        HttpSession session) {
        return validateInput(name, "^[a-zA-Z]*$", lang , session, "firstname");
    }

    public static ResponseEntity<ErrorDTO> validateLastName(String name, LanguageManager lang,
                                                        HttpSession session) {
        return validateInput(name, "^[a-zA-Z]*$", lang , session, "lastname");
    }

    public static ResponseEntity<ErrorDTO> validateEmail(String email, LanguageManager lang,
                                                         HttpSession session) {
        return validateInput(email, "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}(?:\\.[a-zA-Z]{2,})?$",
                lang , session, "email");
    }

    public static ResponseEntity<ErrorDTO> validatePassword(String password, LanguageManager lang,
                                                            HttpSession session) {
        return validateInput(password,
                "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#!)(|}{?><,/.:;\"'$%^&+=])(?=\\S+$).{6,}$",
                lang , session, "password");
    }

    public static ResponseEntity<ErrorDTO> validateTelephone(String telephone, LanguageManager lang,
                                                             HttpSession session) {
        return validateInput(telephone, "\\d{10}|(?:\\d{3}-){2}\\d{4}|\\(\\d{3}\\)\\d{3}-?\\d{4}",
                lang , session, "telephone");
    }

    public static ResponseEntity<ErrorDTO> validateActivationKey(String key, LanguageManager lang,
                                                             HttpSession session) {
        return validateInput(key, "^[0-9]{6}$",
                lang , session, "key");
    }

    public static ResponseEntity<ErrorDTO> validateRecoveryKey(String key, LanguageManager lang,
                                                                 HttpSession session) {
        return validateInput(key, "^[a-fA-F0-9]{64}$",
                lang , session, "token");
    }

    public static ResponseEntity<ErrorDTO> validateAgreedTOS(boolean agreedTOS, LanguageManager lang,
                                                             HttpSession session) {
        try {
            if(agreedTOS) return null;
        } catch (Exception ignore) {}
        return new ResponseEntity<>
                (new ErrorDTO(ErrorDTO.ErrorStatus.ErrorInvalidRequest,
                        lang.get("error.input.tos.disagreed", session),
                        lang.get("error.input.tos.disagreed.help", session)),
                        HttpStatus.OK);
    }



    public static ResponseEntity<ErrorDTO> validateVehicleRegNo(String regNo, LanguageManager lang,
                                                       HttpSession session) {
        return validateInputJson(regNo, "^[A-Za-z]{2,3}-?\\d{4}$",
                lang , session, "Vehicle registration number");
    }


    public static ResponseEntity<ErrorDTO> alreadySignedIn(Session session, LanguageManager lang,
                                                           HttpSession httpSession) {
        if(session != null) {
            return new ResponseEntity<>
                    (new ErrorDTO(ErrorDTO.ErrorStatus.ErrorInvalidRequest,
                            lang.get("error.already.signedin", httpSession),
                            lang.get("error.already.signedin.help", httpSession)),
                            HttpStatus.OK);
        } else {
            return null;
        }
    }


    public static ResponseEntity<ErrorDTO> notSignedIn(Session session, LanguageManager lang,
                                                           HttpSession httpSession) {
        if(session == null) {
            return new ResponseEntity<>
                    (new ErrorDTO(ErrorDTO.ErrorStatus.ErrorNotSignedIn,
                            lang.get("error.not.signedin", httpSession),
                            lang.get("error.not.signedin.help", httpSession)),
                            HttpStatus.OK);
        } else {
            return null;
        }
    }




    public static Object authenticate(HttpServletRequest request, HttpServletResponse response,
                                      SessionManager sessionManager, LanguageManager lang) {
        Session session = sessionManager.getSession(request, response);
        ResponseEntity<ErrorDTO> resp = Validation.notSignedIn(session, lang, request.getSession());
        if(resp != null) return resp;

        UserProfile userProfile = session.getUserProfile();
        if(userProfile == null)
            return new ResponseEntity<>
                    (new ErrorDTO(ErrorDTO.ErrorStatus.ErrorUnauthorized,
                            lang.get("error.session.unauthorized", lang.getLanguage(request.getSession())),
                            lang.get("error.session.unauthorized.help", lang.getLanguage(request.getSession()))),
                            HttpStatus.OK);

        resp = profileOperationAllowed(userProfile, true, false,
                lang.get("error.invalid.account.operation.help", lang.getLanguage(request.getSession())),
                true, lang.getLanguage(request.getSession()), lang);
        if(resp != null) return resp;

        return userProfile;
    }



    public static ResponseEntity<ErrorDTO> profileOperationAllowed(UserProfile userProfile, boolean isSessionUser,
                                                            boolean checkByEmail,
                                                            String helpStr,
                                                            boolean includePendingAct, String language,
                                                            LanguageManager lang) {

        if(userProfile == null) {
            if(isSessionUser) {
                return new ResponseEntity<>
                        (new ErrorDTO(ErrorDTO.ErrorStatus.InternalError,
                                lang.get("error.internal.session.prof.null", language),
                                lang.get("error.internal.session.prof.null.help", language)),
                                HttpStatus.OK);
            } else {
                if(checkByEmail) {
                    return new ResponseEntity<>
                            (new ErrorDTO(ErrorDTO.ErrorStatus.ErrorEmailDoesntExist,
                                    lang.get("error.email.doesnt.exist", language),
                                    lang.get("error.email.doesnt.exist.help", language)),
                                    HttpStatus.OK);
                } else {
                    return new ResponseEntity<>
                            (new ErrorDTO(ErrorDTO.ErrorStatus.ErrorUserDoesntExist,
                                    lang.get("error.user.notexists", language),
                                    lang.get("error.user.notexists.help", language)),
                                    HttpStatus.OK);
                }
            }
        }

        if(userProfile.getStatus() == UserProfile.Status.StatusDeleted) {
            return new ResponseEntity<>
                    (new ErrorDTO(ErrorDTO.ErrorStatus.ErrorInvalidRequest,
                            lang.get("error.deleted.account", language),
                            helpStr),
                            HttpStatus.OK);
        } else if(userProfile.getStatus() == UserProfile.Status.StatusBlocked) {
            return new ResponseEntity<>
                    (new ErrorDTO(ErrorDTO.ErrorStatus.ErrorInvalidRequest,
                            lang.get("error.blocked.account", language),
                            helpStr),
                            HttpStatus.OK);
        } else if (userProfile.getStatus() == UserProfile.Status.StatusInactive) {
            return new ResponseEntity<>
                    (new ErrorDTO(ErrorDTO.ErrorStatus.ErrorInvalidRequest,
                            lang.get("error.inactive.account", language),
                            helpStr),
                            HttpStatus.OK);
        }

        if (includePendingAct && userProfile.getStatus() == UserProfile.Status.StatusPendingActivation) {
            return new ResponseEntity<>
                    (new ErrorDTO(ErrorDTO.ErrorStatus.ErrorPendingActivation,
                            lang.get("error.user.pending.activation", language),
                            lang.get("error.user.pending.activation.help", language)),
                            HttpStatus.OK);
        }

        return null;
    }



}
