package com.mechamate.repo;

import com.mechamate.entity.Service;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ServiceRepository extends MongoRepository<Service,String> {
}
