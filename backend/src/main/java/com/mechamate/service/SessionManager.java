package com.mechamate.service;

import com.mechamate.MechaMate;
import com.mechamate.common.Common;
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
    private LanguageManager lang;

    public Session getSession(HttpServletRequest request, HttpServletResponse response) {
        logger.info("Entering getSession method");
        try {
            if (request == null){
                logger.error("request object is null");
                return null;
            }

            Cookie[] cookies = request.getCookies();
            if (cookies == null) {
                logger.info("no cookies found in the request");
                return null;
            }

            String sessionKey = "";
            String locale = "default";
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals("sessionKey")) {
                    sessionKey = cookie.getValue();
                } else if (cookie.getName().equals("locale")) {
                    locale = cookie.getValue();
                }
            }

            //validate session key with locale
            if(sessionKey == null || sessionKey.length() != 64) {
                logger.info("invalid or missing session key");
                return null;
            }
            if(locale == null || locale.isEmpty()){
                logger.warn("local is missing or null so local set to default");
                locale = "default";
            }

            //retrieve and validate the session
            Session session = databaseAbstractLayer.getSession(sessionKey);
            if(session == null){
                logger.info("no session found for provided session key");
                return null;
            }

            //check if the session has expired
            if(session.hasExpired()) {
                logger.info("session expired so delete the session");
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
                    logger.info("extending session validity");
                    session.setValidUntil(System.currentTimeMillis() + 7200000); // 2 hours
                    if(!databaseAbstractLayer.updateSession(session)){
                        logger.error("failed to update session");
                        return null;
                    }
                }
                return session;
            }
        } catch (Exception e) {
            logger.error("Exception in getting the session: " + e.getMessage(),e);
        }
        logger.info("exiting getSession method");
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
                                HttpStatus.OK);
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
                                HttpStatus.OK);
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
                            HttpStatus.OK);
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
                                HttpStatus.OK);
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
                                HttpStatus.OK);
            }

            Cookie[] cookies = request.getCookies();
            if (cookies == null) {
                return new ResponseEntity<>
                        (new ErrorDTO(ErrorDTO.ErrorStatus.ErrorInvalidRequest,
                                lang.get("error.no.cookies", userProfile.getLanguage()),
                                lang.get("error.no.cookies.help", userProfile.getLanguage())),
                                HttpStatus.OK);
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
                            HttpStatus.OK);

        }

    }


}
