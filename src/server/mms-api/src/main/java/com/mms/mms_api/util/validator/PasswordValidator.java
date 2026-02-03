package com.mms.mms_api.util.validator;

import java.util.Objects;

import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;

import com.mms.mms_api.annotation.PasswordMatch;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class PasswordValidator implements ConstraintValidator<PasswordMatch, Object> {
    private int minLength;

    private boolean ignoreEmpty;

    private static final String SPECIAL_CHARACTERS = "!@#$%^&*()-_=+[]{};:',.?/";

    private static final String PASSWORD_PROPERTY = "password";

    private static final String CONFIRM_PASSWORD_PROPERTY = "confirmPassword";

    @Override
    public void initialize(PasswordMatch annotation) {
        this.minLength = annotation.min();
        this.ignoreEmpty = annotation.ignoreEmpty();
    }

    @Override
    public boolean isValid(Object value, ConstraintValidatorContext context) {
        BeanWrapper wrapper = new BeanWrapperImpl(Objects.requireNonNull(value));

        String password = (String) wrapper.getPropertyValue(PASSWORD_PROPERTY);
        String confirmPassword = (String) wrapper.getPropertyValue(CONFIRM_PASSWORD_PROPERTY);

        if (password == null || password.isBlank()) {
            return ignoreEmpty || buildViolation(context, "{user.password.required}", PASSWORD_PROPERTY);
        }

        if (!containRequirements(password)) {
            return buildViolation(context, "{user.password.invalid}", PASSWORD_PROPERTY);
        }

        if (confirmPassword == null || confirmPassword.isBlank()) {
            return buildViolation(context, "{user.confirmPassword.required}", CONFIRM_PASSWORD_PROPERTY);
        }

        if (!password.equals(confirmPassword)) {
            return buildViolation(context, "{user.password.mismatched}", CONFIRM_PASSWORD_PROPERTY);
        }

        return true;
    }

    private boolean buildViolation(ConstraintValidatorContext context, String message, String property) {
        context.disableDefaultConstraintViolation();
        context.buildConstraintViolationWithTemplate(message)
                .addPropertyNode(property)
                .addConstraintViolation();
        return false;
    }

    private boolean containRequirements(String password) {
        if (password.length() < minLength) {
            return false;
        }

        boolean hasUpper = false;
        boolean hasLower = false;
        boolean hasDigit = false;
        boolean hasSpecial = false;

        for (char c : password.toCharArray()) {
            if (Character.isWhitespace(c)) {
                return false;
            }

            if (Character.isUpperCase(c)) {
                hasUpper = true;
            }

            if (Character.isLowerCase(c)) {
                hasLower = true;
            }

            if (Character.isDigit(c)) {
                hasDigit = true;
            }

            if (SPECIAL_CHARACTERS.indexOf(c) >= 0) {
                hasSpecial = true;
            }
        }

        return hasUpper && hasLower && hasDigit && hasSpecial;
    }
}