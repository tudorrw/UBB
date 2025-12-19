package com.unternehmensplattform.backend.handler;


import lombok.Getter;
import org.springframework.http.HttpStatus;

import java.net.http.HttpClient;

import static org.springframework.http.HttpStatus.*;

public enum BusinessErrorCodes {
    NO_CODE(0, "No code", NOT_IMPLEMENTED),
    ACCOUNT_LOCKED(302, "Account locked", FORBIDDEN),
    INCORRECT_CURRENT_PASSWORD(300, "current password is incorrect", BAD_REQUEST),
    NEW_PASSWORD_DOES_NOT_MATCH(301, "password does not match", BAD_REQUEST),
    ACCOUNT_DISABLED(303, "User is disabled", FORBIDDEN),
    BAD_CREDENTIALS(302, "Login and / or password is incorrect", FORBIDDEN),
    PHONE_NUMBER_ALREADY_EXISTS(304, "Phone number already in use", CONFLICT),
    EMAIL_ALREADY_EXISTS(304, "Email already in use", CONFLICT),
    NAME_ALREADY_EXISTS(304, "Company name already in use", CONFLICT),
    NO_ADMIN_PROVIDED(305, "At least one admin registration must be provided.", BAD_REQUEST),
    LEAVE_REQUEST_OVERLAP(304, "The requested dates overlap with an existing vacation request.", CONFLICT),
    ACTIVITY_REPORT_OVERLAP(304, "The specified times overlap with an existing activity report.", CONFLICT),
    LEAVE_REQUEST_INVALID(305, "Invalid vacation request", BAD_REQUEST),
    NO_LEAVE_REQUEST_PROVIDED(305, "No vacation request found.", BAD_REQUEST),
    VALIDATE_DATES(304, "Validation dates error.", CONFLICT),
    ARRIVAL_BEFORE_LEAVE(305, "Arrival date is before leave date", BAD_REQUEST),
    WD_OVERLAP_WITH_VD(305, "Activity report overlaps with an existing vacation request", BAD_REQUEST),
    ALL_DATES_SAME(305, "Date, start date, and end date must all be on the same day.", BAD_REQUEST),
    VD_OVERLAP_WITH_WD(305, "Vacation Request overlaps with an existing activity report", BAD_REQUEST),
    WD_BEFORE_SIGNING_DATE(305, "Activity report should be after signing date", BAD_REQUEST);

    @Getter
    private final int code;
    @Getter
    private final String description;
    @Getter
    private final HttpStatus httpStatus;

    BusinessErrorCodes(int code, String description, HttpStatus httpStatus) {
        this.code = code;
        this.description = description;
        this.httpStatus = httpStatus;
    }
}
