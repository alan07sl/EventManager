package com.utn.tacs.eventmanager.errors;

import org.springframework.http.HttpStatus;

public class UserNotFoundException extends CustomException {

    public UserNotFoundException() {
        super("USER_NOT_FOUND", "User not found", HttpStatus.BAD_REQUEST);
    }
}
