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
    private static final Logger logger = LoggerFactory.getLogger(DatabaseAbstractLayer.class);

    @Autowired
    private CacheManager cacheManager;

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

    @Autowired
    private TokenRepo tokenRepo;

    @Autowired
    private TrackingInfoRepo trackingInfoRepo;

    @Autowired
    private QrLinkRepo qrLinkRepo;

    @Autowired
    private SimulatedRepo simulatedRepo;

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

        /*
    USERPROFILE SECTION
        */

    public boolean isUserExists(String username) {
        boolean exists = false;
        if (username == null || username.isEmpty()) {
            logger.warn("isUserExists called with null or empty username");
            return exists;
        }
        try {
            if (cacheManager.isUserExistInCache(username)) {
                logger.info("found userprofile in cache for user name for {}", username);
                exists = true;
            } else {
                exists = userProfileRepo.existsByUsername(username);
                logger.info("user existence for {},", username);
            }
        } catch (Exception e) {
            logger.error("exception occurred while checking UserProfile for username: {}. Exception: {}", username, e.toString());
        }
        return exists;
    }

    public boolean addUserProfile(UserProfile userProfile) {
        try {
            if (userProfile == null) {
                logger.warn("try to attempted to add a null UserProfile.");
                return false;
            }
            if (isUserExists(userProfile.getUsername())) {
                logger.info("Attempted to add a UserProfile for an existing user: {}. Operation aborted", userProfile.getUsername());
                return false;
            }
            userProfileRepo.save(userProfile);
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
            logger.error("exception occurred while retrieving UserProfile for username: {} Exception: {}", username, e.toString());
            return null;
        }
    }

    public boolean updateUserProfile(UserProfile userProfile) {
        if (userProfile == null) {
            return false;
        }
        if (!isUserExists(userProfile.getUsername())) {
            return false;
        }
        try {
            cacheManager.deleteFromUserProfileCache(userProfile.getUsername());
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
            cacheManager.deleteFromUserProfileCache(userProfile.getUsername());

            userProfileRepo.delete(userProfile);

            logger.info("UserProfile deleted and cache invalidated for user: {}", userProfile.getUsername());
            return true;
        } catch (Exception e) {
            logger.error("error deleting UserProfile for user: {}", userProfile.getUsername(), e);
            return false;
        }
    }

    //    public boolean isEmailExists(String email) {
//        return userProfileRepo.existsByEmail(email);
//    }
    public boolean isEmailExists(String email) {
        boolean exists = false;
        if (email == null || email.isEmpty()) {
            logger.warn("isEmailExists called with null or empty email");
            return exists;
        }
        try {
            exists = userProfileRepo.existsByEmail(email);
            logger.info("retrieve email {} ", email);
        } catch (Exception e) {
            logger.error("exception occurred while checking email: {}. Exception: {}", email, e.toString());
        }
        return exists;
    }

