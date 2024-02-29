package com.mechamate.controller;


import com.mechamate.common.Common;
import com.mechamate.common.Validation;
import com.mechamate.dto.*;
import com.mechamate.entity.ServiceRecord;
import com.mechamate.entity.UserProfile;
import com.mechamate.entity.Vehicle;
import com.mechamate.service.LanguageManager;
import com.mechamate.service.ProfileManager;
import com.mechamate.service.SessionManager;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/general")
public class GeneralController {


    @Autowired
    private SessionManager sessionManager;

    @Autowired
    private ProfileManager profileManager;

    @Autowired
    private LanguageManager lang;




    @PostMapping("/add-vehicle")
    public ResponseEntity<?> addVehicle(HttpServletRequest request, HttpServletResponse response,
                                        @RequestBody(required = false) Vehicle vehicle) {

        Object obj = Validation.authenticate(request, response, sessionManager, lang);
        if(!(obj instanceof UserProfile)) return ((ResponseEntity<ErrorDTO>) obj);
        UserProfile userProfile = (UserProfile) obj;

        if(vehicle == null)
            return new ResponseEntity<>
                    (new ErrorDTO(ErrorDTO.ErrorStatus.ErrorInvalidRequest,
                            lang.get("error.vehicle.notfound", userProfile.getLanguage()),
                            lang.get("error.vehicle.notfound.help", userProfile.getLanguage())),
                            HttpStatus.OK);

        if (vehicle.getRegNo() == null) vehicle.setRegNo("");
        if (vehicle.getVehicleType() == null) vehicle.setVehicleType(Vehicle.VehicleType.Unknown);
        if(vehicle.getFuelType() == null) vehicle.setFuelType(Vehicle.FuelType.Unknown);

        ResponseEntity<ErrorDTO> resp = Validation.validateVehicleRegNo(vehicle.getRegNo().trim().toUpperCase(),
                                                lang, request.getSession());
        if(resp != null) return resp;

        vehicle.setOwner(userProfile.get_id());

        resp = profileManager.addVehicle(vehicle, userProfile);
        if(resp != null) return resp;

        return new ResponseEntity<>
                (new SuccessDTO(SuccessDTO.SuccessStatus.OperationSucceeded,
                        lang.get("success.addvehicle.succeeded", userProfile.getLanguage()),
                        lang.get("success.addvehicle.succeeded.info", userProfile.getLanguage())),
                        HttpStatus.OK);

    }




    @PostMapping("/update-vehicle")
    public ResponseEntity<?> updateVehicle(HttpServletRequest request, HttpServletResponse response,
                                        @RequestBody(required = false) Vehicle vehicle) {

        Object obj = Validation.authenticate(request, response, sessionManager, lang);
        if(!(obj instanceof UserProfile)) return ((ResponseEntity<ErrorDTO>) obj);
        UserProfile userProfile = (UserProfile) obj;

        if(vehicle == null)
            return new ResponseEntity<>
                    (new ErrorDTO(ErrorDTO.ErrorStatus.ErrorInvalidRequest,
                            lang.get("error.vehicle.notfound", userProfile.getLanguage()),
                            lang.get("error.vehicle.notfound.help", userProfile.getLanguage())),
                            HttpStatus.OK);

        if (vehicle.getRegNo() == null) vehicle.setRegNo("");
        if (vehicle.getVehicleType() == null) vehicle.setVehicleType(Vehicle.VehicleType.Unknown);
        if(vehicle.getFuelType() == null) vehicle.setFuelType(Vehicle.FuelType.Unknown);

        ResponseEntity<ErrorDTO> resp = Validation.validateVehicleRegNo(vehicle.getRegNo().trim().toUpperCase(),
                lang, request.getSession());
        if(resp != null) return resp;

        vehicle.setOwner(userProfile.get_id());

        resp = profileManager.updateVehicle(vehicle, userProfile);
        if(resp != null) return resp;

        return new ResponseEntity<>
                (new SuccessDTO(SuccessDTO.SuccessStatus.OperationSucceeded,
                        lang.get("success.updatevehicle.succeeded", userProfile.getLanguage()),
                        lang.get("success.updatevehicle.succeeded.info", userProfile.getLanguage())),
                        HttpStatus.OK);

    }




