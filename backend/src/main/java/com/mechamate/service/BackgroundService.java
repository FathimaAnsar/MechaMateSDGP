package com.mechamate.service;
import com.mechamate.common.ApiToken;
import com.mechamate.common.DeviceDetails;
import com.mechamate.entity.TrackingInfo;
import com.mechamate.entity.Vehicle;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;

@Component
@EnableScheduling
public class BackgroundService {
    private static final Logger logger = LoggerFactory.getLogger(DatabaseAbstractLayer.class);
    @Autowired
    private DatabaseAbstractLayer databaseAbstractLayer;
    @Autowired
    private APIManager apiManager;

    @Scheduled(fixedRate = 300000)   // @Scheduled(fixedRate = 600000)
    public void run() {
        logger.info("Running background service {},", System.currentTimeMillis());

        ApiToken apiToken = apiManager.getJimiToken();
        if(apiToken == null) {
            logger.info("Api token is null. returning...");
            return;
        }

        List<Vehicle> vehicles = databaseAbstractLayer.getAllVehicles();
        if(vehicles.isEmpty()) return;
        for(Vehicle vehicle : vehicles) {
            if(vehicle.getObd2DeviceID().isEmpty()) continue;
            DeviceDetails deviceDetails = apiManager.getDeviceDetails(apiToken, vehicle);
            if(deviceDetails == null) continue;
            Date nDate = new Date();
            TrackingInfo trackingInfo = new TrackingInfo(vehicle.getRegNo(),
                    deviceDetails.getMileage(),
                    deviceDetails.getEnginTemp(), deviceDetails.getVibration(),
                    deviceDetails.getLongitude(), deviceDetails.getLatitude(),
                    deviceDetails.getEngineRPM(), deviceDetails.getDateTime(), nDate.getHours(), deviceDetails.getDrivingPattern());

            if(!databaseAbstractLayer.addTrackingInfo(trackingInfo)) {
                logger.info("Failed to add tracking info to the db!");
            }

            if(vehicle.getCurrentMileage() < deviceDetails.getMileage()) {
                vehicle.setCurrentMileage(deviceDetails.getMileage());
                databaseAbstractLayer.updateVehicle(vehicle);
            }

        }
    }
}









