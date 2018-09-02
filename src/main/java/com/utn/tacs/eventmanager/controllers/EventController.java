package com.utn.tacs.eventmanager.controllers;

import com.utn.tacs.eventmanager.controllers.dto.EventUsersDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
    public ResponseEntity<String> getEvents(String eventos){
        return new ResponseEntity<String>("Eventos", HttpStatus.OK);
    }

}