    @PostMapping("/delete-vehicle")
    public ResponseEntity<?> deleteVehicle(HttpServletRequest request, HttpServletResponse response,
                                           @RequestBody(required = false) Vehicle vehicle) {

        Object obj = Validation.authenticate(request, response, sessionManager, lang);
        if(!(obj instanceof UserProfile)) return ((ResponseEntity<ErrorDTO>) obj);
        UserProfile userProfile = (UserProfile) obj;

        if(vehicle == null)
            return new ResponseEntity<>
                    (new ErrorDTO(ErrorDTO.ErrorStatus.ErrorInvalidRequest,
                            lang.get("error.vehicle.notfound", userProfile.getLanguage()),
                            lang.get("error.vehicle.notfound.help", userProfile.getLanguage())),
                            HttpStatus.OK);

        if (vehicle.getRegNo() == null) vehicle.setRegNo("");
        if (vehicle.getVehicleType() == null) vehicle.setVehicleType(Vehicle.VehicleType.Unknown);
        if(vehicle.getFuelType() == null) vehicle.setFuelType(Vehicle.FuelType.Unknown);

        ResponseEntity<ErrorDTO> resp = Validation.validateVehicleRegNo(vehicle.getRegNo().trim().toUpperCase(),
                lang, request.getSession());
        if(resp != null) return resp;

        vehicle.setOwner(userProfile.get_id());

        resp = profileManager.deleteVehicle(vehicle, userProfile);
        if(resp != null) return resp;

        return new ResponseEntity<>
                (new SuccessDTO(SuccessDTO.SuccessStatus.OperationSucceeded,
                        lang.get("success.delvehicle.succeeded", userProfile.getLanguage()),
                        lang.get("success.delvehicle.succeeded.info", userProfile.getLanguage())),
                        HttpStatus.OK);

    }




    @PostMapping("/add-service-record")
    public ResponseEntity<?> addServiceRecord(HttpServletRequest request, HttpServletResponse response,
                                        @RequestBody(required = false) ServiceRecordDTO serviceRecordDTO,
                                              @RequestParam(required = false) String vehicleRegNo) {

        Object obj = Validation.authenticate(request, response, sessionManager, lang);
        if(!(obj instanceof UserProfile)) return ((ResponseEntity<ErrorDTO>) obj);
        UserProfile userProfile = (UserProfile) obj;

        if(serviceRecordDTO == null)
            return new ResponseEntity<>
                    (new ErrorDTO(ErrorDTO.ErrorStatus.ErrorInvalidRequest,
                            lang.get("error.srecord.notfound", userProfile.getLanguage()),
                            lang.get("error.srecord.notfound.help", userProfile.getLanguage())),
                            HttpStatus.OK);

        if (serviceRecordDTO.getServiceRecordId() == null) serviceRecordDTO.setServiceRecordId("");
        if (serviceRecordDTO.getServices() == null) serviceRecordDTO.setServices(new ArrayList<>());
        if (serviceRecordDTO.getDescription() == null) serviceRecordDTO.setDescription("");
        if (serviceRecordDTO.getDate() == null) serviceRecordDTO.setDate(new Date());
        if (serviceRecordDTO.getMileage() < 0) serviceRecordDTO.setMileage(0);

        if(vehicleRegNo == null) vehicleRegNo = "";
        ResponseEntity<ErrorDTO> resp = Validation.validateVehicleRegNo(vehicleRegNo.trim().toUpperCase(),
                lang, request.getSession());
        if(resp != null) return resp;

        ServiceRecord serviceRecord = new ServiceRecord(vehicleRegNo.trim().toUpperCase(), serviceRecordDTO.getDescription(),
                serviceRecordDTO.getDate(), serviceRecordDTO.getMileage(), serviceRecordDTO.getServices());
        try {
            serviceRecord.set_id(new ObjectId(serviceRecordDTO.getServiceRecordId()));
        } catch(Exception e) {}

        resp = profileManager.addServiceRecord(serviceRecord, userProfile);
        if(resp != null) return resp;

        return new ResponseEntity<>
                (new SuccessDTO(SuccessDTO.SuccessStatus.OperationSucceeded,
                        lang.get("success.addsrecord.succeeded", userProfile.getLanguage()),
                        lang.get("success.addsrecord.succeeded.info", userProfile.getLanguage())),
                        HttpStatus.OK);

    }


