package com.mechamate.repo;

import com.mechamate.entity.UserProfile;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface UserProfileRepository extends MongoRepository<UserProfile, Long> {
}
