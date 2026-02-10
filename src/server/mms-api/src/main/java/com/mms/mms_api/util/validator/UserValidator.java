package com.mms.mms_api.util.validator;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import com.mms.mms_api.business.command.user.UserCreateCommand;
import com.mms.mms_api.business.command.user.UserUpdateCommand;
import com.mms.mms_api.business.service.validation.RoleValidationService;
import com.mms.mms_api.business.service.validation.UserValidationService;
import com.mms.mms_api.exception.ErrorDetail;
import com.mms.mms_api.exception.InvalidInputException;
import com.mms.mms_api.exception.ResourceNotFoundException;

import lombok.AllArgsConstructor;

@Component
@AllArgsConstructor
public class UserValidator implements BaseValidator {
    private final UserValidationService userValidationService;

    private final RoleValidationService roleValidationService;

    public void validate(UserCreateCommand command) {
        List<ErrorDetail> errors = new ArrayList<>();
        
        validateRoles(errors, command.getRoleIds());
        validateUsername(errors, command.getUsername());
        validateEmail(errors, command.getEmail());
        validatePhoneNumber(errors, command.getPhoneNumber());
        
        if (!errors.isEmpty()) {
            throw new InvalidInputException(errors);
        }
    }

    public void validate(UserUpdateCommand command) {
        List<ErrorDetail> errors = new ArrayList<>();

        validateId(errors, command.getId());

        if (!errors.isEmpty()) {
            throw new ResourceNotFoundException(errors);
        }

        validateRoles(errors, command.getRoleIds());
        validateEmail(errors, command.getEmail(), command.getId());
        validatePhoneNumber(errors, command.getPhoneNumber(), command.getId());

        if (!errors.isEmpty()) {
            throw new InvalidInputException(errors);
        }
    }

    private void validateId(List<ErrorDetail> errors, @NonNull UUID id) {
        if (!userValidationService.existsById(id)) {
            errors.add(new ErrorDetail("id", "user.notFound"));
        }
    }

    private void validateRoles(List<ErrorDetail> errors, List<UUID> rolesIds) {
        if (CollectionUtils.isEmpty(rolesIds)) {
            errors.add(new ErrorDetail("rolesIds", "user.roles.required"));
        }

        if (!roleValidationService.existsAllByIdIn(rolesIds)) {
            errors.add(new ErrorDetail("rolesIds", "user.roles.invalid"));
        }
    }

    private void validateUsername(List<ErrorDetail> errors, String username) {
        if (userValidationService.existsByUsername(username)) {
            errors.add(new ErrorDetail("username", "user.username.unique"));
        }
    }

    private void validateEmail(List<ErrorDetail> errors, String email) {
        if (userValidationService.existsByEmail(email)) {
            errors.add(new ErrorDetail("email", "user.email.unique"));
        }
    }

    private void validateEmail(List<ErrorDetail> errors, String email, UUID id) {
        if (userValidationService.existsByEmailAndIdNot(email, id)) {
            errors.add(new ErrorDetail("email", "user.email.unique"));
        }
    }

    private void validatePhoneNumber(List<ErrorDetail> errors, String phoneNumber) {
        if (userValidationService.existsByPhoneNumber(phoneNumber)) {
            errors.add(new ErrorDetail("phoneNumber", "user.phone.unique"));
        }
    }

    private void validatePhoneNumber(List<ErrorDetail> errors, String phoneNumber, UUID id) {
        if (userValidationService.existsByPhoneNumberAndIdNot(phoneNumber, id)) {
            errors.add(new ErrorDetail("phoneNumber", "user.phone.unique"));
        }
    }
}