    @PostMapping("/update-service-record")
    public ResponseEntity<?> updateServiceRecord(HttpServletRequest request, HttpServletResponse response,
                                        @RequestBody(required = false) ServiceRecordDTO serviceRecordDTO,
                                                 @RequestParam(required = false) String vehicleRegNo) {

        Object obj = Validation.authenticate(request, response, sessionManager, lang);
        if(!(obj instanceof UserProfile)) return ((ResponseEntity<ErrorDTO>) obj);
        UserProfile userProfile = (UserProfile) obj;

        if(serviceRecordDTO == null)
            return new ResponseEntity<>
                    (new ErrorDTO(ErrorDTO.ErrorStatus.ErrorInvalidRequest,
                            lang.get("error.srecord.notfound", userProfile.getLanguage()),
                            lang.get("error.srecord.notfound.help", userProfile.getLanguage())),
                            HttpStatus.OK);

        if (serviceRecordDTO.getServiceRecordId() == null) serviceRecordDTO.setServiceRecordId("");
        if (serviceRecordDTO.getServices() == null) serviceRecordDTO.setServices(new ArrayList<>());
        if (serviceRecordDTO.getDescription() == null) serviceRecordDTO.setDescription("");
        if (serviceRecordDTO.getDate() == null) serviceRecordDTO.setDate(new Date());
        if (serviceRecordDTO.getMileage() < 0) serviceRecordDTO.setMileage(0);

        if(vehicleRegNo == null) vehicleRegNo = "";
        ResponseEntity<ErrorDTO> resp = Validation.validateVehicleRegNo(vehicleRegNo.trim().toUpperCase(),
                lang, request.getSession());
        if(resp != null) return resp;

        ServiceRecord serviceRecord = new ServiceRecord(vehicleRegNo.trim().toUpperCase(), serviceRecordDTO.getDescription(),
                serviceRecordDTO.getDate(), serviceRecordDTO.getMileage(), serviceRecordDTO.getServices());

        try {
            serviceRecord.set_id(new ObjectId(serviceRecordDTO.getServiceRecordId()));
        } catch(Exception e) {}

        resp = profileManager.updateServiceRecord(serviceRecord, userProfile);
        if(resp != null) return resp;

        return new ResponseEntity<>
                (new SuccessDTO(SuccessDTO.SuccessStatus.OperationSucceeded,
                        lang.get("success.updatesrecord.succeeded", userProfile.getLanguage()),
                        lang.get("success.updatesrecord.succeeded.info", userProfile.getLanguage())),
                        HttpStatus.OK);

    }




    @PostMapping("/delete-service-record")
    public ResponseEntity<?> deleteServiceRecord(HttpServletRequest request, HttpServletResponse response,
                                                 @RequestBody(required = false) ServiceRecordDTO serviceRecordDTO,
                                                 @RequestParam(required = false) String vehicleRegNo) {

        Object obj = Validation.authenticate(request, response, sessionManager, lang);
        if(!(obj instanceof UserProfile)) return ((ResponseEntity<ErrorDTO>) obj);
        UserProfile userProfile = (UserProfile) obj;

        if(serviceRecordDTO == null)
            return new ResponseEntity<>
                    (new ErrorDTO(ErrorDTO.ErrorStatus.ErrorInvalidRequest,
                            lang.get("error.srecord.notfound", userProfile.getLanguage()),
                            lang.get("error.srecord.notfound.help", userProfile.getLanguage())),
                            HttpStatus.OK);

        if (serviceRecordDTO.getServiceRecordId() == null) serviceRecordDTO.setServiceRecordId("");
        if (serviceRecordDTO.getServices() == null) serviceRecordDTO.setServices(new ArrayList<>());
        if (serviceRecordDTO.getDescription() == null) serviceRecordDTO.setDescription("");
        if (serviceRecordDTO.getDate() == null) serviceRecordDTO.setDate(new Date());
        if (serviceRecordDTO.getMileage() < 0) serviceRecordDTO.setMileage(0);

        if(vehicleRegNo == null) vehicleRegNo = "";
        ResponseEntity<ErrorDTO> resp = Validation.validateVehicleRegNo(vehicleRegNo.trim().toUpperCase(),
                lang, request.getSession());
        if(resp != null) return resp;

        ServiceRecord serviceRecord = new ServiceRecord(vehicleRegNo.trim().toUpperCase(), serviceRecordDTO.getDescription(),
                serviceRecordDTO.getDate(), serviceRecordDTO.getMileage(), serviceRecordDTO.getServices());

        try {
            serviceRecord.set_id(new ObjectId(serviceRecordDTO.getServiceRecordId()));
        } catch(Exception e) {}

        resp = profileManager.deleteServiceRecord(serviceRecord, userProfile);
        if(resp != null) return resp;

        return new ResponseEntity<>
                (new SuccessDTO(SuccessDTO.SuccessStatus.OperationSucceeded,
                        lang.get("success.delsrecord.succeeded", userProfile.getLanguage()),
                        lang.get("success.delsrecord.succeeded.info", userProfile.getLanguage())),
                        HttpStatus.OK);

    }





