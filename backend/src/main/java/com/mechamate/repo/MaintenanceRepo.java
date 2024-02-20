package com.mechamate.repo;

import com.mechamate.entity.Maintenance;
import com.mechamate.entity.PredictionModel;
import com.mechamate.entity.ServiceRecord;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.ExistsQuery;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("Maintenance")
public interface MaintenanceRepo extends MongoRepository<Maintenance, ObjectId> {
    @Query("{'predictionModels': {$elemMatch: {$eq: ?0}}}")
    public List<Maintenance> findByPredictionModel(ObjectId predictionModel);
}

