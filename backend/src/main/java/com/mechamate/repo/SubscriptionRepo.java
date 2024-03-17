package com.mechamate.repo;

import com.mechamate.entity.Session;
import com.mechamate.entity.Subscription;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.ExistsQuery;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

// Annotation to specify this interface as a repository
@Repository("Subscriptions")
public interface SubscriptionRepo extends MongoRepository<Subscription, ObjectId> {

    // Custom query to find a subscription by username
    @Query("{username:?0}")
    public Optional<Subscription> findByUsername(String username);

    // Custom query to check if a subscription with the given username exists
    @ExistsQuery("{username:?0}")
    public boolean existsByUsername(String username);
}
