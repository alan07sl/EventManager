package com.utn.tacs.eventmanager.errors;

import org.springframework.http.HttpStatus;

public class EventListNotFoundException extends CustomException {

    public EventListNotFoundException() {
        super("EVENT_LIST_NOT_FOUND", "Event list not found", HttpStatus.BAD_REQUEST);
    }
}
