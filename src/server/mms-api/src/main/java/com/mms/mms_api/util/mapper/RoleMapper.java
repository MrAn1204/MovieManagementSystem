package com.mms.mms_api.util.mapper;

import org.mapstruct.Mapper;
import com.mms.mms_api.model.Role;
import com.mms.mms_api.dto.RoleDto;

@Mapper(componentModel = "spring")
public interface RoleMapper {
	RoleDto toDto(Role role);
}
