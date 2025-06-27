package com.mms.mms_api.business.commands;

import com.mms.mms_api.dto.UserDto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UserCreateCommand {
    private UserDto userDto;
}
