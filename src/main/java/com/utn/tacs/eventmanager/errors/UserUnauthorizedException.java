package com.utn.tacs.eventmanager.errors;

import org.springframework.http.HttpStatus;

public class UserUnauthorizedException extends CustomException {

    public UserUnauthorizedException() {
        super("USER_UNAUTHORIZED", "User unauthorized", HttpStatus.UNAUTHORIZED);
    }
}
