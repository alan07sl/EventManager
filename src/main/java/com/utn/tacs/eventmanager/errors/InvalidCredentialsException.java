package com.utn.tacs.eventmanager.errors;

import com.utn.tacs.eventmanager.dao.User;
import org.springframework.http.HttpStatus;

public class InvalidCredentialsException extends CustomException {
  public InvalidCredentialsException() {
    super("INVALID_CREDENTIALS", "Invalid credentials", HttpStatus.UNPROCESSABLE_ENTITY);
  }
}
