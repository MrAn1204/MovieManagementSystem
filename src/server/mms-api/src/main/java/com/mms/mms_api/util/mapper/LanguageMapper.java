package com.mms.mms_api.util.mapper;

import org.mapstruct.Mapper;

import com.mms.mms_api.dto.movie.LanguageDto;
import com.mms.mms_api.model.Language;

@Mapper(config = DefaultMapperConfig.class)
public interface LanguageMapper {
	LanguageDto toDto(Language language);
}
