package com.mechamate.features;

import com.mechamate.dto.PredictionDTO;
import com.mechamate.dto.ServiceDTO;
import com.mechamate.dto.ServiceRecordDTO;
import com.mechamate.dto.VehicleDTO;
import com.mechamate.entity.*;
import com.mechamate.service.DatabaseAbstractLayer;
import com.mechamate.service.LanguageManager;
import com.mechamate.service.NotificationManager;
import com.mechamate.service.SessionManager;
import org.apache.catalina.User;
import org.apache.commons.math3.fitting.PolynomialCurveFitter;
import org.apache.commons.math3.fitting.WeightedObservedPoints;
import org.apache.commons.math3.stat.regression.SimpleRegression;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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

    @Autowired
    private DatabaseAbstractLayer databaseAbstractLayer;

    @Autowired
    private NotificationManager notificationManager;

    @Autowired
    private SessionManager sessionManager;

    @Autowired
    private LanguageManager lang;


    public PredictionModel getTrainedPredictionModel(String csvFileName, String name, String description,
                                                     PredictionModel.modelType modelType,
                                                     List<Maintenance.MaintenanceType> appliedMaintenanceList, PredictionModel.ParameterType paramType) {

        PredictionModel predictionModel = null;

        if (modelType == PredictionModel.modelType.linear){
            Double[] trainedData = trainLinearModel(csvFileName);
            predictionModel = new LinearRegressionModel(name, description, modelType,  trainedData[0], trainedData[1],
                                    appliedMaintenanceList, paramType);
        } else if (modelType == PredictionModel.modelType.polynomial) {
            Double[] trainedData = trainPolynomialmodel(csvFileName);
            predictionModel = new PolynomialRegressionModel(name, description, modelType, trainedData[0], trainedData[1],
                    trainedData[2], appliedMaintenanceList, paramType);
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



    public ResponseEntity<?> getPredictions(List<PredictionDTO> predictionDTOS, UserProfile userProfile,
                                            List<VehicleDTO> vehicles) {

        List<Maintenance.MaintenanceType> maintenanceTypes = new ArrayList<>();

        maintenanceTypes.add(Maintenance.MaintenanceType.EngineOilChange);
        maintenanceTypes.add(Maintenance.MaintenanceType.CoolantChange);
        maintenanceTypes.add(Maintenance.MaintenanceType.PistonChange);
        maintenanceTypes.add(Maintenance.MaintenanceType.TireChange);
        maintenanceTypes.add(Maintenance.MaintenanceType.BrakeCaliperChange);
        maintenanceTypes.add(Maintenance.MaintenanceType.BrakeFluidChange);
        maintenanceTypes.add(Maintenance.MaintenanceType.WheelAlignment);

        for (VehicleDTO v : vehicles) {
            List<ServiceRecordDTO> serviceRecordDTOS = v.getServiceRecords();
            for(Maintenance.MaintenanceType mType : maintenanceTypes) {
                if(serviceRecordDTOS.isEmpty()) {
                    predictionDTOS.add(new PredictionDTO(mType, PredictionDTO.PredictionStatus.ServiceInfoNotFound,
                            0, 0, false));
                    continue;
                }
                long addedDate = 0; ServiceDTO serviceDTO = null; long serviceMileage = 0;
                for(ServiceRecordDTO svcRec : serviceRecordDTOS) {
                    if(svcRec.getServices().isEmpty()) continue;
                    List<ServiceDTO> svDTOS = svcRec.getServices();
                    for(ServiceDTO svDTO : svDTOS) {
                        if(svDTO.getAppliedMaintenanceId() == mType) {
                            if(svDTO.getAddedDate() > addedDate && !svDTO.isDone()) {
                                addedDate = svDTO.getAddedDate();
                                serviceDTO = svDTO;
                                serviceMileage = svcRec.getMileage();
                            }
                        }
                    }
                }

                if(serviceDTO == null) {
                    predictionDTOS.add(new PredictionDTO(mType, PredictionDTO.PredictionStatus.ServiceInfoNotFound,
                            0, 0, false));
                    continue;
                }

                long currentMileage = 0;
                List<TrackingInfo> trackInfo = databaseAbstractLayer.getTrackingInfo(v.getRegistrationNumber());
                for (TrackingInfo tInfo : trackInfo) {
                    if(currentMileage < tInfo.getMileage()) currentMileage = tInfo.getMileage();
                }

                if(trackInfo.isEmpty()) {
                    predictionDTOS.add(new PredictionDTO(mType, PredictionDTO.PredictionStatus.TrackingInfoNotFound,
                            0, 0, false));
                    continue;
                }

                long actualKMsRemaining = (serviceMileage + serviceDTO.getNextServiceInKMs()) - currentMileage;

                List<PredictionModel> predictionModels = databaseAbstractLayer.getPredictionModelListByMaintenance(mType);
                if(predictionModels.isEmpty()) {
                    predictionDTOS.add(new PredictionDTO(mType, PredictionDTO.PredictionStatus.Actual,
                            actualKMsRemaining, actualKMsRemaining, (actualKMsRemaining <= 0)));
                    continue;
                }

                double totalPredicted = 0; double prevMileage = serviceMileage;

                int modelCount = 0;
                for(PredictionModel pModel : predictionModels) {
                    for (TrackingInfo tInfo : trackInfo) {
                        if (tInfo.getDateTime() < serviceDTO.getAddedDate()) continue;
                        double a = 0, b = 0, c = 0, m = 0, x = 0, y = 0;
                        if(pModel.getParamType() == PredictionModel.ParameterType.EngineTemp) x = tInfo.getEnginTemp();
                        else if(pModel.getParamType() == PredictionModel.ParameterType.Vibration) x = tInfo.getVibration();
                        else if(pModel.getParamType() == PredictionModel.ParameterType.DrivingPattern) x = tInfo.getDrivingPattern();
                        else if(pModel.getParamType() == PredictionModel.ParameterType.RPM) x = tInfo.getEngineRPM();
                        else if(pModel.getParamType() == PredictionModel.ParameterType.HourOfTheDay) x = tInfo.getHourOfDay();
                        else
                            continue;

                        if(pModel instanceof LinearRegressionModel) {
                            m = ((LinearRegressionModel) pModel).getmValue();
                            c = ((LinearRegressionModel) pModel).getcValue();
                            y = (m * x) + c;
                        } else if(pModel instanceof PolynomialRegressionModel) {
                            a = ((PolynomialRegressionModel) pModel).getAValue();
                            b = ((PolynomialRegressionModel) pModel).getBValue();
                            c = ((PolynomialRegressionModel) pModel).getCValue();
                            y = (a * (x * x)) + (b * x) + c;
                        }

                        double calcKMs = (tInfo.getMileage() - prevMileage);
                        prevMileage = tInfo.getMileage();
                        totalPredicted += (calcKMs * y);
                    }
                    modelCount++;
                }

                totalPredicted = totalPredicted / modelCount;
                totalPredicted = serviceMileage + totalPredicted;
                totalPredicted = (serviceMileage + serviceDTO.getNextServiceInKMs()) - totalPredicted;

                predictionDTOS.add(new PredictionDTO(mType, PredictionDTO.PredictionStatus.Predicted,
                        actualKMsRemaining, (long) totalPredicted, (totalPredicted <= 0)));
            }
        }

        return new ResponseEntity<>(predictionDTOS, HttpStatus.OK);
    }















}
