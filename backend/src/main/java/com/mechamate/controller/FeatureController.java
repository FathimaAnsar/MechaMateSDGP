package com.mechamate.controller;

import com.mechamate.common.Validation;
import com.mechamate.dto.ErrorDTO;
import com.mechamate.entity.UserProfile;
import com.mechamate.service.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

        System.out.println(apiManager.getNearbyRestaurants(0,0,10000));


        return superUserActionManager.getPredictionModelList();
    }



    @GetMapping("/get-feature")
    public ResponseEntity<?> getDetailedProfile() {
        return null;
    }


}


