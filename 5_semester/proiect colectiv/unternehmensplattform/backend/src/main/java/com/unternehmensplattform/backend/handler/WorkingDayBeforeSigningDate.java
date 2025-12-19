package com.unternehmensplattform.backend.handler;

public class WorkingDayBeforeSigningDate extends RuntimeException {
    public WorkingDayBeforeSigningDate(String message) {
        super(message);
    }
}
