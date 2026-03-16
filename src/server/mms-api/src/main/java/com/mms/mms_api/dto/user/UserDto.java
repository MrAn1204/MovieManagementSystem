package com.mms.mms_api.dto.user;

import java.time.LocalDate;
import java.util.List;

import com.mms.mms_api.dto.BaseDto;

import lombok.Data;
import lombok.EqualsAndHashCode;

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
