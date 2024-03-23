package com.mechamate.service;

import com.mechamate.MechaMate;
import com.mechamate.dto.*;
import com.mechamate.entity.*;
import org.bson.types.ObjectId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class SuperUserActionManager {
    // Logger for logging messages
    private static final Logger logger = LoggerFactory.getLogger(MechaMate.class);

    // Autowired components
    @Autowired
    private DatabaseAbstractLayer databaseAbstractLayer;

    @Autowired
    private NotificationManager notificationManager;

    @Autowired
    private SessionManager sessionManager;

    @Autowired
    private LanguageManager lang;

    // Method to add maintenance
    public ResponseEntity<ErrorDTO> addMaintenance(Maintenance maintenance, UserProfile userProfile) {
        // Check if maintenance already exists
        if(maintenance.get_id() != null && databaseAbstractLayer.isMaintenanceExists(maintenance)) {
            // Log error message
            logger.error("Maintenance object already exists: {}", maintenance);
            // Return error response
            return new ResponseEntity<>
                    (new ErrorDTO(ErrorDTO.ErrorStatus.ErrorInvalidRequest,
                            lang.get("error.mobj.exists", userProfile.getLanguage()),
                            lang.get("error.mobj.exists.help", userProfile.getLanguage())),
                            HttpStatus.OK);
        }

        // Try to add maintenance
        if(!databaseAbstractLayer.addMaintenance(maintenance)) {
            // Log error message
            logger.error("Failed to add maintenance: {}", maintenance);
            // Return error response
            return new ResponseEntity<>(
                    new ErrorDTO(ErrorDTO.ErrorStatus.ErrorOperationFailed,
                            lang.get("error.mobj.add.failed", userProfile.getLanguage()),
                            lang.get("error.mobj.add.failed.help", userProfile.getLanguage())),
                    HttpStatus.OK);
        }

        // Return null if successful (no error)
        return null;
    }

    // Method to get list of maintenance
    public ResponseEntity<List<MaintenanceDTO>> getMaintenanceList() {
        List<MaintenanceDTO> maintenanceDTOS = new ArrayList<>();
        try {
            // Get list of maintenances from database
            List<Maintenance> maintenances = databaseAbstractLayer.getMaintenanceList();
            // Convert each maintenance to DTO
            for(Maintenance m: maintenances) {
                List<ObjectId> objectIds = m.getPredictionModels();
                List<String> pModels = new ArrayList<>();
                for(ObjectId obj : objectIds) pModels.add(obj.toHexString());
                maintenanceDTOS.add(new MaintenanceDTO(m.get_id().toHexString(), m.getMaintenanceType(), m.getName(),
                        m.getDescription(), pModels));
            }
        } catch (Exception e) {
            // Log exception if any
            logger.error("Error while getting maintenance list", e);
        }

        // Return list of maintenance DTOs
        return new ResponseEntity<>(maintenanceDTOS, HttpStatus.OK);
    }

    // Method to delete maintenance
    public ResponseEntity<ErrorDTO> deleteMaintenance(Maintenance maintenance, UserProfile userProfile) {
        // Check if maintenance exists
        if(maintenance.get_id() == null || !databaseAbstractLayer.isMaintenanceExists(maintenance)) {
            // Log error message
            logger.error("Maintenance object doesn't exist: {}", maintenance);
            // Return error response
            return new ResponseEntity<>
                    (new ErrorDTO(ErrorDTO.ErrorStatus.ErrorInvalidRequest,
                            lang.get("error.mobj.doesnt.exist", userProfile.getLanguage()),
                            lang.get("error.mobj.doesnt.exist.help", userProfile.getLanguage())),
                            HttpStatus.OK);
        }
        // Try to delete maintenance
        if(!databaseAbstractLayer.deleteMaintenance(maintenance)) {
            // Log error message
            logger.error("Failed to delete maintenance: {}", maintenance);
            // Return error response
            return new ResponseEntity<>(
                    new ErrorDTO(ErrorDTO.ErrorStatus.ErrorOperationFailed,
                            lang.get("error.mobj.del.failed", userProfile.getLanguage()),
                            lang.get("error.mobj.del.failed.help", userProfile.getLanguage())),
                    HttpStatus.OK);
        }

        // Return null if successful (no error)
        return null;
    }

    // Method to add prediction model
    public ResponseEntity<ErrorDTO> addPredictionModel(PredictionModel predictionModel, UserProfile userProfile) {
        logger.info("Adding prediction model");
        // Check if prediction model already exists
        if(predictionModel.get_id() != null && databaseAbstractLayer.isPredictionModelExists(predictionModel)) {
            // Log error message
            logger.error("Prediction model already exists: {}", predictionModel);
            // Return error response
            return new ResponseEntity<>
                    (new ErrorDTO(ErrorDTO.ErrorStatus.ErrorInvalidRequest,
                            lang.get("error.pmodel.exists", userProfile.getLanguage()),
                            lang.get("error.pmodel.exists.help", userProfile.getLanguage())),
                            HttpStatus.OK);
        }

        // Try to add prediction model
        if(!databaseAbstractLayer.addPredictionModel(predictionModel)) {
            // Log error message
            logger.error("Failed to add prediction model: {}", predictionModel);
            // Return error response
            return new ResponseEntity<>(
                    new ErrorDTO(ErrorDTO.ErrorStatus.ErrorOperationFailed,
                            lang.get("error.pmodel.add.failed", userProfile.getLanguage()),
                            lang.get("error.pmodel.add.failed.help", userProfile.getLanguage())),
                    HttpStatus.OK);
        }

        // Return null if successful (no error)
        return null;
    }

    // Method to get list of prediction models
    public ResponseEntity<List<PredictionModelDTO>> getPredictionModelList() {
        List<PredictionModelDTO> predictionModelDTOS = new ArrayList<>();
        try {
            // Get list of prediction models from database
            List<PredictionModel> predictionModels = databaseAbstractLayer.getPredictionModelList();
            // Convert each prediction model to DTO
            for(PredictionModel model: predictionModels) {
                PredictionModelDTO m = null;
                if (model instanceof LinearRegressionModel) {
                    LinearRegressionModel linearModel = (LinearRegressionModel) model;
                    m = new LinearRegressionModelDTO(model.get_id().toHexString(), model.getName(),
                            model.getDescription(),
                            model.getModelType(),
                            linearModel.getmValue(),
                            linearModel.getcValue(),
                            model.getAppliedMaintenanceList());
                } else if (model instanceof PolynomialRegressionModel) {
                    PolynomialRegressionModel polynomialModel = (PolynomialRegressionModel) model;
                    m = new PolynomialRegressionModelDTO(model.get_id().toHexString(), model.getName(),
                            model.getDescription(),
                            model.getModelType(),
                            polynomialModel.getAValue(),
                            polynomialModel.getBValue(),
                            polynomialModel.getCValue(),
                            model.getAppliedMaintenanceList());
                }
                if (m != null) {
                    predictionModelDTOS.add(m);
                }
            }
        } catch (Exception e) {// Log exception if any
            logger.error("Error while getting prediction model list", e);
        }
        // Return list of prediction model DTOs
        return new ResponseEntity<>(predictionModelDTOS, HttpStatus.OK);
    }

    // Method to delete prediction model
    public ResponseEntity<ErrorDTO> deletePredictionModel(PredictionModel predictionModel, UserProfile userProfile) {
        // Check if prediction model exists
        if(predictionModel.get_id() == null || !databaseAbstractLayer.isPredictionModelExists(predictionModel)) {
            // Log error message
            logger.error("Prediction model doesn't exist: {}", predictionModel);
            // Return error response
            return new ResponseEntity<>
                    (new ErrorDTO(ErrorDTO.ErrorStatus.ErrorInvalidRequest,
                            lang.get("error.pmodel.doesnt.exist", userProfile.getLanguage()),
                            lang.get("error.pmodel.doesnt.exist.help", userProfile.getLanguage())),
                            HttpStatus.OK);
        }

        // Try to delete prediction model
        if(!databaseAbstractLayer.deletePredictionModel(predictionModel)) {
            // Log error message
            logger.error("Failed to delete prediction model: {}", predictionModel);
            // Return error response
            return new ResponseEntity<>(
                    new ErrorDTO(ErrorDTO.ErrorStatus.ErrorOperationFailed,
                            lang.get("error.pmodel.del.failed", userProfile.getLanguage()),
                            lang.get("error.pmodel.del.failed.help", userProfile.getLanguage())),
                    HttpStatus.OK);
        }

        // Return null if successful (no error)
        return null;
    }


}


