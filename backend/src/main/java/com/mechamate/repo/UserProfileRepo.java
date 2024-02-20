package com.mechamate.repo;

import com.mechamate.entity.UserProfile;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.ExistsQuery;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository("UserProfiles")
public interface UserProfileRepo extends MongoRepository<UserProfile, ObjectId> {
    @Query("{username:?0}")
    public Optional<UserProfile> findByUsername(String username);
    @ExistsQuery("{username:?0}")
    public boolean existsByUsername(String username);

    @Query("{email:?0}")
    public Optional<UserProfile> findByEmail(String email);
    @ExistsQuery("{email:?0}")
    public boolean existsByEmail(String email);

    @Query("{recoveryKey:?0}")
    public Optional<UserProfile> findByRecoveryKey(String recoveryKey);
    @ExistsQuery("{recoveryKey:?0}")
    public boolean existsByRecoveryKey(String recoveryKey);

}
