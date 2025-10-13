package com.mms.mms_api.util.mapper;

import org.mapstruct.Mapper;
import com.mms.mms_api.model.Genre;
import com.mms.mms_api.dto.GenreDto;

@Mapper(componentModel = "spring")
public interface GenreMapper {
	GenreDto toDto(Genre genre);
}
