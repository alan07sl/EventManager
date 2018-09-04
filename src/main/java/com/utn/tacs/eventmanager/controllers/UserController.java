package com.utn.tacs.eventmanager.controllers;

import com.utn.tacs.eventmanager.controllers.dto.EventListDTO;
import com.utn.tacs.eventmanager.controllers.dto.ListDTO;
import com.utn.tacs.eventmanager.controllers.dto.UserDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;

@RestController
@RequestMapping("/users")
public class UserController {

    @PostMapping
    public ResponseEntity<Object> createUser(@RequestBody UserDTO user) {
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<UserDTO> getUsers(
            @RequestParam(value = "name", required = false) String name,
            @RequestParam(value = "page", defaultValue = "1") Integer page,
            @RequestParam(value = "size", defaultValue = "10") Integer size ){
        ListDTO<UserDTO> list = new ListDTO<>();
        list.setNext("/events?page=2");
        list.setPrev("/events?page=1");
        list.setPageCount(3);
        list.setPageNumber(page);
        list.setResultCount(100);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserDTO> getById(@PathVariable String id) {
        return new ResponseEntity<>(HttpStatus.OK);
    }
}