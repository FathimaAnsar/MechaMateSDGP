package com.mechamate.service;

import jakarta.mail.Header;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

@Service
public class APIManager {
    @Autowired
    private DatabaseAbstractLayer databaseAbstractLayer;

    @Autowired
    private NotificationManager notificationManager;

    @Autowired
    private SessionManager sessionManager;

    @Autowired
    private LanguageManager lang;


    @Value("${google.api.key.default}")
    private String apiKey;

    @Autowired
    private RestTemplate restTemplate;

    public APIManager() {

    }

    public String getNearbyRestaurants(double latitude, double longitude, int radius) {
//        String url = "https://places.googleapis.com/v1/places:searchNearby"
////                + "?location=" + latitude + "," + longitude
//                + "?location=" + "7.258390,80.555941"
//                + "&radius=" + radius
//                + "&type=restaurant"
//                + "&key=" + apiKey;
//
//        return restTemplate.getForObject(url, String.class);

        makeHttpPostRequest();
        return "";
    }



    public void makeHttpPostRequest() {
        // Define the URL
        String url = "https://places.googleapis.com/v1/places:searchNearby";

        // Define the request headers
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.add("X-Goog-Api-Key", apiKey);
        headers.add("X-Goog-FieldMask", "places.displayName,places.internationalPhoneNumber,places.currentOpeningHours.openNow,places.userRatingCount,places.websiteUri,places.formattedAddress");

        // Define the request body
        String requestBody = """
                    {
                      "includedTypes": ["car_repair", "car_wash"],
                      "maxResultCount": 10,
                      "locationRestriction": {
                        "circle": {
                          "center": {
                            "latitude": 6.755147345216612,
                            "longitude": 80.12233034204453},
                          "radius": 10000.0
                        }
                      }
                    }
                """;

        // ,
        HttpEntity<String> requestEntity = new HttpEntity<>(requestBody, headers);

        ResponseEntity<String> response = restTemplate.exchange(
                url,
                HttpMethod.POST,
                requestEntity,
                String.class
        );

        HttpStatusCode statusCode = response.getStatusCode();
        String responseBody = response.getBody();

        System.out.println(responseBody);

        // Handle the response accordingly
    }

}
