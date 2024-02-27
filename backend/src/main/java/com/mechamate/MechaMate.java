package com.mechamate;

import org.slf4j.LoggerFactory;
import org.slf4j.Logger;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@SpringBootApplication
@Configuration
public class MechaMate implements WebMvcConfigurer {
	@Override
	public void addCorsMappings(CorsRegistry registry) {
		registry.addMapping("/**")
				.allowedOrigins("http://localhost:3001")
				.allowedMethods("GET", "POST")
				.allowCredentials(true)
				.allowedHeaders("*");
	}

	private static final Logger logger = LoggerFactory.getLogger(MechaMate.class);

	public static void main(String[] args) {
		SpringApplication.run(MechaMate.class, args);
	}


}


