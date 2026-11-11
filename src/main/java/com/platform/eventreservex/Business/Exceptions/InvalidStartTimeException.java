package com.platform.eventreservex.Business.Exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class InvalidStartTimeException extends ResponseStatusException {
    public InvalidStartTimeException(String message) {
        super(HttpStatus.BAD_REQUEST, message);
    }
}
