package com.mechamate.controller;

import com.mechamate.common.ApiToken;
import com.mechamate.common.Common;
import com.mechamate.common.DeviceLocation;
import com.mechamate.common.Validation;
import com.mechamate.dto.*;
import com.mechamate.entity.*;
import com.mechamate.features.PdM;
import com.mechamate.service.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.catalina.User;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/api/v1/features")
public class FeatureController {

    @Autowired
    private SessionManager sessionManager;

    @Autowired
    private ProfileManager profileManager;

    @Autowired
    private SuperUserActionManager superUserActionManager;

    @Autowired
    private LanguageManager lang;

    @Autowired
    private APIManager apiManager;

    @Autowired
    private PdM pdM;

    @Value("${spring.config.server.address}")
    private String hostname;

    @GetMapping("/get-features-list")
    public ResponseEntity<?> getFeaturesList(HttpServletRequest request, HttpServletResponse response) {
        Object obj = Validation.authenticate(request, response, sessionManager, lang);
        if(!(obj instanceof UserProfile)) return (ResponseEntity<ErrorDTO>) (obj);
        UserProfile userProfile = (UserProfile) obj;

        return new ResponseEntity<String>("", HttpStatus.OK);
    }



    @GetMapping("/get-feature")
    public ResponseEntity<?> getFeature() {
        return null;
    }

    @GetMapping("/get-nearby-service-stations")
    public ResponseEntity<?> getNearbyServiceStations(HttpServletRequest request, HttpServletResponse response,
                                             @RequestParam(required = false) Double lat,
                                             @RequestParam(required = false) Double lng,
                                             @RequestParam(required = false) Double radius,
                                             @RequestParam(required = false) Integer limit) {
        Object obj = Validation.authenticate(request, response, sessionManager, lang);
        if(!(obj instanceof UserProfile)) return (ResponseEntity<ErrorDTO>) (obj);
        UserProfile userProfile = (UserProfile) obj;

        if(lat == null) lat = 0.0;
        if(lng == null) lng = 0.0;
        if(radius == null) radius = 0.0;
        if(limit == null) limit = 0;

        return new ResponseEntity<String>(
                apiManager.getNearbyServiceStations(lat, lng, radius, limit), HttpStatus.OK);
    }



    @GetMapping("/get-nearby-police-stations")
    public ResponseEntity<?> getNearbyPoliceStations(HttpServletRequest request, HttpServletResponse response,
                                             @RequestParam(required = false) Double lat,
                                             @RequestParam(required = false) Double lng,
                                             @RequestParam(required = false) Double radius,
                                             @RequestParam(required = false) Integer limit) {
        Object obj = Validation.authenticate(request, response, sessionManager, lang);
        if(!(obj instanceof UserProfile)) return (ResponseEntity<ErrorDTO>) (obj);
        UserProfile userProfile = (UserProfile) obj;

        if(lat == null) lat = 0.0;
        if(lng == null) lng = 0.0;
        if(radius == null) radius = 0.0;
        if(limit == null) limit = 0;

        return new ResponseEntity<String>(
                apiManager.getNearbyPoliceStations(lat, lng, radius, limit), HttpStatus.OK);
    }




    @GetMapping("/get-nearby-hospitals")
    public ResponseEntity<?> getNearbyHospitals(HttpServletRequest request, HttpServletResponse response,
                                                     @RequestParam(required = false) Double lat,
                                                     @RequestParam(required = false) Double lng,
                                                     @RequestParam(required = false) Double radius,
                                                     @RequestParam(required = false) Integer limit) {
        Object obj = Validation.authenticate(request, response, sessionManager, lang);
        if(!(obj instanceof UserProfile)) return (ResponseEntity<ErrorDTO>) (obj);
        UserProfile userProfile = (UserProfile) obj;

        if(lat == null) lat = 0.0;
        if(lng == null) lng = 0.0;
        if(radius == null) radius = 0.0;
        if(limit == null) limit = 0;

        return new ResponseEntity<String>(
                apiManager.getNearbyHospitals(lat, lng, radius, limit), HttpStatus.OK);
    }



