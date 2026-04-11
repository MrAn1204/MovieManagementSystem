package com.mms.mms_api.util.mapper;

import org.mapstruct.Mapper;

import com.mms.mms_api.dto.movie.StudioDto;
import com.mms.mms_api.model.Studio;

/**
 * Mapper between studio entities and DTOs.
 */
@Mapper(config = DefaultMapperConfig.class)
public interface StudioMapper {
	StudioDto toDto(Studio studio);
}
