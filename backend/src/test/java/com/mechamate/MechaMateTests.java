package com.mechamate;

import com.mechamate.common.DeviceDetails;
import com.mechamate.common.JimiToken;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.*;

import java.util.HashMap;
import java.util.Map;

@SpringBootTest
class MechaMateTests {


//	public String getHTTPRes(String method, Map<String,String> params) {

//
//		DeviceDetails deviceDetails = new DeviceDetails();
//		String ret = "";
//		try {
//			HttpHeaders headers = new HttpHeaders();
//			headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
//			HttpEntity<String> requestEntity = new HttpEntity<>(requestBody, headers);
//			ResponseEntity<String> response = restTemplate.exchange(jimiAppUri, HttpMethod.POST, requestEntity, String.class);
//			if(response.getStatusCode() == HttpStatus.OK) ret = response.getBody();
//
//			try {
//				if(!ret.isEmpty()) {
//					JSONParser parser = new JSONParser();
//					JSONObject json = (JSONObject) parser.parse(ret);
//					JSONObject result = (JSONObject) json.get("result");
//					if(result != null) {
//						deviceDetails.setStatus(result.get("status").toString());
//						deviceDetails.setImei(result.get("imei").toString());
//						deviceDetails.setVehicleIcon(result.get("vehicleIcon").toString());
//					}
//				}
//			} catch (Exception e) {}
//		} catch (Exception e) {}
//		return deviceDetails;
//	}

//
//	@Test
//	void Signup_InputValidation() {
//
//
//
//	}

}
