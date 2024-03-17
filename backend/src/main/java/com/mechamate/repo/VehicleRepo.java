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

// Annotation to specify this interface as a repository
@Repository("Vehicles")
public interface VehicleRepo extends MongoRepository<Vehicle, ObjectId> {

    // Custom query to find vehicles by owner
    @Query("{owner:?0}")
    public List<Vehicle> findByOwner(ObjectId owner);

    // Custom query to check if vehicles exist for a given owner
    @ExistsQuery("{owner:?0}")
    public boolean existsByOwner(ObjectId owner);

    // Custom query to find a vehicle by registration number
    @Query("{regNo:?0}")
    public Optional<Vehicle> findByRegNo(String regNo);

    // Custom query to check if a vehicle with the given registration number exists
    @ExistsQuery("{regNo:?0}")
    public boolean existsByRegNo(String regNo);
}
