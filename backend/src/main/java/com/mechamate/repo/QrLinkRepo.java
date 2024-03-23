package com.mechamate.repo;

import com.mechamate.entity.QrLink;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.ExistsQuery;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository("QrLinks")
public interface QrLinkRepo extends MongoRepository<QrLink, ObjectId> {

    @Query("{qrKey:?0}")
    public Optional<QrLink> findByQrKey(String qrKey);

    @ExistsQuery("{qrKey:?0}")
    public boolean existsByQrKey(String qrKey);
}

