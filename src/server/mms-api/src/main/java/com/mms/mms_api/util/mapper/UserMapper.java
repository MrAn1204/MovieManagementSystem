package com.mms.mms_api.util.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import com.mms.mms_api.business.command.user.UserCreateCommand;
import com.mms.mms_api.business.command.user.UserUpdateCommand;
import com.mms.mms_api.dto.user.UserDto;
import com.mms.mms_api.model.Role;
import com.mms.mms_api.model.User;

@Mapper(config = DefaultMapperConfig.class, uses = { InvoiceMapper.class })
public interface UserMapper {
    @Mapping(target = "roles", ignore = true)
    @Mapping(target = "invoices", ignore = true)
    @Mapping(target = "score", ignore = true)
    @Mapping(target = "tickets", ignore = true)
    User toEntity(UserCreateCommand command);
    
    UserDto toDto(User user);

    @Mapping(target = "roles", ignore = true)
    @Mapping(target = "invoices", ignore = true)
    @Mapping(target = "tickets", ignore = true)
    void updateEntity(UserUpdateCommand command, @MappingTarget User user);

    default String roleToString(Role role) {
        return role.getName();
    }
}
