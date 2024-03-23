package com.mechamate.controller;

import com.mechamate.common.ApiToken;
import com.mechamate.common.Common;
import com.mechamate.common.DeviceLocation;
import com.mechamate.common.Validation;
import com.mechamate.dto.ErrorDTO;
import com.mechamate.entity.Maintenance;
import com.mechamate.entity.QrLink;
import com.mechamate.entity.UserProfile;
import com.mechamate.entity.Vehicle;
import com.mechamate.service.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

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

//    @GetMapping("/maps-api-url")
//    public ResponseEntity<String> getGoogleMapsApiUrl() {
//        String mapsApiUrl = apiManager.googleMapJs();
//        return ResponseEntity.ok(mapsApiUrl);
//    }


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

//
//        Vehicle vehicle = profileManager.getVehicle(vehicleRegNo);
//        if(vehicle == null)
//            return new ResponseEntity<>
//                    (new ErrorDTO(ErrorDTO.ErrorStatus.ErrorInvalidRequest,
//                            lang.get("error.vehicle.doesnt.exist", userProfile.getLanguage()),
//                            lang.get("error.vehicle.doesnt.exist.help", userProfile.getLanguage())),
//                            HttpStatus.OK);
//
//        if(!userProfile.get_id().toHexString().equals(vehicle.getOwner().toHexString()))
//            return new ResponseEntity<>
//                    (new ErrorDTO(ErrorDTO.ErrorStatus.ErrorUnauthorized,
//                            lang.get("error.no.permission", userProfile.getLanguage()),
//                            lang.get("error.no.permission.help", userProfile.getLanguage())),
//                            HttpStatus.OK);


        Vehicle vehicle = new Vehicle("abc-1234", Vehicle.VehicleType.Bus, Vehicle.FuelType.Unknown, "","","",new Date(),new Date(), null, 0);
        vehicle.setObd2DeviceID("863850060019373"); // for testing, remove this when testings are done

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
    public ResponseEntity<?> getServiceRecordQR(HttpServletRequest request, HttpServletResponse response) {
        Object obj = Validation.authenticate(request, response, sessionManager, lang);
        if(!(obj instanceof UserProfile)) return (ResponseEntity<ErrorDTO>) (obj);
        UserProfile userProfile = (UserProfile) obj;

        String qrKey = Common.getSha256("QRKEY#>>(" + userProfile.getUsername() +
                System.currentTimeMillis() + userProfile.getEmail() + ")<<#");
        QrLink qrLink = new QrLink(qrKey, userProfile);

        ResponseEntity<ErrorDTO> resp = profileManager.addQrLink(qrLink, userProfile);
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


//    @GetMapping("/submit-service-record")
//    public ResponseEntity<?> submitServiceRecord(HttpServletRequest request, HttpServletResponse response,
//                                                 @RequestParam(required = false) String key) {
//        if (key == null) key = "";
//
//        return new ResponseEntity<>(responseObject, HttpStatus.OK);
//    }



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


//       responseObject.put("exist", profileManager.isQrLinkExist(key));

return null;




        // 1. Filter service records of vehicle by maintenance
        // 2. Filter prediction models by maintenance
        // 3. Collect tracking data by vehicle reg no
        // 4. Loop through tracking data and calculate the predicted output recursively using trained prediction models
        // 5. Find the difference between the last maintenance KMs against current KMs. (ie: (givenKMs - predictedKMs) )
        // 6. return the predicted output

//        if(maintenanceType == null)
//            return new ResponseEntity<>
//                    (new ErrorDTO(ErrorDTO.ErrorStatus.ErrorInvalidRequest,
//                            lang.get("error.mtype.is.missing", userProfile.getLanguage()),
//                            lang.get("error.mtype.is.missing.help", userProfile.getLanguage())),
//                            HttpStatus.OK);
//
//

    }





}
