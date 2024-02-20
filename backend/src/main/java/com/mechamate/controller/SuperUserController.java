package com.mechamate.controller;

import com.mechamate.common.Common;
import com.mechamate.common.Validation;
import com.mechamate.dto.ErrorDTO;
import com.mechamate.dto.MaintenanceDTO;
import com.mechamate.dto.PredictionModelDTO;
import com.mechamate.dto.SuccessDTO;
import com.mechamate.entity.*;
import com.mechamate.service.LanguageManager;
import com.mechamate.service.ProfileManager;
import com.mechamate.service.SessionManager;
import com.mechamate.service.SuperUserActionManager;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Date;

@RestController
@RequestMapping("/api/v1/super")
public class SuperUserController {

    @Autowired
    private SessionManager sessionManager;

    @Autowired
    private ProfileManager profileManager;

    @Autowired
    private SuperUserActionManager superUserActionManager;

    @Autowired
    private LanguageManager lang;



    @PostMapping("/add-maintenance")
    public ResponseEntity<?> addMaintenance(HttpServletRequest request, HttpServletResponse response,
                                            @RequestBody(required = false) MaintenanceDTO maintenanceDTO) {
        Object obj = Validation.authenticate(request, response, sessionManager, lang);
        if(!(obj instanceof UserProfile)) return (ResponseEntity<ErrorDTO>) (obj);
        UserProfile userProfile = (UserProfile) obj;

        if(!userProfile.isSuperUser())
            return new ResponseEntity<>
                    (new ErrorDTO(ErrorDTO.ErrorStatus.ErrorUnauthorized,
                            lang.get("error.superuser.operation", userProfile.getLanguage()),
                            lang.get("error.superuser.operation.help", userProfile.getLanguage())),
                            HttpStatus.UNAUTHORIZED);

        if(maintenanceDTO == null)
            return new ResponseEntity<>
                    (new ErrorDTO(ErrorDTO.ErrorStatus.ErrorInvalidRequest,
                            lang.get("error.mobj.notfound", userProfile.getLanguage()),
                            lang.get("error.mobj.notfound.help", userProfile.getLanguage())),
                            HttpStatus.BAD_REQUEST);

        if (maintenanceDTO.getMaintenanceId() == null || maintenanceDTO.getMaintenanceId().isEmpty()) maintenanceDTO.setMaintenanceId("");
        if (maintenanceDTO.getMaintenanceType() == null) maintenanceDTO.setMaintenanceType(Maintenance.MaintenanceType.Default);
        if (maintenanceDTO.getName() == null || maintenanceDTO.getName().isEmpty()) maintenanceDTO.setName("");
        if (maintenanceDTO.getDescription() == null || maintenanceDTO.getDescription().isEmpty()) maintenanceDTO.setDescription("");
        try {
            if (maintenanceDTO.getPredictionModels() == null || maintenanceDTO.getPredictionModels().isEmpty())
                maintenanceDTO.setPredictionModels(new ArrayList<>());
        } catch (Exception e) {}

        Maintenance maintenance = new Maintenance(maintenanceDTO.getMaintenanceType(), maintenanceDTO.getName(),
                maintenanceDTO.getDescription(), Common.getPredictionModelObjectIds(maintenanceDTO.getPredictionModels()));

        try {
            maintenance.set_id(new ObjectId(maintenanceDTO.getMaintenanceId()));
        } catch (Exception e) {}

        ResponseEntity<ErrorDTO> resp = superUserActionManager.addMaintenance(maintenance, userProfile);
        if(resp != null) return resp;

        return new ResponseEntity<>
                (new SuccessDTO(SuccessDTO.SuccessStatus.OperationSucceeded,
                        lang.get("success.addmobj.succeeded", userProfile.getLanguage()),
                        lang.get("success.addmobj.succeeded.info", userProfile.getLanguage())),
                        HttpStatus.OK);

    }




