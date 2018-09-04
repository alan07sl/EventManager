package com.utn.tacs.eventmanager.controllers;

import com.utn.tacs.eventmanager.controllers.dto.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;

@RestController
@RequestMapping("/events")
public class EventController {

	@GetMapping("/{eventId}/users")
	public ResponseEntity<EventUsersDTO> getEventUsers(@PathVariable Integer eventId) {
		EventUsersDTO usersInterested = new EventUsersDTO();
		usersInterested.setAmount(1);
		return new ResponseEntity<>(usersInterested,HttpStatus.OK);
	}

	@GetMapping
    public ResponseEntity<ListDTO<EventDTO>> getEvents(
    		@RequestParam(value = "name", required = false) String name,
            @RequestParam(value = "page", defaultValue = "1") Integer page,
            @RequestParam(value = "size", defaultValue = "10") Integer size ){
        ListDTO<EventDTO> list = new ListDTO<>();
        list.setNext("/events?page=2");
        list.setPrev("/events?page=1");
        list.setPageCount(3);
        list.setPageNumber(page);
        list.setResultCount(100);

        EventDTO result1 = new EventDTO();
        result1.setId(1);

        EventDTO result2 = new EventDTO();
        result2.setId(2);

        list.setResult(Arrays.asList(result1,result2));

        return new ResponseEntity<>(list,HttpStatus.OK);
    }

}
