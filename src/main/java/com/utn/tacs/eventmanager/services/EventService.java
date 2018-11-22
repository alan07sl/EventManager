package com.utn.tacs.eventmanager.services;

import com.utn.tacs.eventmanager.dao.Event;
import com.utn.tacs.eventmanager.repositories.EventRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EventService {

	@Autowired
	EventRepository eventRepository;

	public void addEvent(Event event) {
		eventRepository.save(event);
	}

	public void clearEvents() { eventRepository.deleteAll(); }

	public List<Event> getEventsForAlarm(String alarmId, String userId) {
	    return eventRepository.findAll(Example.of(new Event(userId,alarmId)));
    }
}
