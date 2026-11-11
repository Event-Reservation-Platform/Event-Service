package com.platform.eventreservex.Business.Exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class EventNotFoundException extends ResponseStatusException {
    public EventNotFoundException(String message) {
        super(HttpStatus.NOT_FOUND, message);
    }
}
