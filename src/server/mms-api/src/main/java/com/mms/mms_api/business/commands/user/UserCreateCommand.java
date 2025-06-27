package com.mms.mms_api.business.commands.user;

import com.mms.mms_api.business.commands.BaseCreateCommand;
import com.mms.mms_api.dto.UserDto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UserCreateCommand implements BaseCreateCommand {
    private UserDto userDto;
}