    @GetMapping("/get-nearby-spare-part-shops")
    public ResponseEntity<?> getNearbySparePartShops(HttpServletRequest request, HttpServletResponse response,
                                                @RequestParam(required = false) Double lat,
                                                @RequestParam(required = false) Double lng,
                                                @RequestParam(required = false) Double radius,
                                                @RequestParam(required = false) Integer limit) {
        Object obj = Validation.authenticate(request, response, sessionManager, lang);
        if(!(obj instanceof UserProfile)) return (ResponseEntity<ErrorDTO>) (obj);
        UserProfile userProfile = (UserProfile) obj;

        if(lat == null) lat = 0.0;
        if(lng == null) lng = 0.0;
        if(radius == null) radius = 0.0;
        if(limit == null) limit = 0;

        return new ResponseEntity<String>(
                apiManager.getNearbySparePartShops(lat, lng, radius, limit), HttpStatus.OK);
    }




    @GetMapping("/set-availability")
    public ResponseEntity<?> setAvailability(HttpServletRequest request, HttpServletResponse response,
                                              @RequestParam(required = false) Double lat,
                                              @RequestParam(required = false) Double lng,
                                              @RequestParam(required = false) boolean isAvailable) {
        Object obj = Validation.authenticate(request, response, sessionManager, lang);
        if(!(obj instanceof UserProfile)) return (ResponseEntity<ErrorDTO>) (obj);
        UserProfile userProfile = (UserProfile) obj;

        if(lat == null) lat = 0.0;
        if(lng == null) lng = 0.0;

        if(!userProfile.isServiceAccount())
            return new ResponseEntity<>
                    (new ErrorDTO(ErrorDTO.ErrorStatus.ErrorInvalidRequest,
                            lang.get("error.not.a.service.acc", userProfile.getLanguage()),
                            lang.get("error.not.a.service.acc.help", userProfile.getLanguage())),
                            HttpStatus.OK);

        userProfile.setAvailable(isAvailable);
        userProfile.setLongitude(lng);
        userProfile.setLatitude(lat);
        ResponseEntity<ErrorDTO> resp = profileManager.updateUserProfile(userProfile);
        if(resp != null) return resp;

        return new ResponseEntity<>
                (new SuccessDTO(SuccessDTO.SuccessStatus.OperationSucceeded,
                        lang.get("success.avail.set.succeeded", userProfile.getLanguage()),
                        lang.get("success.avail.set.succeeded.info", userProfile.getLanguage())),
                        HttpStatus.OK);
    }

    public double calculateDistanceInMeters(double lat1, double lon1, double lat2, double lon2) {
        double dLat  = Math.toRadians((lat2 - lat1));
        double dLong = Math.toRadians((lon2 - lon1));
        lat1 = Math.toRadians(lat1);
        lat2 = Math.toRadians(lat2);
        double a = haversine(dLat) + Math.cos(lat1) * Math.cos(lat2) * haversine(dLong);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        return 6371 * c * 1000; // 6371 = EARTH_RADIUS in KMs
    }
    private double haversine(double val) {
        return Math.pow(Math.sin(val / 2), 2);
    }

