package com.mechamate.service;

import com.mechamate.MechaMate;
import com.mechamate.dto.*;
import com.mechamate.entity.*;
import com.mechamate.repo.*;
import org.bson.types.ObjectId;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@Service
public class DatabaseAbstractLayer {
    private static final Logger logger = LoggerFactory.getLogger(MechaMate.class);

    @Autowired
    private  CacheManager cacheManager;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private UserProfileRepo userProfileRepo;

    @Autowired
    private SessionRepo sessionRepo;

    @Autowired
    private VehicleRepo vehicleRepo;

    @Autowired
    private ServiceRecordRepo serviceRecordRepo;

    @Autowired
    private MaintenanceRepo maintenanceRepo;

    @Autowired
    private PredictionModelRepo predictionModelRepo;


    public boolean isUserExists(String username) {
        return userProfileRepo.existsByUsername(username);
    }
    public boolean isEmailExists(String email) {
        return userProfileRepo.existsByEmail(email);
    }
    public boolean isRecoveryKeyExists(String key) {
        return userProfileRepo.existsByRecoveryKey(key);
    }

//    public UserProfile getUserProfileDTO(UserProfile userProfile) {
//        try {
//            if(userProfile == null) return null;
//            return modelMapper.map(userProfile, UserProfileDTO.class);
//        } catch (Exception e) {}
//        return null;
//    }

//    public UserProfile getUserProfile(UserProfileDTO userProfileDTO) {
//        try {
//            if(userProfileDTO == null) return null;
//            return modelMapper.map(userProfileDTO, UserProfile.class);
//        } catch (Exception e) {}
//        return null;
//    }


// user profiles

    public boolean addUserProfile(UserProfile userProfile) {
        try {
            if (userProfile == null) {
                logger.warn("Attempted to add a null UserProfile.");
                return false;
            }
            if (isUserExists(userProfile.getUsername())) {
                logger.info("Attempted to add a UserProfile for an existing user: {}. Operation aborted.", userProfile.getUsername());
                return false;
            }
            userProfileRepo.save(userProfile);
            // Optionally, add the user profile to cache if you expect immediate reads following creation
            cacheManager.putInUserProfileCache(userProfile.getUsername(), userProfile);
            logger.info("UserProfile added for user: {} and added to cache.", userProfile.getUsername());
            return true;
        } catch (Exception e) {
            logger.error("Error adding UserProfile for user: {}", userProfile.getUsername(), e);
            return false;
        }
    }

