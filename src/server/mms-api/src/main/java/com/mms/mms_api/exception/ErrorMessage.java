package com.mms.mms_api.exception;

public enum ErrorMessage {
    USER_NOT_FOUND("Cannot find user with the provided id"),

    BLANK_USERNAME("Username cannot be blank."),

    BLANK_FULLNAME("Fullname cannot be blank."),

    BLANK_PASSWORD("Password cannot be blank."),

    GENDER_REQUIRED("Gender is required."),

    DOB_REQUIRED("Date of birth is required."),

    BLANK_PHONE_NUMBER("Phone number cannot be blank."),

    ROLES_REQUIRED("At least one role must be assigned to the user."),

    INVALID_ROLES("One or more roles provided are invalid.");

    private final String value;

    ErrorMessage(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}