package com.utn.tacs.eventmanager.controllers;

import com.utn.tacs.eventmanager.controllers.dto.*;
import com.utn.tacs.eventmanager.errors.CustomException;
import com.utn.tacs.eventmanager.services.EventbriteService;
import com.utn.tacs.eventmanager.services.dto.EventsResponseDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/events")
public class EventController {

    @Autowired
    private EventbriteService eventbriteService;

	@GetMapping("/{eventId}/users")
	public ResponseEntity<EventUsersDTO> getEventUsers(@PathVariable Integer eventId) {
		EventUsersDTO usersInterested = new EventUsersDTO();
		usersInterested.setAmount(1);
		return new ResponseEntity<>(usersInterested,HttpStatus.OK);
	}

	@GetMapping
    public ResponseEntity<ListDTO<Map<String,Object>>> getEvents(
    		@RequestParam(value = "page", required = false, defaultValue = "1") String page,
            @RequestParam(value = "query", required = false, defaultValue = "") String query) throws CustomException {
	    EventsResponseDTO response = eventbriteService.getEvents(page,query);

        ListDTO<Map<String,Object>> list = new ListDTO<>();
        list.setResult(response.getEvents());
        list.setPageNumber(response.getPagination().getPageNumber());
        list.setResultCount(response.getPagination().getObjectCount());
        list.setPageCount(response.getPagination().getPageCount());
        list.setNext(response.getPagination().hasMoreItems() ? "/events?page="+ (list.getPageNumber() + 1) + "&query=" + query : null);
        list.setPrev(list.getPageNumber() > 1 ? "/events?page="+ (list.getPageNumber() - 1) + "&query=" + query : null);

        return new ResponseEntity<>(list,HttpStatus.OK);
    }

}
