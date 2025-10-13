package com.mms.mms_api.util.mapper;

import org.mapstruct.Mapper;
import com.mms.mms_api.model.Studio;
import com.mms.mms_api.dto.StudioDto;

@Mapper(componentModel = "spring")
public interface StudioMapper {
	StudioDto toDto(Studio studio);
}
