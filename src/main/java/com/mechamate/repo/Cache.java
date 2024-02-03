package com.mechamate.repo;


import com.mechamate.MechaMate.entity.UserProfile;
import com.mechamate.MechaMate.service.Session;

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






