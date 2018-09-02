package com.utn.tacs.eventmanager.controllers;

import com.utn.tacs.eventmanager.controllers.dto.EventUsersDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/events")
public class EventController {

	@GetMapping("/{eventId}/users")
	public ResponseEntity<EventUsersDTO> getEventUsers(@PathVariable Integer eventId) {
		EventUsersDTO usersInterested = new EventUsersDTO();
		usersInterested.setAmmount(1);
		return new ResponseEntity<>(usersInterested,HttpStatus.OK);
	}

}
