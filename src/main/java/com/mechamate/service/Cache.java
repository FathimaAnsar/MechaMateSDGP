package com.mechamate.service;

import com.mechamate.entity.UserProfile;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

@Service
public class Cache {

    @CachePut(value = "userProfiles", key = "#userProfile.username")
    public UserProfile addUserProfile(UserProfile userProfile) {
        return userProfile;
    }

    @CachePut(value = "sessions", key = "#session.sessionKey")
    public Session addSession(Session session) {
        return session;
    }

    @Cacheable(value = "userProfiles", key = "#username")
    public UserProfile getUserProfile(String username) {
        return null; // This method will only be executed if the data is not present in the cache
    }

    @Cacheable(value = "sessions", key = "#sessionKey")
    public Session getSession(String sessionKey) {
        return null; // This method will only be executed if the data is not present in the cache
    }

    @CacheEvict(value = "userProfiles", key = "#username")
    public void removeUserProfile(String username) {
        // This method will remove the entry from the cache
    }

    @CacheEvict(value = "sessions", key = "#sessionKey")
    public void removeSession(String sessionKey) {
        // This method will remove the entry from the cache
    }

    @CachePut(value = "userProfiles", key = "#userProfile.username")
    public UserProfile updateUserProfile(UserProfile userProfile) {
        return userProfile;
    }

    @CachePut(value = "sessions", key = "#session.sessionKey")
    public Session updateSession(Session session) {
        return session;
    }


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




