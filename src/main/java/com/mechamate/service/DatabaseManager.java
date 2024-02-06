package com.mechamate.service;

import com.mechamate.entity.UserProfile;
import com.mechamate.entity.Vehicle;
import com.mechamate.repo.UserProfileRepository;
import com.mechamate.repo.VehicleRepository;
import com.mechamate.service.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class DatabaseManager {
    private Log log;
    @Autowired
    private UserProfileRepository userProfileRepository;

    @Autowired
    private VehicleRepository vehicleRepository;

    public DatabaseManager(){
        String source = this.getClass().getSimpleName() + "::DataBaseManager";


        this.log = new Log(true, "src/test/log_records.txt");
        if (log == null) {
            throw new IllegalArgumentException("Log cannot be null");
        }
        this.log = log;
        log.log(Log.LogLevelEnum.LogDebug, source, "initializing DatabaseManager with repositories.");
        log.log(Log.LogLevelEnum.LogDebug, source, "DatabaseManager initialized successfully");
    }

    public boolean addUserProfile(UserProfile userProfile) {
        String source = this.getClass().getSimpleName() + "::addUserProfile";

        if (userProfile == null) {
            log.log(Log.LogLevelEnum.LogError,source,"UserProfile cannot be null");
//            throw new IllegalArgumentException("UserProfile cannot be null");
            return false;
        }

        // Check if the username already exists
        if (userProfileRepository.findById(userProfile.getId()).isPresent()) {
            log.log(Log.LogLevelEnum.LogError,source, "user profile ID already exists");
//            throw new IllegalArgumentException("user profile ID already exist");
            return false;
        }

        try {
            // Save the new user profile
            userProfileRepository.save(userProfile);
            log.log(Log.LogLevelEnum.LogDebug, source, "user profile added successfully: " + userProfile.getUsername()+" with " +userProfile.getId());
            return true;
        } catch (Exception e) {
            log.log(Log.LogLevelEnum.LogError, source, "error adding user profile: " + e.getMessage());
//            throw new RuntimeException("Failed to add user profile", e);

            return false;
        }
    }
    public UserProfile getUserProfile(String username) {
        String source = this.getClass().getSimpleName() + "::getUserProfile";

        if (username == null || username.trim().isEmpty()) {
            log.log(Log.LogLevelEnum.LogError, source, "The username provided is null or empty.");
            return null;
        }

        try {
            Optional<UserProfile> userProfile = userProfileRepository.findByUsername(username);
            if (userProfile.isPresent()) {
                log.log(Log.LogLevelEnum.LogDebug, source, "UserProfile retrieved successfully for username: " + username);
                return userProfile.get();
            } else {
                log.log(Log.LogLevelEnum.LogError, source, "No UserProfile found for username: " + username);
                return null;
            }
        } catch (Exception e) {
            log.log(Log.LogLevelEnum.LogError, source, "Error retrieving UserProfile: " + e.getMessage());
            return null;
        }
    }
    public boolean updateUserProfile(String id, UserProfile updatedUserProfile) {
        String source = this.getClass().getSimpleName() + "::updateUserProfile";

        if (updatedUserProfile == null) {
            log.log(Log.LogLevelEnum.LogError, source, "Updated UserProfile object cannot be null.");
            return false;
        }

        return userProfileRepository.findById(id)
                .map(existingUserProfile -> {
                    updateExistingUserProfile(existingUserProfile, updatedUserProfile);

                    try {
                        userProfileRepository.save(existingUserProfile);
                        log.log(Log.LogLevelEnum.LogDebug, source, "UserProfile updated successfully for ID: " + id);
                        return true;
                    } catch (Exception e) {
                        log.log(Log.LogLevelEnum.LogError, source, "Error updating UserProfile: " + e.getMessage());
                        return false;
                    }
                })
                .orElseGet(() -> {
                    log.log(Log.LogLevelEnum.LogError, source, "UserProfile not found with ID: " + id);
                    return false;
                });
    }

    private void updateExistingUserProfile(UserProfile existingUserProfile, UserProfile updatedUserProfile) {
        if (updatedUserProfile.getUsername() != null && !updatedUserProfile.getUsername().isEmpty()) {
            existingUserProfile.setUsername(updatedUserProfile.getUsername());
        }
        if (updatedUserProfile.getPassword() != null && !updatedUserProfile.getPassword().isEmpty()) {
            existingUserProfile.setPassword(updatedUserProfile.getPassword());
        }
        if (updatedUserProfile.getFirstName() != null && !updatedUserProfile.getFirstName().isEmpty()) {
            existingUserProfile.setFirstName(updatedUserProfile.getFirstName());
        }
        if (updatedUserProfile.getLastName() != null && !updatedUserProfile.getLastName().isEmpty()) {
            existingUserProfile.setLastName(updatedUserProfile.getLastName());
        }
        if (updatedUserProfile.getStatus() != null) {
            existingUserProfile.setStatus(updatedUserProfile.getStatus());
        }
        if (updatedUserProfile.getVehicles() != null) {
            existingUserProfile.setVehicles(updatedUserProfile.getVehicles());
        }
        //additional methods later

    }
    public boolean deleteUserProfile(String username) {
        String source = this.getClass().getSimpleName() + "::deleteUserProfile";

        if (username == null || username.trim().isEmpty()) {
            log.log(Log.LogLevelEnum.LogError, source, "Username cannot be null or empty.");
            return false;
        }

        // Retrieve the user profile by username
        UserProfile userProfile = userProfileRepository.findByUsername(username)
                .orElse(null);

        if (userProfile == null) {
            log.log(Log.LogLevelEnum.LogError, source, "No user profile found with username: " + username);
            return false;
        }

        try {
            userProfileRepository.delete(userProfile);
            log.log(Log.LogLevelEnum.LogDebug, source, "User profile successfully deleted for username: " + username);
            return true;
        } catch (Exception e) {
            log.log(Log.LogLevelEnum.LogError, source, "Error occurred while deleting user profile: " + e.getMessage());
            return false;
        }
    }
    public List<UserProfile> getAllUserProfiles(){
        String source = this.getClass().getSimpleName() + "::getAllUserProfiles";
        log.log(Log.LogLevelEnum.LogDebug,source,"Getting all user profiles");
        try {
            return userProfileRepository.findAll();
        }catch (Exception e){
            log.log(Log.LogLevelEnum.LogError, source, "Error occurred while retrieving all user profiles: " + e.getMessage());
            return Collections.emptyList();
        }
    }
    public boolean addVehicle(UserProfile userProfile,Vehicle vehicle) {
        String source = this.getClass().getSimpleName() + "::addVehicle";

        if (vehicle == null) {
            log.log(Log.LogLevelEnum.LogError, source, "Vehicle cannot be null");
            return false;
        }

        if (vehicleRepository.findById(vehicle.getVehicleId()).isPresent()) {
            log.log(Log.LogLevelEnum.LogError, source, "Vehicle ID already exists");
            return false;
        }

        try {
            vehicle.setOwner(userProfile);
            userProfile.linkVehicleToUserProfile(vehicle);
            userProfileRepository.save(userProfile);
            vehicleRepository.save(vehicle);
            log.log(Log.LogLevelEnum.LogDebug, source, "Vehicle added successfully: " + vehicle.getVehicleId());
            return true;
        } catch (Exception e) {
            log.log(Log.LogLevelEnum.LogError, source, "Error adding Vehicle: " + e.getMessage());
            return false;
        }
    }
    public boolean setOwner(UserProfile userProfile ){
        String source = this.getClass().getSimpleName() + "::setOwner";
        return  true;
    }


}