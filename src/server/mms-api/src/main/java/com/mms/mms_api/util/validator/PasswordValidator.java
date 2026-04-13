package com.mms.mms_api.util.validator;

import java.util.Objects;

import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;

import com.mms.mms_api.annotation.PasswordMatch;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

/**
 * Validates password and confirm-password fields for password match rules.
 */
public class PasswordValidator implements ConstraintValidator<PasswordMatch, Object> {
    private int minLength;

    private boolean ignoreEmpty;

    private static final String SPECIAL_CHARACTERS = "!@#$%^&*()-_=+[]{};:',.?/";

    private static final String PASSWORD_PROPERTY = "password";

    private static final String CONFIRM_PASSWORD_PROPERTY = "confirmPassword";

    /**
     * Initializes the validator with configuration from the {@link com.mms.mms_api.annotation.PasswordMatch} annotation.
     *
     * @param annotation the annotation instance containing the minimum length and ignoreEmpty flag
     */
    @Override
    public void initialize(PasswordMatch annotation) {
        this.minLength = annotation.min();
        this.ignoreEmpty = annotation.ignoreEmpty();
    }

    /**
     * Validates that the password and confirm-password fields match and meet strength requirements.
     *
     * <p>Performs the following validations:
     * <ul>
     *   <li>The password field is not blank (unless ignoreEmpty is configured).</li>
     *   <li>The password meets all complexity requirements (see {@link #containRequirements(String)}).</li>
     *   <li>The confirm-password field is not blank.</li>
     *   <li>The password and confirm-password fields are equal.</li>
     * </ul>
     *
     * @param value the object containing the password and confirm-password properties
     * @param context the constraint validator context used to build custom violations
     * @return {@code true} if all validations pass; {@code false} otherwise
     */
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

    /**
     * Disables the default constraint violation and adds a custom violation on the specified property.
     *
     * @param context the constraint validator context
     * @param message the violation message template
     * @param property the name of the property node to attach the violation to
     * @return always returns {@code false} to signal validation failure
     */
    private boolean buildViolation(ConstraintValidatorContext context, String message, String property) {
        context.disableDefaultConstraintViolation();
        context.buildConstraintViolationWithTemplate(message)
                .addPropertyNode(property)
                .addConstraintViolation();
        return false;
    }

    /**
     * Checks that the password meets all strength requirements.
     *
     * <p>The password must satisfy all of the following:
     * <ul>
     *   <li>Length is at least {@code minLength} characters.</li>
     *   <li>Contains at least one uppercase letter.</li>
     *   <li>Contains at least one lowercase letter.</li>
     *   <li>Contains at least one digit.</li>
     *   <li>Contains at least one special character from the allowed set.</li>
     *   <li>Contains no whitespace characters.</li>
     * </ul>
     *
     * @param password the password string to check
     * @return {@code true} if the password meets all requirements; {@code false} otherwise
     */
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