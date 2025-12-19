package com.unternehmensplattform.backend.handler;

public class DuplicateNameException extends RuntimeException {
    public DuplicateNameException(String message) { super (message); }
}
