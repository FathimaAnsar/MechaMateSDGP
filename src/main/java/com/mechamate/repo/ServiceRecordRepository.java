package com.mechamate.repo;

import com.mechamate.service.ServiceRecord;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ServiceRecordRepository extends MongoRepository<ServiceRecord, String> {
}
