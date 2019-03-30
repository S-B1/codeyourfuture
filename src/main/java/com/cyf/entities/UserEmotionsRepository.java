package com.cyf.entities;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface UserEmotionsRepository extends MongoRepository<UserEmotions, String>
{
}
