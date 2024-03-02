package com.mechamate.repo;

import com.mechamate.entity.ServiceRecord;
import com.mechamate.entity.TrackingInfo;
import com.mechamate.entity.Vehicle;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.ExistsQuery;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository("TrackingInfo")
public interface TrackingInfoRepo extends MongoRepository<TrackingInfo, ObjectId> {

    @Query("{vehicleRegNo:?0}")
    public Optional<TrackingInfo> findByVehicleRegNo(String vehicleRegNo);
    @Query("{vehicleRegNo:?0}")
    public List<TrackingInfo> findAllByVehicleRegNo(String vehicleRegNo);
    @ExistsQuery("{vehicleRegNo:?0}")
    public boolean existsByVehicleRegNo(String vehicleRegNo);

}