    public UserProfile getUserProfile(String username) {

        if (username == null || username.isEmpty()) {
            logger.warn("getUserProfile called with null or empty username");
            return null;
        }

        try {
            UserProfile cachedProfile = cacheManager.getFromUserProfileCache(username);
            if (cachedProfile != null) {
                logger.info("Retrieved UserProfile from cache for username: {}", username);
                return cachedProfile;
            }

            Optional<UserProfile> userProfileOptional = userProfileRepo.findByUsername(username);
            if (userProfileOptional.isPresent()) {
                UserProfile userProfile = userProfileOptional.get();
                cacheManager.putInUserProfileCache(username, userProfile);
                logger.info("Retrieved UserProfile from database and add to the cache by username: {}", username);
                return userProfile;
            } else {
                logger.info("UserProfile not found for username: {}", username);
                return null;
            }
        } catch (Exception e) {
            logger.error("exception occurred while retrieving UserProfile for username: {}. Exception: {}", username, e.toString());
            return null;
        }
    }

//    public UserProfile getUserProfileByEmail(String email) {
//        if (email == null || email.isEmpty()) {
//            logger.warn("getUserProfileByEmail called with null or empty email");
//            return null;
//        }
//
//        try {
//            // Attempt to retrieve the UserProfile from cache
//            UserProfile cachedProfile = cacheManager.getFromCache(email, UserProfile.class);
//            if (cachedProfile != null) {
//                logger.info("Retrieved UserProfile from cache for email: {}", email);
//                return cachedProfile;
//            }
//
//            // If not in cache, retrieve from database
//            Optional<UserProfile> userProfileOptional = userProfileRepo.findByEmail(email);
//            if (userProfileOptional.isPresent()) {
//                UserProfile userProfile = userProfileOptional.get();
//                // Cache the retrieved profile
//                cacheManager.putInCache(email, userProfile);
//                logger.info("Retrieved UserProfile from database and cached for email: {}", email);
//                return userProfile;
//            } else {
//                logger.info("UserProfile not found for email: {}", email);
//                return null;
//            }
//        } catch (Exception e) {
//            logger.error("Error retrieving UserProfile for email: {}", email, e);
//            return null;
//        }
//    }

//    public UserProfile getUserProfileByRecoveryKey(String key) {
//        if (key == null || key.trim().isEmpty()) {
//            logger.warn("getUserProfileByRecoveryKey called with null or empty key");
//            return null;
//        }
//
//        // Attempt to retrieve the UserProfile from cache
//        String cacheKey = "recoveryKey:" + key; // Ensure unique cache keys, especially if combining different caches
//        UserProfile cachedProfile = cacheManager.getFromCache(cacheKey, UserProfile.class);
//        if (cachedProfile != null) {
//            logger.info("Retrieved UserProfile from cache for recovery key: {}", key);
//            return cachedProfile;
//        }
//
//        try {
//            Optional<UserProfile> userProfileOptional = userProfileRepo.findByRecoveryKey(key);
//            if (userProfileOptional.isPresent()) {
//                UserProfile userProfile = userProfileOptional.get();
//                cacheManager.putInCache(cacheKey, userProfile); // Cache the retrieved profile
//                logger.info("Retrieved UserProfile from database and cached for recovery key: {}", key);
//                return userProfile;
//            } else {
//                logger.info("UserProfile not found for recovery key: {}", key);
//                return null;
//            }
//        } catch (Exception e) {
//            logger.error("Error retrieving UserProfile for recovery key: {}", key, e);
//            return null;
//        }
//    }

    public boolean updateUserProfile(UserProfile userProfile) {
        if (userProfile == null) {
            return false;
        }
        if (!isUserExists(userProfile.getUsername())) {
            return false;
        }
        try {
            userProfileRepo.save(userProfile);

            cacheManager.putInUserProfileCache(userProfile.getUsername(), userProfile);

            logger.info("Updated UserProfile in database and cache for username: {}", userProfile.getUsername());
            return true;
        } catch (Exception e) {
            logger.error("Error updating UserProfile for username: {}", userProfile.getUsername(), e);
        }
        return false;
    }


    public boolean deleteUserProfile(UserProfile userProfile) {
        try {
            if (userProfile == null) {
                logger.warn("Attempted to delete a null UserProfile.");
                return false;
            }
            if (!isUserExists(userProfile.getUsername())) {
                logger.info("Attempted to delete a UserProfile for a non-existing user: {}. No action needed.", userProfile.getUsername());
                return true;
            }
            userProfileRepo.delete(userProfile);
            cacheManager.deleteFromUserProfileCache(userProfile.getUsername());
            logger.info("UserProfile deleted and cache invalidated for user: {}", userProfile.getUsername());
            return true;
        } catch (Exception e) {
            logger.error("error deleting UserProfile for user: {}", userProfile.getUsername(), e);
            return false;
        }
    }

//sessions

    public boolean isSessionExists(String sessionKey) {
        return sessionRepo.existsBySessionKey(sessionKey);
    }

    public boolean addSession(Session session) {
        try {
            if(session == null) return false;
            if(isSessionExists(session.getSessionKey())) return false;
            sessionRepo.save(session);
            return true;
        } catch (Exception e) {}
        return false;
    }

    public Session getSession(String sessionKey) {
        try {
            Optional<Session> session = sessionRepo.findBySessionKey(sessionKey);
            return session.orElse(null);
        } catch (Exception e) {}
        return null;
    }

    public boolean updateSession(Session session) {
        try {
            if(session == null) return false;
            if(!isSessionExists(session.getSessionKey())) return false;
            sessionRepo.save(session);
            return true;
        } catch (Exception e) {}
        return false;
    }

