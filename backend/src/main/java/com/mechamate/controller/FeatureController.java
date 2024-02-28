package com.mechamate.controller;

import com.mechamate.common.DeviceDetails;
import com.mechamate.common.JimiToken;
import com.mechamate.common.Validation;
import com.mechamate.dto.ErrorDTO;
import com.mechamate.entity.UserProfile;
import com.mechamate.service.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping("/get-features-list")
    public ResponseEntity<?> getFeaturesList(HttpServletRequest request, HttpServletResponse response) {
        Object obj = Validation.authenticate(request, response, sessionManager, lang);
        if(!(obj instanceof UserProfile)) return (ResponseEntity<ErrorDTO>) (obj);
        UserProfile userProfile = (UserProfile) obj;



        return new ResponseEntity<String>("", HttpStatus.OK);
    }



    @GetMapping("/get-feature")
    public ResponseEntity<?> getDetailedProfile() {
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


        JimiToken jimiToken = apiManager.getJimiToken();

        JimiToken token2 = apiManager.getJimiRefreshToken(jimiToken);


        DeviceDetails dev = apiManager.getDeviceLocation(token2, "863850060019373");


        return new ResponseEntity<String>(
                apiManager.getNearbyParking(lat, lng, radius, limit), HttpStatus.OK);
    }









}


