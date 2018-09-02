package com.utn.tacs.eventmanager.controllers;

import com.utn.tacs.eventmanager.controllers.dto.UserDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
public class UserController {

    @PostMapping
    public ResponseEntity<Object> createUser(@RequestBody UserDTO user) {
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<String> getUsers(UserDTO user) {
        return new ResponseEntity<String>("users", HttpStatus.OK);
    }

    @RequestMapping("/{id}")
    @GetMapping
    public @ResponseBody
    ResponseEntity<String>
    getById(@PathVariable String id) {
        return new ResponseEntity<String>("GET ID : "
                + id, HttpStatus.OK);
    }
}