//    public boolean isRecoveryKeyExists(String key) {
//        return userProfileRepo.existsByRecoveryKey(key);
//    }

    public boolean isRecoveryKeyExists(String key) {
        boolean exists = false;
        if (key == null || key.isEmpty()) {
            logger.warn("isRecoveryKeyExists called with null or empty RecoveryKey");
            return exists;
        }
        try {
            exists = userProfileRepo.existsByRecoveryKey(key);
            logger.info("retrieve RecoveryKey {} ", key);
        } catch (Exception e) {
            logger.error("exception occurred while checking RecoveryKey: {}. Exception: {}", key, e.toString());
        }
        return exists;
    }

    public UserProfile getUserProfileByEmail(String email) {
        if (email == null || email.isEmpty()) {
            logger.warn("getUserProfileByEmail called with null or empty email");
            return null;
        }

        try {
            Optional<UserProfile> userProfileOptional = userProfileRepo.findByEmail(email);
            if (userProfileOptional.isPresent()) {
                UserProfile userProfile = userProfileOptional.get();
                logger.info("retrieved UserProfile from database for email: {}", email);
                return userProfile;
            } else {
                logger.info("userProfile not found for email: {}", email);
                return null;
            }
        } catch (Exception e) {
            logger.error("error retrieving UserProfile for email: {}", email, e);
            return null;
        }
    }

    public UserProfile getUserProfileByRecoveryKey(String key) {
        if (key == null || key.trim().isEmpty()) {
            logger.warn("getUserProfileByRecoveryKey called with null or empty key");
            return null;
        }

        try {
            Optional<UserProfile> userProfileOptional = userProfileRepo.findByRecoveryKey(key);
            if (userProfileOptional.isPresent()) {
                UserProfile userProfile = userProfileOptional.get();
                logger.info("retrieved UserProfile from database for recovery key: {}", key);
                return userProfile;
            } else {
                logger.info("userProfile not found for recovery key: {}", key);
                return null;
            }
        } catch (Exception e) {
            logger.error("error retrieving UserProfile for recovery key: {}", key, e);
            return null;
        }
    }


    public List<UserProfile> getServiceAccounts() {
        try {
            List<UserProfile> userProfiles = userProfileRepo.findByServiceAccount(true);
            if (userProfiles != null && !userProfiles.isEmpty()) {
                return userProfiles;
            } else {
                return new ArrayList<UserProfile>();
            }
        } catch (Exception e) {
            return new ArrayList<UserProfile>();
        }
    }


    /*
    SESSION SECTION
    */

    public boolean isSessionExists(String sessionKey) {
        logger.info("Check session: {}", sessionKey);

        if (sessionKey == null || sessionKey.trim().isEmpty()) {
            logger.warn("Session key empty. Assume no session");
            return false;
        }

        try {
            if (cacheManager.isSessionExistInCache(sessionKey)) {
                logger.info("Session found in cache for key: {}", sessionKey);
                return true;
            }
            boolean exists = sessionRepo.existsBySessionKey(sessionKey);
            logger.info("Session checked in database for key: {}, exists: {}", sessionKey, exists);
            return exists;
        } catch (Exception e) {
            logger.error("Error checking session for key: {}", sessionKey, e);
            return false;
        }
    }

    public boolean addSession(Session session) {
        logger.info("Adding session");

        if (session == null || session.getSessionKey() == null || session.getSessionKey().trim().isEmpty()) {
            logger.warn("Session or session key is null/empty");
            return false;
        }


        if (isSessionExists(session.getSessionKey())) {
            logger.info("Session already exists cannot add");
            return false;
        }

        try {
            sessionRepo.save(session);
            cacheManager.putInSessionCache(session.getSessionKey(), session);
            logger.info("Session added and cached for key: {}", session.getSessionKey());
            return true;
        } catch (Exception e) {
            logger.error("Error adding session", e);
            return false;
        }
    }

    public Session getSession(String sessionKey) {
        logger.info("Getting session");

        if (sessionKey == null || sessionKey.trim().isEmpty()) {
            logger.warn("Session key is null or empty.return null");
            return null;
        }
        Session sessionCache = cacheManager.getFromSessionCache(sessionKey);
        if (sessionCache != null) {
            logger.info("session retrieved from cache key {}", sessionKey);
            return sessionCache;
        }

        try {
            Optional<Session> sessionOptional = sessionRepo.findBySessionKey(sessionKey);
            if (sessionOptional.isPresent()) {

                Session session = sessionOptional.get();
                cacheManager.putInSessionCache(sessionKey, session);
                logger.info("session retrieved from db and then cache for key {}", sessionKey);
                return session;
            } else {
                logger.info("session not found in database for key {}", sessionKey);
                return null;
            }
        } catch (Exception e) {
            logger.error("error retrieving session for key {}", sessionKey, e);
            return null;
        }
    }


    public boolean updateSession(Session session) {
        logger.info("updating session");

        if (session == null || session.getSessionKey() == null || session.getSessionKey().trim().isEmpty()) {
            logger.warn("session or session key is null or empty");
            return false;
        }

        if (!isSessionExists(session.getSessionKey())) {
            logger.warn("session does not exist not updated");
            return false;
        }

        try {
            cacheManager.deleteFromSessionCache(session.getSessionKey());

            sessionRepo.save(session);

            cacheManager.putInSessionCache(session.getSessionKey(),session);
            logger.info("session updated in db and cache for key {}",session.getSessionKey());
            return true;
        } catch (Exception e) {
            logger.error("error on updating session ", e);
            return false;
        }
    }

    public boolean deleteSession(Session session) {
        logger.info("deleting session");

        if (session == null || session.getSessionKey() == null || session.getSessionKey().trim().isEmpty()) {
            logger.warn("session or session key null or empty");
            return false;
        }

        if (!isSessionExists(session.getSessionKey())) {
            logger.info("session does not exist and consider as deleted");
            return true;
        }

        try {
            cacheManager.deleteFromSessionCache(session.getSessionKey());
            sessionRepo.delete(session);
            logger.info("session deleted from database and cache for key {}",session.getSessionKey());
            return true;
        } catch (Exception e) {
            logger.error("delete session failed", e);
            return false;
        }
    }


        /*
    Vehicle SECTION
    */


    public boolean isVehicleExists(String regNo) {
        logger.info("checking if vehicle exists");

        if (regNo == null || regNo.trim().isEmpty()) {
            logger.warn("registration number is null or blank returning false");
            return false;
        }

        try {
            boolean exists = vehicleRepo.existsByRegNo(regNo);
            logger.info("vehicle existence check completed");
            return exists;
        } catch (Exception e) {
            logger.error("vehicle existence check failed", e);
            return false;
        }
    }

    public boolean isVehicleExistsByOwner(ObjectId owner) {
        logger.info("checking if vehicle exists by owner");

        if (owner == null) {
            logger.warn("owner id is null returning false");
            return false;
        }

        try {
            boolean exists = vehicleRepo.existsByOwner(owner);
            logger.info("vehicle existence by owner check completed");
            return exists;
        } catch (Exception e) {
            logger.error("vehicle existence by owner check failed", e);
            return false;
        }
    }


    public boolean addVehicle(Vehicle vehicle) {
        logger.info("adding vehicle");

        if (vehicle == null) {
            logger.warn("vehicle is null not added");
            return false;
        }

        if (vehicle.get_id() != null && isVehicleExists(vehicle.getRegNo())) {
            logger.warn("vehicle already exists not added");
            return false;
        }

        try {
            vehicleRepo.save(vehicle);
            logger.info("vehicle added");
            return true;
        } catch (Exception e) {
            logger.error("add vehicle failed", e);
            return false;
        }
    }

    public boolean updateVehicle(Vehicle vehicle) {
        logger.info("updating vehicle");

        if (vehicle == null) {
            logger.warn("vehicle is null not updated");
            return false;
        }

        if (!isVehicleExists(vehicle.getRegNo())) {
            logger.warn("vehicle does not exist not updated");
            return false;
        }

        try {
            vehicleRepo.save(vehicle);
            logger.info("vehicle updated");
            return true;
        } catch (Exception e) {
            logger.error("update vehicle failed", e);
            return false;
        }
    }


    public boolean deleteVehicle(Vehicle vehicle) {
        logger.info("deleting vehicle");

        if (vehicle == null) {
            logger.warn("vehicle is null not deleted");
            return false;
        }

        if (!isVehicleExists(vehicle.getRegNo())) {
            logger.info("vehicle does not exist considered deleted");
            return true;
        }

        try {
            vehicleRepo.delete(vehicle);
            logger.info("vehicle deleted");
            return true;
        } catch (Exception e) {
            logger.error("delete vehicle failed", e);
            return false;
        }
    }

    public List<Vehicle> getVehicles(ObjectId owner) {
        logger.info("retrieving vehicles");

        if (owner == null) {
            logger.warn("owner is null returning empty list");
            return new ArrayList<>();
        }

        try {
            List<Vehicle> vehicles = vehicleRepo.findByOwner(owner);
            if (vehicles == null || vehicles.isEmpty()) {
                logger.info("no vehicles found");
                return new ArrayList<>();
            }

            logger.info("vehicles retrieved");
            return vehicles;
        } catch (Exception e) {
            logger.error("retrieve vehicles failed", e);
            return new ArrayList<>();
        }
    }

    public List<Vehicle> getAllVehicles() {
        logger.info("retrieving vehicles");
        try {
            List<Vehicle> vehicles = vehicleRepo.findAll();
            if (vehicles == null || vehicles.isEmpty()) {
                logger.info("no vehicles found");
                return new ArrayList<>();
            }

            logger.info("vehicles retrieved");
            return vehicles;
        } catch (Exception e) {
            logger.error("retrieve vehicles failed", e);
            return new ArrayList<>();
        }
    }


    public Vehicle getVehicle(String regNo) {
        logger.info("retrieving vehicle");

        if (regNo == null || regNo.trim().isEmpty()) {
            logger.warn("registration number is null or blank returning null");
            return null;
        }

        try {
            Optional<Vehicle> vehicle = vehicleRepo.findByRegNo(regNo);
            if (!vehicle.isPresent()) {
                logger.info("vehicle not found");
                return null;
            }

            logger.info("vehicle retrieved");
            return vehicle.get();
        } catch (Exception e) {
            logger.error("retrieve vehicle failed", e);
            return null;
        }
    }

    public List<VehicleDTO> getVehicleDTOs(ObjectId owner) {
        logger.info("retrieving vehicle dtos");

        List<VehicleDTO> vehicleDTOS = new ArrayList<>();
        if (owner == null) {
            logger.warn("owner is null returning empty list");
            return vehicleDTOS;
        }

        List<Vehicle> vehicles;
        try {
            vehicles = vehicleRepo.findByOwner(owner);
            if (vehicles == null || vehicles.isEmpty()) {
                logger.info("no vehicles found");
                return vehicleDTOS;
            }

            for (Vehicle v : vehicles) {
                VehicleDTO vDTO = new VehicleDTO(
                        v.getRegNo(),
                        v.getVehicleType(),
                        v.getFuelType(),
                        v.getVehicleMake(),
                        v.getVehicleModel(),
                        v.getInsNo(),
                        v.getInsExpDate(),
                        v.getRegExpDate(),
                        getServiceRecordDTOs(v.getRegNo()),
                        v.getCurrentMileage(),
                        v.getObd2DeviceID());
                vehicleDTOS.add(vDTO);
            }

            logger.info("vehicle dtos retrieved");
        } catch (Exception e) {
            logger.error("retrieve vehicle dtos failed", e);
        }
        return vehicleDTOS;
    }


    public VehicleDTO getVehicleDTOEx(String vehicleRegNo) {
        try {
            Vehicle vehicle = getVehicle(vehicleRegNo);
            if (vehicle == null) return null;
            return new VehicleDTO(
                    vehicle.getRegNo(),
                    vehicle.getVehicleType(),
                    vehicle.getFuelType(),
                    vehicle.getVehicleMake(),
                    vehicle.getVehicleModel(),
                    vehicle.getInsNo(),
                    vehicle.getInsExpDate(),
                    vehicle.getRegExpDate(),
                    getServiceRecordDTOs(vehicle.getRegNo()),
                    vehicle.getCurrentMileage(),
                    vehicle.getObd2DeviceID());
        } catch (Exception e) {}
        return null;
    }



    /*
    SERVICE RECORD SECTION
    */


    public boolean isServiceRecordExists(ObjectId serviceRecordId) {
        logger.info("checking if service record exists");

        if (serviceRecordId == null) {
            logger.warn("service record id is null returning false");
            return false;
        }

        try {
            boolean exists = serviceRecordRepo.existsById(serviceRecordId);
            logger.info("service record existence check completed");
            return exists;
        } catch (Exception e) {
            logger.error("service record existence check failed", e);
            return false;
        }
    }


    public boolean isServiceRecordExistsByVehicle(String vehicleRegNo) {
        logger.info("checking if service record exists by vehicle registration number");

        if (vehicleRegNo == null || vehicleRegNo.trim().isEmpty()) {
            logger.warn("vehicle registration number is null or blank returning false");
            return false;
        }

        try {
            boolean exists = serviceRecordRepo.existsByVehicle(vehicleRegNo);
            logger.info("service record existence check by vehicle completed");
            return exists;
        } catch (Exception e) {
            logger.error("service record existence check by vehicle failed", e);
            return false;
        }
    }

    public boolean addServiceRecord(ServiceRecord serviceRecord) {
        logger.info("adding service record");

        if (serviceRecord == null) {
            logger.warn("service record is null not added");
            return false;
        }

        if (serviceRecord.get_id() != null && isServiceRecordExists(serviceRecord.get_id())) {
            logger.warn("service record already exists not added");
            return false;
        }

        try {
            serviceRecordRepo.save(serviceRecord);
            logger.info("service record added");
            return true;
        } catch (Exception e) {
            logger.error("add service record failed", e);
            return false;
        }
    }


    public boolean updateServiceRecord(ServiceRecord serviceRecord) {
        logger.info("updating service record");

        if (serviceRecord == null) {
            logger.warn("service record is null not updated");
            return false;
        }

        if (serviceRecord.get_id() == null || !isServiceRecordExists(serviceRecord.get_id())) {
            logger.warn("service record does not exist or id is null not updated");
            return false;
        }

        try {
            serviceRecordRepo.save(serviceRecord);
            logger.info("service record updated");
            return true;
        } catch (Exception e) {
            logger.error("update service record failed", e);
            return false;
        }
    }

    public boolean deleteServiceRecord(ServiceRecord serviceRecord) {
        logger.info("deleting service record");

        if (serviceRecord == null) {
            logger.warn("service record is null not deleted");
            return false;
        }


        if (serviceRecord.get_id() == null || !isServiceRecordExists(serviceRecord.get_id())) {
            logger.info("service record does not exist or id is null considered deleted");
            return true;
        }

        try {
            serviceRecordRepo.delete(serviceRecord);
            logger.info("service record deleted");
            return true;
        } catch (Exception e) {
            logger.error("delete service record failed", e);
            return false;
        }
    }

    public ServiceRecord getServiceRecord(ObjectId recordId) {
        logger.info("retrieving service record");

        if (recordId == null) {
            logger.warn("record id is null returning null");
            return null;
        }

        try {
            Optional<ServiceRecord> serviceRecord = serviceRecordRepo.findById(recordId);
            if (!serviceRecord.isPresent()) {
                logger.info("service record not found");
            } else {
                logger.info("service record retrieved");
            }
            return serviceRecord.orElse(null);
        } catch (Exception e) {
            logger.error("retrieve service record failed", e);
            return null;
        }
    }

    public List<ServiceRecord> getServiceRecords(String vehicleRegNo) {
        logger.info("retrieving service records for vehicle");

        if (vehicleRegNo == null || vehicleRegNo.trim().isEmpty()) {
            logger.warn("vehicle registration number is null or blank returning empty list");
            return new ArrayList<>();
        }

        try {
            List<ServiceRecord> serviceRecords = serviceRecordRepo.findByVehicle(vehicleRegNo);
            if (serviceRecords == null || serviceRecords.isEmpty()) {
                logger.info("no service records found");
                return new ArrayList<>();
            } else {
                logger.info("service records retrieved");
                return serviceRecords;
            }
        } catch (Exception e) {
            logger.error("retrieve service records failed", e);
            return new ArrayList<>();
        }
    }


    public List<ServiceRecordDTO> getServiceRecordDTOs(String vehicleRegNo) {
        logger.info("retrieving service record DTOs for vehicle");

        List<ServiceRecordDTO> serviceRecordDTOS = new ArrayList<>();
        if (vehicleRegNo == null || vehicleRegNo.trim().isEmpty()) {
            logger.warn("vehicle registration number is null or blank returning empty list");
            return serviceRecordDTOS;
        }

        List<ServiceRecord> serviceRecords;
        try {
            serviceRecords = serviceRecordRepo.findByVehicle(vehicleRegNo);
            if (serviceRecords == null || serviceRecords.isEmpty()) {
                logger.info("no service records found");
                return serviceRecordDTOS;
            }

            for (ServiceRecord s : serviceRecords) {
                ServiceRecordDTO sDTO = new ServiceRecordDTO(s.get_id().toHexString(), s.getDescription(), s.getDate(), s.getMileage(),
                        s.getServices());

                serviceRecordDTOS.add(sDTO);
            }

            logger.info("service record DTOs retrieved");
        } catch (Exception e) {
            logger.error("retrieve service record DTOs failed", e);
            return new ArrayList<>();
        }
        return serviceRecordDTOS;
    }



        /*
    MAINTENANCE SECTION
    */


    public boolean isMaintenanceExists(Maintenance maintenance) {
        logger.info("checking if maintenance exists");

        if (maintenance == null || maintenance.get_id() == null) {
            logger.warn("maintenance or maintenance id is null returning false");
            return false;
        }

        try {
            boolean exists = maintenanceRepo.existsById(maintenance.get_id());
            logger.info("maintenance existence check completed");
            return exists;
        } catch (Exception e) {
            logger.error("maintenance existence check failed", e);
            return false;
        }
    }

    public boolean addMaintenance(Maintenance maintenance) {
        logger.info("adding maintenance");

        if (maintenance == null) {
            logger.warn("maintenance is null not added");
            return false;
        }


        if (maintenance.get_id() != null && isMaintenanceExists(maintenance)) {
            logger.warn("maintenance already exists not added");
            return false;
        }

        try {
            maintenanceRepo.save(maintenance);
            logger.info("maintenance added");
            return true;
        } catch (Exception e) {
            logger.error("add maintenance failed", e);
            return false;
        }
    }

    public List<Maintenance> getMaintenanceList() {
        logger.info("retrieving all maintenance records");

        try {
            List<Maintenance> maintenances = maintenanceRepo.findAll();
            if (maintenances == null || maintenances.isEmpty()) {
                logger.info("no maintenance records found");
                return new ArrayList<>();
            } else {
                logger.info("maintenance records retrieved");
                return maintenances;
            }
        } catch (Exception e) {
            logger.error("retrieve maintenance records failed", e);
            return new ArrayList<>();
        }
    }

    public List<Maintenance> getMaintenanceListByPredictionModel(PredictionModel predictionModel) {
        logger.info("retrieving maintenance records by prediction model");

        if (predictionModel == null || predictionModel.get_id() == null) {
            logger.warn("prediction model or model id is null returning empty list");
            return new ArrayList<>();
        }

        try {
            List<Maintenance> maintenances = maintenanceRepo.findByPredictionModel(predictionModel.get_id());
            if (maintenances == null || maintenances.isEmpty()) {
                logger.info("no maintenance records found for given prediction model");
                return new ArrayList<>();
            } else {
                logger.info("maintenance records retrieved for given prediction model");
                return maintenances;
            }
        } catch (Exception e) {
            logger.error("retrieve maintenance records by prediction model failed", e);
            return new ArrayList<>();
        }
    }

    public boolean deleteMaintenance(Maintenance maintenance) {
        logger.info("deleting maintenance");

        if (maintenance == null) {
            logger.warn("maintenance is null not deleted");
            return false;
        }


        if (!isMaintenanceExists(maintenance)) {
            logger.info("maintenance does not exist considered deleted");
            return true;
        }

        try {
            maintenanceRepo.delete(maintenance);
            logger.info("maintenance deleted");
            return true;
        } catch (Exception e) {
            logger.error("delete maintenance failed", e);
            return false;
        }
    }



    /*
    PREDICTION MODEL SECTION
    */

    public boolean isPredictionModelExists(PredictionModel predictionModel) {
        logger.info("checking if prediction model exists");

        if (predictionModel == null || predictionModel.get_id() == null) {
            logger.warn("prediction model or model id is null returning false");
            return false;
        }

        try {
            boolean exists = predictionModelRepo.existsById(predictionModel.get_id());
            logger.info("prediction model existence check completed");
            return exists;
        } catch (Exception e) {
            logger.error("prediction model existence check failed", e);
            return false;
        }
    }

    public boolean addPredictionModel(PredictionModel predictionModel) {
        logger.info("adding prediction model");

        if (predictionModel == null) {
            logger.warn("prediction model is null not added");
            return false;
        }

        if (predictionModel.get_id() != null && isPredictionModelExists(predictionModel)) {
            logger.warn("prediction model already exists not added");
            return false;
        }

        try {
            predictionModelRepo.save(predictionModel);
            logger.info("prediction model added");
            return true;
        } catch (Exception e) {
            logger.error("add prediction model failed", e);
            return false;
        }
    }

    public boolean deletePredictionModel(PredictionModel predictionModel) {
        logger.info("deleting prediction model");

        if (predictionModel == null) {
            logger.warn("prediction model is null not deleted");
            return false;
        }

        if (!isPredictionModelExists(predictionModel)) {
            logger.info("prediction model does not exist considered deleted");
            return true;
        }

        try {
            predictionModelRepo.delete(predictionModel);
            logger.info("prediction model deleted");
            return true;
        } catch (Exception e) {
            logger.error("delete prediction model failed", e);
            return false;
        }
    }


    public List<PredictionModel> getPredictionModelList() {
        logger.info("retrieving all prediction models");

        try {
            List<PredictionModel> predictionModels = predictionModelRepo.findAll();
            if (predictionModels == null || predictionModels.isEmpty()) {
                logger.info("no prediction models found");
                return new ArrayList<>();
            } else {
                logger.info("prediction models retrieved");
                return predictionModels;
            }
        } catch (Exception e) {
            logger.error("retrieve prediction models failed", e);
            return new ArrayList<>();
        }
    }

    public List<PredictionModel> getPredictionModelListByMaintenance(Maintenance.MaintenanceType
                                                                             maintenanceType) {
        logger.info("retrieving prediction models by maintenance type");

        if (maintenanceType == null) {
            logger.warn("maintenance type is null returning empty list");
            return new ArrayList<>();
        }

        try {
            List<PredictionModel> predictionModels = predictionModelRepo.findByMaintenanceType(maintenanceType);
            if (predictionModels == null || predictionModels.isEmpty()) {
                logger.info("no prediction models found for the given maintenance type");
                return new ArrayList<>();
            } else {
                logger.info("prediction models retrieved for the given maintenance type");
                return predictionModels;
            }
        } catch (Exception e) {
            logger.error("retrieve prediction models by maintenance type failed", e);
            return new ArrayList<>();
        }
    }


    /*
    TOKEN SECTION
    */


    public boolean isTokenExists(String tokenName) {
        logger.info("checking if token exists");

        if (tokenName == null || tokenName.trim().isEmpty()) {
            logger.warn("token name is null or empty returning false");
            return false;
        }

        try {
            boolean exists = tokenRepo.existsByTokenName(tokenName);
            logger.info("token existence check completed");
            return exists;
        } catch (Exception e) {
            logger.error("token existence check failed", e);
            return false;
        }
    }


    public boolean addToken(Token token) {
        logger.info("adding token");

        if (token == null) {
            logger.warn("token is null not added");
            return false;
        }

        if (token.get_id() != null && isTokenExists(token.getTokenName())) {
            logger.warn("token already exists not added");
            return false;
        }

        try {
            tokenRepo.save(token);
            logger.info("token added");
            return true;
        } catch (Exception e) {
            logger.error("add token failed", e);
            return false;
        }
    }


    public boolean updateToken(Token token) {
        logger.info("updating token");

        if (token == null) {
            logger.warn("token is null not updated");
            return false;
        }

        if (!isTokenExists(token.getTokenName())) {
            logger.warn("token does not exist not updated");
            return false;
        }

        try {
            tokenRepo.save(token);
            logger.info("token updated");
            return true;
        } catch (Exception e) {
            logger.error("update token failed", e);
            return false;
        }
    }

    public boolean deleteToken(Token token) {
        logger.info("deleting token");

        if (token == null) {
            logger.warn("token is null not deleted");
            return false;
        }

        if (!isTokenExists(token.getTokenName())) {
            logger.info("token does not exist considered deleted");
            return true;
        }

        try {
            tokenRepo.delete(token);
            logger.info("token deleted");
            return true;
        } catch (Exception e) {
            logger.error("delete token failed", e);
            return false;
        }
    }


    public Token getToken(String tokenName) {
        logger.info("retrieving token by name");

        if (tokenName == null || tokenName.trim().isEmpty()) {
            logger.warn("token name is null or empty returning null");
            return null;
        }

        try {
            Optional<Token> token = tokenRepo.findByTokenName(tokenName);
            if (!token.isPresent()) {
                logger.info("token not found");
            } else {
                logger.info("token retrieved");
            }
            return token.orElse(null);
        } catch (Exception e) {
            logger.error("retrieve token failed", e);
            return null;
        }
    }


    /*
    TRACKING INFORMATION SECTION
    */


    public boolean isTrackingInfoExists(String vehicleRegNo) {
        logger.info("checking if tracking info exists for vehicle");

        if (vehicleRegNo == null || vehicleRegNo.trim().isEmpty()) {
            logger.warn("vehicle registration number is null or empty returning false");
            return false;
        }

        try {
            boolean exists = trackingInfoRepo.existsByVehicleRegNo(vehicleRegNo);
            logger.info("tracking info existence check completed");
            return exists;
        } catch (Exception e) {
            logger.error("tracking info existence check failed", e);
            return false;
        }
    }


    public boolean addTrackingInfo(TrackingInfo trackingInfo) {
        logger.info("adding tracking info");

        if (trackingInfo == null) {
            logger.warn("tracking info is null not added");
            return false;
        }

        try {
            trackingInfoRepo.save(trackingInfo);
            logger.info("tracking info added");
            return true;
        } catch (Exception e) {
            logger.error("add tracking info failed", e);
            return false;
        }
    }


    public boolean deleteTrackingInfo(TrackingInfo trackingInfo) {
        logger.info("deleting tracking info");

        if (trackingInfo == null) {
            logger.warn("tracking info is null not deleted");
            return false;
        }

        if (!isTrackingInfoExists(trackingInfo.getVehicleRegNo())) {
            logger.info("tracking info does not exist considered deleted");
            return true;
        }

        try {
            trackingInfoRepo.delete(trackingInfo);
            logger.info("tracking info deleted");
            return true;
        } catch (Exception e) {
            logger.error("delete tracking info failed", e);
            return false;
        }
    }

    public List<TrackingInfo> getTrackingInfo(String vehicleRegNo) {
        logger.info("retrieving tracking info for vehicle");

        if (vehicleRegNo == null || vehicleRegNo.trim().isEmpty()) {
            logger.warn("vehicle registration number is null or empty returning null");
            return null;
        }

        try {
            List<TrackingInfo> trackingInfo = trackingInfoRepo.findAllByVehicleRegNo(vehicleRegNo);
            if (trackingInfo == null || trackingInfo.isEmpty()) {
                logger.info("no tracking info found for vehicle");
            } else {
                logger.info("tracking info retrieved for vehicle");
            }
            return trackingInfo;
        } catch (Exception e) {
            logger.error("retrieve tracking info failed", e);
            return null;
        }
    }



    public boolean isQrLinkExists(String qrKey) {
        if (qrKey == null || qrKey.trim().isEmpty()) {
            return false;
        }
        try {
            if (cacheManager.isQrLinkExistInCache(qrKey)) {
                return true;
            }
            return qrLinkRepo.existsByQrKey(qrKey);
        } catch (Exception e) {
            return false;
        }
    }

    public boolean addQrLink(QrLink qrLink) {
        if (qrLink == null || qrLink.getQrKey().trim().isEmpty()) {
            return false;
        }
        if (isQrLinkExists(qrLink.getQrKey())) {
            return false;
        }
        try {
            qrLinkRepo.save(qrLink);
            cacheManager.putInQrLinkCache(qrLink.getQrKey(), qrLink);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public QrLink getQrLink(String qrKey) {
        if (qrKey == null || qrKey.trim().isEmpty()) {
            return null;
        }
        QrLink qrLink = cacheManager.getFromQrLinkCache(qrKey);
        if (qrLink != null) {
            return qrLink;
        }

        try {
            Optional<QrLink> qrLinkOptional = qrLinkRepo.findByQrKey(qrKey);
            if (qrLinkOptional.isPresent()) {

                QrLink qrLink2 = qrLinkOptional.get();
                cacheManager.putInQrLinkCache(qrKey, qrLink2);
                return qrLink2;
            } else {
                return null;
            }
        } catch (Exception e) {
            return null;
        }
    }


    public boolean deleteQrLink(QrLink qrLink) {
        if (qrLink == null) {
            return false;
        }
        if (!isQrLinkExists(qrLink.getQrKey())) {
            return true;
        }
        try {
            qrLinkRepo.delete(qrLink);
            return true;
        } catch (Exception e) {
            return false;
        }
    }


/////////////////////SimulatedData Section////////////////////////
    public boolean isSimulatedDataExists(String simulatedKey) {
        if (simulatedKey == null || simulatedKey.trim().isEmpty()) {
            return false;
        }
        try {
            if (cacheManager.isSimulatedKeyExistInCache(simulatedKey)) {
                return true;
            }
            return simulatedRepo.existsBySimulatedKey(simulatedKey);
        } catch (Exception e) {
            return false;
        }
    }

    public boolean addSimulatedData(SimulatedData simulatedData) {
        if (simulatedData == null || simulatedData.getSimulatedKey().trim().isEmpty()) {
            return false;
        }
        if (isQrLinkExists(simulatedData.getSimulatedKey())) {
            return false;
        }
        try {
            simulatedRepo.save(simulatedData);
            cacheManager.putInSimulatedDataCache(simulatedData.getSimulatedKey(), simulatedData);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public SimulatedData getSimulatedData(String simulatedKey ) {
        if (simulatedKey == null || simulatedKey.trim().isEmpty()) {
            return null;
        }
        SimulatedData simulatedData = cacheManager.getFromSimulatedDataCache(simulatedKey);
        if (simulatedData != null) {
            return simulatedData;
        }

        try {
            Optional<SimulatedData> simulatedDataOptional = simulatedRepo.findBySimulatedKey(simulatedKey);
            if (simulatedDataOptional.isPresent()) {

                SimulatedData simulatedData2 = simulatedDataOptional.get();
                cacheManager.putInSimulatedDataCache(simulatedKey, simulatedData2);
                return simulatedData2;
            } else {
                return null;
            }
        } catch (Exception e) {
            return null;
        }
    }


    public boolean deleteSimulatedData(SimulatedData simulatedData) {
        if (simulatedData == null) {
            return false;
        }
        if (!isSimulatedDataExists(simulatedData.getSimulatedKey())) {
            return true;
        }
        try {
            cacheManager.deleteFromSimulatedDataCache(simulatedData.getSimulatedKey());
            simulatedRepo.delete(simulatedData);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
