package com.mms.mms_api.exception;

public enum ErrorMessage {
    USER_NOT_FOUND("Cannot find user with the provided id");

    private final String value;

    ErrorMessage(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}