package com.platform.eventreservex.Exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class InvalidArgumentException extends ResponseStatusException {
    public InvalidArgumentException(String message) {
        super(HttpStatus.BAD_REQUEST, message);
    }
}
