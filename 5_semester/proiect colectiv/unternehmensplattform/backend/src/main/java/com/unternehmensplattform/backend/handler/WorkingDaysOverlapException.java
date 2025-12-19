package com.unternehmensplattform.backend.handler;

public class WorkingDaysOverlapException extends RuntimeException {
    public WorkingDaysOverlapException(String message) {
        super(message);
    }
}
