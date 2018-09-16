package com.utn.tacs.eventmanager.services;

import com.utn.tacs.eventmanager.dao.Event;
import com.utn.tacs.eventmanager.repositories.EventRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EventService {

	@Autowired
	EventRepository eventRepository;

	public void addEvent(Event event) {
		eventRepository.save(event);
	}
}
