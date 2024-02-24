package com.mechamate.service;
import com.mechamate.MechaMate;
import com.mechamate.entity.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class CacheManager {
    private static final Logger logger = LoggerFactory.getLogger(MechaMate.class);
    private final ConcurrentHashMap<String, UserProfile> userProfileCache = new ConcurrentHashMap<>();
    private final ConcurrentHashMap<String, Session> sessionCache = new ConcurrentHashMap<>();
    private final ConcurrentHashMap<String, Vehicle> VehicleCache = new ConcurrentHashMap<>();
    private final ConcurrentHashMap<String, ServiceRecord> ServiceRecordCache = new ConcurrentHashMap<>();
    private final ConcurrentHashMap<String, Maintenance> MaintenanceCache = new ConcurrentHashMap<>();
    private final ConcurrentHashMap<String, ServiceRecord> PredictionModelCache = new ConcurrentHashMap<>();

    //********************************** user profiles ************************************************
    public boolean isUserExistInCache (String key){
        boolean isExist = false;
        if (key == null) {
            logger.warn("attempt to get a UserProfile with a null key.");
        }
        try {
            isExist = userProfileCache.containsKey(key);
            logger.info("found UserProfile for key: {}", key);

        }catch (Exception e){
            logger.error("exception happened while retrieving UserProfile from cache for key: {}. Exception: {}", key, e.toString());
        }
        return isExist;
    }

    public UserProfile getFromUserProfileCache(String key) {

        if (key == null) {
            logger.warn("attempt to get a UserProfile with a null key.");
            return null;
        }

        try {

            UserProfile userProfile = userProfileCache.get(key);

            if (userProfile == null) {
                logger.info("cache miss for UserProfile key: {}", key);
                return null;
            } else {
                logger.info("found UserProfile for key: {}", key);
                return userProfile;
            }
        } catch (Exception e) {
            logger.error("exception happened while retrieving UserProfile from cache for key: {}. Exception: {}", key, e.toString());
            return null;
        }
    }


    public void putInUserProfileCache(String key, UserProfile userProfile) {
        if (key == null || userProfile == null) {
            logger.warn("attempt to put a null key or UserProfile into the cache. Key: {}, UserProfile: {}", key, userProfile);
            return;
        }

        try {
            userProfileCache.put(key, userProfile);
            logger.info("UserProfile put into cache under key: {}", key);
        } catch (Exception e) {
            logger.error("exception happens while putting UserProfile into cache. Key: {}, Exception: {}", key, e.toString());
        }
    }


    public void deleteFromUserProfileCache(String key) {
        if (key == null) {
            logger.warn("attempt to delete UserProfile from cache with null key.");
            return;
        }

        try {
            if (!userProfileCache.containsKey(key)) {
                logger.info("attempt to delete a non UserProfile key from cache: {}", key);
                return;
            }
            userProfileCache.remove(key);
            logger.info("userProfile removed from cache for key: {}", key);

        } catch (Exception e) {
            logger.error("exception occurred while deleting UserProfile from cache. Key: {}, Exception: {}", key, e.toString());
        }
    }
    //************************************* sessions ************************************************

    public boolean isSessionExistInCache (String key){
        boolean isExist = false;
        if (key == null) {
            logger.warn("attempt to get a session with a null key.");
        }
        try {
            isExist = sessionCache.containsKey(key);
            logger.info("found session for key: {}", key);

        }catch (Exception e){
            logger.error("exception happened while retrieving session from cache for key: {}. Exception: {}", key, e.toString());
        }
        return isExist;
    }

    public Session getFromSessionCache(String key) {

        if (key == null) {
            logger.warn("attempt to get a session with a null key.");
            return null;
        }

        try {

            Session session = sessionCache.get(key);

            if (session == null) {
                logger.info("cache miss for session key: {}", key);
                return null;
            } else {
                logger.info("found session for key: {}", key);
                return session;
            }
        } catch (Exception e) {
            logger.error("exception happened while retrieving session from cache for key: {}. Exception: {}", key, e.toString());
            return null;
        }
    }


    public void putInSessionCache(String key, Session session) {
        if (key == null || session == null) {
            logger.warn("attempt to put a null key or session into the cache. Key: {}, UserProfile: {}", key, session);
            return;
        }

        try {
            sessionCache.put(key, session);
            logger.info("session put into cache under key: {}", key);
        } catch (Exception e) {
            logger.error("exception happens while putting session into cache. Key: {}, Exception: {}", key, e.toString());
        }
    }


    public void deleteFromSessionCache(String key) {
        if (key == null) {
            logger.warn("attempt to delete session from cache with null key.");
            return;
        }

        try {
            if (!sessionCache.containsKey(key)) {
                logger.info("attempt to delete a non session key from cache: {}", key);
                return;
            }
            sessionCache.remove(key);
            logger.info("session removed from cache for key: {}", key);

        } catch (Exception e) {
            logger.error("exception occurred while deleting session from cache. Key: {}, Exception: {}", key, e.toString());
        }
    }
}
