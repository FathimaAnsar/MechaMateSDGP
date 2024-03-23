package com.mechamate.features;

import com.mechamate.entity.*;
import com.mechamate.service.DatabaseAbstractLayer;
import org.apache.commons.math3.fitting.PolynomialCurveFitter;
import org.apache.commons.math3.fitting.WeightedObservedPoints;
import org.apache.commons.math3.stat.regression.SimpleRegression;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.stream.DoubleStream;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;





@Service
public class PdM {

    private static final Logger logger = LoggerFactory.getLogger(DatabaseAbstractLayer.class);

    public PredictionModel getTrainedPredictionModel(String csvFileName, String name, String description,
                                                     PredictionModel.modelType modelType,
                                                     List<Maintenance.MaintenanceType> appliedMaintenanceList) {

        PredictionModel predictionModel = null;

        if (modelType == PredictionModel.modelType.linear){
            Double[] trainedData = trainLinearModel(csvFileName);
            predictionModel = new LinearRegressionModel(name, description, modelType,  trainedData[0], trainedData[1],
                                    appliedMaintenanceList);
        } else if (modelType == PredictionModel.modelType.polynomial) {
            Double[] trainedData = trainPolynomialmodel(csvFileName);
            predictionModel = new PolynomialRegressionModel(name, description, modelType, trainedData[0], trainedData[1],
                    trainedData[2], appliedMaintenanceList);
        } else {
            logger.error("Failed to train model");
        }

        return predictionModel;
    }

    private Double[] trainLinearModel(String csvFileName){
        logger.info("Start training linear model");

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

        Double[] result = new Double[2];
        result[0] = regression.getSlope();
        result[1] = regression.getIntercept();

        return result;
    }

    private static Double[] trainPolynomialmodel(String filePath) {
        logger.info("Start training polynomial model");

        WeightedObservedPoints obs = new WeightedObservedPoints();

        try (BufferedReader reader = new BufferedReader(
                new FileReader(System.getProperty("user.dir") + "/datasets/" + filePath))) {
            String line;
            boolean skipFirstLine = true;
            while ((line = reader.readLine()) != null) {
                if (skipFirstLine) {
                    skipFirstLine = false;
                    continue;
                }
                String[] parts = line.split(",");
                if (parts.length == 2) {
                    double x = Double.parseDouble(parts[0]);
                    double y = Double.parseDouble(parts[1]);
                    obs.add(x, y);
                }
            }
        } catch (IOException e) {
            logger.warn("Error: ", e);
            e.printStackTrace();
            return null;
        }

        PolynomialCurveFitter fitter = PolynomialCurveFitter.create(2);
        double[] coefficients = fitter.fit(obs.toList());

        logger.warn("");

        Double[] coefficientsDouble = {coefficients[2], coefficients[1], coefficients[0]};
        logger.info("Polynomial model trained successfully");
        return coefficientsDouble;
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
