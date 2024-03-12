package com.mechamate.features;

import com.mechamate.dto.ErrorDTO;
import com.mechamate.entity.Maintenance;
import com.mechamate.entity.PredictionModel;
import com.mechamate.entity.UserProfile;
import com.mechamate.entity.Vehicle;
import org.apache.commons.math3.stat.regression.SimpleRegression;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.io.FileReader;
import java.io.IOException;
import java.util.List;
import java.util.Scanner;

@Service
public class PdM {


    @Value("${mechamate.dataset.dir}")
    private static String datasetDir = "../datasets/";

    public double predict(double[] coefficients, double x) {
        if (coefficients == null || coefficients.length != 2) {
            throw new IllegalArgumentException("Coefficients array must be of length 2.");
        }
        double m = coefficients[0];
        double c = coefficients[1];
        return m * x + c;
    }


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






}