    @GetMapping("/get-service-providers-list")
    public ResponseEntity<?> getServiceProviders(HttpServletRequest request, HttpServletResponse response,
                                              @RequestParam(required = false) Double lat,
                                              @RequestParam(required = false) Double lng,
                                              @RequestParam(required = false) Double radius,
                                              @RequestParam(required = false) Integer limit) {
        Object obj = Validation.authenticate(request, response, sessionManager, lang);
        if(!(obj instanceof UserProfile)) return (ResponseEntity<ErrorDTO>) (obj);
        UserProfile userProfile = (UserProfile) obj;

        if(lat == null) lat = 0.0;
        if(lng == null) lng = 0.0;
        if(radius == null) radius = 0.0;
        if(limit == null) limit = 0;

        List<UserProfile> userProfiles = profileManager.getAllServiceAccounts();
        List<ProfileDTO> filteredList = new ArrayList<>();
        if(userProfiles.isEmpty()) return new ResponseEntity<>(filteredList, HttpStatus.OK);

        for(UserProfile usr : userProfiles) {
            double calcRadius = calculateDistanceInMeters(lat, lng, usr.getLatitude(), usr.getLongitude());
            if(calcRadius < radius) filteredList.add(profileManager.getProfileInfo(usr));
        }
        return new ResponseEntity<>(filteredList, HttpStatus.OK);
    }


    @GetMapping("/get-nearby-parking")
    public ResponseEntity<?> getNearbyParking(HttpServletRequest request, HttpServletResponse response,
                                                     @RequestParam(required = false) Double lat,
                                                     @RequestParam(required = false) Double lng,
                                                     @RequestParam(required = false) Double radius,
                                                     @RequestParam(required = false) Integer limit) {
        Object obj = Validation.authenticate(request, response, sessionManager, lang);
        if(!(obj instanceof UserProfile)) return (ResponseEntity<ErrorDTO>) (obj);
        UserProfile userProfile = (UserProfile) obj;

        if(lat == null) lat = 0.0;
        if(lng == null) lng = 0.0;
        if(radius == null) radius = 0.0;
        if(limit == null) limit = 0;

        return new ResponseEntity<String>(
                apiManager.getNearbyParking(lat, lng, radius, limit), HttpStatus.OK);
    }


    @GetMapping("/get-directions")
    public ResponseEntity<?> getDirections(HttpServletRequest request, HttpServletResponse response,
                                           @RequestParam(required = false) String origin,
                                           @RequestParam(required = false) String destination) {
        Object obj = Validation.authenticate(request, response, sessionManager, lang);
        if (!(obj instanceof UserProfile)) return (ResponseEntity<?>) obj;
        UserProfile userProfile = (UserProfile) obj;

        if (origin == null || origin.isEmpty() || destination == null || destination.isEmpty()) {
            return ResponseEntity.badRequest().body("{ \"error\": \"ValidationError\", \"message\": \"Origin and destination are required.\" }");
        }

        String result = apiManager.getDirections(origin, destination);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }


    @GetMapping("/get-device-location")
    public ResponseEntity<?> getDeviceLocation(HttpServletRequest request, HttpServletResponse response,
                                               @RequestParam(required = false) String vehicleRegNo) {
        Object obj = Validation.authenticate(request, response, sessionManager, lang);
        if(!(obj instanceof UserProfile)) return (ResponseEntity<ErrorDTO>) (obj);
        UserProfile userProfile = (UserProfile) obj;

        if (vehicleRegNo == null) vehicleRegNo ="";
        ResponseEntity<ErrorDTO> resp = Validation.validateVehicleRegNo(vehicleRegNo.trim().toUpperCase(),
                lang, request.getSession());
        if(resp != null) return resp;

        ApiToken apiToken = apiManager.getJimiToken();
        if(apiToken == null)
            return new ResponseEntity<>
                    (new ErrorDTO(ErrorDTO.ErrorStatus.InternalError,
                            lang.get("error.token.error", userProfile.getLanguage()),
                            lang.get("error.token.error.help", userProfile.getLanguage())),
                            HttpStatus.OK);

        Vehicle vehicle = profileManager.getVehicle(vehicleRegNo);
        if(vehicle == null)
            return new ResponseEntity<>
                    (new ErrorDTO(ErrorDTO.ErrorStatus.ErrorInvalidRequest,
                            lang.get("error.vehicle.doesnt.exist", userProfile.getLanguage()),
                            lang.get("error.vehicle.doesnt.exist.help", userProfile.getLanguage())),
                            HttpStatus.OK);

        if(!userProfile.get_id().toHexString().equals(vehicle.getOwner().toHexString()))
            return new ResponseEntity<>
                    (new ErrorDTO(ErrorDTO.ErrorStatus.ErrorUnauthorized,
                            lang.get("error.no.permission", userProfile.getLanguage()),
                            lang.get("error.no.permission.help", userProfile.getLanguage())),
                            HttpStatus.OK);

        if(vehicle.getObd2DeviceID().isEmpty())
            return new ResponseEntity<>
                    (new ErrorDTO(ErrorDTO.ErrorStatus.ErrorInvalidRequest,
                            lang.get("error.no.device.found", userProfile.getLanguage()),
                            lang.get("error.no.device.found.help", userProfile.getLanguage())),
                            HttpStatus.OK);

        return new ResponseEntity<DeviceLocation>(
                apiManager.getDeviceLocation(apiToken, vehicle), HttpStatus.OK);
    }


