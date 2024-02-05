package com.mechamate.repo;

import com.mechamate.entity.Maintenance;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface MaintenanceRepository extends MongoRepository<Maintenance, Long> {

}
