package com.mechamate.controller;

import com.mechamate.common.Validation;
import com.mechamate.dto.ErrorDTO;
import com.mechamate.entity.UserProfile;
import com.mechamate.features.PdM;
import com.mechamate.service.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/api/v1/simulated")
public class SimulatedAPI {




    @GetMapping("/get-device-info")
    public ResponseEntity<?> getFeaturesList(HttpServletRequest request, HttpServletResponse response) {

        String s = """
                {
                    "":"",
                    "":"",
                    "":"",
                    "":"",
                    "":""
                }
                """;

        return new ResponseEntity<String>(s, HttpStatus.OK);
    }
}