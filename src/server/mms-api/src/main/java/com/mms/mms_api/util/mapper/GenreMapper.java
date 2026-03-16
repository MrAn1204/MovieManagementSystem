package com.mms.mms_api.util.mapper;

import org.mapstruct.Mapper;

import com.mms.mms_api.dto.movie.GenreDto;
import com.mms.mms_api.model.Genre;

@Mapper(config = DefaultMapperConfig.class)
public interface GenreMapper {
	GenreDto toDto(Genre genre);
}
