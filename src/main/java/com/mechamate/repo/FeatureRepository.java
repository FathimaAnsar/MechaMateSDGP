package com.mechamate.repo;

import com.mechamate.entity.Feature;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface FeatureRepository extends MongoRepository<Feature, String> {
}
