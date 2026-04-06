package com.mms.mms_api.exception;

public enum ErrorType {
    CONSTRAINT_VIOLATION("Constraint Violation"),
    RESOURCE_NOT_FOUND("Resource Not Found"),
    INVALID_INPUT("Invalid Input"),
    AUTHENTICATION_FAILED("Authentication Failed"),
    ACCESS_DENIED("Access Denied"),
    SERVER_ERROR("Internal Server Error");

    private final String value;

    ErrorType(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}