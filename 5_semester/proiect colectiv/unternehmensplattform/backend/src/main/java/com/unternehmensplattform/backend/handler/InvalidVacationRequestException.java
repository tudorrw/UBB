package com.unternehmensplattform.backend.handler;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

public class InvalidVacationRequestException extends RuntimeException {
    public InvalidVacationRequestException(String message) {
        super(message);
    }
}