    public boolean deleteSession(Session session) {
        try {
            if(session == null) return false;
            if(!isSessionExists(session.getSessionKey())) return true;
            sessionRepo.delete(session);
            return true;
        } catch (Exception e) {}
        return false;
    }

    //vehicles
    public boolean isVehicleExists(String regNo) {
        return vehicleRepo.existsByRegNo(regNo);
    }
    public boolean isVehicleExistsByOwner(ObjectId owner) {
        return vehicleRepo.existsByOwner(owner);
    }


    public boolean addVehicle(Vehicle vehicle) {
        try {
            if(vehicle == null) return false;
            if(vehicle.get_id() != null && isVehicleExists(vehicle.getRegNo())) return false;
            vehicleRepo.save(vehicle);
            return true;
        } catch (Exception e) {}
        return false;
    }


    public boolean updateVehicle(Vehicle vehicle) {
        try {
            if(vehicle == null) return false;
            if(!isVehicleExists(vehicle.getRegNo())) return false;
            vehicleRepo.save(vehicle);
            return true;
        } catch (Exception e) {}
        return false;
    }


    public boolean deleteVehicle(Vehicle vehicle) {
        try {
            if(vehicle == null) return false;
            if(!isVehicleExists(vehicle.getRegNo())) return true;
            vehicleRepo.delete(vehicle);
            return true;
        } catch (Exception e) {}
        return false;
    }

    public List<Vehicle> getVehicles(ObjectId owner) {
        try {
            List<Vehicle> vehicles = vehicleRepo.findByOwner(owner);
            return (vehicles == null || vehicles.isEmpty()) ? new ArrayList<>() : vehicles;
        } catch (Exception e) {}
        return new ArrayList<>();
    }

    public Vehicle getVehicle(String regNo) {
        try {
            Optional<Vehicle> vehicle = vehicleRepo.findByRegNo(regNo);
            return vehicle.orElse(null);
        } catch (Exception e) {}
        return null;
    }

    public List<VehicleDTO> getVehicleDTOs(ObjectId owner) {
        List<Vehicle> vehicles = vehicleRepo.findByOwner(owner);
        List<VehicleDTO> vehicleDTOS = new ArrayList<>();
        try {
            if(vehicles == null || vehicles.isEmpty()) return vehicleDTOS;
            for(Vehicle v: vehicles) {
                VehicleDTO vDTO = new VehicleDTO(v.getRegNo(), v.getModel(), v.getFuelType(), new ArrayList<>());
                vehicleDTOS.add(vDTO);
            }
        } catch (Exception e) {}
        return vehicleDTOS;
    }



//service records

    public boolean isServiceRecordExists(ObjectId serviceRecordId) {
        try {
            return serviceRecordRepo.existsById(serviceRecordId);
        } catch (Exception e) {}
        return false;
    }



    public boolean isServiceRecordExistsByVehicle(String vehicleRegNo) {
        return serviceRecordRepo.existsByVehicle(vehicleRegNo);
    }


    public boolean addServiceRecord(ServiceRecord serviceRecord) {
        try {
            if(serviceRecord == null) return false;
            if(serviceRecord.get_id() != null && isServiceRecordExists(serviceRecord.get_id())) return false;
            serviceRecordRepo.save(serviceRecord);
            return true;
        } catch (Exception e) {}
        return false;
    }


    public boolean updateServiceRecord(ServiceRecord serviceRecord) {
        try {
            if(serviceRecord == null) return false;
            if(!isServiceRecordExists(serviceRecord.get_id())) return false;
            serviceRecordRepo.save(serviceRecord);
            return true;
        } catch (Exception e) {}
        return false;
    }

    public boolean deleteServiceRecord(ServiceRecord serviceRecord) {
        try {
            if(serviceRecord == null) return false;
            if(!isServiceRecordExists(serviceRecord.get_id())) return true;
            serviceRecordRepo.delete(serviceRecord);
            return true;
        } catch (Exception e) {}
        return false;
    }

    public ServiceRecord getServiceRecord(ObjectId recordId) {
        try {
            if(recordId == null) return null;
            Optional<ServiceRecord> serviceRecord = serviceRecordRepo.findById(recordId);
            return serviceRecord.orElse(null);
        } catch (Exception e) {}
        return null;
    }

