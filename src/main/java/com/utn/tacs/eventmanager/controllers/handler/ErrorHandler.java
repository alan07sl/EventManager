package com.utn.tacs.eventmanager.controllers.handler;

import com.utn.tacs.eventmanager.errors.CustomError;
import com.utn.tacs.eventmanager.errors.CustomException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ErrorHandler {

    @ExceptionHandler(CustomException.class)
    public ResponseEntity<CustomError> handleBusinessException(CustomException customException) {
        return new ResponseEntity<>(new CustomError(customException.getMessage(),
                        customException.getDescription(),
                        customException.getStatus().value()), customException.getStatus());
    }

}
