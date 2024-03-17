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

// Annotation to specify this interface as a repository
@Repository("Maintenance")
public interface MaintenanceRepo extends MongoRepository<Maintenance, ObjectId> {

    // Custom query to find maintenance records by prediction model
    @Query("{'predictionModels': {$elemMatch: {$eq: ?0}}}")
    public List<Maintenance> findByPredictionModel(ObjectId predictionModel);
}

