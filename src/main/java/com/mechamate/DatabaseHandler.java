package com.mechamate.service;

import com.mechamate.Log;
import com.mechamate.entity.Session;
import com.mechamate.entity.UserProfile;
import com.mechamate.repo.SessionRepo;
import com.mechamate.repo.UserProfileRepo;
import com.mechamate.repo.VehicleRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class DatabaseHandler {

    @Autowired
    private UserProfileRepo userProfileRepo;

    @Autowired
    private VehicleRepo vehicleRepo;

    @Autowired
    private SessionRepo sessionRepo;

    public DatabaseHandler() {}

    Log log;
    public DatabaseHandler(Log log) {
        this.log = log;
    }

    public boolean addUserProfile(UserProfile userProfile) {
        if(userProfile == null) return false;
        if(userProfileRepo.existsByUsername(userProfile.getUsername())) return false;
        userProfileRepo.save(userProfile);
        return true;
    }

    public boolean updateUserProfile(UserProfile userProfile) {
        if(userProfile == null) return false;
        if(!userProfileRepo.existsByUsername(userProfile.getUsername())) return false;
        userProfileRepo.save(userProfile);
        return true;
    }

    public UserProfile getUserProfile(String username) {
        if(username.isEmpty()) return null;
        return userProfileRepo.findByUsername(username).orElse(null);
    }

    public boolean isUserProfileExist(String username) {
        if(username.isEmpty()) return false;
        try {
            return userProfileRepo.existsByUsername(username);
        } catch (Exception ignore) {
            return false;
        }
    }

    public boolean removeUserProfile(String username) {
        if(username.isEmpty()) return false;
        Optional<UserProfile> uProf = userProfileRepo.findByUsername(username);
        if(uProf.isEmpty()) return true;
        userProfileRepo.deleteById(uProf.get().getId());
        return true;
    }



    public boolean addSession(Session session) {
        if(session == null) return false;
        if(sessionRepo.existsBySessionKey(session.getSessionKey())) return false;
        sessionRepo.save(session);
        return true;
    }

    public Session getSession(String sessionKey) {
        if(sessionKey.isEmpty()) return null;
        return sessionRepo.findBySessionKey(sessionKey).orElse(null);
    }

    public boolean isSessionExist(String sessionKey) {
        if(sessionKey.isEmpty()) return false;
        try {
            return sessionRepo.existsBySessionKey(sessionKey);
        } catch (Exception ignore) {
            return false;
        }
    }

    public boolean removeSession(String sessionKey) {
        if(sessionKey.isEmpty()) return false;
        Optional<Session> sn = sessionRepo.findBySessionKey(sessionKey);
        if(sn.isEmpty()) return true;
        sessionRepo.deleteById(sn.get().getId());
        return true;
    }




}