    @GetMapping("/profile")
    public ResponseEntity<?> profile(HttpServletRequest request, HttpServletResponse response) {

        Object obj = Validation.authenticate(request, response, sessionManager, lang);
        if(!(obj instanceof UserProfile)) return (ResponseEntity<ErrorDTO>) (obj);
        UserProfile userProfile = (UserProfile) obj;

        ProfileDTO profileDTO = profileManager.getProfileInfo(userProfile);

        if(profileDTO == null)
            return new ResponseEntity<>
                    (new ErrorDTO(ErrorDTO.ErrorStatus.InternalError,
                            lang.get("error.internal.error", userProfile.getLanguage()),
                            lang.get("error.internal.error.help", userProfile.getLanguage())),
                            HttpStatus.OK);

        return new ResponseEntity<>(profileDTO, HttpStatus.OK);
    }



    @GetMapping("/detailed-profile")
    public ResponseEntity<?> getDetailedProfile(HttpServletRequest request, HttpServletResponse response) {

        Object obj = Validation.authenticate(request, response, sessionManager, lang);
        if(!(obj instanceof UserProfile)) return (ResponseEntity<ErrorDTO>) (obj);
        UserProfile userProfile = (UserProfile) obj;

        DetailedProfileDTO detailedProfileDTO = profileManager.getDetailedProfileInfo(userProfile);

        if(detailedProfileDTO == null)
            return new ResponseEntity<>
                    (new ErrorDTO(ErrorDTO.ErrorStatus.InternalError,
                            lang.get("error.internal.error", userProfile.getLanguage()),
                            lang.get("error.internal.error.help", userProfile.getLanguage())),
                            HttpStatus.OK);

        return new ResponseEntity<>(detailedProfileDTO, HttpStatus.OK);
    }


    @PostMapping("/update-profile")
    public ResponseEntity<?> updateProfile(HttpServletRequest request, HttpServletResponse response,
                                           @RequestBody(required = false) ProfileDTO2 profileDTO) {

        Object obj = Validation.authenticate(request, response, sessionManager, lang);
        if(!(obj instanceof UserProfile)) return ((ResponseEntity<ErrorDTO>) obj);
        UserProfile userProfile = (UserProfile) obj;

        if(profileDTO == null)
            return new ResponseEntity<>
                    (new ErrorDTO(ErrorDTO.ErrorStatus.ErrorInvalidRequest,
                            lang.get("error.profinfo.notfound", userProfile.getLanguage()),
                            lang.get("error.profinfo.notfound.help", userProfile.getLanguage())),
                            HttpStatus.OK);

        if(profileDTO.getFirstName() == null || profileDTO.getFirstName().isEmpty()) profileDTO.setFirstName(userProfile.getFirstname());
        if(profileDTO.getLastName() == null || profileDTO.getLastName().isEmpty()) profileDTO.setLastName(userProfile.getLastname());
        if(profileDTO.getTelephone() == null || profileDTO.getTelephone().isEmpty()) profileDTO.setTelephone(userProfile.getTelephone());
        if(profileDTO.getPassword() == null || profileDTO.getPassword().isEmpty()) profileDTO.setPassword(userProfile.getPassword());
        if(profileDTO.getLanguage() == null || profileDTO.getLanguage().isEmpty()) profileDTO.setLanguage(userProfile.getLanguage());

        ResponseEntity<ErrorDTO> resp = Validation.validatePassword(profileDTO.getPassword(), lang, request.getSession());
        if(resp != null) return resp;

        resp = Validation.validateFirstName(profileDTO.getFirstName().trim().toLowerCase(), lang, request.getSession());
        if(resp != null) return resp;

        resp = Validation.validateLastName(profileDTO.getLastName().trim().toLowerCase(), lang, request.getSession());
        if(resp != null) return resp;

        resp = Validation.validateTelephone(profileDTO.getTelephone().trim(), lang, request.getSession());
        if(resp != null) return resp;

        userProfile.setPassword(Common.getSha256("AUTH#>>(" + profileDTO.getPassword() + ")<<#"));
        userProfile.setFirstname(Common.toTitleCase(profileDTO.getFirstName().trim().toLowerCase()));
        userProfile.setLastname(Common.toTitleCase(profileDTO.getLastName().trim().toLowerCase()));
        userProfile.setTelephone(profileDTO.getTelephone().trim());
        userProfile.setLanguage(profileDTO.getLanguage().trim());

        resp = profileManager.updateUserProfile(userProfile);
        if(resp != null) return resp;

        return new ResponseEntity<>
                (new SuccessDTO(SuccessDTO.SuccessStatus.OperationSucceeded,
                        lang.get("success.profupdate.succeeded", userProfile.getLanguage()),
                        lang.get("success.profupdate.succeeded.info", userProfile.getLanguage())),
                        HttpStatus.OK);
    }



}

