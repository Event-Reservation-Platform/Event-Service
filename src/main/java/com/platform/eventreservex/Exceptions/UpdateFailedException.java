package com.platform.eventreservex.Exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class UpdateFailedException extends ResponseStatusException {
    public UpdateFailedException(String message) {
        super(HttpStatus.BAD_REQUEST, message);
    }
}
