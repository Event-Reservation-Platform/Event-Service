package com.platform.eventreservex.Exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class EventNotFoundException extends ResponseStatusException {
    public EventNotFoundException(String message) {
        super(HttpStatus.NOT_FOUND, message);
    }
}
