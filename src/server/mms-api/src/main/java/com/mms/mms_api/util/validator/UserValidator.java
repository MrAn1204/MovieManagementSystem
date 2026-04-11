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

    public void validate(UserCreateCommand command) {
        ErrorSet errors = new ErrorSet();

        validateRoles(errors, command.getRoleIds());
        validateUsername(errors, command.getUsername());
        validateEmail(errors, command.getEmail());
        validatePhoneNumber(errors, command.getPhoneNumber());

        errors.throwIfNotEmpty(ErrorType.INVALID_INPUT);
    }

    public void validate(UserUpdateCommand command) {
        ErrorSet errors = new ErrorSet();

        validateId(errors, command.getId());

        errors.throwIfNotEmpty(ErrorType.RESOURCE_NOT_FOUND);

        validateRoles(errors, command.getRoleIds());
        validateEmail(errors, command.getEmail(), command.getId());
        validatePhoneNumber(errors, command.getPhoneNumber(), command.getId());

        errors.throwIfNotEmpty(ErrorType.INVALID_INPUT);

    }

    public void validate(RegisterCommand command) {
        ErrorSet errors = new ErrorSet();

        validateUsername(errors, command.getUsername());
        validateEmail(errors, command.getEmail());
        validatePhoneNumber(errors, command.getPhoneNumber());

        errors.throwIfNotEmpty(ErrorType.INVALID_INPUT);
    }

    private void validateId(ErrorSet errors, @NonNull UUID id) {
        if (!userValidationService.existsById(id)) {
            errors.add("id", "user.notFound");
        }
    }

    private void validateRoles(ErrorSet errors, List<UUID> roleIds) {
        if (CollectionUtils.isEmpty(roleIds)) {
            errors.add("roleIds", "user.roles.required");
            return;
        }

        if (!roleValidationService.existsAllByIdIn(roleIds)) {
            errors.add("roleIds", "user.roles.invalid");
        }
    }

    private void validateUsername(ErrorSet errors, String username) {
        if (userValidationService.existsByUsername(username)) {
            errors.add("username", "user.username.unique");
        }
    }

    private void validateEmail(ErrorSet errors, String email) {
        if (email == null || email.isBlank()) {
            return;
        }

        if (userValidationService.existsByEmail(email)) {
            errors.add("email", "user.email.unique");
        }
    }

    private void validateEmail(ErrorSet errors, String email, UUID id) {
        if (email == null || email.isBlank()) {
            return;
        }

        if (userValidationService.existsByEmailAndIdNot(email, id)) {
            errors.add("email", "user.email.unique");
        }
    }

    private void validatePhoneNumber(ErrorSet errors, String phoneNumber) {
        if (userValidationService.existsByPhoneNumber(phoneNumber)) {
            errors.add("phoneNumber", "user.phone.unique");
        }
    }

    private void validatePhoneNumber(ErrorSet errors, String phoneNumber, UUID id) {
        if (userValidationService.existsByPhoneNumberAndIdNot(phoneNumber, id)) {
            errors.add("phoneNumber", "user.phone.unique");
        }
    }
}
