package com.utn.tacs.eventmanager.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController(value = "/users")
public class UserController {

    @PostMapping
    public @ResponseBody ResponseEntity<Object> createUser() {
        return new ResponseEntity<>(HttpStatus.CREATED);
    }
}
