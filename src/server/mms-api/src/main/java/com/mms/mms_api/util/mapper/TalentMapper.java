package com.mms.mms_api.util.mapper;

import org.mapstruct.Mapper;

import com.mms.mms_api.dto.movie.TalentDto;
import com.mms.mms_api.model.Talent;

@Mapper(config = DefaultMapperConfig.class)
public interface TalentMapper {
	TalentDto toDto(Talent talent);
}
