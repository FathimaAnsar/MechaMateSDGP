package com.mechamate.controller;

import com.mechamate.entity.Maintenance;
import com.mechamate.entity.UserProfile;
import com.mechamate.entity.Vehicle;
import com.mechamate.repo.MaintenanceRepository;
import com.mechamate.repo.UserProfileRepository;
import com.mechamate.repo.VehicleRepository;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(value = "api/v1/general")
public class GeneralController {

    @Autowired
    private UserProfileRepository userProfileRepository;

    @Autowired
    private VehicleRepository vehicleRepository;

    @GetMapping("/tst")
    public List<UserProfile> getAllUserProfiles() {
        return userProfileRepository.findAll();
    }


    @PostMapping("/vehicle")
    public ResponseEntity<String> createVehicle(@Valid @RequestBody Vehicle vehicle, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return ResponseEntity.badRequest().body("Validation error: " + bindingResult.getAllErrors());
        }


        vehicleRepository.save(vehicle);
        return ResponseEntity.ok("Done Vehicle");
    }

    @PostMapping
    public ResponseEntity<String> createUser(@Valid @RequestBody UserProfile profile, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return ResponseEntity.badRequest().body("Validation error: " + bindingResult.getAllErrors());
        }

        userProfileRepository.save(profile);
        return ResponseEntity.ok("Done");
    }

}
