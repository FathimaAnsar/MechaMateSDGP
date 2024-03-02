package com.mechamate.features;

import com.mechamate.dto.ErrorDTO;
import com.mechamate.entity.Maintenance;
import com.mechamate.entity.PredictionModel;
import com.mechamate.entity.UserProfile;
import com.mechamate.entity.Vehicle;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PdM {



    public PredictionModel getTrainedPredictionModel(String csvFileName, String name, String description,
                                                     List<Maintenance.MaintenanceType> appliedMaintenanceList) {
        double m = 0;
        double c = 0;




        PredictionModel predictionModel = new PredictionModel(name, description, m, c, appliedMaintenanceList);
        return predictionModel;
    }






}
