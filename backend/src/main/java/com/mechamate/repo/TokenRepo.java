package com.mechamate.repo;

import com.mechamate.entity.Token;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.ExistsQuery;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository("Tokens")
public interface TokenRepo extends MongoRepository<Token, ObjectId> {
    @Query("{tokenName:?0}")
    public Optional<Token> findByTokenName(String tokenName);
    @ExistsQuery("{tokenName:?0}")
    public boolean existsByTokenName(String tokenName);

}