    @GetMapping("/get-service-record-qr")
    public ResponseEntity<?> getServiceRecordQR(HttpServletRequest request, HttpServletResponse response,
                                                @RequestParam(required = false) String vehicleRegNo) {
        Object obj = Validation.authenticate(request, response, sessionManager, lang);
        if(!(obj instanceof UserProfile)) return (ResponseEntity<ErrorDTO>) (obj);
        UserProfile userProfile = (UserProfile) obj;

        if (vehicleRegNo == null) vehicleRegNo ="";
        ResponseEntity<ErrorDTO> resp = Validation.validateVehicleRegNo(vehicleRegNo.trim().toUpperCase(),
                lang, request.getSession());
        if(resp != null) return resp;

        Vehicle vehicle = profileManager.getVehicle(vehicleRegNo);
        if(vehicle == null)
            return new ResponseEntity<>
                    (new ErrorDTO(ErrorDTO.ErrorStatus.ErrorInvalidRequest,
                            lang.get("error.vehicle.doesnt.exist", userProfile.getLanguage()),
                            lang.get("error.vehicle.doesnt.exist.help", userProfile.getLanguage())),
                            HttpStatus.OK);

        if(!userProfile.get_id().toHexString().equals(vehicle.getOwner().toHexString()))
            return new ResponseEntity<>
                    (new ErrorDTO(ErrorDTO.ErrorStatus.ErrorUnauthorized,
                            lang.get("error.no.permission", userProfile.getLanguage()),
                            lang.get("error.no.permission.help", userProfile.getLanguage())),
                            HttpStatus.OK);

        String qrKey = Common.getSha256("QRKEY#>>(" + userProfile.getUsername() +
                System.currentTimeMillis() + userProfile.getEmail() + ")<<#");
        QrLink qrLink = new QrLink(qrKey, vehicle);

        resp = profileManager.addQrLink(qrLink, userProfile);
        if(resp != null) return resp;

        Map<String, Object> responseObject = new HashMap<>();
        responseObject.put("url", hostname + "/add-service-record?key=" + qrKey);
        return new ResponseEntity<>(responseObject, HttpStatus.OK);
    }


    @GetMapping("/check-service-record-qr")
    public ResponseEntity<?> checkServiceRecordQR(@RequestParam(required = false) String key) {
        if (key == null) key = "";
        Map<String, Object> responseObject = new HashMap<>();
        responseObject.put("exist", profileManager.isQrLinkExist(key));
        return new ResponseEntity<>(responseObject, HttpStatus.OK);
    }


