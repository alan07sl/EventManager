package com.utn.tacs.eventmanager.services;

import com.utn.tacs.eventmanager.dao.EventList;
import com.utn.tacs.eventmanager.errors.CustomException;
import com.utn.tacs.eventmanager.errors.EventListNotFoundException;
import com.utn.tacs.eventmanager.repositories.EventListRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class EventListService {

    @Autowired
    private EventListRepository eventListRepository;

    public void createEventList(EventList eventList) {
        eventList.setCreationDate(new Date());
        eventListRepository.save(eventList);
    }

    public void updateEventList(Integer id, EventList eventList) throws CustomException {
        EventList actualEventList = findById(id);
        actualEventList.setName(eventList.getName());
        eventListRepository.save(actualEventList);
    }

    public void addEvent(Integer id, Long eventId) throws CustomException {
        EventList eventList = findById(id);
        eventList.getEvents().add(eventId);
        eventListRepository.save(eventList);
    }

    public EventList findById(Integer id) throws CustomException {
        return eventListRepository.findById(id.longValue()).orElseThrow(() -> new EventListNotFoundException());
    }

    public void delete(Long id) {
        if(eventListRepository.existsById(id)) {
            eventListRepository.deleteById(id);
        }
    }

    public Integer countEvents(Date from) {
        List<Long> events = eventListRepository.findByCreationDateAfter(from).stream()
                .flatMap((EventList e) -> e.getEvents().stream())
                .collect(Collectors.toList());
        return events.size();
    }

    public Page<EventList> searchPaginated(String name, Integer page, Integer size) {
        EventList eventList = new EventList(name.length() > 0 ? name : null);
        Pageable pageable = new PageRequest(page - 1, size);
        return eventListRepository.findAll(Example.of(eventList), pageable);
    }

    public Integer usersInterested(Long eventId) {
        return eventListRepository.findByEventsContains(eventId).stream().map((EventList e) -> e.getUser()).distinct().collect(Collectors.toList()).size();
    }
}