package com.mechamate.repo;

import com.mechamate.entity.TrainedModel;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface TrainedModelRepository extends MongoRepository<TrainedModel, Integer> {
}
