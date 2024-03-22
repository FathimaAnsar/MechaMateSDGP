package com.mechamate.features;

import com.mechamate.common.Validation;
import com.mechamate.dto.ErrorDTO;
import com.mechamate.entity.*;
import jakarta.servlet.http.HttpServletRequest;
import org.apache.commons.math3.stat.regression.SimpleRegression;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

@Service
public class PdM {



    public PredictionModel getTrainedPredictionModel(String csvFileName, String name, String description,
                                                     List<Maintenance.MaintenanceType> appliedMaintenanceList) {

        SimpleRegression regression = new SimpleRegression();
        try (Scanner scanner = new Scanner(new FileReader(System.getProperty("user.dir") + "/datasets/" + csvFileName))) {
            while (scanner.hasNextLine()) {
                try {
                    String[] data = scanner.nextLine().split(",");
                    double x = Double.parseDouble(data[0]);
                    double y = Double.parseDouble(data[1]);
                    regression.addData(x, y);
                } catch (Exception e) {}
            }
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }

        PredictionModel predictionModel = new PredictionModel(name, description, regression.getSlope(),
                regression.getIntercept(), appliedMaintenanceList);
        return predictionModel;
    }


    public double predict(double m, double c, double x) {
        return m * x + c;
    }


    public ArrayList<ServiceRecord> getServiceRecordsFiltered(Maintenance.MaintenanceType maintenanceType,
                                                       String vehicleRegNo) {
        return null;
    }

    public ArrayList<PredictionModel> getFilteredPredictionModels(Maintenance.MaintenanceType maintenanceType) {
        return null;
    }

    public ArrayList<TrackingInfo> collectTrackingData(Maintenance.MaintenanceType maintenanceType) {
        return null;
    }

    public double processPrediction(ArrayList<PredictionModel> predictionModels,
                                               ArrayList<TrackingInfo> trackingInfos,
                                               ArrayList<ServiceRecord> serviceRecords) {


        return 0.0;
    }



    // 1. Filter service records of vehicle by maintenance
    // 2. Filter prediction models by maintenance
    // 3. Collect tracking data by vehicle reg no
    // 4. Loop through tracking data and calculate the predicted output recursively using trained prediction models
    // 5. Find the difference between the last maintenance KMs against current KMs. (ie: (givenKMs - predictedKMs) )
    // 6. return the predicted output



}