    public List<ServiceRecord> getServiceRecords(String vehicleRegNo) {
        try {
            List<ServiceRecord> serviceRecords = serviceRecordRepo.findByVehicle(vehicleRegNo);
            return (serviceRecords == null || serviceRecords.isEmpty()) ? new ArrayList<>() : serviceRecords;
        } catch (Exception e) {}
        return new ArrayList<>();
    }


    public List<ServiceRecordDTO> getServiceRecordDTOs(String vehicleRegNo) {
        List<ServiceRecord> serviceRecords = serviceRecordRepo.findByVehicle(vehicleRegNo);
        List<ServiceRecordDTO> serviceRecordDTOS = new ArrayList<>();
        try {
            if(serviceRecords == null || serviceRecords.isEmpty()) return serviceRecordDTOS;
            for(ServiceRecord s: serviceRecords) {
                ServiceRecordDTO sDTO = new ServiceRecordDTO(s.get_id().toHexString(), s.getDescription(), s.getDate(), s.getMileage(), new ArrayList<>());
                serviceRecordDTOS.add(sDTO);
            }
        } catch (Exception e) {}
        return serviceRecordDTOS;
    }



//maintaince

    public boolean isMaintenanceExists(Maintenance maintenance) {
        return maintenanceRepo.existsById(maintenance.get_id());
    }

    public boolean addMaintenance(Maintenance maintenance) {
        try {
            if(maintenance == null) return false;
            if(maintenance.get_id() != null && isMaintenanceExists(maintenance)) return false;
            maintenanceRepo.save(maintenance);
            return true;
        } catch (Exception e) {}
        return false;
    }

    public List<Maintenance> getMaintenanceList() {
        try {
            List<Maintenance> maintenances = maintenanceRepo.findAll();
            return maintenances.isEmpty() ? new ArrayList<>() : maintenances;
        } catch (Exception e) {}
        return new ArrayList<>();
    }

    public List<Maintenance> getMaintenanceListByPredictionModel(PredictionModel predictionModel) {
        try {
            if(predictionModel == null) return new ArrayList<>();
            List<Maintenance> maintenances = maintenanceRepo.findByPredictionModel(predictionModel.get_id());
            return maintenances.isEmpty() ? new ArrayList<>() : maintenances;
        } catch (Exception e) {}
        return new ArrayList<>();
    }

    public boolean deleteMaintenance(Maintenance maintenance) {
        try {
            if(maintenance == null) return false;
            if(!isMaintenanceExists(maintenance)) return true;
            maintenanceRepo.delete(maintenance);
            return true;
        } catch (Exception e) {}
        return false;
    }



//prediction model

    public boolean isPredictionModelExists(PredictionModel predictionModel) {
        return predictionModelRepo.existsById(predictionModel.get_id());
    }

    public boolean addPredictionModel(PredictionModel predictionModel) {
        try {
            if(predictionModel == null) return false;
            if(predictionModel.get_id() != null && isPredictionModelExists(predictionModel)) return false;
            predictionModelRepo.save(predictionModel);
            return true;
        } catch (Exception e) {}
        return false;
    }

    public boolean deletePredictionModel(PredictionModel predictionModel) {
        try {
            if(predictionModel == null) return false;
            if(!isPredictionModelExists(predictionModel)) return true;
            predictionModelRepo.delete(predictionModel);
            return true;
        } catch (Exception e) {}
        return false;
    }


    public List<PredictionModel> getPredictionModelList() {
        try {
            List<PredictionModel> predictionModels = predictionModelRepo.findAll();
            return predictionModels.isEmpty() ? new ArrayList<>() : predictionModels;
        } catch (Exception e) {}
        return new ArrayList<>();
    }

    public List<PredictionModel> getPredictionModelListByMaintenance(Maintenance.MaintenanceType maintenanceType) {
        try {
            List<PredictionModel> predictionModels = predictionModelRepo.findByMaintenanceType(maintenanceType);
            return predictionModels.isEmpty() ? new ArrayList<>() : predictionModels;
        } catch (Exception e) {}
        return new ArrayList<>();
    }





}
