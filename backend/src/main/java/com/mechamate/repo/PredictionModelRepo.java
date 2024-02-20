package com.mechamate.repo;

import com.mechamate.entity.Maintenance;
import com.mechamate.entity.PredictionModel;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.ExistsQuery;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("PredictionModels")
public interface PredictionModelRepo extends MongoRepository<PredictionModel, ObjectId> {
    @Query("{'appliedMaintenanceList': {$elemMatch: {$eq: ?0}}}")
    public List<PredictionModel> findByMaintenanceType(Maintenance.MaintenanceType maintenanceType);

}

