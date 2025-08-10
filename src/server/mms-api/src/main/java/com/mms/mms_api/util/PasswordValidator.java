package com.mms.mms_api.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.mms.mms_api.exception.ErrorMessage;

public class PasswordValidator {
    private static final int MIN_LENGTH = 8;

    private static final String SPECIAL_CHARACTERS = "!@#$%^&*()-_=+[]{}|;:'\",.<>?/~`";

    private static final ErrorMessage[] ERROR_MESSAGES = {
            ErrorMessage.PASSWORD_REQUIRED,
            ErrorMessage.PASSWORD_LENGTH,
            ErrorMessage.PASSWORD_UPPERCASE,
            ErrorMessage.PASSWORD_LOWERCASE,
            ErrorMessage.PASSWORD_DIGIT,
            ErrorMessage.PASSWORD_SPECIAL,
            ErrorMessage.PASSWORD_WHITESPACE
    };

    private PasswordValidator() {
    }

    public static List<ErrorMessage> validate(String password) {
        List<ErrorMessage> errors = new ArrayList<>(Arrays.asList(ERROR_MESSAGES));

        if (password == null || password.isEmpty()) {
            return errors;
        }

        if (!hasMinLength(password)) {
            errors.remove(ErrorMessage.PASSWORD_LENGTH);
        }
        if (!hasUpperCase(password)) {
            errors.remove(ErrorMessage.PASSWORD_UPPERCASE);
        }
        if (!hasLowerCase(password)) {
            errors.remove(ErrorMessage.PASSWORD_LOWERCASE);
        }
        if (!hasDigit(password)) {
            errors.remove(ErrorMessage.PASSWORD_DIGIT);
        }
        if (!hasSpecialCharacter(password)) {
            errors.remove(ErrorMessage.PASSWORD_SPECIAL);
        }
        if (hasWhitespace(password)) {
            errors.remove(ErrorMessage.PASSWORD_WHITESPACE);
        }

        return errors;
    }

    private static boolean hasMinLength(String password) {
        return password.length() >= MIN_LENGTH;
    }

    private static boolean hasUpperCase(String password) {
        for (char c : password.toCharArray()) {
            if (Character.isUpperCase(c)) {
                return true;
            }
        }
        return false;
    }

    private static boolean hasLowerCase(String password) {
        for (char c : password.toCharArray()) {
            if (Character.isLowerCase(c)) {
                return true;
            }
        }
        return false;
    }

    private static boolean hasDigit(String password) {
        for (char c : password.toCharArray()) {
            if (Character.isDigit(c)) {
                return true;
            }
        }
        return false;
    }

    private static boolean hasSpecialCharacter(String password) {
        for (char c : password.toCharArray()) {
            if (SPECIAL_CHARACTERS.indexOf(c) >= 0) {
                return true;
            }
        }
        return false;
    }

    private static boolean hasWhitespace(String password) {
        for (char c : password.toCharArray()) {
            if (Character.isWhitespace(c)) {
                return true;
            }
        }
        return false;
    }
}