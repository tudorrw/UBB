package com.unternehmensplattform.backend.handler;

public class OperationNotPermittedException extends RuntimeException {

    public OperationNotPermittedException() {
    }
    public OperationNotPermittedException(String message) {
        super(message);
    }
}