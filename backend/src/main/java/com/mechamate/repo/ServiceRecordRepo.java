package com.mechamate.repo;


import com.mechamate.entity.ServiceRecord;
import com.mechamate.entity.Vehicle;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.ExistsQuery;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

// Annotation to specify this interface as a repository
@Repository("ServiceRecords")
public interface ServiceRecordRepo extends MongoRepository<ServiceRecord, ObjectId> {

    // Custom query to find service records by vehicle registration number
    @Query("{vehicleRegNo:?0}")
    public List<ServiceRecord> findByVehicle(String vehicleRegNo);

    // Custom query to check if service records exist for a vehicle with the given registration number
    @ExistsQuery("{vehicleRegNo:?0}")
    public boolean existsByVehicle(String vehicleRegNo);
}

