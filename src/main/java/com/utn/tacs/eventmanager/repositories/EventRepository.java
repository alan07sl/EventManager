package com.utn.tacs.eventmanager.repositories;

import com.utn.tacs.eventmanager.dao.Event;
import org.springframework.data.mongodb.repository.MongoRepository;


public interface EventRepository extends MongoRepository<Event,String> {

}
