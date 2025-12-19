package com.unternehmensplattform.backend.handler;

public class WorkingDaySameDatesException extends RuntimeException {
    public WorkingDaySameDatesException(String message) {
        super(message);
    }
}
