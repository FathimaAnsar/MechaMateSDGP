package com.mechamate;

import org.slf4j.LoggerFactory;
import org.slf4j.Logger;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@SpringBootApplication
public class MechaMate {
	private static final Logger logger = LoggerFactory.getLogger(MechaMate.class);

	public static void main(String[] args) {
		SpringApplication.run(MechaMate.class, args);
	}


}


