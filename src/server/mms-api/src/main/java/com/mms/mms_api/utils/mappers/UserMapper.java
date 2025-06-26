package com.mms.mms_api.utils.mappers;

import org.mapstruct.Mapper;

import com.mms.mms_api.dto.UserDto;
import com.mms.mms_api.models.User;

@Mapper(componentModel = "spring")
public interface UserMapper {
    User toEntity(UserDto userDto);
    UserDto toDto(User user);
}
