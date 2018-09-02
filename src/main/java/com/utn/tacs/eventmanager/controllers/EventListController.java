package com.utn.tacs.eventmanager.controllers;

import com.utn.tacs.eventmanager.controllers.dto.EventDTO;
import com.utn.tacs.eventmanager.controllers.dto.EventListDTO;
import com.utn.tacs.eventmanager.controllers.dto.ListDTO;
import com.utn.tacs.eventmanager.controllers.dto.UserDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Arrays;

@RestController
@RequestMapping("/events_lists")
public class EventListController {

    @GetMapping
    public ResponseEntity<ListDTO<EventListDTO>> getEventsLists(@RequestParam(value = "name", required = false) String name,
           @RequestParam(value = "page", defaultValue = "1") Integer page,
           @RequestParam(value = "size", defaultValue = "10") Integer size ) {

        ListDTO<EventListDTO> list = new ListDTO<>();
        list.setNext("/events_lists?page=3");
        list.setPrev("/events_lists?page=1");
        list.setPageCount(3);
        list.setPageNumber(page);
        list.setResultCount(100);

        EventListDTO result1 = new EventListDTO();
        result1.setName("r1");
        result1.setId(1);

        EventListDTO result2 = new EventListDTO();
        result2.setId(2);
        result2.setName("r2");

        list.setResult(Arrays.asList(result1,result2));

        return new ResponseEntity<>(list,HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Object> createEventList(@RequestBody EventListDTO eventList) {
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PatchMapping("/{eventListId}")
    public ResponseEntity<Object> addEvent(@PathVariable Integer eventListId, @RequestBody EventDTO event) {
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PutMapping("/{eventListId}")
    public ResponseEntity<Object> modifyEvent(@PathVariable Integer eventListId, @RequestBody EventListDTO eventList) {
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/{eventListId}")
    public ResponseEntity<Object> deleteEventList(@PathVariable Integer eventListId) {
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
