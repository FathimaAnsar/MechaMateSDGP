package com.mechamate.service;

import com.mechamate.common.Common;
import com.mechamate.common.DeviceDetails;
import com.mechamate.common.ApiToken;
import com.mechamate.common.DeviceLocation;
import com.mechamate.entity.Token;
import com.mechamate.entity.Vehicle;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

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

    @Value("${google.nearby.uri}")
    private String apiNearbyUri;

    @Value("${jimi.iot.app.account}")
    private String jimiAppAccount;

    @Value("${jimi.iot.app.key}")
    private String jimiAppKey;

    @Value("${jimi.iot.app.secret}")
    private String jimiAppSecret;

    @Value("${jimi.iot.app.password}")
    private String jimiAppPassword;

    @Value("${jimi.iot.app.uri}")
    private String jimiAppUri;

    @Autowired
    private RestTemplate restTemplate;

    public APIManager() {

    }

    public String getNearbyServiceStations(double latitude, double longitude, double radius, int limitCount) {
        String ret = "{ \"error\": \"InternalError\", \"message\": \"Internal API connection failed\", \"help\": \"Please contact support center\" }";
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            headers.add("X-Goog-Api-Key", apiKey);
            headers.add("X-Goog-FieldMask",
                    "places.displayName.text,places.parkingOptions,places.internationalPhoneNumber,places.nationalPhoneNumber,places.priceLevel,places.googleMapsUri,places.location,places.currentOpeningHours.openNow,places.rating,places.userRatingCount,places.websiteUri,places.formattedAddress,places.reviews.relativePublishTimeDescription,places.reviews.rating,places.reviews.authorAttribution.displayName,places.reviews.originalText.text");

            String requestBody = """
                    { "includedTypes": ["car_repair", "car_wash"], "maxResultCount": """ + limitCount + """
                    ,"rankPreference": "DISTANCE", "locationRestriction": { "circle": { "center": {
                    "latitude": """ + latitude + """
                    ,
                    "longitude": """ + longitude + """
                    },
                    "radius": """ + radius + """
                    }}}
                    """;
            HttpEntity<String> requestEntity = new HttpEntity<>(requestBody, headers);
            ResponseEntity<String> response = restTemplate.exchange(apiNearbyUri, HttpMethod.POST, requestEntity, String.class);
            if(response.getStatusCode() == HttpStatus.OK) ret = response.getBody();
        } catch (Exception e) {}
        return ret;
    }






    public String getNearbyPoliceStations(double latitude, double longitude, double radius, int limitCount) {
        String ret = "{ \"error\": \"InternalError\", \"message\": \"Internal API connection failed\", \"help\": \"Please contact support center\" }";
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            headers.add("X-Goog-Api-Key", apiKey);
            headers.add("X-Goog-FieldMask",
                    "places.displayName.text,places.location,places.internationalPhoneNumber,places.nationalPhoneNumber,places.formattedAddress,places.googleMapsUri");

            String requestBody = """
                    { "includedTypes": ["police"], "maxResultCount": """ + limitCount + """
                    ,"rankPreference": "DISTANCE", "locationRestriction": { "circle": { "center": {
                    "latitude": """ + latitude + """
                    ,
                    "longitude": """ + longitude + """
                    },
                    "radius": """ + radius + """
                    }}}
                    """;
            HttpEntity<String> requestEntity = new HttpEntity<>(requestBody, headers);
            ResponseEntity<String> response = restTemplate.exchange(apiNearbyUri, HttpMethod.POST, requestEntity, String.class);
            if(response.getStatusCode() == HttpStatus.OK) ret = response.getBody();
        } catch (Exception e) {}
        return ret;
    }





    public String getNearbyHospitals(double latitude, double longitude, double radius, int limitCount) {
        String ret = "{ \"error\": \"InternalError\", \"message\": \"Internal API connection failed\", \"help\": \"Please contact support center\" }";
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            headers.add("X-Goog-Api-Key", apiKey);
            headers.add("X-Goog-FieldMask",
                    "places.displayName.text,places.location,places.internationalPhoneNumber,places.nationalPhoneNumber,places.formattedAddress,places.googleMapsUri");

            String requestBody = """
                    { "includedTypes": ["hospital","doctor"], "maxResultCount": """ + limitCount + """
                    ,"rankPreference": "DISTANCE", "locationRestriction": { "circle": { "center": {
                    "latitude": """ + latitude + """
                    ,
                    "longitude": """ + longitude + """
                    },
                    "radius": """ + radius + """
                    }}}
                    """;
            HttpEntity<String> requestEntity = new HttpEntity<>(requestBody, headers);
            ResponseEntity<String> response = restTemplate.exchange(apiNearbyUri, HttpMethod.POST, requestEntity, String.class);
            if(response.getStatusCode() == HttpStatus.OK) ret = response.getBody();
        } catch (Exception e) {}
        return ret;
    }





    public String getNearbySparePartShops(double latitude, double longitude, double radius, int limitCount) {
        String ret = "{ \"error\": \"InternalError\", \"message\": \"Internal API connection failed\", \"help\": \"Please contact support center\" }";
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            headers.add("X-Goog-Api-Key", apiKey);
            headers.add("X-Goog-FieldMask",
                    "places.displayName.text,places.parkingOptions,places.internationalPhoneNumber,places.nationalPhoneNumber,places.priceLevel,places.googleMapsUri,places.location,places.currentOpeningHours.openNow,places.rating,places.userRatingCount,places.websiteUri,places.formattedAddress,places.reviews.relativePublishTimeDescription,places.reviews.rating,places.reviews.authorAttribution.displayName,places.reviews.originalText.text");

            String requestBody = """
                    { "includedTypes": ["auto_parts_store"], "maxResultCount": """ + limitCount + """
                    ,"rankPreference": "DISTANCE", "locationRestriction": { "circle": { "center": {
                    "latitude": """ + latitude + """
                    ,
                    "longitude": """ + longitude + """
                    },
                    "radius": """ + radius + """
                    }}}
                    """;
            HttpEntity<String> requestEntity = new HttpEntity<>(requestBody, headers);
            ResponseEntity<String> response = restTemplate.exchange(apiNearbyUri, HttpMethod.POST, requestEntity, String.class);
            if(response.getStatusCode() == HttpStatus.OK) ret = response.getBody();
        } catch (Exception e) {}
        return ret;
    }




    public String getNearbyParking(double latitude, double longitude, double radius, int limitCount) {
        String ret = "{ \"error\": \"InternalError\", \"message\": \"Internal API connection failed\", \"help\": \"Please contact support center\" }";
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            headers.add("X-Goog-Api-Key", apiKey);
            headers.add("X-Goog-FieldMask",
                    "places.displayName.text,places.parkingOptions,places.internationalPhoneNumber,places.nationalPhoneNumber,places.priceLevel,places.googleMapsUri,places.location,places.currentOpeningHours.openNow,places.rating,places.userRatingCount,places.websiteUri,places.formattedAddress,places.reviews.relativePublishTimeDescription,places.reviews.rating,places.reviews.authorAttribution.displayName,places.reviews.originalText.text");

            String requestBody = """
                    { "includedTypes": ["parking"], "maxResultCount": """ + limitCount + """
                    ,"rankPreference": "DISTANCE", "locationRestriction": { "circle": { "center": {
                    "latitude": """ + latitude + """
                    ,
                    "longitude": """ + longitude + """
                    },
                    "radius": """ + radius + """
                    }}}
                    """;
            HttpEntity<String> requestEntity = new HttpEntity<>(requestBody, headers);
            ResponseEntity<String> response = restTemplate.exchange(apiNearbyUri, HttpMethod.POST, requestEntity, String.class);
            if(response.getStatusCode() == HttpStatus.OK) ret = response.getBody();
        } catch (Exception e) {}
        return ret;
    }



    public DeviceLocation getDeviceLocation(ApiToken apiToken, Vehicle vehicle) {

        Map<String,String> params = new HashMap<>();
        params.put("access_token", apiToken.getAccessToken());
        params.put("imeis", vehicle.getObd2DeviceID());
        params.put("map_type", "GOOGLE");
        String requestBody = getRequestParams("jimi.device.location.get", params);

        DeviceLocation deviceLocation = new DeviceLocation(false, vehicle.getRegNo(), 0,0,"", "");
        String ret = "";
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
            HttpEntity<String> requestEntity = new HttpEntity<>(requestBody, headers);
            ResponseEntity<String> response = restTemplate.exchange(jimiAppUri, HttpMethod.POST, requestEntity, String.class);
            if(response.getStatusCode() == HttpStatus.OK) ret = response.getBody();

            try {
                if(!ret.isEmpty()) {
                    JSONParser parser = new JSONParser();
                    JSONObject json = (JSONObject) parser.parse(ret);
                    JSONArray result = (JSONArray) json.get("result");
                    if(result != null && !result.isEmpty()) {
                        JSONObject jobj = (JSONObject) result.get(0);
                        deviceLocation.setDeviceOnline(jobj.get("status").toString().equals("1"));
                        deviceLocation.setLatitude(Double.parseDouble(jobj.get("lat").toString()));
                        deviceLocation.setLongitude(Double.parseDouble(jobj.get("lng").toString()));
                        deviceLocation.setLocationDateTime(jobj.get("gpsTime").toString());
                        deviceLocation.setMapUrl("");
                    }
                }
            } catch (Exception e) {}
        } catch (Exception e) {}
        return deviceLocation;
    }








    public DeviceDetails getDeviceDetails(ApiToken apiToken, String imei) {
        Map<String,String> params = new HashMap<>();
        params.put("access_token", apiToken.getAccessToken());
        params.put("imei", imei);
        String requestBody = getRequestParams("jimi.track.device.detail", params);

        DeviceDetails deviceDetails = new DeviceDetails();
        String ret = "";
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
            HttpEntity<String> requestEntity = new HttpEntity<>(requestBody, headers);
            ResponseEntity<String> response = restTemplate.exchange(jimiAppUri, HttpMethod.POST, requestEntity, String.class);
            if(response.getStatusCode() == HttpStatus.OK) ret = response.getBody();

            try {
                if(!ret.isEmpty()) {
                    JSONParser parser = new JSONParser();
                    JSONObject json = (JSONObject) parser.parse(ret);
                    JSONObject result = (JSONObject) json.get("result");
                    if(result != null) {
                        deviceDetails.setStatus(result.get("status").toString());
                        deviceDetails.setImei(result.get("imei").toString());
                        deviceDetails.setVehicleIcon(result.get("vehicleIcon").toString());
                    }
                }
            } catch (Exception e) {}
        } catch (Exception e) {}
        return deviceDetails;
    }










    public ApiToken getJimiRefreshToken(ApiToken apiToken) {
        Map<String,String> params = new HashMap<>();
        params.put("access_token", apiToken.getAccessToken());
        params.put("refresh_token", apiToken.getRefreshToken());
        params.put("expires_in", "7200");
        String requestBody = getRequestParams("jimi.oauth.token.refresh", params);

        ApiToken token = null;
        String ret = "";
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
            HttpEntity<String> requestEntity = new HttpEntity<>(requestBody, headers);
            ResponseEntity<String> response = restTemplate.exchange(jimiAppUri, HttpMethod.POST, requestEntity, String.class);
            if(response.getStatusCode() == HttpStatus.OK) ret = response.getBody();

            try {
                if(!ret.isEmpty()) {
                    JSONParser parser = new JSONParser();
                    JSONObject json = (JSONObject) parser.parse(ret);
                    JSONObject result = (JSONObject) json.get("result");
                    if(result != null) {
                        token = new ApiToken();
                        token.setAccessToken(result.get("accessToken").toString());
                        token.setRefreshToken(result.get("refreshToken").toString());
                        token.setExpiresIn(Long.parseLong(result.get("expiresIn").toString()));
                        token.setTime(System.currentTimeMillis());
                        Token dbToken = new Token("jimiToken", token);
                        if(databaseAbstractLayer.isTokenExists("jimiToken")) {
                            databaseAbstractLayer.updateToken(dbToken);
                        } else {
                            databaseAbstractLayer.addToken(dbToken);
                        }
                    }
                }
            } catch (Exception e) {}
        } catch (Exception e) {}
        return token;
    }







    public ApiToken getJimiToken() {
        ApiToken token = null;
        if(databaseAbstractLayer.isTokenExists("jimiToken")) {
            Token dbToken = databaseAbstractLayer.getToken("jimiToken");
            if(dbToken != null) token = dbToken.getToken();
            if(token != null) {
                if((token.getTime() + (token.getExpiresIn() * 1000)) < System.currentTimeMillis()) {
                    ApiToken retToken = getJimiRefreshToken(token);
                    if(retToken != null) return retToken;
                    databaseAbstractLayer.deleteToken(dbToken);
                    token = null;
                } else {
                    return token;
                }
            }
        }

        Map<String,String> params = new HashMap<>();
        params.put("user_id", jimiAppAccount);
        params.put("user_pwd_md5", jimiAppPassword);
        params.put("expires_in", "7200");
        String requestBody = getRequestParams("jimi.oauth.token.get", params);

        String ret = "";
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
            HttpEntity<String> requestEntity = new HttpEntity<>(requestBody, headers);
            ResponseEntity<String> response = restTemplate.exchange(jimiAppUri, HttpMethod.POST, requestEntity, String.class);
            if(response.getStatusCode() == HttpStatus.OK) ret = response.getBody();

            try {
                if(!ret.isEmpty()) {
                    JSONParser parser = new JSONParser();
                    JSONObject json = (JSONObject) parser.parse(ret);
                    JSONObject result = (JSONObject) json.get("result");
                    if(result != null) {
                        token = new ApiToken();
                        token.setAccessToken(result.get("accessToken").toString());
                        token.setRefreshToken(result.get("refreshToken").toString());
                        token.setExpiresIn(Long.parseLong(result.get("expiresIn").toString()));
                        token.setTime(System.currentTimeMillis());
                        Token dbToken = new Token("jimiToken", token);
                        if(databaseAbstractLayer.isTokenExists("jimiToken")) {
                            databaseAbstractLayer.updateToken(dbToken);
                        } else {
                            databaseAbstractLayer.addToken(dbToken);
                        }
                    }
                }
            } catch (Exception e) {}
        } catch (Exception e) {}
        return token;
    }


    private String getRequestParams(String method, Map<String, String> additional) {
        Map<String,String> params = new HashMap<>();
        params.put("timestamp", LocalDateTime.now(java.time.ZoneOffset.UTC).format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
        params.put("app_key", jimiAppKey);
        params.put("format", "json");
        params.put("v", "1.0");
        params.put("sign_method", "md5");
        params.put("method", method);
        if(additional != null) params.putAll(additional);
        TreeMap<String, String> sortedParams = new TreeMap<>(params);

        String slist = "", paramList = "";
        for(String key: sortedParams.keySet()) {
            slist += key + sortedParams.get(key);
            paramList += key + "=" + sortedParams.get(key) + "&";
        }

        if(!paramList.isEmpty()) paramList = paramList.substring(0, paramList.length() - 1);
        return (paramList + "&sign=" + Common.getMD5(jimiAppSecret + slist + jimiAppSecret).toUpperCase());
    }


}
