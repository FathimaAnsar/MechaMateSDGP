package com.mechamate.service;

import com.mechamate.dto.ErrorDTO;
import com.mechamate.dto.MaintenanceDTO;
import com.mechamate.dto.PredictionModelDTO;
import com.mechamate.entity.*;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class SuperUserActionManager {

    @Autowired
    private DatabaseAbstractLayer databaseAbstractLayer;

    @Autowired
    private NotificationManager notificationManager;

    @Autowired
    private SessionManager sessionManager;

    @Autowired
    private LanguageManager lang;



    public ResponseEntity<ErrorDTO> addMaintenance(Maintenance maintenance, UserProfile userProfile) {

        if(maintenance.get_id() != null && databaseAbstractLayer.isMaintenanceExists(maintenance)) {
            return new ResponseEntity<>
                    (new ErrorDTO(ErrorDTO.ErrorStatus.ErrorInvalidRequest,
                            lang.get("error.mobj.exists", userProfile.getLanguage()),
                            lang.get("error.mobj.exists.help", userProfile.getLanguage())),
                            HttpStatus.BAD_REQUEST);
        }

        if(!databaseAbstractLayer.addMaintenance(maintenance))
            return new ResponseEntity<>
                    (new ErrorDTO(ErrorDTO.ErrorStatus.ErrorOperationFailed,
                            lang.get("error.mobj.add.failed", userProfile.getLanguage()),
                            lang.get("error.mobj.add.failed.help", userProfile.getLanguage())),
                            HttpStatus.INTERNAL_SERVER_ERROR);

        return null;
    }


    public ResponseEntity<List<MaintenanceDTO>> getMaintenanceList() {
        List<MaintenanceDTO> maintenanceDTOS = new ArrayList<>();
        try {
            List<Maintenance> maintenances = databaseAbstractLayer.getMaintenanceList();
            for(Maintenance m: maintenances) {
                List<ObjectId> objectIds = m.getPredictionModels();
                List<String> pModels = new ArrayList<>();
                for(ObjectId obj : objectIds) pModels.add(obj.toHexString());
                maintenanceDTOS.add(new MaintenanceDTO(m.get_id().toHexString(), m.getMaintenanceType(), m.getName(),
                        m.getDescription(), pModels));
            }
        } catch (Exception e) {}
        return new ResponseEntity<>(maintenanceDTOS, HttpStatus.OK);
    }




    public ResponseEntity<ErrorDTO> deleteMaintenance(Maintenance maintenance, UserProfile userProfile) {

        if(maintenance.get_id() == null || !databaseAbstractLayer.isMaintenanceExists(maintenance)) {
            return new ResponseEntity<>
                    (new ErrorDTO(ErrorDTO.ErrorStatus.ErrorInvalidRequest,
                            lang.get("error.mobj.doesnt.exist", userProfile.getLanguage()),
                            lang.get("error.mobj.doesnt.exist.help", userProfile.getLanguage())),
                            HttpStatus.BAD_REQUEST);
        }

        if(!databaseAbstractLayer.deleteMaintenance(maintenance))
            return new ResponseEntity<>
                    (new ErrorDTO(ErrorDTO.ErrorStatus.ErrorOperationFailed,
                            lang.get("error.mobj.del.failed", userProfile.getLanguage()),
                            lang.get("error.mobj.del.failed.help", userProfile.getLanguage())),
                            HttpStatus.INTERNAL_SERVER_ERROR);

        return null;
    }













    public ResponseEntity<ErrorDTO> addPredictionModel(PredictionModel predictionModel, UserProfile userProfile) {

        if(predictionModel.get_id() != null && databaseAbstractLayer.isPredictionModelExists(predictionModel)) {
            return new ResponseEntity<>
                    (new ErrorDTO(ErrorDTO.ErrorStatus.ErrorInvalidRequest,
                            lang.get("error.pmodel.exists", userProfile.getLanguage()),
                            lang.get("error.pmodel.exists.help", userProfile.getLanguage())),
                            HttpStatus.BAD_REQUEST);
        }

        if(!databaseAbstractLayer.addPredictionModel(predictionModel))
            return new ResponseEntity<>
                    (new ErrorDTO(ErrorDTO.ErrorStatus.ErrorOperationFailed,
                            lang.get("error.pmodel.add.failed", userProfile.getLanguage()),
                            lang.get("error.pmodel.add.failed.help", userProfile.getLanguage())),
                            HttpStatus.INTERNAL_SERVER_ERROR);

        return null;
    }


    public ResponseEntity<List<PredictionModelDTO>> getPredictionModelList() {
        List<PredictionModelDTO> predictionModelDTOS = new ArrayList<>();
        try {
            List<PredictionModel> predictionModels = databaseAbstractLayer.getPredictionModelList();
            for(PredictionModel m: predictionModels) {
                predictionModelDTOS.add(new PredictionModelDTO(m.get_id().toHexString(), m.getName(), m.getDescription(),
                        m.getAppliedMaintenanceList()));
            }
        } catch (Exception e) {}
        return new ResponseEntity<>(predictionModelDTOS, HttpStatus.OK);
    }




    public ResponseEntity<ErrorDTO> deletePredictionModel(PredictionModel predictionModel, UserProfile userProfile) {

        if(predictionModel.get_id() == null || !databaseAbstractLayer.isPredictionModelExists(predictionModel)) {
            return new ResponseEntity<>
                    (new ErrorDTO(ErrorDTO.ErrorStatus.ErrorInvalidRequest,
                            lang.get("error.pmodel.doesnt.exist", userProfile.getLanguage()),
                            lang.get("error.pmodel.doesnt.exist.help", userProfile.getLanguage())),
                            HttpStatus.BAD_REQUEST);
        }

        if(!databaseAbstractLayer.deletePredictionModel(predictionModel))
            return new ResponseEntity<>
                    (new ErrorDTO(ErrorDTO.ErrorStatus.ErrorOperationFailed,
                            lang.get("error.pmodel.del.failed", userProfile.getLanguage()),
                            lang.get("error.pmodel.del.failed.help", userProfile.getLanguage())),
                            HttpStatus.INTERNAL_SERVER_ERROR);

        return null;
    }



}