    @GetMapping("/get-maintenance-list")
    public ResponseEntity<?> getMaintenanceList(HttpServletRequest request, HttpServletResponse response) {
        Object obj = Validation.authenticate(request, response, sessionManager, lang);
        if(!(obj instanceof UserProfile)) return (ResponseEntity<ErrorDTO>) (obj);
        UserProfile userProfile = (UserProfile) obj;
        if(!userProfile.isSuperUser())
            return new ResponseEntity<>
                    (new ErrorDTO(ErrorDTO.ErrorStatus.ErrorUnauthorized,
                            lang.get("error.superuser.operation", userProfile.getLanguage()),
                            lang.get("error.superuser.operation.help", userProfile.getLanguage())),
                            HttpStatus.UNAUTHORIZED);

        return superUserActionManager.getMaintenanceList();
    }





    @PostMapping("/delete-maintenance")
    public ResponseEntity<?> deleteMaintenance(HttpServletRequest request, HttpServletResponse response,
                                               @RequestBody(required = false) MaintenanceDTO maintenanceDTO) {
        Object obj = Validation.authenticate(request, response, sessionManager, lang);
        if(!(obj instanceof UserProfile)) return (ResponseEntity<ErrorDTO>) (obj);
        UserProfile userProfile = (UserProfile) obj;
        if(!userProfile.isSuperUser())
            return new ResponseEntity<>
                    (new ErrorDTO(ErrorDTO.ErrorStatus.ErrorUnauthorized,
                            lang.get("error.superuser.operation", userProfile.getLanguage()),
                            lang.get("error.superuser.operation.help", userProfile.getLanguage())),
                            HttpStatus.UNAUTHORIZED);

        if(maintenanceDTO == null)
            return new ResponseEntity<>
                    (new ErrorDTO(ErrorDTO.ErrorStatus.ErrorInvalidRequest,
                            lang.get("error.mobj.notfound", userProfile.getLanguage()),
                            lang.get("error.mobj.notfound.help", userProfile.getLanguage())),
                            HttpStatus.BAD_REQUEST);

        if (maintenanceDTO.getMaintenanceId() == null || maintenanceDTO.getMaintenanceId().isEmpty()) maintenanceDTO.setMaintenanceId("");

        Maintenance maintenance = new Maintenance(Maintenance.MaintenanceType.Default, "", "", null);

        try {
            maintenance.set_id(new ObjectId(maintenanceDTO.getMaintenanceId()));
        } catch (Exception e) {}

        ResponseEntity<ErrorDTO> resp = superUserActionManager.deleteMaintenance(maintenance, userProfile);
        if(resp != null) return resp;

        return new ResponseEntity<>
                (new SuccessDTO(SuccessDTO.SuccessStatus.OperationSucceeded,
                        lang.get("success.delmobj.succeeded", userProfile.getLanguage()),
                        lang.get("success.delmobj.succeeded.info", userProfile.getLanguage())),
                        HttpStatus.OK);

    }





    @PostMapping("/add-prediction-model")
    public ResponseEntity<?> addPredictionModel(HttpServletRequest request, HttpServletResponse response,
                                            @RequestBody(required = false) PredictionModelDTO predictionModelDTO) {
        Object obj = Validation.authenticate(request, response, sessionManager, lang);
        if(!(obj instanceof UserProfile)) return (ResponseEntity<ErrorDTO>) (obj);
        UserProfile userProfile = (UserProfile) obj;

        if(!userProfile.isSuperUser())
            return new ResponseEntity<>
                    (new ErrorDTO(ErrorDTO.ErrorStatus.ErrorUnauthorized,
                            lang.get("error.superuser.operation", userProfile.getLanguage()),
                            lang.get("error.superuser.operation.help", userProfile.getLanguage())),
                            HttpStatus.UNAUTHORIZED);

        if(predictionModelDTO == null)
            return new ResponseEntity<>
                    (new ErrorDTO(ErrorDTO.ErrorStatus.ErrorInvalidRequest,
                            lang.get("error.pmodel.notfound", userProfile.getLanguage()),
                            lang.get("error.pmodel.notfound.help", userProfile.getLanguage())),
                            HttpStatus.BAD_REQUEST);

        if (predictionModelDTO.getModelId() == null || predictionModelDTO.getModelId().isEmpty()) predictionModelDTO.setModelId("");
        if (predictionModelDTO.getName() == null || predictionModelDTO.getName().isEmpty()) predictionModelDTO.setName("<No name>");
        if (predictionModelDTO.getDescription() == null || predictionModelDTO.getDescription().isEmpty()) predictionModelDTO.setDescription("<No description>");
        try {
            if (predictionModelDTO.getAppliedMaintenanceList() == null || predictionModelDTO.getAppliedMaintenanceList().isEmpty())
                predictionModelDTO.setAppliedMaintenanceList(new ArrayList<>());
        } catch (Exception e) {}

        PredictionModel predictionModel = new PredictionModel(predictionModelDTO.getName(), predictionModelDTO.getDescription(),
                predictionModelDTO.getAppliedMaintenanceList());

        try {
            predictionModel.set_id(new ObjectId(predictionModelDTO.getModelId()));
        } catch (Exception e) {}


        ResponseEntity<ErrorDTO> resp = superUserActionManager.addPredictionModel(predictionModel, userProfile);
        if(resp != null) return resp;

        return new ResponseEntity<>
                (new SuccessDTO(SuccessDTO.SuccessStatus.OperationSucceeded,
                        lang.get("success.addpmodel.succeeded", userProfile.getLanguage()),
                        lang.get("success.addpmodel.succeeded.info", userProfile.getLanguage())),
                        HttpStatus.OK);

    }




