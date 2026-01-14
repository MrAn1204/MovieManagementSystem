package com.mms.mms_api.util.validator;

import java.util.List;
import java.util.UUID;

import org.springframework.util.CollectionUtils;

import com.mms.mms_api.exception.InvalidInputException;
import com.mms.mms_api.model.Role;

public class UserValidator {
    private UserValidator() {} 

    public static void validateRoles(List<UUID> rolesIds, List<Role> mappedRoles) {
        if (CollectionUtils.isEmpty(rolesIds)) {
            throw new InvalidInputException("user.roles.required");
        }

        if (mappedRoles == null || rolesIds.size() != mappedRoles.size()) {
            throw new InvalidInputException("user.roles.invalid");
        }
    }
}
