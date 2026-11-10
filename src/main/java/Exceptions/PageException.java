package Exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class PageException extends ResponseStatusException {
    public PageException(String message) {
        super(HttpStatus.BAD_REQUEST, message);
    }
}
