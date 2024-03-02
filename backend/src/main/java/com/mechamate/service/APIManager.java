package com.mechamate.service;

import com.mechamate.MechaMate;
import com.mechamate.common.Common;
import com.mechamate.common.DeviceDetails;
import com.mechamate.common.ApiToken;
import com.mechamate.common.DeviceLocation;
import com.mechamate.entity.Token;
import com.mechamate.entity.Vehicle;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.net.http.HttpClient;
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
    private static final Logger logger = LoggerFactory.getLogger(MechaMate.class);

    public String getNearbyServiceStations(double latitude, double longitude, double radius, int limitCount) {

        logger.info("enter to the getNearbyServiceStations with latitude: {}, longitude: {}, radius: {}, limitCount: {}", latitude, longitude, radius, limitCount);

        if (latitude < -90 || latitude > 90) {
            logger.error("Invalid latitude: {}. Latitude must be between -90 and 90.", latitude);
            return "{ \"error\": \"ValidationError\", \"message\": \"Invalid latitude value\" }";
        }
        if (longitude < -180 || longitude > 180) {
            logger.error("Invalid longitude: {}. Longitude must be between -180 and 180.", longitude);
            return "{ \"error\": \"ValidationError\", \"message\": \"Invalid longitude value\" }";
        }
        if (radius <= 0) {
            logger.error("Invalid radius: {}. Radius must be greater than 0.", radius);
            return "{ \"error\": \"ValidationError\", \"message\": \"Invalid radius value\" }";
        }
        if (limitCount <= 0) {
            logger.error("Invalid limitCount: {}. limitCount must be greater than 0.", limitCount);
            return "{ \"error\": \"ValidationError\", \"message\": \"Invalid limitCount value\" }";
        }

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

            logger.debug("Response status: {}", response.getStatusCode());
            logger.debug("Response body: {}", response.getBody());

            if (response.getStatusCode() == HttpStatus.OK) {
                ret = response.getBody();
            } else {
                logger.error("error response received with status: {}", response.getStatusCode());
            }
        } catch (Exception e) {
            logger.error("unexpected error in getNearbyServiceStations: {}", e.getMessage(), e);
        }
        logger.info("exiting from getNearbyServiceStations");
        return ret;
    }






    public String getNearbyPoliceStations(double latitude, double longitude, double radius, int limitCount) {

        logger.info("enter to the getNearbyPoliceStations with latitude: {}, longitude: {}, radius: {}, limitCount: {}", latitude, longitude, radius, limitCount);

        if (latitude < -90 || latitude > 90) {
            logger.error("Invalid latitude: {}", latitude);
            return "{ \"error\": \"ValidationError\", \"message\": \"Invalid latitude value\" }";
        }
        if (longitude < -180 || longitude > 180) {
            logger.error("Invalid longitude: {}", longitude);
            return "{ \"error\": \"ValidationError\", \"message\": \"Invalid longitude value\" }";
        }
        if (radius <= 0) {
            logger.error("Invalid radius: {}", radius);
            return "{ \"error\": \"ValidationError\", \"message\": \"Invalid radius value\" }";
        }
        if (limitCount <= 0) {
            logger.error("Invalid limitCount: {}", limitCount);
            return "{ \"error\": \"ValidationError\", \"message\": \"Invalid limit count value\" }";
        }
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

            logger.debug("Response status: {}", response.getStatusCode());
            logger.debug("Response body: {}", response.getBody());

            if(response.getStatusCode() == HttpStatus.OK){
                ret = response.getBody();
            }else {
                logger.error("error response received with status: {}", response.getStatusCode());
            }
        } catch (Exception e) {
            logger.error("unexpected error in getNearbyServiceStations: {}", e.getMessage(), e);
        }
        logger.info("exiting from getNearbyPoliceStations");
        return ret;
    }





    public String getNearbyHospitals(double latitude, double longitude, double radius, int limitCount) {

        logger.info("enter to the getNearbyHospitals with latitude: {}, longitude: {}, radius: {}, limitCount: {}", latitude, longitude, radius, limitCount);


        // Input validation
        if (latitude < -90 || latitude > 90) {
            logger.error("Invalid latitude: {}", latitude);
            return "{ \"error\": \"ValidationError\", \"message\": \"Invalid latitude value\" }";
        }
        if (longitude < -180 || longitude > 180) {
            logger.error("Invalid longitude: {}", longitude);
            return "{ \"error\": \"ValidationError\", \"message\": \"Invalid longitude value\" }";
        }
        if (radius <= 0) {
            logger.error("Invalid radius: {}", radius);
            return "{ \"error\": \"ValidationError\", \"message\": \"Invalid radius value\" }";
        }
        if (limitCount <= 0) {
            logger.error("Invalid limitCount: {}", limitCount);
            return "{ \"error\": \"ValidationError\", \"message\": \"Invalid limit count value\" }";
        }


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

            logger.debug("Response status: {}", response.getStatusCode());
            logger.debug("Response body: {}", response.getBody());

            if(response.getStatusCode() == HttpStatus.OK){
                ret = response.getBody();
            }else {
                logger.error("error response received with status: {}", response.getStatusCode());
            }
        } catch (Exception e) {
            logger.error("unexpected error in getNearbyHospitals: {}", e.getMessage(), e);
        }
        logger.info("exiting from getNearbyHospitals");
        return ret;
    }





    public String getNearbySparePartShops(double latitude, double longitude, double radius, int limitCount) {

        logger.info("enter to the getNearbySparePartShops with latitude: {}, longitude: {}, radius: {}, limitCount: {}", latitude, longitude, radius, limitCount);

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

            logger.debug("Response status: {}", response.getStatusCode());
            logger.debug("Response body: {}", response.getBody());

            if(response.getStatusCode() == HttpStatus.OK) {
                ret = response.getBody();
            }else {
                logger.error("error response received with status: {}", response.getStatusCode());
            }
        } catch (Exception e) {
            logger.error("unexpected error in getNearbySparePartShops: {}", e.getMessage(), e);
        }
        logger.info("exiting from getNearbySparePartShops");
        return ret;
    }




    public String getNearbyParking(double latitude, double longitude, double radius, int limitCount) {
        logger.info("enter to the getNearbyParking with latitude: {}, longitude: {}, radius: {}, limitCount: {}", latitude, longitude, radius, limitCount);
        if (latitude < -90 || latitude > 90) {
            logger.error("Invalid latitude: {}", latitude);
            return "{ \"error\": \"ValidationError\", \"message\": \"Invalid latitude value\" }";
        }
        if (longitude < -180 || longitude > 180) {
            logger.error("Invalid longitude: {}", longitude);
            return "{ \"error\": \"ValidationError\", \"message\": \"Invalid longitude value\" }";
        }
        if (radius <= 0) {
            logger.error("Invalid radius: {}", radius);
            return "{ \"error\": \"ValidationError\", \"message\": \"Invalid radius value\" }";
        }
        if (limitCount <= 0) {
            logger.error("Invalid limitCount: {}", limitCount);
            return "{ \"error\": \"ValidationError\", \"message\": \"Invalid limit count value\" }";
        }
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

            logger.debug("Response status: {}", response.getStatusCode());
            logger.debug("Response body: {}", response.getBody());

            if(response.getStatusCode() == HttpStatus.OK) {
                ret = response.getBody();
            }else {
                logger.error("error response received with status: {}", response.getStatusCode());
            }
        } catch (Exception e) {
            logger.error("unexpected error in getNearbyParking: {}", e.getMessage());
        }
        logger.info("exiting from getNearbyParking");
        return ret;
    }



    public DeviceLocation getDeviceLocation(ApiToken apiToken, Vehicle vehicle) {
        logger.info("enter to the getDeviceLocation");
        DeviceLocation deviceLocation = new DeviceLocation(false, false, vehicle.getRegNo(), 0,0,"", "");

        Map<String,String> params = new HashMap<>();
        params.put("access_token", apiToken.getAccessToken());
        params.put("imeis", vehicle.getObd2DeviceID());
        params.put("map_type", "GOOGLE");
        String requestBody = getRequestParams("jimi.device.location.get", params);


        String ret = "";

        try {

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
            HttpEntity<String> requestEntity = new HttpEntity<>(requestBody, headers);
            ResponseEntity<String> response = restTemplate.exchange(jimiAppUri, HttpMethod.POST, requestEntity, String.class);

            logger.debug("Response status: {}", response.getStatusCode());
            logger.debug("Response body: {}", response.getBody());

            if(response.getStatusCode() == HttpStatus.OK){
                ret = response.getBody();
            }else {
                logger.error("error response received with status: {}", response.getStatusCode());
            }

            try {
                if(!ret.isEmpty()) {
                    JSONParser parser = new JSONParser();
                    JSONObject json = (JSONObject) parser.parse(ret);
                    JSONArray result = (JSONArray) json.get("result");
                    if(result != null && !result.isEmpty()) {
                        JSONObject jobj = (JSONObject) result.get(0);
                        deviceLocation.setDeviceOnline(jobj.get("status").toString().equals("1"));
                        deviceLocation.setEngineRunning(!jobj.get("speed").toString().equals("0"));
                        deviceLocation.setLatitude(Double.parseDouble(jobj.get("lat").toString()));
                        deviceLocation.setLongitude(Double.parseDouble(jobj.get("lng").toString()));
                        deviceLocation.setLocationDateTime(jobj.get("gpsTime").toString());
                        deviceLocation.setMapUrl("https://maps.google.com/maps?q=" + deviceLocation.getLatitude() + "," + deviceLocation.getLongitude());
                    }
                }
            } catch (Exception e) {
                logger.error("error response received with status: {}", response.getStatusCode());
            }
        } catch (Exception e) {
            logger.error("unexpected error in getDeviceLocation: {}", e.getMessage());
        }
        logger.info("exiting from getDeviceLocation");
        return deviceLocation;
    }



    public DeviceDetails getDeviceDetails(ApiToken apiToken, String imei) {
        logger.info("enter to the getDeviceDetails");
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
            } catch (Exception e) {
                logger.info("exit from the getDeviceDetails");
            }
        } catch (Exception e) {
            logger.info("exit from the getDeviceDetails");
        }
        logger.info("exit from the getDeviceDetails");
        return deviceDetails;
    }








    public ApiToken getJimiRefreshToken(ApiToken apiToken) {

        logger.info("enter to the getJimiRefreshToken");

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

            if(response.getStatusCode() == HttpStatus.OK) {
                ret = response.getBody();}
            else {
                logger.error("error response received with status: {}", response.getStatusCode());
            }

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
            } catch (Exception e) {
                logger.info("exit from the getJimiRefreshToken");
            }
        } catch (Exception e) {
            logger.info("exit from the getJimiRefreshToken");
        }
        logger.info("exit from the getJimiRefreshToken");
        return token;
    }






    public ApiToken getJimiToken() {
        logger.info("Entering getJimiToken method");
        ApiToken token = null;
        if(databaseAbstractLayer.isTokenExists("jimiToken")) {
            Token dbToken = databaseAbstractLayer.getToken("jimiToken");
            if(dbToken != null) token = dbToken.getToken();
            if(token != null) {
                if((token.getTime() + (token.getExpiresIn() * 1000)) < System.currentTimeMillis()) {
                    ApiToken retToken = getJimiRefreshToken(token);
                    if(retToken != null) {
                        logger.info("token successfully refreshed");
                        return retToken;
                    }
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
            logger.debug("Response status from requesting new token: {}", response.getStatusCode());
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
            } catch (Exception e) {
                logger.error("failed to receive a valid response for new token request");
            }
        } catch (Exception e) {
            logger.error("an error occurred in getJimiToken: {}", e.getMessage(), e);
        }
        logger.info("exiting getJimiToken method");
        return token;
    }


    private String getRequestParams(String method, Map<String, String> additional) {
        logger.info("Entering getRequestParams with method: {}", method);

        if (method == null || method.trim().isEmpty()) {
            logger.error("Invalid 'method' parameter");
            return null;
        }
        if (additional == null) {
            logger.warn("'additional' parameter is null, with empty map");
            additional = new HashMap<>();
        }

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

        try {
            for (String key : sortedParams.keySet()) {
                slist += key + sortedParams.get(key);
                paramList += key + "=" + sortedParams.get(key) + "&";
            }

            if (!paramList.isEmpty()) {
                paramList = paramList.substring(0, paramList.length() - 1);
            }

            String signature = Common.getMD5(jimiAppSecret + slist + jimiAppSecret).toUpperCase();
            return paramList + "&sign=" + signature;
        } catch (Exception e) {
            logger.error("Error generating request parameters: {}", e.getMessage());
            return null;
        } finally {
            logger.info("Exiting getRequestParams");
        }
    }
}
