package com.utn.tacs.eventmanager.controllers;

import com.utn.tacs.eventmanager.controllers.dto.*;
import com.utn.tacs.eventmanager.dao.EventList;
import com.utn.tacs.eventmanager.errors.CustomException;
import com.utn.tacs.eventmanager.repositories.EventListRepository;
import com.utn.tacs.eventmanager.services.EventListService;
import com.utn.tacs.eventmanager.services.EventbriteService;
import com.utn.tacs.eventmanager.services.UserService;
import ma.glasnost.orika.MapperFacade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.*;
import java.util.stream.Collectors;

@RestController
@CrossOrigin
@RequestMapping("/events_lists")
public class EventListController {

    @Autowired
    private EventListService eventListService;

    @Autowired
    private UserService userService;

    @Autowired
    private EventbriteService eventbriteService;

    @Autowired
    private MapperFacade orikaMapper;

    @GetMapping
    public ResponseEntity<ListDTO<EventListDTO>> getEventsLists(@RequestParam(value = "name", required = false, defaultValue = "") String name,
           @RequestParam(value = "page", defaultValue = "1") Integer page,
           @RequestParam(value = "size", defaultValue = "10") Integer size ) {

        Page<EventList> result = eventListService.searchPaginated(name, page, size);

        ListDTO<EventListDTO> list = new ListDTO<>();
        list.setPageNumber(page);
        list.setPageCount(result.getTotalPages());
        list.setResultCount(result.getTotalElements());
        list.setResult(result.getContent().stream().map((EventList e) -> orikaMapper.map(e, EventListDTO.class)).collect(Collectors.toList()));
        list.setNext(result.hasNext() ? "/events_lists?page="+ (list.getPageNumber() + 1) + "&name=" + name + "&size=" + size : null);
        list.setPrev(list.getPageNumber() > 1 ? "/events_lists?page="+ (list.getPageNumber() - 1) + "&name=" + name + "&size=" + size : null);

        return new ResponseEntity<>(list,HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Object> createEventList(@Valid @RequestBody EventListDTO eventListDTO) {
        EventList eventList = orikaMapper.map(eventListDTO, EventList.class);
        eventList.setUser(userService.findCurrentUser());
        eventListService.createEventList(eventList);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PatchMapping("/{eventListId}")
    public ResponseEntity<Object> addEvent(@PathVariable Integer eventListId, @Valid @RequestBody EventDTO event) throws CustomException {
        eventbriteService.getEvent(event.getId());
        eventListService.addEvent(eventListId,event.getId(), userService.findCurrentUser());
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PutMapping("/{eventListId}")
    public ResponseEntity<Object> modifyEventList(@PathVariable Integer eventListId,
                                                  @Valid @RequestBody EventListDTO eventList) throws CustomException {
        eventListService.updateEventList(eventListId,orikaMapper.map(eventList, EventList.class), userService.findCurrentUser());
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/{eventListId}")
    public ResponseEntity<Object> deleteEventList(@PathVariable Integer eventListId) throws CustomException {
        eventListService.delete(eventListId.longValue(), userService.findCurrentUser());
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/events")
    public ResponseEntity<Object> getEvents(@RequestParam("from") @DateTimeFormat(pattern="yyyy-MM-dd") Date from) {
        return new ResponseEntity<>(new EventStatsDTO(eventListService.countEvents(from)),HttpStatus.OK);
    }

    @GetMapping("/match")
    public ResponseEntity<List<Map<String,Object>>> getCommonEvents(@RequestParam("eventListId1") Integer eventListId,
                                                                    @RequestParam("eventListId2") Integer eventListId2) throws CustomException{
        EventList eventList1 = eventListService.findById(eventListId);
        EventList eventList2 = eventListService.findById(eventListId2);

        Set<Long> commonEventsIds = new HashSet<>(eventList1.getEvents());
        commonEventsIds.retainAll(eventList2.getEvents());

        return new ResponseEntity<>(eventbriteService.getEvents(commonEventsIds),HttpStatus.OK);
    }

    @GetMapping("/{eventListId}/events")
    public ResponseEntity<List<Map<String,Object>>> getEventsFromEventList(@PathVariable Integer eventListId) throws CustomException {
        EventList eventList = eventListService.findById(eventListId);
        return new ResponseEntity<>(eventbriteService.getEvents(eventList.getEvents()),HttpStatus.OK);
    }
}
