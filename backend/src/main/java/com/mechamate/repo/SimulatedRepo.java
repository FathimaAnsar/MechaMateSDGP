package com.mechamate.repo;
import com.mechamate.entity.SimulatedData;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.ExistsQuery;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.Optional;

public interface SimulatedRepo extends MongoRepository<SimulatedData, ObjectId> {
    @Query("{simulatedKey:?0}")
    public Optional<SimulatedData> findBySimulatedKey(String simulatedKey);

    @ExistsQuery("{simulatedKey:?0}")
    public boolean existsBySimulatedKey(String simulatedKey);

}
