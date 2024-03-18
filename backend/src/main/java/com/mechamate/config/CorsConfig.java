package com.mechamate.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CorsConfig implements WebMvcConfigurer {
//    @Override
//    public void addCorsMappings(CorsRegistry registry) {
//        registry.addMapping("/**")
//                .allowedOrigins(
//                        "http://localhost:3000", // For local development
//                        "https://mechamate-413916.el.r.appspot.com", // production server
//                        "capacitor://localhost", // iOS capacitor,
//                        "http://localhost") // android capacitor
//                .allowCredentials(true);
//    }
}

