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
import java.util.List;
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



    //endpoint to add a new vehicle
    @PostMapping("/add-vehicle")
    public ResponseEntity<?> addVehicle(HttpServletRequest request, HttpServletResponse response,
                                        @RequestBody(required = false) VehicleDTO vehicleDTO) {

        //athentication and authorization
        Object obj = Validation.authenticate(request, response, sessionManager, lang);
        if(!(obj instanceof UserProfile)) return ((ResponseEntity<ErrorDTO>) obj);
        UserProfile userProfile = (UserProfile) obj;

        //handling invalid request
        if(vehicleDTO == null)
            return new ResponseEntity<>
                    (new ErrorDTO(ErrorDTO.ErrorStatus.ErrorInvalidRequest,
                            lang.get("error.vehicle.notfound", userProfile.getLanguage()),
                            lang.get("error.vehicle.notfound.help", userProfile.getLanguage())),
                            HttpStatus.OK);

        //handling missing DTO values for vehicle
        if (vehicleDTO.getRegistrationNumber() == null) vehicleDTO.setRegistrationNumber("");
        if (vehicleDTO.getVehicleType() == null) vehicleDTO.setVehicleType(Vehicle.VehicleType.Unknown);
        if(vehicleDTO.getFuelType() == null) vehicleDTO.setFuelType(Vehicle.FuelType.Unknown);
        if(vehicleDTO.getObd2DeviceID() == null) vehicleDTO.setObd2DeviceID("");

        ResponseEntity<ErrorDTO> resp = Validation.validateVehicleRegNo(vehicleDTO.getRegistrationNumber().trim().toUpperCase(),
                                                lang, request.getSession());
        if(resp != null) return resp;
        //create the vehicle entity
        Vehicle vehicle = new Vehicle(vehicleDTO.getRegistrationNumber(),
                vehicleDTO.getVehicleType(),
                vehicleDTO.getFuelType(),
                vehicleDTO.getVehicleMake(),
                vehicleDTO.getVehicleModel(),
                vehicleDTO.getInsNo(),
                vehicleDTO.getInsExpDate(),
                vehicleDTO.getRegExpDate(),
                userProfile.get_id(),
                vehicleDTO.getCurrentMileage());
        //add the vehicle through the profile manager
        resp = profileManager.addVehicle(vehicle, userProfile);
        if(resp != null) return resp;

        return new ResponseEntity<>
                (new SuccessDTO(SuccessDTO.SuccessStatus.OperationSucceeded,
                        lang.get("success.addvehicle.succeeded", userProfile.getLanguage()),
                        lang.get("success.addvehicle.succeeded.info", userProfile.getLanguage())),
                        HttpStatus.OK);

    }


    //endpoint to update the vehicle
    @PostMapping("/update-vehicle")
    public ResponseEntity<?> updateVehicle(HttpServletRequest request, HttpServletResponse response,
                                        @RequestBody(required = false) VehicleDTO vehicleDTO) {

        Object obj = Validation.authenticate(request, response, sessionManager, lang);
        if(!(obj instanceof UserProfile)) return ((ResponseEntity<ErrorDTO>) obj);
        UserProfile userProfile = (UserProfile) obj;

        if(vehicleDTO == null)
            return new ResponseEntity<>
                    (new ErrorDTO(ErrorDTO.ErrorStatus.ErrorInvalidRequest,
                            lang.get("error.vehicle.notfound", userProfile.getLanguage()),
                            lang.get("error.vehicle.notfound.help", userProfile.getLanguage())),
                            HttpStatus.OK);
        //handle missing values
        if (vehicleDTO.getRegistrationNumber() == null) vehicleDTO.setRegistrationNumber("");
        if (vehicleDTO.getVehicleType() == null) vehicleDTO.setVehicleType(Vehicle.VehicleType.Unknown);
        if(vehicleDTO.getFuelType() == null) vehicleDTO.setFuelType(Vehicle.FuelType.Unknown);
        if(vehicleDTO.getObd2DeviceID() == null) vehicleDTO.setObd2DeviceID("");

        ResponseEntity<ErrorDTO> resp = Validation.validateVehicleRegNo(vehicleDTO.getRegistrationNumber().trim().toUpperCase(),
                lang, request.getSession());
        if(resp != null) return resp;
        //updated vehicle
        Vehicle vehicle = new Vehicle(vehicleDTO.getRegistrationNumber(),
                vehicleDTO.getVehicleType(),
                vehicleDTO.getFuelType(),
                vehicleDTO.getVehicleMake(),
                vehicleDTO.getVehicleModel(),
                vehicleDTO.getInsNo(),
                vehicleDTO.getInsExpDate(),
                vehicleDTO.getRegExpDate(),
                userProfile.get_id(),
                vehicleDTO.getCurrentMileage());

        resp = profileManager.updateVehicle(vehicle, userProfile);
        if(resp != null) return resp;

        return new ResponseEntity<>
                (new SuccessDTO(SuccessDTO.SuccessStatus.OperationSucceeded,
                        lang.get("success.updatevehicle.succeeded", userProfile.getLanguage()),
                        lang.get("success.updatevehicle.succeeded.info", userProfile.getLanguage())),
                        HttpStatus.OK);

    }



    //endpoint to delete vehicle
    @PostMapping("/delete-vehicle")
    public ResponseEntity<?> deleteVehicle(HttpServletRequest request, HttpServletResponse response,
                                           @RequestBody(required = false) VehicleDTO vehicleDTO) {

        Object obj = Validation.authenticate(request, response, sessionManager, lang);
        if(!(obj instanceof UserProfile)) return ((ResponseEntity<ErrorDTO>) obj);
        UserProfile userProfile = (UserProfile) obj;

        if(vehicleDTO == null)
            return new ResponseEntity<>
                    (new ErrorDTO(ErrorDTO.ErrorStatus.ErrorInvalidRequest,
                            lang.get("error.vehicle.notfound", userProfile.getLanguage()),
                            lang.get("error.vehicle.notfound.help", userProfile.getLanguage())),
                            HttpStatus.OK);

        if (vehicleDTO.getRegistrationNumber() == null) vehicleDTO.setRegistrationNumber("");

        ResponseEntity<ErrorDTO> resp = Validation.validateVehicleRegNo(vehicleDTO.getRegistrationNumber().trim().toUpperCase(),
                lang, request.getSession());
        if(resp != null) return resp;

        Vehicle vehicle = new Vehicle(vehicleDTO.getRegistrationNumber(),
                vehicleDTO.getVehicleType(),
                vehicleDTO.getFuelType(),
                vehicleDTO.getVehicleMake(),
                vehicleDTO.getVehicleModel(),
                vehicleDTO.getInsNo(),
                vehicleDTO.getInsExpDate(),
                vehicleDTO.getRegExpDate(),
                userProfile.get_id(),
                vehicleDTO.getCurrentMileage());

        resp = profileManager.deleteVehicle(vehicle, userProfile);
        if(resp != null) return resp;

        return new ResponseEntity<>
                (new SuccessDTO(SuccessDTO.SuccessStatus.OperationSucceeded,
                        lang.get("success.delvehicle.succeeded", userProfile.getLanguage()),
                        lang.get("success.delvehicle.succeeded.info", userProfile.getLanguage())),
                        HttpStatus.OK);

    }



    //endpoint to add a service record
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

    //endpoint to update a service record
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



    //endpoint to delete service records
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




    //endpoint to access profile
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


    @GetMapping("/get-vehicles")
    public ResponseEntity<?> getVehicles(HttpServletRequest request, HttpServletResponse response) {

        Object obj = Validation.authenticate(request, response, sessionManager, lang);
        if(!(obj instanceof UserProfile)) return (ResponseEntity<ErrorDTO>) (obj);
        UserProfile userProfile = (UserProfile) obj;

        List<VehicleDTO> vehicleDTOList = profileManager.getVehicleDTOs(userProfile);
        return new ResponseEntity<>(vehicleDTOList, HttpStatus.OK);
    }


    //endpoint to access detailed profile
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

    //endpoint to update profile
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

