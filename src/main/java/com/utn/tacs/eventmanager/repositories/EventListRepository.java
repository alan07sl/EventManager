package com.utn.tacs.eventmanager.repositories;

import com.utn.tacs.eventmanager.dao.EventList;
import com.utn.tacs.eventmanager.dao.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Date;
import java.util.List;
import java.util.Set;


public interface EventListRepository extends JpaRepository<EventList,Long> {

    List<EventList> findByCreationDateAfter(Date creationDate);

    List<EventList> findByEventsContains(Long eventId);

}
