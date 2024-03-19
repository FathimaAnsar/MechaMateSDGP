package com.mechamate.repo;

import com.mechamate.entity.Feature;
import com.mechamate.entity.Session;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.ExistsQuery;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

// Annotation to specify this interface as a repository
@Repository("Features")
public interface FeatureRepo extends MongoRepository<Feature, ObjectId> {

    // Custom query to find a session by session key
    @Query("{sessionKey:?0}")
    public Optional<Session> findBySessionKey(String sessionKey);

    // Custom query to check if a session with the given session key exists
    @ExistsQuery("{sessionKey:?0}")
    public boolean existsBySessionKey(String sessionKey);
}
