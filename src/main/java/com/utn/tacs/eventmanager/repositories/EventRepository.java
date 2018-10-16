package com.utn.tacs.eventmanager.repositories;

import com.utn.tacs.eventmanager.dao.Event;
import org.springframework.data.jpa.repository.JpaRepository;


public interface EventRepository extends JpaRepository<Event,Long> {

}
