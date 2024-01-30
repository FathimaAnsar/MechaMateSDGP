package com.mechamate.MechaMate;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
public class MechaMate {
	public static void main(String[] args) {
		SpringApplication.run(MechaMate.class, args);
	}
	@RequestMapping(value = "/")
	public String hello() {
		return "<center><h3>MechaMate is Under Construction!</h3></center>";
	}
}

