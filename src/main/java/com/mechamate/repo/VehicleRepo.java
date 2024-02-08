package com.mechamate.repo;

import com.mechamate.entity.Vehicle;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface VehicleRepo extends MongoRepository<Vehicle, Long> {
}
