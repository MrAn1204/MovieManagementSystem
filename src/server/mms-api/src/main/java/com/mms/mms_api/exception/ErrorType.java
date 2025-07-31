package com.mms.mms_api.exception;

public enum ErrorType {
    RESOURCE_NOT_FOUND("Resource Not Found"),
    INVALID_INPUT("Invalid Input");

    private final String value;

    ErrorType(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}