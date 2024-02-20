package com.mechamate.service;

import com.mechamate.MechaMate;
//import com.mechamate.common.Common;
import com.mechamate.dto.ErrorDTO;
import com.mechamate.entity.UserProfile;
import com.mechamate.entity.Session;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.LoggerFactory;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class SessionManager {

    private static final Logger logger = LoggerFactory.getLogger(MechaMate.class);

    @Autowired
    private DatabaseAbstractLayer databaseAbstractLayer;

    @Autowired
//    private LanguageManager lang;

    public Session getSession(HttpServletRequest request, HttpServletResponse response) {
        try {
            if (request == null) return null;

            Cookie[] cookies = request.getCookies();
            if (cookies == null) return null;

            String sessionKey = "";
            String locale = "default";
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals("sessionKey")) {
                    sessionKey = cookie.getValue();
                } else if (cookie.getName().equals("locale")) {
                    locale = cookie.getValue();
                }
            }
            if(sessionKey == null || sessionKey.length() != 64) return null;
            if(locale == null || locale.isEmpty()) locale = "default";

            Session session = databaseAbstractLayer.getSession(sessionKey);
            if(session == null) return null;

            if(session.hasExpired()) {
                databaseAbstractLayer.deleteSession(session);
                Cookie cookie = new Cookie("sessionKey", null);
                cookie.setHttpOnly(true);
                cookie.setSecure(false);
                cookie.setPath("/");
                cookie.setMaxAge(0);
                if(response != null) response.addCookie(cookie);
                return null;
            } else {
                if((session.getValidUntil() - System.currentTimeMillis()) < 3600000) { // 1 hour
                    session.setValidUntil(System.currentTimeMillis() + 7200000); // 2 hours
                    if(!databaseAbstractLayer.updateSession(session)) return null;
                }
                return session;
            }
        } catch (Exception e) {
            logger.error("Exception thrown: " + e.getMessage());
        }
        return null;
    }

    public ResponseEntity<ErrorDTO> createSession(HttpServletResponse response, UserProfile userProfile,
                                                  boolean isPersistent) {
        try {
            if(response == null) {
                return new ResponseEntity<>
                        (new ErrorDTO(ErrorDTO.ErrorStatus.InternalError,
                                lang.get("error.internal.response.invalid", userProfile.getLanguage()),
                                lang.get("error.internal.response.invalid.help", userProfile.getLanguage())),
                                HttpStatus.INTERNAL_SERVER_ERROR);
            }

            String sessionKey = Common.getSha256("SKEY#>" + userProfile.getUsername() +
                    System.currentTimeMillis() + userProfile.getPassword() + "<#");

            Session session = new Session((isPersistent ? (System.currentTimeMillis() + 259200000) :
                    (System.currentTimeMillis() + 10800000)),
                    sessionKey, userProfile);

            if(!databaseAbstractLayer.addSession(session)) {
                return new ResponseEntity<>
                        (new ErrorDTO(ErrorDTO.ErrorStatus.InternalError,
                                lang.get("error.internal.session.db.failed", userProfile.getLanguage()),
                                lang.get("error.internal.session.db.failed.help", userProfile.getLanguage())),
                                HttpStatus.INTERNAL_SERVER_ERROR);
            }

            Cookie sessionCookie = new Cookie("sessionKey", sessionKey);
            sessionCookie.setSecure(false); sessionCookie.setHttpOnly(true);
            sessionCookie.setPath("/");
            sessionCookie.setMaxAge((isPersistent ? 259200 : -1));
            Cookie localeCookie = new Cookie("locale", userProfile.getLanguage());
            localeCookie.setSecure(false); localeCookie.setHttpOnly(false);
            localeCookie.setPath("/");
            localeCookie.setMaxAge(31536000);

            response.addCookie(sessionCookie);
            response.addCookie(localeCookie);
            return null;

        } catch (Exception e) {
            return new ResponseEntity<>
                    (new ErrorDTO(ErrorDTO.ErrorStatus.InternalError,
                            lang.get("error.internal.session.creation.failed", userProfile.getLanguage()),
                            lang.get("error.internal.session.creation.failed.help", userProfile.getLanguage())),
                            HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    public ResponseEntity<ErrorDTO> deleteSession(HttpServletRequest request, HttpServletResponse response,
                                                  UserProfile userProfile) {
        try {

            if (response == null) {
                return new ResponseEntity<>
                        (new ErrorDTO(ErrorDTO.ErrorStatus.InternalError,
                                lang.get("error.internal.response.invalid", userProfile.getLanguage()),
                                lang.get("error.internal.response.invalid.help", userProfile.getLanguage())),
                                HttpStatus.INTERNAL_SERVER_ERROR);
            }

            Cookie cookie = new Cookie("sessionKey", null);
            cookie.setHttpOnly(true);
            cookie.setSecure(false);
            cookie.setPath("/");
            cookie.setMaxAge(0);
            response.addCookie(cookie);

            if (request == null) {
                return new ResponseEntity<>
                        (new ErrorDTO(ErrorDTO.ErrorStatus.InternalError,
                                lang.get("error.internal.request.invalid", userProfile.getLanguage()),
                                lang.get("error.internal.request.invalid.help", userProfile.getLanguage())),
                                HttpStatus.INTERNAL_SERVER_ERROR);
            }

            Cookie[] cookies = request.getCookies();
            if (cookies == null) {
                return new ResponseEntity<>
                        (new ErrorDTO(ErrorDTO.ErrorStatus.ErrorInvalidRequest,
                                lang.get("error.no.cookies", userProfile.getLanguage()),
                                lang.get("error.no.cookies.help", userProfile.getLanguage())),
                                HttpStatus.BAD_REQUEST);
            }

            String sessionKey = "";
            String locale = "default";
            for (Cookie c : cookies) {
                if (c.getName().equals("sessionKey")) {
                    sessionKey = c.getValue();
                    break;
                }
            }
            if(sessionKey == null || sessionKey.length() != 64) return null;

            Session session = databaseAbstractLayer.getSession(sessionKey);
            if(session == null) return null;

            databaseAbstractLayer.deleteSession(session);
            return null;

        } catch (Exception e) {
            logger.error("Exception thrown: " + e.getMessage());

            return new ResponseEntity<>
                    (new ErrorDTO(ErrorDTO.ErrorStatus.InternalError,
                            lang.get("error.internal.session.deletion.failed", userProfile.getLanguage()),
                            lang.get("error.internal.session.deletion.failed.help", userProfile.getLanguage())),
                            HttpStatus.INTERNAL_SERVER_ERROR);

        }

    }


}
