package com.utn.tacs.eventmanager.repositories;

import com.utn.tacs.eventmanager.dao.EventList;
import com.utn.tacs.eventmanager.dao.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Date;
import java.util.List;


public interface EventListRepository extends JpaRepository<EventList,Long> {

    List<EventList> findByCreationDateAfter(Date creationDate);

}
