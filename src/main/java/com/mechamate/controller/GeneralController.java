package com.mechamate.controller;

import com.mechamate.entity.UserProfile;
import com.mechamate.entity.Vehicle;
import com.mechamate.repo.UserProfileRepository;
import com.mechamate.service.DatabaseManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping(value = "api/v1/general")
public class GeneralController {




    @PostMapping("/vehicle")
    public ResponseEntity<String> createVehicle(@Valid @RequestBody Vehicle vehicle, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return ResponseEntity.badRequest().body("Validation error: " + bindingResult.getAllErrors());
        }

        return ResponseEntity.ok("Done Vehicle");
    }

    @PostMapping("/signup")
    public ResponseEntity<String> signUp(@Valid @RequestBody UserProfile userProfile, BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            return ResponseEntity.badRequest().body("Validation error: " + bindingResult.getAllErrors());
        }
        DatabaseManager databaseManager = new DatabaseManager();
        databaseManager.addUserProfile(userProfile);

        return ResponseEntity.ok("User Profile Created");
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@Valid @RequestBody UserProfile userProfile, BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            return ResponseEntity.badRequest().body("Validation error: " + bindingResult.getAllErrors());
        }
        DatabaseManager databaseManager = new DatabaseManager();
        databaseManager.getUserProfile(userProfile.getUsername());

        return ResponseEntity.ok("User Profile Created");
    }

}
