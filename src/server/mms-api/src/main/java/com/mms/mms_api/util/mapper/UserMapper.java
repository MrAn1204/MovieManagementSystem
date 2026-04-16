package com.mms.mms_api.util.mapper;

import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import com.mms.mms_api.business.command.auth.RegisterCommand;
import com.mms.mms_api.business.command.user.UserCreateCommand;
import com.mms.mms_api.business.command.user.UserUpdateCommand;
import com.mms.mms_api.dto.user.UserDetailDto;
import com.mms.mms_api.dto.user.UserDto;
import com.mms.mms_api.model.Role;
import com.mms.mms_api.model.User;

/**
 * Mapper for user commands and user DTO projections.
 */
@Mapper(config = DefaultMapperConfig.class, uses = { InvoiceMapper.class })
public interface UserMapper {
    @Mapping(target = "roles", ignore = true)
    @Mapping(target = "invoices", ignore = true)
    @Mapping(target = "score", ignore = true)
    @Mapping(target = "tickets", ignore = true)
    User toEntity(UserCreateCommand command);
    
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "username", source = "command.username")
    @Mapping(target = "fullname", source = "command.fullname")
    @Mapping(target = "gender", source = "command.gender")
    @Mapping(target = "dateOfBirth", source = "command.dateOfBirth")
    @Mapping(target = "email", source = "command.email")
    @Mapping(target = "phoneNumber", source = "command.phoneNumber")
    User toEntity(RegisterCommand command);

    UserDto toDto(User user);

    UserDetailDto toDetailDto(User user);

    @Mapping(target = "username", ignore = true)
    @Mapping(target = "password", ignore = true)
    @Mapping(target = "roles", ignore = true)
    @Mapping(target = "invoices", ignore = true)
    @Mapping(target = "tickets", ignore = true)
    void updateEntity(UserUpdateCommand command, @MappingTarget User user);

    default String roleToString(Role role) {
        return role.getName();
    }
}
