package com.mechamate.MechaMate.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping (value = "api/v1/auth")
public class AuthController {

    @GetMapping("")
    public String root() {
        return "nothing";
    }

}