    @GetMapping("/submit-service-record-by-service-provider")
    public ResponseEntity<?> submitServiceRecord(HttpServletRequest request, HttpServletResponse response,
                                                 @RequestBody(required = false) ServiceRecordDTO serviceRecordDTO,
                                                 @RequestParam(required = false) String key) {

        if (key == null) key = "";
        if(!profileManager.isQrLinkExist(key))
            return new ResponseEntity<>
                    (new ErrorDTO(ErrorDTO.ErrorStatus.ErrorInvalidRequest,
                            lang.get("error.invalid.link", "default"),
                            lang.get("error.invalid.link.help", "default")),
                            HttpStatus.OK);


        QrLink qrLink = profileManager.getQrLink(key);
        if(qrLink == null || qrLink.getVehicle() == null)
            return new ResponseEntity<>
                    (new ErrorDTO(ErrorDTO.ErrorStatus.ErrorInvalidRequest,
                            lang.get("error.invalid.link", "default"),
                            lang.get("error.invalid.link.help", "default")),
                            HttpStatus.OK);

        if(serviceRecordDTO == null)
            return new ResponseEntity<>
                    (new ErrorDTO(ErrorDTO.ErrorStatus.ErrorInvalidRequest,
                            lang.get("error.srecord.notfound", "default"),
                            lang.get("error.srecord.notfound.help", "default")),
                            HttpStatus.OK);

        if (serviceRecordDTO.getServiceRecordId() == null) serviceRecordDTO.setServiceRecordId("");
        if (serviceRecordDTO.getServices() == null) serviceRecordDTO.setServices(new ArrayList<>());
        if (serviceRecordDTO.getDescription() == null) serviceRecordDTO.setDescription("");
        if (serviceRecordDTO.getDate() == null) serviceRecordDTO.setDate(new Date());
        if (serviceRecordDTO.getMileage() < 0) serviceRecordDTO.setMileage(0);

        ServiceRecord serviceRecord = new ServiceRecord(qrLink.getVehicle().getRegNo().trim().toUpperCase(), serviceRecordDTO.getDescription(),
                serviceRecordDTO.getDate(), serviceRecordDTO.getMileage(), serviceRecordDTO.getServices());

        try {
            serviceRecord.set_id(new ObjectId(serviceRecordDTO.getServiceRecordId()));
        } catch(Exception e) {}

        if(!serviceRecord.getServices().isEmpty()) {
            List<ServiceDTO> svcDTOs = serviceRecord.getServices();
            for (ServiceDTO svDTO : svcDTOs) {
                try {
                    svDTO.setAddedDate(System.currentTimeMillis());
                } catch (Exception e) {}
            }
        }

        ResponseEntity<ErrorDTO> resp = profileManager.addServiceRecordByProvider(serviceRecord);
        if(resp != null) return resp;

        return new ResponseEntity<>
                (new SuccessDTO(SuccessDTO.SuccessStatus.OperationSucceeded,
                        lang.get("success.added.svcrec.succeeded", "default"),
                        lang.get("success.added.svcrec.succeeded.info", "default")),
                        HttpStatus.OK);

    }


    @GetMapping("/get-maintenance-prediction")
    public ResponseEntity<?> getPredictedOutput(HttpServletRequest request, HttpServletResponse response,
                                                     @RequestParam(required = false) String vehicleRegNo) {
        Object obj = Validation.authenticate(request, response, sessionManager, lang);
        if(!(obj instanceof UserProfile)) return (ResponseEntity<ErrorDTO>) (obj);
        UserProfile userProfile = (UserProfile) obj;

        if (vehicleRegNo == null) vehicleRegNo ="";
        ResponseEntity<ErrorDTO> resp = Validation.validateVehicleRegNo(vehicleRegNo.trim().toUpperCase(),
                lang, request.getSession());
        if(resp != null) return resp;

        VehicleDTO vehicleDTO = profileManager.getVehicleDTO(vehicleRegNo);
        if(vehicleDTO == null)
            return new ResponseEntity<>
                    (new ErrorDTO(ErrorDTO.ErrorStatus.ErrorInvalidRequest,
                            lang.get("error.no.vehicles.found", userProfile.getLanguage()),
                            lang.get("error.no.vehicles.found.help", userProfile.getLanguage())),
                            HttpStatus.OK);

        List<PredictionDTO> pDTO = new ArrayList<>();
        return pdM.getPredictions(pDTO, userProfile, vehicleDTO);
    }








}
