package com.mechamate.controller;

import com.mechamate.entity.Maintenance;
import com.mechamate.repo.MaintenanceRepo;
import org.springframework.beans.factory.annotation.Autowired;
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

  //  @GetMapping("")
   // public String root() {
   //     return "nothing";
   // }


    @GetMapping
    public List<Maintenance> getAllMaintenances() {
        return maintenanceRepo.findAll();
    }

    @PostMapping
    public ResponseEntity<String> createUser(@Valid @RequestBody Maintenance maintenance, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return ResponseEntity.badRequest().body("Validation error: " + bindingResult.getAllErrors());
        }

        maintenanceRepo.save(maintenance);
        return ResponseEntity.ok("Maintenance added successfully");
    }

}
