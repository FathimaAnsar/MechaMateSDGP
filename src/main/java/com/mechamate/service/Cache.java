package com.mechamate.service;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

@Service
public class MyCacheService {

    @Cacheable(value = "myCache", key = "#key")
    public String getCachedData(String key) {
        // Simulate fetching data from database or external service
        // This method will only be executed if the data is not present in the cache
        System.out.println("Fetching data for key: " + key);
        return "Cached data for key: " + key;
    }

    // Add more cacheable methods as needed
}

/*
import java.util.HashMap;
import java.util.Map;

public class Cache {
    private Map<String, UserProfile> userProfiles = new HashMap<>();
    private Map<String, Session> sessions = new HashMap<>();

    public Cache(Log log) {
    }

    public void addUserProfile(UserProfile userProfile) {
        userProfiles.put(userProfile.getUsername(), userProfile);
    }

    public void addSession(Session session) {
        sessions.put(session.getSessionKey(), session);
    }

    public boolean isUserExists(String username) {
        return userProfiles.keySet().contains(username);
    }

    public boolean isSessionExists(String sessionKey) {
        return sessions.keySet().contains(sessionKey);
    }

    public Session getSession(String sessionKey) {
        if(sessions.keySet().contains(sessionKey)) {
            return sessions.get(sessionKey);
        } else {
            return null;
        }
    }

    public UserProfile getUserProfile(String username) {
        if(userProfiles.keySet().contains(username)) {
            return userProfiles.get(username);
        } else {
            return null;
        }
    }


    public boolean removeUserProfile(String username) {
        if(userProfiles.keySet().contains(username)) {
            userProfiles.remove(username);
            return true;
        } else {
            return false;
        }
    }


    public boolean removeSession(String sessionKey) {
        if(sessions.keySet().contains(sessionKey)) {
            sessions.remove(sessionKey);
            return true;
        } else {
            return false;
        }
    }

    public boolean updateUserProfile(UserProfile userProfile) {
        if(userProfiles.keySet().contains(userProfile.getUsername())) {
            userProfiles.replace(userProfile.getUsername(), userProfile);
            return true;
        } else {
            return false;
        }
    }

    public boolean updateSession(Session session) {
        if(sessions.keySet().contains(session.getSessionKey())) {
            sessions.replace(session.getSessionKey(), session);
            return true;
        } else {
            return false;
        }
    }


}

*/




