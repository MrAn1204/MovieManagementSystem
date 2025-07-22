package com.mms.mms_api.util.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;

import com.mms.mms_api.business.command.user.UserCreateCommand;
import com.mms.mms_api.business.command.user.UserUpdateCommand;
import com.mms.mms_api.dto.user.UserDto;
import com.mms.mms_api.model.Role;
import com.mms.mms_api.model.User;

@Mapper(componentModel = "spring", 
    nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE, 
    unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface UserMapper {
    // Roles will be manually mapped in UserCreateHandler
    @Mapping(target = "roles", ignore = true)
    User toEntity(UserCreateCommand command);
    
    UserDto toDto(User user);
    
    // Roles will be manually mapped in UserUpdateHandler
    @Mapping(target = "roles", ignore = true)
    void updateEntity(UserUpdateCommand command, @MappingTarget User user);

    default String roleToString(Role role) {
        return role.getName();
    }
}
