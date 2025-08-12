package com.mms.mms_api.util.validator;

import java.time.LocalDate;
import java.util.List;

import org.springframework.util.StringUtils;
import org.springframework.util.CollectionUtils;

import com.mms.mms_api.exception.ErrorMessage;
import com.mms.mms_api.exception.InvalidInputException;
import com.mms.mms_api.model.Role;
import com.mms.mms_api.util.PasswordValidator;

public class UserValidator {
    private UserValidator() {} 

    public static void validateUsername(String username) {
        if (!StringUtils.hasText(username)) {
            throw new InvalidInputException(ErrorMessage.USERNAME_REQUIRED);
        }
    }

    public static void validateFullname(String fullname) {
        if (!StringUtils.hasText(fullname)) {
            throw new InvalidInputException(ErrorMessage.FULLNAME_REQUIRED);
        }
    }

    public static void validatePassword(String password) {
        List<ErrorMessage> passwordErrors = PasswordValidator.validate(password);
        if (!passwordErrors.isEmpty()) {
            throw new InvalidInputException(passwordErrors.get(0));
        }
    }

    public static void validateGender(Object gender) {
        if (gender == null) {
            throw new InvalidInputException(ErrorMessage.GENDER_REQUIRED);
        }
    }

    public static void validateDateOfBirth(LocalDate dateOfBirth) {
        if (dateOfBirth == null) {
            throw new InvalidInputException(ErrorMessage.DOB_REQUIRED);
        }
    }

    public static void validatePhoneNumber(String phoneNumber) {
        if (!StringUtils.hasText(phoneNumber)) {
            throw new InvalidInputException(ErrorMessage.PHONE_REQUIRED);
        }
    }

    public static void validateRoles(List<String> rolesNames, List<Role> mappedRoles) {
        if (CollectionUtils.isEmpty(rolesNames)) {
            throw new InvalidInputException(ErrorMessage.ROLES_REQUIRED);
        }

        if (mappedRoles.size() != rolesNames.size()) {
            throw new InvalidInputException(ErrorMessage.ROLES_INVALID);
        }
    }
}
