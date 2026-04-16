package com.mms.mms_api.util.validator;

import java.util.List;
import java.util.UUID;

import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import com.mms.mms_api.business.command.auth.RegisterCommand;
import com.mms.mms_api.business.command.user.UserCreateCommand;
import com.mms.mms_api.business.command.user.UserUpdateCommand;
import com.mms.mms_api.business.service.validation.RoleValidationService;
import com.mms.mms_api.business.service.validation.UserValidationService;
import com.mms.mms_api.exception.ErrorSet;
import com.mms.mms_api.exception.ErrorType;

import lombok.AllArgsConstructor;

/**
 * Validator for user-related create, update, and registration commands.
 */
@Component
@AllArgsConstructor
public class UserValidator implements BaseValidator {
    private final UserValidationService userValidationService;

    private final RoleValidationService roleValidationService;

    /**
     * Validates a user create command.
     *
     * <p>Performs the following validations:
     * <ul>
     *   <li>At least one role is provided and all referenced roles exist.</li>
     *   <li>The username is not already taken.</li>
     *   <li>The email address is not already taken, if provided.</li>
     *   <li>The phone number is not already taken.</li>
     * </ul>
     *
     * @param command the user create command to validate
     * @throws com.mms.mms_api.exception.InvalidInputException if any field fails validation
     */
    public void validate(UserCreateCommand command) {
        ErrorSet errors = new ErrorSet();

        validateRoles(errors, command.getRoleIds());
        validateUsername(errors, command.getUsername());
        validateEmail(errors, command.getEmail());
        validatePhoneNumber(errors, command.getPhoneNumber());

        errors.throwIfNotEmpty(ErrorType.INVALID_INPUT);
    }

    /**
     * Validates a user update command.
     *
     * <p>Performs the following validations:
     * <ul>
     *   <li>A user with the given id exists.</li>
     *   <li>At least one role is provided and all referenced roles exist.</li>
     *   <li>The email address is not already taken by another user, if provided.</li>
     *   <li>The phone number is not already taken by another user.</li>
     * </ul>
     *
     * @param command the user update command to validate
     * @throws com.mms.mms_api.exception.ResourceNotFoundException if the user is not found
     * @throws com.mms.mms_api.exception.InvalidInputException if any field fails validation
     */
    public void validate(UserUpdateCommand command) {
        ErrorSet errors = new ErrorSet();

        validateId(errors, command.getId());

        errors.throwIfNotEmpty(ErrorType.RESOURCE_NOT_FOUND);

        validateRoles(errors, command.getRoleIds());
        validateEmail(errors, command.getEmail(), command.getId());
        validatePhoneNumber(errors, command.getPhoneNumber(), command.getId());

        errors.throwIfNotEmpty(ErrorType.INVALID_INPUT);

    }

    /**
     * Validates a registration command.
     *
     * <p>Performs the following validations:
     * <ul>
     *   <li>The username is not already taken.</li>
     *   <li>The email address is not already taken, if provided.</li>
     *   <li>The phone number is not already taken.</li>
     * </ul>
     *
     * @param command the registration command to validate
     * @throws com.mms.mms_api.exception.InvalidInputException if any field fails validation
     */
    public void validate(RegisterCommand command) {
        ErrorSet errors = new ErrorSet();

        validateUsername(errors, command.getUsername());
        validateEmail(errors, command.getEmail());
        validatePhoneNumber(errors, command.getPhoneNumber());

        errors.throwIfNotEmpty(ErrorType.INVALID_INPUT);
    }

    /**
     * Checks that a user with the given id exists and adds an error if not.
     *
     * @param errors the error accumulator
     * @param id the user id to look up
     */
    private void validateId(ErrorSet errors, @NonNull UUID id) {
        if (!userValidationService.existsById(id)) {
            errors.add("id", "user.notFound");
        }
    }

    /**
     * Checks that the role list is not empty and all referenced roles exist.
     *
     * @param errors the error accumulator
     * @param roleIds the list of role ids to validate
     */
    private void validateRoles(ErrorSet errors, List<UUID> roleIds) {
        if (CollectionUtils.isEmpty(roleIds)) {
            errors.add("roleIds", "user.roles.required");
            return;
        }

        if (!roleValidationService.existsAllByIdIn(roleIds)) {
            errors.add("roleIds", "user.roles.invalid");
        }
    }

    /**
     * Checks that the username is not already taken and adds an error if it is.
     *
     * @param errors the error accumulator
     * @param username the username to check for uniqueness
     */
    private void validateUsername(ErrorSet errors, String username) {
        if (userValidationService.existsByUsername(username)) {
            errors.add("username", "user.username.unique");
        }
    }

    /**
     * Checks that the email address is not already taken and adds an error if it is.
     * Skips validation if the email is null or blank.
     *
     * @param errors the error accumulator
     * @param email the email address to check for uniqueness
     */
    private void validateEmail(ErrorSet errors, String email) {
        if (email == null || email.isBlank()) {
            return;
        }

        if (userValidationService.existsByEmail(email)) {
            errors.add("email", "user.email.unique");
        }
    }

    /**
     * Checks that the email address is not already taken by another user and adds an error if it is.
     * Skips validation if the email is null or blank.
     *
     * @param errors the error accumulator
     * @param email the email address to check for uniqueness
     * @param id the id of the user being updated, excluded from the uniqueness check
     */
    private void validateEmail(ErrorSet errors, String email, UUID id) {
        if (email == null || email.isBlank()) {
            return;
        }

        if (userValidationService.existsByEmailAndIdNot(email, id)) {
            errors.add("email", "user.email.unique");
        }
    }

    /**
     * Checks that the phone number is not already taken and adds an error if it is.
     *
     * @param errors the error accumulator
     * @param phoneNumber the phone number to check for uniqueness
     */
    private void validatePhoneNumber(ErrorSet errors, String phoneNumber) {
        if (userValidationService.existsByPhoneNumber(phoneNumber)) {
            errors.add("phoneNumber", "user.phone.unique");
        }
    }

    /**
     * Checks that the phone number is not already taken by another user and adds an error if it is.
     *
     * @param errors the error accumulator
     * @param phoneNumber the phone number to check for uniqueness
     * @param id the id of the user being updated, excluded from the uniqueness check
     */
    private void validatePhoneNumber(ErrorSet errors, String phoneNumber, UUID id) {
        if (userValidationService.existsByPhoneNumberAndIdNot(phoneNumber, id)) {
            errors.add("phoneNumber", "user.phone.unique");
        }
    }
}