    @GetMapping("/get-prediction-model-list")
    public ResponseEntity<?> getPredictionModelList(HttpServletRequest request, HttpServletResponse response) {
        Object obj = Validation.authenticate(request, response, sessionManager, lang);
        if(!(obj instanceof UserProfile)) return (ResponseEntity<ErrorDTO>) (obj);
        UserProfile userProfile = (UserProfile) obj;
        if(!userProfile.isSuperUser())
            return new ResponseEntity<>
                    (new ErrorDTO(ErrorDTO.ErrorStatus.ErrorUnauthorized,
                            lang.get("error.superuser.operation", userProfile.getLanguage()),
                            lang.get("error.superuser.operation.help", userProfile.getLanguage())),
                            HttpStatus.UNAUTHORIZED);

        return superUserActionManager.getPredictionModelList();
    }





    @PostMapping("/delete-prediction-model")
    public ResponseEntity<?> deletePredictionModel(HttpServletRequest request, HttpServletResponse response,
                                               @RequestBody(required = false) PredictionModelDTO predictionModelDTO) {
        Object obj = Validation.authenticate(request, response, sessionManager, lang);
        if(!(obj instanceof UserProfile)) return (ResponseEntity<ErrorDTO>) (obj);
        UserProfile userProfile = (UserProfile) obj;
        if(!userProfile.isSuperUser())
            return new ResponseEntity<>
                    (new ErrorDTO(ErrorDTO.ErrorStatus.ErrorUnauthorized,
                            lang.get("error.superuser.operation", userProfile.getLanguage()),
                            lang.get("error.superuser.operation.help", userProfile.getLanguage())),
                            HttpStatus.UNAUTHORIZED);

        if(predictionModelDTO == null)
            return new ResponseEntity<>
                    (new ErrorDTO(ErrorDTO.ErrorStatus.ErrorInvalidRequest,
                            lang.get("error.pmodel.notfound", userProfile.getLanguage()),
                            lang.get("error.pmodel.notfound.help", userProfile.getLanguage())),
                            HttpStatus.BAD_REQUEST);

        if (predictionModelDTO.getModelId() == null || predictionModelDTO.getModelId().isEmpty()) predictionModelDTO.setModelId("");

        PredictionModel predictionModel = new PredictionModel("", "", null);

        try {
            predictionModel.set_id(new ObjectId(predictionModelDTO.getModelId()));
        } catch (Exception e) {}

        ResponseEntity<ErrorDTO> resp = superUserActionManager.deletePredictionModel(predictionModel, userProfile);
        if(resp != null) return resp;

        return new ResponseEntity<>
                (new SuccessDTO(SuccessDTO.SuccessStatus.OperationSucceeded,
                        lang.get("success.delpmodel.succeeded", userProfile.getLanguage()),
                        lang.get("success.delpmodel.succeeded.info", userProfile.getLanguage())),
                        HttpStatus.OK);

    }





}

