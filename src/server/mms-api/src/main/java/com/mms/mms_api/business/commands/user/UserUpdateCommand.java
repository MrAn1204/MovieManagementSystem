package com.mms.mms_api.business.commands.user;

import com.mms.mms_api.dto.UserDto;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class UserUpdateCommand {
    private final UserDto userDto;
}