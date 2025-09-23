package com.mms.mms_api.util.validator;

import java.util.List;

import org.springframework.util.CollectionUtils;

import com.mms.mms_api.exception.InvalidInputException;
import com.mms.mms_api.model.Role;

public class UserValidator {
    private UserValidator() {} 

    public static void validateRoles(List<String> rolesNames, List<Role> mappedRoles) {
        if (CollectionUtils.isEmpty(rolesNames)) {
            throw new InvalidInputException("user.roles.required");
        }

        if (mappedRoles.size() != rolesNames.size()) {
            throw new InvalidInputException("user.roles.invalid");
        }
    }
}
