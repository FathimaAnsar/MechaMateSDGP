package com.mechamate.repo;

import com.mechamate.entity.Feature;
import com.mechamate.entity.Session;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.ExistsQuery;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository("Features")
public interface FeatureRepo extends MongoRepository<Feature, ObjectId> {
    @Query("{sessionKey:?0}")
    public Optional<Session> findBySessionKey(String sessionKey);
    @ExistsQuery("{sessionKey:?0}")
    public boolean existsBySessionKey(String sessionKey);

}
