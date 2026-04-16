package com.mms.mms_api.dto.user;


import com.mms.mms_api.dto.BaseDto;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * DTO representing a user role.
 *
 * Maps from {@link com.mms.mms_api.model.Role Role}.
 *
 * @see com.mms.mms_api.model.Role Role
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class RoleDto extends BaseDto {
    private String name;

    private String description;
}
