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
    private final ConcurrentHashMap<String, Vehicle> vehicleCache = new ConcurrentHashMap<>();
    private final ConcurrentHashMap<String, ServiceRecord> serviceRecordCache = new ConcurrentHashMap<>();
    private final ConcurrentHashMap<String, Maintenance> maintenanceCache = new ConcurrentHashMap<>();
    private final ConcurrentHashMap<String, PredictionModel> predictionModelCache = new ConcurrentHashMap<>();
    private final ConcurrentHashMap<String, Token> tokenCache = new ConcurrentHashMap<>();
    private final ConcurrentHashMap<String, TrackingInfo> trackingInfoCache = new ConcurrentHashMap<>();

            /*
    USERPROFILE SECTION
        */

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
                logger.debug("found UserProfile for key: {}", key);
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


        /*
    SESSION SECTION
        */

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
           /*
    SERVICE RECORD SECTION
        */

    public boolean isServiceRecordCacheExistInCache (String key){
        boolean isExist = false;
        if (key == null) {
            logger.warn("attempt to get a ServiceRecord with a null key.");
        }
        try {
            isExist = serviceRecordCache.containsKey(key);
            logger.info("found ServiceRecord for key: {}", key);

        }catch (Exception e){
            logger.error("exception happened while retrieving ServiceRecord from cache for key: {}. Exception: {}", key, e.toString());
        }
        return isExist;
    }

    public ServiceRecord getFromServiceRecordCacheCache(String key) {

        if (key == null) {
            logger.warn("attempt to get a ServiceRecord with a null key.");
            return null;
        }

        try {
            ServiceRecord serviceRecord = serviceRecordCache.get(key);

            if (serviceRecord == null) {
                logger.info("cache miss for ServiceRecord key: {}", key);
                return null;
            } else {
                logger.info("found ServiceRecord for key: {}", key);
                return serviceRecord;
            }
        } catch (Exception e) {
            logger.error("exception happened while retrieving ServiceRecord from cache for key: {}. Exception: {}", key, e.toString());
            return null;
        }
    }


    public void putInServiceRecordCache(String key, ServiceRecord serviceRecord) {
        if (key == null || serviceRecord == null) {
            logger.warn("attempt to put a null key or ServiceRecord into the cache. Key: {}, Vehicle: {}", key, serviceRecord);
            return;
        }

        try {
            serviceRecordCache.put(key, serviceRecord);
            logger.info("ServiceRecord put into cache under key: {}", key);
        } catch (Exception e) {
            logger.error("exception happens while putting ServiceRecord into cache. Key: {}, Exception: {}", key, e.toString());
        }
    }


    public void deleteFromServiceRecordCache(String key) {
        if (key == null) {
            logger.warn("attempt to delete ServiceRecord from cache with null key.");
            return;
        }

        try {
            if (!serviceRecordCache.containsKey(key)) {
                logger.info("attempt to delete a non ServiceRecord key from cache: {}", key);
                return;
            }
            serviceRecordCache.remove(key);
            logger.info("ServiceRecord removed from cache for key: {}", key);

        } catch (Exception e) {
            logger.error("exception occurred while deleting ServiceRecord from cache. Key: {}, Exception: {}", key, e.toString());
        }
    }

           /*
    VEHICLE SECTION
        */

    public boolean isVehicleExistInCache (String key){
        boolean isExist = false;
        if (key == null) {
            logger.warn("attempt to get a vehicle with a null key.");
        }
        try {
            isExist = vehicleCache.containsKey(key);
            logger.info("found vehicle for key: {}", key);

        }catch (Exception e){
            logger.error("exception happened while retrieving vehicle from cache for key: {}. Exception: {}", key, e.toString());
        }
        return isExist;
    }

    public Vehicle getFromVehicleCache(String key) {

        if (key == null) {
            logger.warn("attempt to get a vehicle with a null key.");
            return null;
        }

        try {
            Vehicle vehicle = vehicleCache.get(key);

            if (vehicle == null) {
                logger.info("cache miss for vehicle key: {}", key);
                return null;
            } else {
                logger.info("found vehicle for key: {}", key);
                return vehicle;
            }
        } catch (Exception e) {
            logger.error("exception happened while retrieving vehicle from cache for key: {}. Exception: {}", key, e.toString());
            return null;
        }
    }


    public void putInVehicleCache(String key, Vehicle vehicle) {
        if (key == null || vehicle == null) {
            logger.warn("attempt to put a null key or vehicle into the cache. Key: {}, Vehicle: {}", key, vehicle);
            return;
        }

        try {
            vehicleCache.put(key, vehicle);
            logger.info("vehicle put into cache under key: {}", key);
        } catch (Exception e) {
            logger.error("exception happens while putting vehicle into cache. Key: {}, Exception: {}", key, e.toString());
        }
    }


    public void deleteFromVehicleCache(String key) {
        if (key == null) {
            logger.warn("attempt to delete vehicle from cache with null key.");
            return;
        }

        try {
            if (!vehicleCache.containsKey(key)) {
                logger.info("attempt to delete a non vehicle key from cache: {}", key);
                return;
            }
            vehicleCache.remove(key);
            logger.info("vehicle removed from cache for key: {}", key);

        } catch (Exception e) {
            logger.error("exception occurred while deleting vehicle from cache. Key: {}, Exception: {}", key, e.toString());
        }
    }

           /*
    MAINTENANCE SECTION
        */

    public boolean isMaintenanceCacheExistInCache (String key){
        boolean isExist = false;
        if (key == null) {
            logger.warn("attempt to get a Maintenance with a null key.");
        }
        try {
            isExist = maintenanceCache.containsKey(key);
            logger.info("found Maintenance for key: {}", key);

        }catch (Exception e){
            logger.error("exception happened while retrieving Maintenance from cache for key: {}. Exception: {}", key, e.toString());
        }
        return isExist;
    }

    public Maintenance getFromMaintenanceCache(String key) {

        if (key == null) {
            logger.warn("attempt to get a Maintenance with a null key.");
            return null;
        }

        try {
            Maintenance maintenance = maintenanceCache.get(key);

            if (maintenance == null) {
                logger.info("cache miss for Maintenance key: {}", key);
                return null;
            } else {
                logger.info("found Maintenance for key: {}", key);
                return maintenance;
            }
        } catch (Exception e) {
            logger.error("exception happened while retrieving Maintenance from cache for key: {}. Exception: {}", key, e.toString());
            return null;
        }
    }


    public void putInMaintenanceCache(String key, Maintenance maintenance) {
        if (key == null || maintenance == null) {
            logger.warn("attempt to put a null key or Maintenance into the cache. Key: {}, Vehicle: {}", key, maintenance);
            return;
        }

        try {
            maintenanceCache.put(key, maintenance);
            logger.info("Maintenance put into cache under key: {}", key);
        } catch (Exception e) {
            logger.error("exception happens while putting Maintenance into cache. Key: {}, Exception: {}", key, e.toString());
        }
    }


    public void deleteFromMaintenanceCache(String key) {
        if (key == null) {
            logger.warn("attempt to delete Maintenance from cache with null key.");
            return;
        }

        try {
            if (!maintenanceCache.containsKey(key)) {
                logger.info("attempt to delete a non Maintenance key from cache: {}", key);
                return;
            }
            maintenanceCache.remove(key);
            logger.info("Maintenance removed from cache for key: {}", key);

        } catch (Exception e) {
            logger.error("exception occurred while deleting Maintenance from cache. Key: {}, Exception: {}", key, e.toString());
        }
    }

               /*
    TOKEN SECTION
        */


    public boolean isTokenCacheExistInCache (String key){
        boolean isExist = false;
        if (key == null) {
            logger.warn("attempt to get a Token with a null key.");
        }
        try {
            isExist = tokenCache.containsKey(key);
            logger.info("found Token for key: {}", key);

        }catch (Exception e){
            logger.error("exception happened while retrieving Token from cache for key: {}. Exception: {}", key, e.toString());
        }
        return isExist;
    }

    public Token getFromTokenCache(String key) {

        if (key == null) {
            logger.warn("attempt to get a Token with a null key.");
            return null;
        }

        try {
            Token token = tokenCache.get(key);

            if (token == null) {
                logger.info("cache miss for Token key: {}", key);
                return null;
            } else {
                logger.info("found Token for key: {}", key);
                return token;
            }
        } catch (Exception e) {
            logger.error("exception happened while retrieving Token from cache for key: {}. Exception: {}", key, e.toString());
            return null;
        }
    }


    public void putInTokenCache(String key, Token token) {
        if (key == null || token == null) {
            logger.warn("attempt to put a null key or Token into the cache. Key: {}, Token: {}", key, token);
            return;
        }

        try {
            tokenCache.put(key, token);
            logger.info("Token put into cache under key: {}", key);
        } catch (Exception e) {
            logger.error("exception happens while putting Token into cache. Key: {}, Exception: {}", key, e.toString());
        }
    }


    public void deleteFromTokenCache(String key) {
        if (key == null) {
            logger.warn("attempt to delete Token from cache with null key.");
            return;
        }

        try {
            if (!tokenCache.containsKey(key)) {
                logger.info("attempt to delete a non Token key from cache: {}", key);
                return;
            }
            tokenCache.remove(key);
            logger.info("Token removed from cache for key: {}", key);

        } catch (Exception e) {
            logger.error("exception occurred while deleting Token from cache. Key: {}, Exception: {}", key, e.toString());
        }
    }

               /*
    PREDICTION MODEL SECTION
        */

    public boolean isPredictionModelCacheExistInCache (String key){
        boolean isExist = false;
        if (key == null) {
            logger.warn("attempt to get a PredictionModel with a null key.");
        }
        try {
            isExist = predictionModelCache.containsKey(key);
            logger.info("found vehicle for key: {}", key);

        }catch (Exception e){
            logger.error("exception happened while retrieving PredictionModel from cache for key: {}. Exception: {}", key, e.toString());
        }
        return isExist;
    }

    public PredictionModel getFromPredictionModelCache(String key) {

        if (key == null) {
            logger.warn("attempt to get a PredictionModel with a null key.");
            return null;
        }

        try {
            PredictionModel predictionModel = predictionModelCache.get(key);

            if (predictionModel == null) {
                logger.info("cache miss for PredictionModel key: {}", key);
                return null;
            } else {
                logger.info("found PredictionModel for key: {}", key);
                return predictionModel;
            }
        } catch (Exception e) {
            logger.error("exception happened while retrieving PredictionModel from cache for key: {}. Exception: {}", key, e.toString());
            return null;
        }
    }


    public void putInPredictionModelCache(String key, PredictionModel predictionModel) {
        if (key == null || predictionModel == null) {
            logger.warn("attempt to put a null key or PredictionModel into the cache. Key: {}, PredictionModel: {}", key, predictionModel);
            return;
        }

        try {
            predictionModelCache.put(key, predictionModel);
            logger.info("PredictionModel put into cache under key: {}", key);
        } catch (Exception e) {
            logger.error("exception happens while putting PredictionModel into cache. Key: {}, Exception: {}", key, e.toString());
        }
    }


    public void deleteFromPredictionModelCache(String key) {
        if (key == null) {
            logger.warn("attempt to delete PredictionModel from cache with null key.");
            return;
        }

        try {
            if (!predictionModelCache.containsKey(key)) {
                logger.info("attempt to delete a non PredictionModel key from cache: {}", key);
                return;
            }
            predictionModelCache.remove(key);
            logger.info("PredictionModel removed from cache for key: {}", key);

        } catch (Exception e) {
            logger.error("exception occurred while deleting PredictionModel from cache. Key: {}, Exception: {}", key, e.toString());
        }
    }

                   /*
    TRACKING INFO SECTION
        */

    public boolean TrackingInfoCacheExistInCache (String key){
        boolean isExist = false;
        if (key == null) {
            logger.warn("attempt to get a TrackingInfo with a null key.");
        }
        try {
            isExist = trackingInfoCache.containsKey(key);
            logger.info("found TrackingInfo for key: {}", key);

        }catch (Exception e){
            logger.error("exception happened while retrieving TrackingInfo from cache for key: {}. Exception: {}", key, e.toString());
        }
        return isExist;
    }

    public TrackingInfo getFromTrackingInfoCache(String key) {

        if (key == null) {
            logger.warn("attempt to get a TrackingInfo with a null key.");
            return null;
        }

        try {
            TrackingInfo trackingInfo = trackingInfoCache.get(key);

            if (trackingInfo == null) {
                logger.info("cache miss for TrackingInfo key: {}", key);
                return null;
            } else {
                logger.info("found TrackingInfo for key: {}", key);
                return trackingInfo;
            }
        } catch (Exception e) {
            logger.error("exception happened while retrieving TrackingInfo from cache for key: {}. Exception: {}", key, e.toString());
            return null;
        }
    }


    public void putInTrackingInfoCache(String key, TrackingInfo trackingInfo) {
        if (key == null || trackingInfo == null) {
            logger.warn("attempt to put a null key or TrackingInfo into the cache. Key: {}, TrackingInfo: {}", key, trackingInfo);
            return;
        }

        try {
            trackingInfoCache.put(key, trackingInfo);
            logger.info("TrackingInfo put into cache under key: {}", key);
        } catch (Exception e) {
            logger.error("exception happens while putting TrackingInfo into cache. Key: {}, Exception: {}", key, e.toString());
        }
    }


    public void deleteFromTrackingInfoCache(String key) {
        if (key == null) {
            logger.warn("attempt to delete TrackingInfo from cache with null key.");
            return;
        }

        try {
            if (!trackingInfoCache.containsKey(key)) {
                logger.info("attempt to delete a non TrackingInfo key from cache: {}", key);
                return;
            }
            trackingInfoCache.remove(key);
            logger.info("TrackingInfo removed from cache for key: {}", key);

        } catch (Exception e) {
            logger.error("exception occurred while deleting TrackingInfo from cache. Key: {}, Exception: {}", key, e.toString());
        }
    }

}
