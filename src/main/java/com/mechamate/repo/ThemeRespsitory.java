package com.mechamate.repo;

import com.mechamate.entity.Theme;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ThemeRespsitory extends MongoRepository<Theme, Integer> {
}
