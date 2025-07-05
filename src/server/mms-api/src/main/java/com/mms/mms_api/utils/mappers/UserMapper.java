package com.mms.mms_api.utils.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;

import com.mms.mms_api.business.commands.user.UserCreateCommand;
import com.mms.mms_api.business.commands.user.UserUpdateCommand;
import com.mms.mms_api.dto.user.UserDto;
import com.mms.mms_api.models.User;

@Mapper(componentModel = "spring", 
    nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE, 
    unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface UserMapper {
    // Roles will be manually mapped in UserCreateHandler
    @Mapping(target = "roles", ignore = true)
    User toEntity(UserCreateCommand command);
    
    UserDto toDto(User user);
    
    void updateEntity(UserUpdateCommand command, @MappingTarget User user);
}
