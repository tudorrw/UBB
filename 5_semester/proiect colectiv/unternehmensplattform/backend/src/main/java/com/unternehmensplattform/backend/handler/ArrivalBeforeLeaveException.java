package com.unternehmensplattform.backend.handler;

public class ArrivalBeforeLeaveException extends RuntimeException {
    public ArrivalBeforeLeaveException(String message) {
        super(message);
    }
}
