package com.mechamate.repo;

import com.mechamate.entity.Session;
import com.mechamate.entity.Vehicle;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.ExistsQuery;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository("Vehicles")
public interface VehicleRepo extends MongoRepository<Vehicle, ObjectId> {
    @Query("{owner:?0}")
    public List<Vehicle> findByOwner(ObjectId owner);
    @ExistsQuery("{owner:?0}")
    public boolean existsByOwner(ObjectId owner);

    @Query("{regNo:?0}")
    public Optional<Vehicle> findByRegNo(String regNo);
    @ExistsQuery("{regNo:?0}")
    public boolean existsByRegNo(String regNo);

}
