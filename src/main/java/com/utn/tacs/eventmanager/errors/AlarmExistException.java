package com.utn.tacs.eventmanager.errors;

import org.springframework.http.HttpStatus;

public class AlarmExistException extends CustomException {

    public AlarmExistException(String name) {
        super("ALARM_EXIST", "Alarm with name " + name + " already exist", HttpStatus.BAD_REQUEST);
    }
}
