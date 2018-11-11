package com.utn.tacs.eventmanager.repositories;

import com.utn.tacs.eventmanager.dao.User;
import org.springframework.data.mongodb.repository.MongoRepository;


public interface UserRepository extends MongoRepository<User,String> {
    User findByUsername(String username);
}
