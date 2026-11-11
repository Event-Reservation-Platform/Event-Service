package com.platform.eventreservex.Component.Exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class ServiceTimeOutException extends ResponseStatusException {
    public ServiceTimeOutException(String message) { super(HttpStatus.REQUEST_TIMEOUT, message); }
}
