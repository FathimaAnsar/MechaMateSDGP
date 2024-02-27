package com.mechamate;

import org.slf4j.LoggerFactory;
import org.slf4j.Logger;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@SpringBootApplication
public class MechaMate implements WebMvcConfigurer {

	private static final Logger logger = LoggerFactory.getLogger(MechaMate.class);

	public static void main(String[] args) {
		SpringApplication.run(MechaMate.class, args);
	}

	@Override
	public void addCorsMappings(CorsRegistry registry) {
		registry.addMapping("/**")
				// Replace "http://localhost:3000" with the actual origin of your React frontend
				// If you have multiple, add them separately like below:
				// .allowedOrigins("http://localhost:3000", "https://yourproductiondomain.com")
				.allowedOrigins("http://localhost:3000")
				.allowedMethods("GET", "POST", "PUT", "PATCH", "DELETE", "OPTIONS")
				.allowedHeaders("*")
				.allowCredentials(true); // Be cautious with this if you're handling sensitive data
	}
}