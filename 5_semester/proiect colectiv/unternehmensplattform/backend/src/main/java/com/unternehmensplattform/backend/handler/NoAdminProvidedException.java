package com.unternehmensplattform.backend.handler;

public class NoAdminProvidedException extends RuntimeException {
    public NoAdminProvidedException(String message ) {super(message);}
}
