package com.mms.mms_api.util.mapper;

import org.mapstruct.Mapper;
import com.mms.mms_api.model.Language;
import com.mms.mms_api.dto.LanguageDto;

@Mapper(config = DefaultMapperConfig.class)
public interface LanguageMapper {
	LanguageDto toDto(Language language);
}
