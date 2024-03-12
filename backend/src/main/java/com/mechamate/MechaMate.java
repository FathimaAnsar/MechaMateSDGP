package com.mechamate;

import com.mechamate.entity.Maintenance;
import com.mechamate.entity.PredictionModel;
import com.mechamate.features.PdM;
import org.slf4j.LoggerFactory;
import org.slf4j.Logger;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.ArrayList;
import java.util.List;

@SpringBootApplication
public class MechaMate {

	private static final Logger logger = LoggerFactory.getLogger(MechaMate.class);

	public static void main(String[] args) {


	//	double x = 248.5;

		List<Maintenance.MaintenanceType> maintenanceTypes = new ArrayList<>();

		maintenanceTypes.add(Maintenance.MaintenanceType.EngineOilChange);

		PredictionModel predictionModel = PdM.getTrainedPredictionModel(
				"engine-heat-dataset.csv", "Engine Oil",
				"This model applies to predict lifespan of engine oil",maintenanceTypes);

		System.out.println(predictionModel.toString());

		return;

//		SpringApplication.run(MechaMate.class, args);

	}


}


