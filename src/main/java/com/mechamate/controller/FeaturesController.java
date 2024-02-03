package com.mechamate.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping (value = "api/v1/features")
public class FeaturesController {

    @GetMapping("")
    public String root() {
        return "nothing";
    }

}
