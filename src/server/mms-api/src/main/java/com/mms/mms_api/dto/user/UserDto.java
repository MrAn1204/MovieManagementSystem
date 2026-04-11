package com.mms.mms_api.dto.user;

import java.time.LocalDate;
import java.util.List;

import com.mms.mms_api.dto.BaseDto;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * DTO representing user summary information.
 *
 * Maps from {@link com.mms.mms_api.model.User User}.
 * Includes user roles.
 *
 * @see com.mms.mms_api.model.User User
 * @see RoleDto RoleDto
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class UserDto extends BaseDto {
    private String username;

    private String fullname;

    private LocalDate dateOfBirth;

    private String email;

    private String phoneNumber;

    private List<RoleDto> roles;
}
