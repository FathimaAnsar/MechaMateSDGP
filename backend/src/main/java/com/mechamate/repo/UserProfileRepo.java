package com.mechamate.repo;

import com.mechamate.entity.UserProfile;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.ExistsQuery;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.Optional;

// Annotation to specify this interface as a repository
@Repository("UserProfiles")
public interface UserProfileRepo extends MongoRepository<UserProfile, ObjectId> {

    // Custom query to find a user profile by username
    @Query("{username:?0}")
    public Optional<UserProfile> findByUsername(String username);

    // Custom query to check if a user profile with the given username exists
    @ExistsQuery("{username:?0}")
    public boolean existsByUsername(String username);

    // Custom query to find a user profile by email
    @Query("{email:?0}")
    public Optional<UserProfile> findByEmail(String email);

    // Custom query to check if a user profile with the given email exists
    @ExistsQuery("{email:?0}")
    public boolean existsByEmail(String email);

    // Custom query to find a user profile by recovery key
    @Query("{recoveryKey:?0}")
    public Optional<UserProfile> findByRecoveryKey(String recoveryKey);

    // Custom query to check if a user profile with the given recovery key exists
    @ExistsQuery("{recoveryKey:?0}")
    public boolean existsByRecoveryKey(String recoveryKey);
}
