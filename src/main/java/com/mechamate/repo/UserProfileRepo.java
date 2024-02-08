package com.mechamate.repo;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.ExistsQuery;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository("UserProfile")
public interface UserProfileRepo extends MongoRepository<UserProfile, ObjectId> {
    @Query("{username:?0}")
    public Optional<UserProfile> findByUsername(String username);
    @ExistsQuery("{username:?0}")
    public boolean existsByUsername(String username);

}
