package com.mms.mms_api.util.mapper;

import org.mapstruct.Mapper;

import com.mms.mms_api.dto.user.RoleDto;
import com.mms.mms_api.model.Role;

/**
 * Mapper between role entities and DTOs.
 */
@Mapper(config = DefaultMapperConfig.class)
public interface RoleMapper {
	RoleDto toDto(Role role);
}
