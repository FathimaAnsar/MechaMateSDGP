package com.mechamate.controller;

import com.mechamate.entity.Maintenance;
import com.mechamate.entity.UserProfile;
import com.mechamate.repo.MaintenanceRepo;
import com.mechamate.repo.UserProfileRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.embedded.undertow.UndertowServletWebServer;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping (value = "api/v1/general")
public class GeneralController {

    @Autowired
    private MaintenanceRepo maintenanceRepo;

    @Autowired
    private UserProfileRepo userProfileRepo;

    @GetMapping("/users")
    public List<UserProfile> getAllUserProfiles() {
        return userProfileRepo.findAll();
    }
  //  @GetMapping("")
   // public String root() {
   //     return "nothing";
   // }

    @GetMapping
    public List<Maintenance> getAllMaintenances() {
        return maintenanceRepo.findAll();
    }


    @PostMapping("/users")
    public List<UserProfile> blah(@Valid @RequestBody UserProfile userProfile, BindingResult bindingResult) {

        userProfileRepo.save(userProfile);
        return userProfileRepo.findAll();
    }



    @PostMapping
    public ResponseEntity<String> createUser(@Valid @RequestBody Maintenance maintenance, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return ResponseEntity.badRequest().body("Validation error: " + bindingResult.getAllErrors());
        }
        if(maintenance.getName().equals("test")){
            maintenance.setName("Ado ela bn!");
        }

        List<Maintenance> list = getAllMaintenances();

        for(Maintenance m: list){
            System.out.println(m.getName());
            System.out.println();
        }


        maintenanceRepo.save(maintenance);
        return ResponseEntity.ok("Maintenance added successfully");
    }

}
