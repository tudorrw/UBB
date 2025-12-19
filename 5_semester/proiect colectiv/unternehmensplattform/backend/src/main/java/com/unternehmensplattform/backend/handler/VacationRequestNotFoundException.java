package com.unternehmensplattform.backend.handler;

public class VacationRequestNotFoundException extends RuntimeException{
    public VacationRequestNotFoundException(String message) {
        super(message);
    }
}
