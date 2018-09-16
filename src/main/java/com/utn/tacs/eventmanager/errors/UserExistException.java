package com.utn.tacs.eventmanager.errors;

import org.springframework.http.HttpStatus;

public class UserExistException extends CustomException {

    public UserExistException(String username) {
        super("USER_EXIST", "User with username "+username+ " already exist", HttpStatus.BAD_REQUEST);
    }
}
