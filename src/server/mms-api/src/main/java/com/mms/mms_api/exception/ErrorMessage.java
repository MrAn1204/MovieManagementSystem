package com.mms.mms_api.exception;

public enum ErrorMessage {
    USER_NOT_FOUND("Cannot find user with the provided id"),

    USERNAME_REQUIRED("Username is required."),

    FULLNAME_REQUIRED("Fullname is required."),

    PASSWORD_REQUIRED("Password is required."),

    GENDER_REQUIRED("Gender is required."),

    DOB_REQUIRED("Date of birth is required."),

    PHONE_REQUIRED("Phone number is required."),

    ROLES_REQUIRED("At least one role must be assigned to the user."),

    ROLES_INVALID("One or more roles provided are invalid."),

    MOVIE_NOT_FOUND("Cannot find movie with the provided id"),

    NAME_REQUIRED("Name is required.");

    private final String value;

    ErrorMessage(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}