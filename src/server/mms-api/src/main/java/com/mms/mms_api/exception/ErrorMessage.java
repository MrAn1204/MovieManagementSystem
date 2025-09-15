package com.mms.mms_api.exception;

public enum ErrorMessage {
    // General
    NAME_REQUIRED("Name is required."),

    // User-related
    USER_NOT_FOUND("Cannot find user with the provided id"),

    USERNAME_REQUIRED("Username is required."),

    FULLNAME_REQUIRED("Fullname is required."),

    PASSWORD_REQUIRED("Password is required."),

    PASSWORD_LENGTH("Password must have at least 8 characters."),

    PASSWORD_UPPERCASE("Password must have at least one uppercase letter."),

    PASSWORD_LOWERCASE("Password must have at least one lower letter."),

    PASSWORD_DIGIT("Password must have at least one digit."),

    PASSWORD_SPECIAL("Password must have at least one special character."),

    PASSWORD_WHITESPACE("Password must have no whitespace."),

    GENDER_REQUIRED("Gender is required."),

    DOB_REQUIRED("Date of birth is required."),

    PHONE_REQUIRED("Phone number is required."),

    ROLES_REQUIRED("At least one role must be assigned to the user."),

    ROLES_INVALID("One or more roles provided are invalid."),

    // Movie-related
    MOVIE_NOT_FOUND("Cannot find movie with the provided id"),

    GENRES_INVALID("One or more genres provided are invalid."),

    LANGUAGE_INVALID("The provided language is invalid."),

    STUDIOS_INVALID("One or more studios provided are invalid."),

    TALENTS_INVALID("One or more talents provided are invalid."),

    // Room-related
    ROOM_NOT_FOUND("Cannot find room with the provided id"),

    QUANTITY_INVALID("Quantity must be greater than zero."),

    // Seat-related
    SEAT_NOT_FOUND("Cannot find seat with the provided id"),

    COLUMN_INVALID("Column number is out of range."),

    ROW_INVALID("Row number is out of range."),
    
    SEAT_TYPE_INVALID("Seat type is invalid."),

    // Schedule-related
    SCHEDULE_NOT_FOUND("Cannot find schedule with the provided id");

    private final String value;

    ErrorMessage(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}