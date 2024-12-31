package com.nat.demo.repository;


import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

import com.nat.demo.entity.DemoEntity;

public interface DemoRepository extends MongoRepository<DemoEntity, ObjectId> {
    
}