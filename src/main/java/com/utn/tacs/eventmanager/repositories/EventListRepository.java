package com.utn.tacs.eventmanager.repositories;

import com.utn.tacs.eventmanager.dao.EventList;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Date;
import java.util.List;


public interface EventListRepository extends MongoRepository<EventList,String> {

    List<EventList> findByCreationDateAfter(Date creationDate);

    List<EventList> findByEventsContains(Long eventId);

    List<EventList> findByUserId(String userId);

}
