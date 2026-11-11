package com.platform.eventreservex.Business.Exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class OutboxCreationFailedException extends ResponseStatusException {
    public OutboxCreationFailedException(String message) {
        super(HttpStatus.INTERNAL_SERVER_ERROR, message);
    }
}
